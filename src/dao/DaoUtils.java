package  dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DaoUtils {

	static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/db_museu", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void fechaConexoes(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (rs != null && !rs.isClosed())
				ps.close();
		} catch (Exception e) {
		}
	}
}
