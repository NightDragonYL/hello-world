/**
 * 
 */
package org.yelong.model.generator.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.sql.defaults.JdbcBaseDataBaseOperation;

/**
 * @author PengFei
 * @date 2020年1月10日下午3:12:00
 */
public class TableTest {

	public static void main(String[] args) throws ClassNotFoundException {
		Connection conn = getBaseDataBaseOperation().getConnection();
		String sql = "select * from TB_GATEWAY_ROUTE";
		PreparedStatement stmt ;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData data = rs.getMetaData();
			for (int i = 0; i < data.getColumnCount(); i++) {
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static BaseDataBaseOperation getBaseDataBaseOperation() throws ClassNotFoundException {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@//192.168.209.132:1521/ORCL";
		String username = "cert";
		String password = "cert";
		JdbcBaseDataBaseOperation baseDataBaseOperation = new JdbcBaseDataBaseOperation(driver, url, username, password);
		return baseDataBaseOperation;
	}

}
