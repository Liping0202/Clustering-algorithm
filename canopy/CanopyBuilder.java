package canopy;

import java.util.ArrayList;
import java.util.Iterator;
import java.text.DecimalFormat;
import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  

public class CanopyBuilder {
	private double T1 = 8;
	private double T2 = 4;
	private ArrayList<Point> points=new ArrayList<Point>();
	private ArrayList<Canopy> canopies= new ArrayList<Canopy>();;
	DecimalFormat df=new DecimalFormat("#.####");
	public CanopyBuilder(ArrayList<Point> points) {//构造函数
			this.points=points;
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
						index++;//每次canopy遍历循环之前index归零，current到一个canopy的center距离大于T1时，
						        //则index++，如果index=canopy的个数，说明该点current到所有canopy的距离均大于T1，生成新的canopy
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
		ArrayList<Point> arr = new ArrayList<>();      
        arr=new Readpoint().read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\canopy\\\\data3.txt");  
		CanopyBuilder builder = new CanopyBuilder(arr);
		builder.run();
	}
}
class Readpoint{  
    //从文件中读取数据  
public ArrayList<Point> read(String fileName){  
    ArrayList<Point> arr=new ArrayList<>();  
    try {  
        BufferedReader reader = new BufferedReader(new FileReader(fileName));  
        String line = null; 
        int i=0;
        while((line=reader.readLine())!=null){ 
            String str[] = line.split("\\s+");    
            double x=Double.parseDouble(str[0].trim());  
            double y=Double.parseDouble(str[1].trim());  
            arr.add(new Point(x,y));
        }  
    }catch (FileNotFoundException e) {  
        e.printStackTrace();  
    }catch (IOException e) {  
        e.printStackTrace();  
    }       
    return arr;        
    }  
}
