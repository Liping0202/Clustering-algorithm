package Kmeans2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 * 
 * ���ݿ�������
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
			// ����mysql������
			Class.forName(driver);
			// �������ݿ�����
			con = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("����������ʧ��");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ע��������ʧ��");
			e.printStackTrace();
		}
		return con;
	}
}

