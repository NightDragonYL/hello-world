/**
 * 
 */
package org.yelong.model.generator.pdm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yelong.core.model.table.vice.DefaultFieldAndColumn;
import org.yelong.core.model.table.vice.FieldAndColumn;
import org.yelong.model.generator.FieldAndColumnBuildException;
import org.yelong.model.generator.GeneratorFieldAndColumn;
import org.yelong.model.generator.GeneratorModelAndTable;
import org.yelong.model.generator.ModelAndTableBuildException;

/**
 * @author PengFei
 * @date 2020年1月10日下午4:23:06
 */
public class DefaultPDMResolver implements PDMResolver{

	private static final DocumentBuilderFactory builderFactory;
	
	private static final DocumentBuilder builder;
	
	static {
		try {
			builderFactory = DocumentBuilderFactory.newInstance();
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<GeneratorModelAndTable> resolve(File pdm) throws PDMResolverException{
		try {
			return resolve(new FileInputStream(pdm));
		} catch (FileNotFoundException e) {
			throw new PDMResolverException(e);
		}
	}
	
	
	@Override
	public List<GeneratorModelAndTable> resolve(InputStream is) throws PDMResolverException{
		try {
			Document xml = builder.parse(is);
			NodeList root = xml.getElementsByTagName("c:Tables");
			if( null == root || root.getLength() <= 0 ) {
				return Collections.emptyList();	
			}
			NodeList tableList = root.item(0).getChildNodes();
			if( tableList == null || tableList.getLength() <= 0 ) {
				return Collections.emptyList();	
			}
			List<GeneratorModelAndTable> modelAndTables = new ArrayList<GeneratorModelAndTable>();
			for (int i = 0; i < tableList.getLength(); i++) {
				Node table = tableList.item(i);
				if( 1 != table.getNodeType() ) {
					continue;
				}
				GeneratorModelAndTable modelAndTable = buildModelAndTable(table);
				if( null != modelAndTable) {
					modelAndTables.add(modelAndTable);
				}
			}
			return modelAndTables;
		} catch (Exception e) {
			throw new PDMResolverException(e);
		}
	}

	/**
	 * 构建模型与表
	 * @param table c:Tables节点
	 * @return 模型与表
	 */
	protected GeneratorModelAndTable buildModelAndTable(Node table) {
		if(1 != table.getNodeType()) {
			throw new ModelAndTableBuildException("不符合的节点");
		}
		NodeList childNodeList = table.getChildNodes();
		if( null == childNodeList || childNodeList.getLength() <= 0 ) {
			return null;
		}
		GeneratorModelAndTable modelAndTable = null;
		String tableName = "";
		String tableCode = "";
		String modelPackage = "";
		String modelName = "";
		List<FieldAndColumn> fieldAndColumns = new ArrayList<FieldAndColumn>();
		for (int i = 0 ; i < childNodeList.getLength() ; i ++ ) {
			Node childNode = childNodeList.item(i);
			if( 1 != childNode.getNodeType()) {
				continue;
			}
			String childNodeName = childNode.getNodeName();
			if("a:Name".equals(childNodeName)) {//表描述
				tableName = childNode.getTextContent();
			} else if("a:Code".equals(childNodeName)) {//表名
				tableCode = childNode.getTextContent();
			} else if("a:Comment".equals(childNodeName)) {//备注->表全名称
				String textContent = childNode.getTextContent();
				int index = textContent.lastIndexOf(".");
				if( -1 != index) {
					modelPackage = textContent.substring(0, index);
				}
				modelName = textContent.substring(index + 1, textContent.length());
			} else if("c:Columns".equals(childNodeName)) {
				NodeList columnList = childNode.getChildNodes();
				if( null != columnList && columnList.getLength()>0) {
					for (int j = 0; j < columnList.getLength(); j++) {
						Node column = columnList.item(j);
						if(1 == column.getNodeType()) {
							FieldAndColumn fieldAndColumn = buildFieldAndColumn(column);
							if( null != fieldAndColumn ) {
								fieldAndColumns.add(fieldAndColumn);
							}
						}
					}
				}
			}
		}
		modelAndTable = new GeneratorModelAndTable(tableCode, fieldAndColumns);
		modelAndTable.setTableDesc(tableName);
		modelAndTable.setModelName(modelName);
		modelAndTable.setModelPackage(modelPackage);
		return modelAndTable;
	}
	
	protected FieldAndColumn buildFieldAndColumn(Node column) {
		if(1 != column.getNodeType()) {
			throw new FieldAndColumnBuildException("不符合的节点");
		}
		NodeList columnPropertyList = column.getChildNodes();
		if( null == columnPropertyList || columnPropertyList.getLength() <= 0 ) {
			return new DefaultFieldAndColumn(null, "");
		}
		String columnName = "";
		Class<?> fieldType = null;
		String columnDesc = "";
		Long columnLength = Long.MAX_VALUE;
		boolean mandatory = true;
		String columnType = "";
		for (int i = 0; i < columnPropertyList.getLength(); i++) {
			Node columnProperty = columnPropertyList.item(i);
			if(1 != columnProperty.getNodeType()) {
				continue;
			}
			String nodeName = columnProperty.getNodeName();
			if("a:Code".equals(nodeName)) { // 列名
				columnName = columnProperty.getTextContent();
			} else if("a:Name".equals(nodeName)) { // 列名称说明
				columnDesc = columnProperty.getTextContent();
			} else if("a:DataType".equals(nodeName)) { // 数据类型
				String dataType = columnProperty.getTextContent().toUpperCase();
				if (dataType.contains("INTEGER")) {
					fieldType = Integer.class;
					columnType = "INTEGER";
				} else if (dataType.contains("FLOAT")) {
					fieldType = Float.class;
					columnType = "FLOAT";
				} else if ((dataType.contains("NUMBER")) || (dataType.contains("NUMERIC")) || (dataType.contains("DECIMAL"))) {
					fieldType = Double.class;
					columnType = "NUMBER";
				} else if ((dataType.contains("DATE")) || (dataType.contains("TIMESTAMP"))) {
					fieldType = Date.class;
					columnType = "TIMESTAMP";
				} else {
					fieldType = String.class;
					columnType = "VARCHAR";
				}
			} else if("a:Length".equals(nodeName)) {
				String length = columnProperty.getTextContent();
				if(StringUtils.isNotBlank(length)) {
					columnLength = Long.valueOf(length);
				}
			} else if("a:Column.Mandatory".equals(nodeName)) {
				mandatory = false;
			}
		}
		GeneratorFieldAndColumn fieldAndColumn = new GeneratorFieldAndColumn(columnName);
		fieldAndColumn.setDesc(columnDesc);
		fieldAndColumn.setJdbcType(columnType);
		fieldAndColumn.setMaxLength(columnLength);
		fieldAndColumn.setAllowNull(mandatory);
		fieldAndColumn.setFieldName(columnName);
		fieldAndColumn.setFieldType(fieldType);
		return fieldAndColumn;
	}

}
