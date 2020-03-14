/**
 * 
 */
package org.yelong.cocoon.generator.test;

import org.yelong.cocoon.generator.CodeGen;
import org.yelong.labbol.customer.model.Customer;

/**
 * @author PengFei
 * @date 2020年3月12日下午7:42:59
 * @since 1.0
 */
public class CodeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CodeGen codeGen = new CodeGen(Customer.class, "D:\\java\\数据模型\\model", "");
		codeGen.process();
	}

}
