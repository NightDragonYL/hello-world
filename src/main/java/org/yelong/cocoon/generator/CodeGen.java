/**
 * 
 */
package org.yelong.cocoon.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.yelong.core.model.Model;
import org.yelong.core.model.annotation.Column;
import org.yelong.core.model.annotation.Table;
import org.yelong.model.generator.TemplateModel;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author PengFei
 * @date 2020年3月12日下午7:27:48
 * @since 1.0
 */
public class CodeGen {
	private Class<? extends Model> modelClass;
	private String codePath;
	private String dbType;

	public CodeGen(Class<? extends Model> modelClass, String codePath, String dbType)
	{
		this.modelClass = modelClass;
		this.codePath = codePath;
		this.dbType = (StringUtils.isBlank(dbType) ? "mysql" : dbType);
	}

	public void process()
	{
		long beginTime = System.currentTimeMillis();
		System.out.println("������...");
		try
		{
			Code code = new Code();

			Clazz clazz = ClassUtils.getClazz(this.modelClass.getName());
			Model model = (Model)clazz.getClazz().newInstance();

			code.setClassNameWithPackage(clazz.getClassName());
			code.setClassName(clazz.getClassSimpleName());
			code.setClassNameLowerPrefix(clazz.getLowerCasePrefixClassName());
			code.setClassNameLowerCase(code.getClassName().toLowerCase());
			code.setClassNameUpperCase(code.getClassName().toUpperCase());
			code.setBasePackage(code.getClassNameWithPackage().substring(0, code.getClassNameWithPackage().indexOf(".model.")));
			String classNameActionInvocation = "";
			for (int i = 0; i < code.getClassNameLowerPrefix().length(); i++)
			{
				char classChar = code.getClassNameLowerPrefix().charAt(i);
				if ((classChar >= 'A') && (classChar <= 'Z')) {
					classNameActionInvocation = classNameActionInvocation + "_" + String.valueOf(classChar).toLowerCase();
				} else {
					classNameActionInvocation = classNameActionInvocation + classChar;
				}
			}
			code.setClassNameActionInvocation(classNameActionInvocation);
			Table table = (Table)clazz.getClazz().getAnnotation(Table.class);
			code.setTableName(table.value());
			//code.setModelIdentity(model.getModelIdentity());
			//FormConfig formConfig = (FormConfig)clazz.getClazz().getAnnotation(FormConfig.class);
			boolean singleColumn = false;
			//			if (formConfig != null) {
			//				singleColumn = formConfig.singleColumn();
			//			}
			List<Field> fields = clazz.getClassFields();
			List<FieldAttr> fieldAttrList = new ArrayList();
			int useableColumnCount = 0;
			Column config;
			for (Field field : fields) {
				if ((!"serialVersionUID".equals(field.getName())) )
				{
					config = field.getAnnotation(Column.class);
					String fieldName = field.getName();
					//String fieldDisplayName = field.getAnnotation(FieldName.class) != null ? ((FieldName)field.getAnnotation(FieldName.class)).value() : fieldName;
					String fieldDisplayName = StringUtils.isEmpty(config.desc()) ? fieldName : config.desc();
					//FieldType fieldType = (FieldType)field.getAnnotation(FieldType.class);
					FieldAttr attr = new FieldAttr();
					attr.setFieldName(fieldName);
					attr.setFieldDisplayName(fieldDisplayName);
					attr.setFieldType(getFieldType(field.getType().getSimpleName()));
					if (config != null)
					{
						attr.setAllowBlank(config.allowBlank());
						//attr.setBlankText(config.blankText());
						//attr.setEditable(config.editable());
						//attr.setInputType(config.inputType());
						attr.setMaxLength((int)config.maxLength());
						//attr.setMaxLengthText(config.maxLengthText());
						attr.setMinLength((int)config.minLength());
						//attr.setMinLengthText(config.minLengthText());
					}
					//attr.setHidden(field.getAnnotation(Hidden.class) != null);
					attr.setHidden(false);
					//attr.setSearchable(field.getAnnotation(Searchable.class) != null);
					//					ComboBoxLocal comboxLocal = (ComboBoxLocal)field.getAnnotation(ComboBoxLocal.class);
					//					if (comboxLocal != null)
					//					{
					//						attr.setComboBoxLocal(true);
					//						attr.setComboBoxData(comboxLocal.data());
					//						attr.setDefaultValue(comboxLocal.defaultValue());
					//					}
					//					ComboBoxDict comboxDict = (ComboBoxDict)field.getAnnotation(ComboBoxDict.class);
					//					if (comboxDict != null)
					//					{
					//						attr.setComboBoxDict(true);
					//						attr.setDictType(comboxDict.type());
					//						attr.setDefaultValue(comboxDict.defaultValue());
					//					}
					//					ComboBoxTree comboxTree = (ComboBoxTree)field.getAnnotation(ComboBoxTree.class);
					//					if (comboxTree != null)
					//					{
					//						attr.setComboBoxTree(true);
					//						attr.setComboTreeId(comboxTree.comboTreeId());
					//						attr.setComboTreeUrl(comboxTree.comboTreeUrl());
					//					}
					if (!attr.isHidden()) {
						useableColumnCount++;
					}
					fieldAttrList.add(attr);
				}
			}
			String classFields = "";
			String searchFields = "";
			String searchEmptyTexts = "";
			String gridColumns = "Co.gridRowNumberer(),";
			int columnWidth = 1000 / useableColumnCount;
			for (FieldAttr attr : fieldAttrList) {
				if (!attr.isIgnore())
				{
					classFields = classFields + "\"" + attr.getFieldName() + "\",";
					gridColumns = gridColumns + "\n\t\t{header : \"" + attr.getFieldDisplayName() + "\", dataIndex : \"" + attr.getFieldName() + "\", width : " + columnWidth + ", hidden : " + attr.isHidden() + ",";
					if (attr.isComboBoxDict())
					{
						gridColumns = gridColumns + " renderer : function(v) {";
						gridColumns = gridColumns + "\n\t\t\treturn Co.dictText(\"" + attr.getFieldName() + "\", v);\n\t\t},";
					}
					gridColumns = gridColumns.substring(0, gridColumns.length() - 1) + "},";
					if (attr.isSearchable())
					{
						searchFields = searchFields + "\"" + attr.getFieldName() + "\",";
						searchEmptyTexts = searchEmptyTexts + "\"������" + attr.getFieldDisplayName() + "..." + "\",";
					}
				}
			}
			String formItems = "";
			List<FieldAttr> hiddenFieldList = new ArrayList();
			int size = fieldAttrList.size();
			FieldAttr attr;
			for (int i = 0; i < size;)
			{
				attr = (FieldAttr)fieldAttrList.get(i);
				if (!attr.isIgnore())
				{
					if (!attr.isHidden())
					{
						if (singleColumn)
						{
							formItems = getFormFiled(attr, formItems, singleColumn);
							i++;
						}
						else
						{
							String cw = null;
							formItems = formItems + "{\n\t\tlayout : \"column\",\n\t\tborder : false,\n\t\tbodyCls : \"panel-background-color\",\n\t\titems : [";
							for (int j = 1; j <= 2; j++)
							{
								cw = ".5";
								if (i >= size) {
									break;
								}
								attr = (FieldAttr)fieldAttrList.get(i);
								if (attr.isIgnore())
								{
									i++;
								}
								else if (attr.isHidden())
								{
									hiddenFieldList.add(attr);
									i++;
								}
								else
								{
									i++;
									if (i >= size) {
										cw = "1";
									}
									formItems = formItems + "{\n\t\t\tcolumnWidth : " + cw + ",\n\t\t\tborder : false,\n\t\t\tbodyCls : \"panel-background-color\",\n\t\t\tlayout : \"form\",\n\t\t\titems : [";
									formItems = getFormFiled(attr, formItems, singleColumn);
								}
							}
							formItems = formItems.substring(0, formItems.length() - 1) + "]\n\t},";
						}
					}
					else
					{
						hiddenFieldList.add(attr);
						i++;
					}
				}
				else {
					i++;
				}
			}
			for (FieldAttr hf : hiddenFieldList)
			{
				formItems = formItems + "{\n\t\txtype : \"hiddenfield\",";
				formItems = formItems + "\n\t\tid : \"" + hf.getFieldName() + "\",";
				formItems = formItems + "\n\t\tname : \"model." + hf.getFieldName() + "\"\n\t},";
			}
			formItems = formItems + "{\n\t\txtype : \"hiddenfield\",";
			formItems = formItems + "\n\t\tid : \"creator\",";
			formItems = formItems + "\n\t\tname : \"model.creator\"\n\t},";

			formItems = formItems + "{\n\t\txtype : \"hiddenfield\",";
			formItems = formItems + "\n\t\tid : \"createTime\",";
			formItems = formItems + "\n\t\tname : \"model.createTime\"\n\t},";

			classFields = StringUtils.isNotBlank(classFields) ? classFields.substring(0, classFields.length() - 1) : classFields;
			searchFields = StringUtils.isNotBlank(searchFields) ? searchFields.substring(0, searchFields.length() - 1) : searchFields;
			searchEmptyTexts = StringUtils.isNotBlank(searchEmptyTexts) ? searchEmptyTexts.substring(0, searchEmptyTexts.length() - 1) : searchEmptyTexts;
			gridColumns = StringUtils.isNotBlank(gridColumns) ? gridColumns.substring(0, gridColumns.lastIndexOf(",")) : gridColumns;
			formItems = StringUtils.isNotBlank(formItems) ? formItems.substring(0, formItems.length() - 1) : formItems;

			code.setClassFields(classFields);
			code.setSearchFields(searchFields);
			code.setSearchEmptyTexts(searchEmptyTexts);
			code.setGridColumns(gridColumns);
			code.setFormItems(formItems);

			code.setFormWindowWidth(Integer.valueOf(singleColumn ? 450 : 650));
			int totalRow = singleColumn ? useableColumnCount : useableColumnCount / 2 + useableColumnCount % 2;
			int wHeight = singleColumn ? 120 : 150;
			if (totalRow > 1) {
				wHeight = singleColumn ? totalRow * 30 + 60 : wHeight + (totalRow - 1) * 30;
			}
			code.setFormWindowHeight(Integer.valueOf(wHeight));

			//			CodeFile codeFile = new CodeFile();
			//			codeFile.setActionFile(code.getClassName() + "Action.java");
			//			codeFile.setJspFile(code.getClassNameLowerPrefix() + "Manage.jsp");
			//			codeFile.setSqlFile("sql-mapper-" + code.getClassNameLowerPrefix() + ".properties");
			//			codeFile.setJsFile(code.getClassNameLowerPrefix() + "Manage.js");

			System.out.println("��������...");

			//			genFile(codeFile.getActionFile(), "action", code);
			//			genFile(codeFile.getJspFile(), "jsp", code);
			//			genFile(codeFile.getSqlFile(), "sql-mapper", code);
			//			genFile(codeFile.getJsFile(), "js", code);
			genFile(code, codePath);
			System.out.println("����������������" + (System.currentTimeMillis() - beginTime) / 1000L + "s");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("����������");
		}
	}

