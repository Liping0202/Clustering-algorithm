package BisectingKmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//K均值聚类算法
public class Kmeans {
	private int k;// 分成多少簇
	private int m;// 迭代次数
	private int M;//试验次数
	private int dataSetLength;// 数据集元素个数
	private List<float[]> dataSet;// 数据集
	private List<float[]> center;// 中心链表
	private List<List<float[]>> cluster; // 簇
	private List<List<List<float[]>>> returnclu;
	private List<Float> jc;// 误差平方和，k越接近dataSetLength，误差越小
	private Random random;
	float minerror;//最小误差平方和
	int minindex;//最小误差平方和对应的index

	public Kmeans(int k) {
		if (k <= 0) {
			k = 1;
		}
		this.k = k;
	}
	public void setDataSet(List<float[]> dataSet) {
		this.dataSet = dataSet;
	}
	public void execute(int tm) {
		M=tm;
		returnclu=new ArrayList<>();
		int i=0;
		minindex=0;
		kmeans();
		minerror=jc.get(m);
		returnclu.add(cluster);
		System.out.println("第"+i+"次的误差平方和为："+minerror);
		i++;
		while(M>1) {
			kmeans();
			returnclu.add(cluster);
			System.out.println("第"+i+"次的误差平方和为："+jc.get(m));
			if(jc.get(m)<minerror) {
				minerror=jc.get(m);
				minindex=i;
				System.out.println("出现误差平方和更小的试验");
			}
			i++;
			M--;
		}
	}
		
		//Kmeans算法核心过程方法
			public void kmeans() {
				init();   //初始化簇中心点集合center,初始化簇集合
				// 循环分组，直到误差不变为止
				while (true) {
					clusterSet();
					countRule();
					if (m != 0) {
						if (jc.get(m)-jc.get(m-1)==0) {
							break;
						}
					}
					setNewCenter();
					m++;
					cluster.clear();
					cluster = initCluster();
				}
			}
		
	private void init() {
		m = 0;
		random = new Random();
		if (dataSet == null || dataSet.size() == 0) {
			System.out.println("数据为空，请输入数据！！！！");
		}
		dataSetLength = dataSet.size();
		if (k > dataSetLength) {
			k = dataSetLength;
		}
		center = initCenters();
		cluster = initCluster();
		jc = new ArrayList<Float>();
	}
//初始化中心数据链表，分成多少簇就有多少个中心点
	private ArrayList<float[]> initCenters() {
		ArrayList<float[]> center = new ArrayList<float[]>();
		int[] randoms = new int[k];
		boolean flag;
		int temp = random.nextInt(dataSetLength);
		randoms[0] = temp;
		for (int i = 1; i < k; i++) {
			flag = true;
			while (flag) {
				temp = random.nextInt(dataSetLength);
				int j = 0;
				while (j < i) {
					if (temp == randoms[j]) {
						break;
					}
					j++;
				}
				if (j == i) {
					flag = false;
				}
			}
			randoms[i] = temp;
		}
		for (int i = 0; i < k; i++) {
			center.add(dataSet.get(randoms[i]));// 生成初始化中心链表
		}
		return center;
	}
 //初始化簇集合
	private List<List<float[]>> initCluster() {
		List<List<float[]>> cluster = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			cluster.add(new ArrayList<float[]>());
		}
		return cluster;
	}


	//核心，将当前元素放到最小距离中心相关的簇中
	private void clusterSet() {
		float[] distance = new float[k];
		for (int i = 0; i < dataSetLength; i++) {
			for (int j = 0; j < k; j++) {
				distance[j] = ClusterSet.distance(dataSet.get(i), center.get(j));
			}
			int minLocation = minDistance(distance);
			cluster.get(minLocation).add(dataSet.get(i));// 核心，将当前元素放到最小距离中心相关的簇中
		}
	}
	private int minDistance(float[] distance) {
		float minDistance = distance[0];
		int minLocation = 0;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] < minDistance) {
				minDistance = distance[i];
				minLocation = i;
			} else if (distance[i] == minDistance) // 如果相等，随机返回一个位置
			{
				if (random.nextInt(10) < 5) {
					minLocation = i;
				}
			}
		}
		return minLocation;
	}

 // 计算误差平方和准则函数方法
	private void countRule() {
		float jcF = 0;
		for (int i = 0; i < cluster.size(); i++) {
			for (int j = 0; j < cluster.get(i).size(); j++) {
				jcF += ClusterSet.errorSquare(cluster.get(i).get(j), center.get(i));
			}
		}
		jc.add(jcF);
	}

//设置新的簇中心方法
	private void setNewCenter() {
		for (int i = 0; i < k; i++) {
			int n = cluster.get(i).size();
			if (n != 0) {
				float[] newCenter = { 0, 0 };
				for (int j = 0; j < n; j++) {
					newCenter[0] += cluster.get(i).get(j)[0];
					newCenter[1] += cluster.get(i).get(j)[1];
				}
				// 设置一个平均值
				newCenter[0] = newCenter[0] / n;
				newCenter[1] = newCenter[1] / n;
				center.set(i, newCenter);
			}
		}
	}
	public void setCenter(List<float[]> center) {
		this.center = center;
	}
	public List<float[]> getCenter() {
		return center;
	}	
	// 获取结果分组
	public List<List<float[]>> getCluster() {
		return returnclu.get(minindex);
	}
	/*public List<List<float[]>> getReturnClu() {
		return returnclu;
	}*/
}
