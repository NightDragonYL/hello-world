/**
 * 
 */
package org.yelong.model.generator;

import java.io.File;

/**
 * @author PengFei
 * @date 2020年1月10日下午7:00:05
 */
public interface ModelGenerator {

	/**
	 * 模型生成器
	 * @param modelAndTable
	 * @param filePath
	 * @return
	 */
	File generate(GeneratorModelAndTable modelAndTable,String filePath);
	
}
