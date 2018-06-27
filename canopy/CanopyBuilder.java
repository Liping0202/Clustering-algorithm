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
	public CanopyBuilder(ArrayList<Point> points) {//���캯��
			this.points=points;
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
						index++;//ÿ��canopy����ѭ��֮ǰindex���㣬current��һ��canopy��center�������T1ʱ��
						        //��index++�����index=canopy�ĸ�����˵���õ�current������canopy�ľ��������T1�������µ�canopy
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
		ArrayList<Point> arr = new ArrayList<>();      
        arr=new Readpoint().read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\canopy\\\\data3.txt");  
		CanopyBuilder builder = new CanopyBuilder(arr);
		builder.run();
	}
}
class Readpoint{  
    //���ļ��ж�ȡ����  
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
