package cn.ypjalt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	static Connection conn;
	static String Driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/library";
	static String name = "root";
	static String password = "123456";

	public static Connection openConnection() {
		try {
			Class.forName(Driver);
			conn = DriverManager.getConnection(url, name, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if (conn != null)
				conn.close();
			if (st != null)
				st.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static boolean executeSql(String sql) {
		Connection conn = openConnection();
		Statement st = null;
		try {
			st = conn.createStatement();
			return st.executeUpdate(sql) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, st, null);
		}
		return false;
	}

}
