/**
 * 
 */
package org.yelong.model.generator.test.pdm;

import java.io.File;
import java.util.List;

import org.yelong.core.model.table.vice.ModelAndTable;
import org.yelong.model.generator.DefaultModelGenerator;
import org.yelong.model.generator.GeneratorModelAndTable;
import org.yelong.model.generator.ModelGenerator;
import org.yelong.model.generator.pdm.DefaultPDMResolver;
import org.yelong.model.generator.pdm.PDMResolver;
import org.yelong.model.generator.pdm.PDMResolverException;

/**
 * @author PengFei
 * @date 2020年1月10日下午5:27:16
 */
public class PDMTest {

	public static void main(String[] args) throws PDMResolverException {
		PDMResolver pdmResolver = new DefaultPDMResolver();
		List<GeneratorModelAndTable> modelAndTables = pdmResolver.resolve(new File("D:\\PowerDesigner\\数模\\generator.pdm"));
		//System.out.println(modelAndTables);
		ModelGenerator modelGenerator = new DefaultModelGenerator();
		for (GeneratorModelAndTable generatorModelAndTable : modelAndTables) {
			modelGenerator.generate(generatorModelAndTable, "D:\\PowerDesigner\\数模\\model");
		}
	}
	
	
}
