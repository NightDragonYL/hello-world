/**
 * 
 */
package org.yelong.model.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.yelong.core.model.table.vice.FieldAndColumn;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author PengFei
 * @date 2020年1月10日下午7:05:52
 */
public class DefaultModelGenerator implements ModelGenerator{

	private static final FreeMarkerConfigurationFactoryBean factory = new FreeMarkerConfigurationFactoryBean();

	private static Configuration freemarkerConfiguration = null;

	private static final String DICT_FTL_NAME = "model.ftl";

	static {
		factory.setTemplateLoaderPath("org/yelong/model/generator/tpl");
		try {
			freemarkerConfiguration = factory.createConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public File generate(GeneratorModelAndTable modelAndTable, String filePath) {
		TemplateModel templateModel = convert(modelAndTable);
		File file = null;
		try {
			Template template = freemarkerConfiguration.getTemplate(DICT_FTL_NAME,"UTF-8");
			file = new File(filePath,templateModel.getModelName()+".java");
			if(!file.exists()) {
				file.createNewFile();
			}
			Map<String,Object> root = new HashMap<>();
			root.put("model", templateModel);
			root.put("existDateField", existDateField(modelAndTable));
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			//生成word文件
			template.process(root,writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	private boolean existDateField(GeneratorModelAndTable modelAndTable) {
		for (FieldAndColumn fieldAndColumn : modelAndTable.getFieldAndColumns()) {
			Class<?> fieldType = fieldAndColumn.getFieldType();
			if(fieldType.isAssignableFrom(Date.class) ) {
				return true;
			}
		}
		return false;
	}


	public TemplateModel convert(GeneratorModelAndTable modelAndTable) {
		TemplateModel templateModel = new TemplateModel();
		String modelName = modelAndTable.getModelName();
		templateModel.setModelName(modelName);
		templateModel.setModelPackage(modelAndTable.getModelPackage());
		templateModel.setTableName(modelAndTable.getTableName());
		templateModel.setModelNamePrefixLowerCase(modelName.substring(0, 1).toLowerCase()+modelName.substring(1));
		templateModel.setTableDesc(modelAndTable.getTableDesc());
		
		
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
		List<TemplateModelField> modelFields = new ArrayList<TemplateModelField>(fieldAndColumns.size());
		for (FieldAndColumn fieldAndColumn : fieldAndColumns) {
			TemplateModelField templateModelField = new TemplateModelField();
			String fieldName = fieldAndColumn.getFieldName();
			templateModelField.setCode(fieldName);
			templateModelField.setCodePrefixUpperCase(fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1));
			templateModelField.setLength(fieldAndColumn.getMaxLength().toString());
			templateModelField.setMandatory(Boolean.toString(fieldAndColumn.isAllowNull()));
			templateModelField.setName(fieldAndColumn.getDesc());
			templateModelField.setPrimaryKey(Boolean.toString(fieldAndColumn.isPrimaryKey()));
			templateModelField.setType(fieldAndColumn.getFieldType().getSimpleName());
			StringBuilder columnAnnotation = new StringBuilder("@Column(");
			columnAnnotation.append("column = \""+fieldName+"\"");
			if(fieldAndColumn.getMaxLength() < Long.MAX_VALUE) {
				columnAnnotation.append(",maxLength = "+fieldAndColumn.getMaxLength());
			}
			columnAnnotation.append(",allowNull = "+fieldAndColumn.isAllowNull());
			columnAnnotation.append(",jdbcType = \""+fieldAndColumn.getJdbcType()+"\"");
			columnAnnotation.append(",desc = \""+fieldAndColumn.getDesc()+"\"");
			columnAnnotation.append(")");
			templateModelField.setColumnAnnotation(columnAnnotation.toString());
			
			
			modelFields.add(templateModelField);
		}
		templateModel.setModelFields(modelFields);
		return templateModel;
	}






}
