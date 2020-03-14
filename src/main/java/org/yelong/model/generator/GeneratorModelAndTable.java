/**
 * 
 */
package org.yelong.model.generator;

import java.util.List;

import org.yelong.core.model.Model;
import org.yelong.core.model.table.vice.DefaultModelAndTable;
import org.yelong.core.model.table.vice.FieldAndColumn;

/**
 * @author PengFei
 * @date 2020年1月10日下午5:39:26
 */
public class GeneratorModelAndTable extends DefaultModelAndTable{

	private String modelName;
	
	private String modelPackage;
	
	
	public GeneratorModelAndTable(String tableName, List<FieldAndColumn> fieldAndColumns) {
		super(null, tableName, fieldAndColumns);
	}

	@Override
	public <M extends Model> Class<M> getModelClass() {
		throw new UnsupportedOperationException("生成器的字段与列中，不允许获取模型类，这是不存在的！");
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	@Override
	public String toString() {
		return "GeneratorModelAndTable [modelName=" + modelName + ", modelPackage=" + modelPackage + "]";
	}
	
}