	private String getFormFiled(FieldAttr attr, String formItems, boolean singleColumn)
	{
		String tab = singleColumn ? "\t\t" : "\t\t\t\t";
		formItems = formItems + "{\n" + tab + "xtype : \"" + attr.getFieldType() + "\",";
		if (!"text".equals(attr.getInputType())) {
			formItems = formItems + "\n" + tab + "inputType : \"" + attr.getInputType() + "\",";
		}
		formItems = formItems + "\n" + tab + "id : \"" + attr.getFieldName() + "\",";
		formItems = formItems + "\n" + tab + "name : \"model." + attr.getFieldName() + "\",";
		formItems = formItems + "\n" + tab + "fieldLabel : \"" + attr.getFieldDisplayName() + "\",";
		formItems = formItems + "\n" + tab + "allowBlank : " + attr.isAllowBlank() + ",";
		if (!attr.isAllowBlank()) {
			formItems = formItems + "\n" + tab + "blankText : \"" + (StringUtils.isBlank(attr.getBlankText()) ? attr.getFieldDisplayName() + "��������" : attr.getBlankText()) + "\",";
		}
		formItems = formItems + "\n" + tab + "editable : " + attr.isEditable() + ",";
		formItems = formItems + "\n" + tab + "readOnly : " + attr.isReadOnly() + ",";
		if (-1 != attr.getMaxLength())
		{
			formItems = formItems + "\n" + tab + "maxLength: " + attr.getMaxLength() + ",";
			formItems = formItems + "\n" + tab + "maxLengthText: \"" + (StringUtils.isBlank(attr.getMaxLengthText()) ? attr.getFieldDisplayName() + "������������" + attr.getMaxLength() + "����" : attr.getMaxLengthText()) + "\",";
			formItems = formItems + "\n" + tab + "enforceMaxLength: true,";
		}
		if (-1 != attr.getMinLength())
		{
			formItems = formItems + "\n" + tab + "minLength: " + attr.getMinLength() + ",";
			formItems = formItems + "\n" + tab + "minLengthText: \"" + (StringUtils.isBlank(attr.getMinLengthText()) ? attr.getFieldDisplayName() + "��������" + attr.getMinLength() + "����" : attr.getMinLengthText()) + "\",";
		}
		if ("datefield".equals(attr.getFieldType())) {
			formItems = formItems + "\n" + tab + "format: Co.dateFormat,";
		}
		if (singleColumn) {
			formItems = formItems.substring(0, formItems.length() - 1) + "\n\t},";
		} else {
			formItems = formItems.substring(0, formItems.length() - 1) + "\n\t\t\t}]\n\t\t},";
		}
		return formItems;
	}

	private String getFieldType(String typeClass)
	{
		if (("Integer".equals(typeClass)) || ("Float".equals(typeClass)) || ("Double".equals(typeClass)) || ("Long".equals(typeClass)) || ("Number".equals(typeClass))) {
			return "numberfield";
		}
		if ("Date".equals(typeClass)) {
			return "datefield";
		}
		return "textfield";
	}

	
	
	
	
	
	
	
	private static final FreeMarkerConfigurationFactoryBean factory = new FreeMarkerConfigurationFactoryBean();

	private static Configuration freemarkerConfiguration = null;

	private static final String DICT_FTL_NAME = "js.ftl";

	static {
		factory.setTemplateLoaderPath("org/yelong/cocoon/generator/tpl");
		try {
			freemarkerConfiguration = factory.createConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void genFile(Code code,String filePath) {
		File file = null;
		try {
			Template template = freemarkerConfiguration.getTemplate(DICT_FTL_NAME,"UTF-8");
			file = new File(filePath,code.getClassNameLowerPrefix()+"Manage.js");
			if(!file.exists()) {
				file.createNewFile();
			}
			Map<String,Object> root = new HashMap<>();
			root.put("code", code);
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			//生成word文件
			template.process(root,writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
