package canopy;

import java.util.ArrayList;
import java.util.Iterator;
import java.text.DecimalFormat;

public class CanopyBuilder {
	private double T1 = 8;
	private double T2 = 4;
	private ArrayList<Point> points = null;
	private ArrayList<Canopy> canopies = null;
	DecimalFormat df=new DecimalFormat("#.####");
	public CanopyBuilder() {//构造函数
		init();
	}
	public void init() {
		points = new ArrayList<Point>();
		points.add(new Point(8.1, 8.1));
		points.add(new Point(7.1, 7.1));
		points.add(new Point(6.2, 6.2));
		points.add(new Point(7.1, 7.1));
		points.add(new Point(2.1, 2.1));
		points.add(new Point(1.1, 1.1));
		points.add(new Point(0.1, 0.1));
		points.add(new Point(3.0, 3.0));
		canopies = new ArrayList<Canopy>();
	}	
	
	public void run() {
		while (points.size() > 0) {
			Iterator<Point> iterator = points.iterator();
			while (iterator.hasNext()) {
				Point current = iterator.next();
				System.out.println("current point: (" + current+")");
				//取一个点做为初始canopy
				if (canopies.size() == 0) {
					Canopy canopy = new Canopy();
					canopy.setCenter(current);
					canopy.getPoints().add(current);//刚开始points集合为null,则空集合中加入了current点
					canopies.add(canopy);
					iterator.remove();
					continue;
				}
				boolean isRemove = false;
				int index = 0;
				for (Canopy canopy : canopies) {
					Point center = canopy.getCenter();
					System.out.println("center:(" + center+")");
					double d = manhattanDistance(current, center);
					System.out.println("当前点到中心点的distance: " +df.format(d));
					//距离小于T1加入canopy，打上弱标记
					if (d < T1) {
						current.setMark(Point.MARK_WEAK);
						canopy.getPoints().add(current);
					} else if (d > T1) {
						index++;
					} 
					//距离小于T2则从列表中移除，打上强标记
					if (d <= T2) {
						current.setMark(Point.MARK_STRONG);
						isRemove = true;
					}
				}
				//如果到所有canopy的距离都大于T1,生成新的canopy
				if (index == canopies.size()) {
					Canopy newCanopy = new Canopy();
					newCanopy.setCenter(current);
					newCanopy.getPoints().add(current);
					canopies.add(newCanopy);
					isRemove = true;
					System.out.println("输入点("+current+"),建立了一个新canopy");
				}
				if (isRemove) {
					iterator.remove();
				}
			}
		}
		int i=1;
		for (Canopy c : canopies) {
			System.out.print("第"+i+"个canopy的old center: (" + c.getCenter()+")");
			c.computeCenter();
			System.out.println("    第"+i+"个canopy的new center:( " + c.getCenter()+")");
			System.out.println(c.getPoints());
		}
	}
	//计算两点之间的曼哈顿距离
		public double manhattanDistance(Point a, Point b) {
			return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
		}
		//计算两点之间的欧氏距离
		public double euclideanDistance(Point a, Point b) {
			double sum =  Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2);
			return Math.sqrt(sum);
		}
	
	public static void main(String[] args) {
		CanopyBuilder builder = new CanopyBuilder();
		builder.run();
	}
}