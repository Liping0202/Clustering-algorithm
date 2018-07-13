package Kmeans2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 * 
 * 数据库连接类
 * 
 */
public class DBConnection {
	public static final String driver = "com.mysql.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost:3306/iris";
	public static final String user = "root";
	public static final String pwd = "1993";
 
	public static Connection dBConnection() {
		Connection con = null;
		try {
			// 加载mysql驱动器
			Class.forName(driver);
			// 建立数据库连接
			con = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("加载驱动器失败");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("注册驱动器失败");
			e.printStackTrace();
		}
		return con;
	}
}

