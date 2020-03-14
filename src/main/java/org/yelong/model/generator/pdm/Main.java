/**
 * 
 */
package org.yelong.model.generator.pdm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jfinal.template.Template;

/**
 * @author PengFei
 * @date 2020年1月10日下午3:39:44
 */
public class Main {

	private static final List<String> EXCLUDE_FIELDS = new ArrayList();

	static {
		 EXCLUDE_FIELDS.add("createTime");
		    EXCLUDE_FIELDS.add("creator");
		    EXCLUDE_FIELDS.add("updateTime");
		    EXCLUDE_FIELDS.add("updator");
		    EXCLUDE_FIELDS.add("state");
	}
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		String fileName = "D:\\yl\\数据模型\\YL-认证.pdm";
		SAXReader reader = new SAXReader();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xml = builder.parse(new File(fileName));
		 List<ModelAttr> modelList = new ArrayList();
		NodeList root = xml.getElementsByTagName("c:Tables");
		if ((root != null) && (root.getLength() > 0))
		{
			NodeList tableList = root.item(0).getChildNodes();
			int j;
			for (int i = 0; i < tableList.getLength(); i++)
			{
				Node table = tableList.item(i);
				if (1 == table.getNodeType())
				{
					ModelAttr model = new ModelAttr();
					NodeList childNodeList = table.getChildNodes();
					if ((childNodeList != null) && (childNodeList.getLength() > 0)) {
						for (j = 0; j < childNodeList.getLength(); j++)
						{
							Node childNode = childNodeList.item(j);
							if (1 == childNode.getNodeType())
							{
								model.setAuthor(System.getProperty("user.name"));
								if ("a:Name".equals(childNode.getNodeName()))
								{
									model.setTableName(childNode.getTextContent());
								}
								else if ("a:Code".equals(childNode.getNodeName()))
								{
									model.setTableCode(childNode.getTextContent());
								}
								else if ("a:Comment".equals(childNode.getNodeName()))
								{
									String modelName = childNode.getTextContent();
									int index = modelName.lastIndexOf(".");
									if (-1 != index) {
										model.setModelPackage(modelName.substring(0, index));
									}
									model.setModelName(modelName.substring(index + 1, modelName.length()));
									model.setNameLowerCase(model.getModelName().toLowerCase());
								}
								else if ("c:Columns".equals(childNode.getNodeName()))
								{
									NodeList columnList = childNode.getChildNodes();
									if ((columnList != null) && (columnList.getLength() > 0)) {
										for (int k = 0; k < columnList.getLength(); k++)
										{
											Node column = columnList.item(k);
											if (1 == column.getNodeType())
											{
												ModelField	mf = new ModelField();
												NodeList fieldList = column.getChildNodes();
												if ((fieldList != null) && (fieldList.getLength() > 0)) {
													for (int m = 0; m < fieldList.getLength(); m++)
													{
														Node field = fieldList.item(m);
														if (1 == field.getNodeType()) {
															if ("a:Code".equals(field.getNodeName()))
															{
																mf.setCode(field.getTextContent());
																if ("id".equals(mf.getCode())) {
																	mf.setIsPrimaryKey("true");
																}
																//mf.setCodePrefixUpperCase(StringUtilsExt.upperCasePrefixString(mf.getCode()));
															}
															else if ("a:Name".equals(field.getNodeName()))
															{
																mf.setName(field.getTextContent());
															}
															else if ("a:DataType".equals(field.getNodeName()))
															{
																String dataType = field.getTextContent();
																if (dataType.contains("INTEGER")) {
																	dataType = "Integer";
																} else if (dataType.contains("FLOAT")) {
																	dataType = "Float";
																} else if ((dataType.contains("NUMBER")) || (dataType.contains("NUMERIC")) || (dataType.contains("DECIMAL"))) {
																	dataType = "Double";
																} else if ((dataType.contains("DATE")) || (dataType.contains("TIMESTAMP"))) {
																	dataType = "Date";
																} else {
																	dataType = "String";
																}
																mf.setType(dataType);
															}
															else if ("a:Length".equals(field.getNodeName()))
															{
																String len = field.getTextContent();
																if (StringUtils.isNotBlank(len)) {
																	mf.setLength(String.valueOf(Integer.valueOf(len).intValue() / 2));
																}
															}
															else if ("a:Column.Mandatory".equals(field.getNodeName()))
															{
																mf.setIsMandatory("true");
															}
														}
													}
												}
												if (!EXCLUDE_FIELDS.contains(mf.getCode())) {
													model.addModelField(mf);
												}
											}
										}
									}
								}
							}
						}
					}
					modelList.add(model);
				}
			}
//			if (modelList.size() > 0)
//			{
//				System.out.println("����" + modelList.size() + "��������������");
//				long begin = System.currentTimeMillis();
//				int i = 1;
//				for (ModelAttr m : modelList)
//				{
//					System.out.println("����������" + i++ + "��������" + m.getModelName() + ".java");
//					Map<String, Object> modelContext = new HashMap();
//					Writer out = null;
//					try
//					{
//						Template template = freemarkerConfiguration.getTemplate("model.ftl", "UTF-8");
//						modelContext.put("model", m);
//						File file = new File(this.savePath);
//						if (!file.exists()) {
//							file.mkdirs();
//						}
//						out = new OutputStreamWriter(new FileOutputStream(file.getCanonicalPath() + "\\" + m.getModelName() + ".java"), "UTF-8");
//
//						template.process(modelContext, out);
//					}
//					catch (Exception e)
//					{
//						e.printStackTrace();
//						System.out.println("��������������");
//					}
//					finally
//					{
//						out.close();
//					}
//				}
//				System.out.println("��������������������" + (System.currentTimeMillis() - begin) / 1000L + "s");
//			}
		}
		else
		{
			System.out.println("������������");
		}
	}

}
