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
		List<DataPoint> dpList = new ArrayList<>();// 结果队列存储核心对象及其该核心对象的直接可达对象
		List<DataPoint> dpQue = new ArrayList<>();// 有序队列存储样本点的输出次序

		int total = 0;
		while (total < dataPoints.size()) {
			if (isContainedInList(dataPoints.get(total), dpList) == -1) {
				List<DataPoint> tmpDpList = isKeyAndReturnObjects(
						dataPoints.get(total), dataPoints, radius, ObjectNum);//判断一个数据点是否为核心对象，
				//且返回该核心对象所对应的直接密度可达样本点集合
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

					// 对Q进行重新排序
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
			System.out.print("点"+dp.getName() + "的可达距离:" + dp.getReachableDistance()+"    ");
		} 
		System.out.println();
	}

	//判断数据点是都包含在结果队列中
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
			List<DataPoint> dataPoints, double radius, int ObjectNum) {// 找所有直接密度可达点
		List<DataPoint> arrivableObjects = new ArrayList<DataPoint>(); // 用来存储所有直接密度可达对象
		List<Double> distances = new ArrayList<Double>(); // 欧几里得距离
		double coreDistance; // 核心距离

		for (int i = 0; i < dataPoints.size(); i++) {
			DataPoint dp = dataPoints.get(i);
			double distance = getDistance(dataPoint, dp);
			if (distance <= radius) {
				distances.add(distance);
				arrivableObjects.add(dp);//只要数据点在以p为中心点，radius为半径的圆内均看做是p的直接密度可到点
			}
		}

		if (arrivableObjects.size() >= ObjectNum) {
			List<Double> newDistances = new ArrayList<Double>(distances);
			Collections.sort(distances);
			coreDistance = distances.get(ObjectNum - 1);//通过对distances排序，得到点的数目为ObjectNum时的距离为核心距离
			for (int j = 0; j < arrivableObjects.size(); j++) {
				//为arrivableObjects中数据点设置可达距离，若数据点在核心距离内，则可达距离为核心距离，否者为两点之间的距离
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