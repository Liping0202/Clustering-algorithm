package Kmeans2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * 取出数据
 * 
 * @return pointList
 * 
 */
public class SelectData {
	public static final String SELECT = "select* from iris";
 
	public ArrayList<Point> getPoints() throws SQLException {
		ArrayList<Point> pointsList = new ArrayList<Point>();
		Connection con = DBConnection.dBConnection();
		ResultSet rs;
		// 创建一个PreparedStatement对象
		PreparedStatement pstmt = con.prepareStatement(SELECT);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			Point point = new Point();
			point.setX(rs.getDouble(2));
			point.setY(rs.getDouble(3));
			point.setZ(rs.getDouble(4));
			point.setW(rs.getDouble(5));
			pointsList.add(point);
		}
		System.out.println("数据集: " + pointsList);
		pstmt.close();
		rs.close();
		con.close();
		return pointsList;
	}
}


