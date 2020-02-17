package com.ryan.dmtool.common;


import com.ryan.dmtool.DmTool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
	private static String JDBC_Driver;
	private static String JDBC_Url;
	private static String JDBC_Username;
	private static String JDBC_Password;

	public static Connection getConnection(){
		Connection conn = null;
		try {
			loadJDBC("oracle");
			Class.forName(JDBC_Driver);
			conn = DriverManager.getConnection(JDBC_Url,JDBC_Username,JDBC_Password);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return conn;
	}

	private static void loadJDBC(String DBType) throws IOException {
		DBType = "jdbc." + DBType.toLowerCase();
		JDBC_Driver = Config.jdbc.getString(DBType + ".class");
		JDBC_Url = Config.jdbc.getString(DBType + ".url");
		JDBC_Username = Config.jdbc.getString(DBType + ".username");
		JDBC_Password = Config.jdbc.getString(DBType + ".password");
	}
}
