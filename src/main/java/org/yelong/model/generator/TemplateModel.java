/**
 * 
 */
package org.yelong.model.generator;

import java.util.List;

/**
 * @author PengFei
 * @date 2020年1月10日下午7:21:23
 */
public class TemplateModel {

	private String modelPackage;
	
	private String tableName;
	
	private String tableDesc;
	
	private String modelName;
	
	private String modelNamePrefixLowerCase;
	
	private List<TemplateModelField> modelFields;
	
	private String author = System.getProperty("user.name");

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<TemplateModelField> getModelFields() {
		return modelFields;
	}

	public void setModelFields(List<TemplateModelField> modelFields) {
		this.modelFields = modelFields;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getModelNamePrefixLowerCase() {
		return modelNamePrefixLowerCase;
	}

	public void setModelNamePrefixLowerCase(String modelNamePrefixLowerCase) {
		this.modelNamePrefixLowerCase = modelNamePrefixLowerCase;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}
	
}
