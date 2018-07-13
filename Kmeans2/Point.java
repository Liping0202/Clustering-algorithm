package Kmeans2;

public class Point {
	private double x;
	private double y;
	private double z;
	private double w;
 
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
 
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
 
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
 
	public double getW() {
		return w;
	}
	public void setW(double w) {
		this.w = w;
	}
 
	public Point() {
	}
 
	public Point(double x, double y, double z, double w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
 
	public String toString() {
		return "Point [ x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}
 
	public boolean equals(Object obj) {
		Point point = (Point) obj;
		if (this.getX() == point.getX() && this.getY() == point.getY()
				&& this.getZ() == point.getZ() && this.getW() == point.getW()) {
			return true;
		}
		return false;
	}
 
	public int hashCode() {
		return (int) (x + y + z + w);
	}
}
