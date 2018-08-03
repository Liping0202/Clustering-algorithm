package CHAMELEON;
/**
 * 坐标点类
 *
 */
public class Point{
	//坐标点id号,id号唯一
	int id;
	Integer x;
	Integer y;
	//是否已经被访问过
	boolean isVisited;
	
	public Point(String id, String x, String y){
		this.id = Integer.parseInt(id);
		this.x = Integer.parseInt(x);
		this.y = Integer.parseInt(y);
	}
	
	/**
	 * 计算当前点与制定点之间的欧式距离
	 * 
	 */
	public double ouDistance(Point p) {
		double distance = 0;
		distance = (this.x - p.x) * (this.x - p.x) + (this.y - p.y)
				* (this.y - p.y);
		distance = Math.sqrt(distance);
 
		return distance;
	}
	
	/**
	 * 判断2个坐标点是否为坐标点
	 * 
	 */
	public boolean isTheSame(Point p) {
		boolean isSamed = false;
 
		if (this.x == p.x && this.y == p.y) {
			isSamed = true;
		}
 
		return isSamed;
	}
}

