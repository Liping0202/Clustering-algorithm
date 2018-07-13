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
		// ���ԭʼ����
		Map<Point, ArrayList<Point>> nowCenterClusterMap = new HashMap<>();
		// ��ǰ���ļ�����ڵĵ�
		Map<Point, ArrayList<Point>> lastCenterClusterMap = null;// ��һ�����ļ�����ڵĵ�
		try {
			dataList = new SelectData().getPoints();
			
			starTime=System.currentTimeMillis();
			
			// �������K������Ϊ��ʼ����
			Random rd = new Random();
			int[] initIndex = { 50, 50, 50 };
			int[] tempIndex = { 0, 50, 100 };
			System.out.println("��ʼ�����±�: ");
			for (int i = 0; i < k; i++) {
				int index = rd.nextInt(initIndex[i]) + tempIndex[i];
				System.out.println("��" + (i + 1) + "�� : " + index);
				nowCenterClusterMap.put(dataList.get(index),
						new ArrayList<Point>());
			}
			// �����ʼ����
			System.out.println("��ʼ����: ");
			for (Point point : nowCenterClusterMap.keySet())
				System.out.println("key:  " + point);
 
			// �����ݵ�point�����䵽���������map��value��
			ManagePoint managePoint = new ManagePoint();
			while (true) {
				for (Point point : dataList) {
					double shortestDistance = Double.MAX_VALUE;// ��ʼ����̾���ΪDouble�����ֵ
					Point key = null;
					for (Entry<Point, ArrayList<Point>> entry : nowCenterClusterMap
							.entrySet()) {
						// ��������������ľ���
						double distance = managePoint.getDistance(
								entry.getKey(), point);
						if (distance < shortestDistance) {
							shortestDistance = distance;
							key = entry.getKey();
						}
					}
					nowCenterClusterMap.get(key).add(point);
				}
				// ����µ��������ϴε�������ȣ����˳�����ѭ��
				if (managePoint.isEqual(lastCenterClusterMap,
						nowCenterClusterMap)) {
					System.out.println("����ˡ�");
					break;
				}
				// ��������
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
			System.out.println("���ݿ����ʧ��");
			e.printStackTrace();
		}
		endTime=System.currentTimeMillis();
		Time=endTime-starTime;
		return nowCenterClusterMap;
	}
	
	/*
	 * ����������β�����ݼ����࣬����������ȷ��
	 */
	public double Correctrate(Map<Point, ArrayList<Point>> result) {
		int snum=dataList.size();//���弯�ϵ���������
		int crtnum=0;//�������������ȷ��������		
		
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
		 //��ԭʼ����������Ϊ����
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
		int K = 3;// ��Ϊ������
		Kmeans ms=new Kmeans();
		Map<Point, ArrayList<Point>> result = ms.executeKmeans(K);
		double corate=ms.Correctrate(result);
		// �������
		System.out.println("===========������: ============");
		for (Entry<Point, ArrayList<Point>> entry : result.entrySet()) {
			System.out.println("\n" + "�ȶ�������: " + entry.getKey());
			System.out.println("�ôصĴ�С: " + entry.getValue().size());
			System.out.println("����ĵ�:" + entry.getValue());
		}
		 System.out.println("Kmeans�㷨��ʱΪ"+ms.Time+"ms");
		 System.out.println("Kmeans�㷨����ȷ��Ϊ��"+corate*100+"%");
	}
}

