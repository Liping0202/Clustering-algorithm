package Optics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ClusterAnalysis {
	class ComparatorDp implements Comparator<DataPoint> {
		public int compare(DataPoint p1, DataPoint p2) {
			double temp = p1.getReachableDistance()	- p2.getReachableDistance();
			int a = 0;
			if (temp < 0) {
				a = -1;
			} else {
				a = 1;
			}
			return a;
		}
	}

	public List<DataPoint> startAnalysis(List<DataPoint> dataPoints,double radius, int ObjectNum) {
		List<DataPoint> dpList = new ArrayList<>();// ������д洢���Ķ�����ú��Ķ����ֱ�ӿɴ����
		List<DataPoint> dpQue = new ArrayList<>();// ������д洢��������������

		int total = 0;
		while (total < dataPoints.size()) {
			if (isContainedInList(dataPoints.get(total), dpList) == -1) {
				List<DataPoint> tmpDpList = isKeyAndReturnObjects(
						dataPoints.get(total), dataPoints, radius, ObjectNum);//�ж�һ�����ݵ��Ƿ�Ϊ���Ķ���
				//�ҷ��ظú��Ķ�������Ӧ��ֱ���ܶȿɴ������㼯��
				if (tmpDpList != null && tmpDpList.size() > 0) {
					DataPoint newDataPoint = new DataPoint(dataPoints.get(total));
					dpQue.add(newDataPoint);
				}
			}
			while (!dpQue.isEmpty()) {
				DataPoint tempDpfromQ = dpQue.remove(0);
				DataPoint newDataPoint = new DataPoint(tempDpfromQ);
				dpList.add(newDataPoint);
				List<DataPoint> tempDpList = isKeyAndReturnObjects(tempDpfromQ,
						dataPoints, radius, ObjectNum);
				System.out.println(newDataPoint.getName() + ":"
						+ newDataPoint.getReachableDistance());
				if (tempDpList != null && tempDpList.size() > 0) {
					for (int i = 0; i < tempDpList.size(); i++) {
						DataPoint tempDpfromList = tempDpList.get(i);
						int indexInList = isContainedInList(tempDpfromList,dpList);
						int indexInQ = isContainedInList(tempDpfromList, dpQue);
						if (indexInList == -1) {
							if (indexInQ > -1) {
								int index = -1;
								for (DataPoint dataPoint : dpQue) {
									index++;
									if (index == indexInQ) {
										if (dataPoint.getReachableDistance() > tempDpfromList.getReachableDistance()) 
										{
											dataPoint.setReachableDistance(tempDpfromList.getReachableDistance());
										}
									}
								}
							} else {
								dpQue.add(new DataPoint(tempDpfromList));
							}
						}
					}

					// ��Q������������
					Collections.sort(dpQue, new ComparatorDp());
				}
			}
			//System.out.println("------total : "+ total);
			System.out.println("------");
			total++;
		}

		return dpList;
	}

	public void displayDataPoints(List<DataPoint> dps) {
		System.out.println();
		for (DataPoint dp : dps) {
			System.out.print("��"+dp.getName() + "�Ŀɴ����:" + dp.getReachableDistance()+"    ");
		} 
		System.out.println();
	}

	//�ж����ݵ��Ƕ������ڽ��������
	private int isContainedInList(DataPoint dp, List<DataPoint> dpList) {
		int index = -1;
		for (DataPoint dataPoint : dpList) {
			index++;
			if (dataPoint.getName().equals(dp.getName())) {
				return index;
			}
		}
		return -1;
	}

	private List<DataPoint> isKeyAndReturnObjects(DataPoint dataPoint,
			List<DataPoint> dataPoints, double radius, int ObjectNum) {// ������ֱ���ܶȿɴ��
		List<DataPoint> arrivableObjects = new ArrayList<DataPoint>(); // �����洢����ֱ���ܶȿɴ����
		List<Double> distances = new ArrayList<Double>(); // ŷ����þ���
		double coreDistance; // ���ľ���

		for (int i = 0; i < dataPoints.size(); i++) {
			DataPoint dp = dataPoints.get(i);
			double distance = getDistance(dataPoint, dp);
			if (distance <= radius) {
				distances.add(distance);
				arrivableObjects.add(dp);//ֻҪ���ݵ�����pΪ���ĵ㣬radiusΪ�뾶��Բ�ھ�������p��ֱ���ܶȿɵ���
			}
		}

		if (arrivableObjects.size() >= ObjectNum) {
			List<Double> newDistances = new ArrayList<Double>(distances);
			Collections.sort(distances);
			coreDistance = distances.get(ObjectNum - 1);//ͨ����distances���򣬵õ������ĿΪObjectNumʱ�ľ���Ϊ���ľ���
			for (int j = 0; j < arrivableObjects.size(); j++) {
				//ΪarrivableObjects�����ݵ����ÿɴ���룬�����ݵ��ں��ľ����ڣ���ɴ����Ϊ���ľ��룬����Ϊ����֮��ľ���
				if (coreDistance > newDistances.get(j)) {
					if (newDistances.get(j) == 0) {
						dataPoint.setReachableDistance(coreDistance);
					}
					arrivableObjects.get(j).setReachableDistance(coreDistance);
				} else {
					arrivableObjects.get(j).setReachableDistance(newDistances.get(j));
				}
			}
			return arrivableObjects;
		}

		return null;
	}

	private double getDistance(DataPoint dp1, DataPoint dp2) {
		double distance = 0.0;
		double[] dim1 = dp1.getDimensioin();
		double[] dim2 = dp2.getDimensioin();
		if (dim1.length == dim2.length) {
			for (int i = 0; i < dim1.length; i++) {
				double temp = Math.pow((dim1[i] - dim2[i]), 2);
				distance = distance + temp;
			}
			distance = Math.pow(distance, 0.5);
			return distance;
		}
		return distance;
	}

	public static void main(String[] args) {
		ArrayList<DataPoint> dpoints = new ArrayList<>();      
        dpoints=new ReadPoint().read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\Optics\\\\dataOptics.txt");  
		ClusterAnalysis ca = new ClusterAnalysis();
		List<DataPoint> dps = ca.startAnalysis(dpoints, 2, 4);
		ca.displayDataPoints(dps);
	}
}