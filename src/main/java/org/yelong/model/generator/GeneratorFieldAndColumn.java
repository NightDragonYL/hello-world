/**
 * 
 */
package org.yelong.model.generator;

import java.lang.reflect.Field;

import org.yelong.core.model.table.vice.DefaultFieldAndColumn;

/**
 * @author PengFei
 * @date 2020年1月10日下午5:40:10
 */
public class GeneratorFieldAndColumn extends DefaultFieldAndColumn{

	private String fieldName;
	
	private Class<?> fieldType;
	
	public GeneratorFieldAndColumn(String column) {
		super(null, column);
	}

	@Override
	public Field getField() {
		throw new UnsupportedOperationException("生成器的字段与列中，不允许获取字段，这是不存在的！");
	}
	
	@Override
	public String getFieldName() {
		return this.fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public Class<?> getFieldType() {
		return this.fieldType;
	}
	
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}
	
}
