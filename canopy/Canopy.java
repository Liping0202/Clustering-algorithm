package canopy;

import java.math.BigDecimal;
import java.util.ArrayList;
public class Canopy {
	private Point center = null;
	private ArrayList<Point> points = null;
	
	public void setCenter(Point center) {
		this.center = center;
	}
	public Point getCenter() {
		return center;
	}
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	public ArrayList<Point> getPoints() {
		if (null == points) {
			points = new ArrayList<Point>();
		}
		return points;
	}
	public void printCanopy() {
		ArrayList<Point> arr=this.getPoints();
		for(Point p:arr)
			System.out.print("("+p.toString()+")");
		System.out.println();
	}
	
	public void computeCenter() {
		double x = 0.0;
		double y = 0.0;
		for (Point point : getPoints()) {
			x += point.getX();
			y += point.getY();
		}
		double z = getPoints().size();
		double x1=x/z;  
		double y1=y/z; 
		BigDecimal b1=new BigDecimal(x1);  
		BigDecimal b2=new BigDecimal(y1);
		double X1= b1.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		double Y1= b2.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		setCenter(new Point(X1,Y1));
		//setCenter(new Point(x/z,y/z));
	}
}
