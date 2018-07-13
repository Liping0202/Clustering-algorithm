package Kmeans2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

 
public class Kmeans {
	ArrayList<Point> dataList = new ArrayList<>();
	long starTime;
	long endTime;
	long Time;
	public Map<Point, ArrayList<Point>> executeKmeans(int k) {
		// 存放原始数据
		Map<Point, ArrayList<Point>> nowCenterClusterMap = new HashMap<>();
		// 当前质心及其簇内的点
		Map<Point, ArrayList<Point>> lastCenterClusterMap = null;// 上一个质心及其簇内的点
		try {
			dataList = new SelectData().getPoints();
			
			starTime=System.currentTimeMillis();
			
			// 随机创建K个点作为起始质心
			Random rd = new Random();
			int[] initIndex = { 50, 50, 50 };
			int[] tempIndex = { 0, 50, 100 };
			System.out.println("起始质心下标: ");
			for (int i = 0; i < k; i++) {
				int index = rd.nextInt(initIndex[i]) + tempIndex[i];
				System.out.println("第" + (i + 1) + "个 : " + index);
				nowCenterClusterMap.put(dataList.get(index),
						new ArrayList<Point>());
			}
			// 输出起始质心
			System.out.println("起始质心: ");
			for (Point point : nowCenterClusterMap.keySet())
				System.out.println("key:  " + point);
 
			// 将数据点point加入配到离其最近的map的value中
			ManagePoint managePoint = new ManagePoint();
			while (true) {
				for (Point point : dataList) {
					double shortestDistance = Double.MAX_VALUE;// 初始化最短距离为Double的最大值
					Point key = null;
					for (Entry<Point, ArrayList<Point>> entry : nowCenterClusterMap
							.entrySet()) {
						// 计算质心与各点间的距离
						double distance = managePoint.getDistance(
								entry.getKey(), point);
						if (distance < shortestDistance) {
							shortestDistance = distance;
							key = entry.getKey();
						}
					}
					nowCenterClusterMap.get(key).add(point);
				}
				// 如果新的质心与上次的质心相等，则退出整个循环
				if (managePoint.isEqual(lastCenterClusterMap,
						nowCenterClusterMap)) {
					System.out.println("相等了。");
					break;
				}
				// 更新质心
				lastCenterClusterMap = nowCenterClusterMap;
				nowCenterClusterMap = new HashMap<Point, ArrayList<Point>>();
				System.out.println("------------------------------------------------------------------");
				for (Entry<Point, ArrayList<Point>> entry : lastCenterClusterMap
						.entrySet()) {
					nowCenterClusterMap.put(
							managePoint.getNewCenter(entry.getValue()),
							new ArrayList<Point>());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库操作失败");
			e.printStackTrace();
		}
		endTime=System.currentTimeMillis();
		Time=endTime-starTime;
		return nowCenterClusterMap;
	}
	
	/*
	 * 计算相对于鸢尾花数据集分类，聚类结果的正确率
	 */
	public double Correctrate(Map<Point, ArrayList<Point>> result) {
		int snum=dataList.size();//定义集合的总数据量
		int crtnum=0;//定义聚类后聚类正确的数据量		
		
		for (Point point : result.keySet()) {
			ArrayList<Point> biList=getList(point);
			ArrayList<Point> arr=result.get(point);
			for(Point p:arr) {
				for(Point pp:biList) {
					if(p.equals(pp))
						crtnum++;
				}					
			}
		}
		
		double crate=(double)crtnum/(double)snum;
		return crate;
	}
	
	public ArrayList<Point> getList(Point point){
		 //将原始数据量划分为三类
		ArrayList<Point> arr1=new ArrayList<>();
		ArrayList<Point> arr2=new ArrayList<>();
		ArrayList<Point> arr3=new ArrayList<>();
		ArrayList<Point> arr=new ArrayList<>();
		int i=0;
		for(Point p:dataList) {	
			if(i<50) {
				arr1.add(p);
			} 
			else if(i<100) {
				arr2.add(p);
			}
			else if(i<150) {
				arr3.add(p);
			}	
			i++;
		}
		double[] distance=new double[3];
		ManagePoint managePoint = new ManagePoint();
		for(Point p:arr1) {
			distance[0]+=managePoint.getDistance(p,point);
		}
		for(Point p:arr2) {
			distance[1]+=managePoint.getDistance(p,point);
		}
		for(Point p:arr3) {
			distance[2]+=managePoint.getDistance(p,point);
		}
		if(distance[0]<distance[1]&&distance[0]<distance[2]) 
				arr=arr1;
		if(distance[1]<distance[0]&&distance[1]<distance[2])
			arr=arr2;
		if(distance[2]<distance[0]&&distance[2]<distance[1])
			arr=arr3;
			return arr;
	}
 
	public static void main(String[] args) {
		int K = 3;// 分为三个类
		Kmeans ms=new Kmeans();
		Map<Point, ArrayList<Point>> result = ms.executeKmeans(K);
		double corate=ms.Correctrate(result);
		// 输出分类
		System.out.println("===========聚类结果: ============");
		for (Entry<Point, ArrayList<Point>> entry : result.entrySet()) {
			System.out.println("\n" + "稳定的质心: " + entry.getKey());
			System.out.println("该簇的大小: " + entry.getValue().size());
			System.out.println("簇里的点:" + entry.getValue());
		}
		 System.out.println("Kmeans算法耗时为"+ms.Time+"ms");
		 System.out.println("Kmeans算法的正确率为："+corate*100+"%");
	}
}

