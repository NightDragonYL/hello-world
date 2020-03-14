/**
 * 
 */
package org.yelong.model.generator.pdm;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.yelong.model.generator.GeneratorModelAndTable;

/**
 * @author PengFei
 * @date 2020年1月10日下午4:21:17
 */
public interface PDMResolver {
	
	List<GeneratorModelAndTable> resolve(InputStream is) throws PDMResolverException;
	
	List<GeneratorModelAndTable> resolve(File pdm) throws PDMResolverException;
	
}
