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
	public CanopyBuilder() {//���캯��
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
				//ȡһ������Ϊ��ʼcanopy
				if (canopies.size() == 0) {
					Canopy canopy = new Canopy();
					canopy.setCenter(current);
					canopy.getPoints().add(current);//�տ�ʼpoints����Ϊnull,��ռ����м�����current��
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
					System.out.println("��ǰ�㵽���ĵ��distance: " +df.format(d));
					//����С��T1����canopy�����������
					if (d < T1) {
						current.setMark(Point.MARK_WEAK);
						canopy.getPoints().add(current);
					} else if (d > T1) {
						index++;
					} 
					//����С��T2����б����Ƴ�������ǿ���
					if (d <= T2) {
						current.setMark(Point.MARK_STRONG);
						isRemove = true;
					}
				}
				//���������canopy�ľ��붼����T1,�����µ�canopy
				if (index == canopies.size()) {
					Canopy newCanopy = new Canopy();
					newCanopy.setCenter(current);
					newCanopy.getPoints().add(current);
					canopies.add(newCanopy);
					isRemove = true;
					System.out.println("�����("+current+"),������һ����canopy");
				}
				if (isRemove) {
					iterator.remove();
				}
			}
		}
		int i=1;
		for (Canopy c : canopies) {
			System.out.print("��"+i+"��canopy��old center: (" + c.getCenter()+")");
			c.computeCenter();
			System.out.println("    ��"+i+"��canopy��new center:( " + c.getCenter()+")");
			System.out.println(c.getPoints());
		}
	}
	//��������֮��������پ���
		public double manhattanDistance(Point a, Point b) {
			return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
		}
		//��������֮���ŷ�Ͼ���
		public double euclideanDistance(Point a, Point b) {
			double sum =  Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2);
			return Math.sqrt(sum);
		}
	
	public static void main(String[] args) {
		CanopyBuilder builder = new CanopyBuilder();
		builder.run();
	}
}