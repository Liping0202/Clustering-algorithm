package BisectingKmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//K��ֵ�����㷨
public class Kmeans {
	private int k;// �ֳɶ��ٴ�
	private int m;// ��������
	private int M;//�������
	private int dataSetLength;// ���ݼ�Ԫ�ظ���
	private List<float[]> dataSet;// ���ݼ�
	private List<float[]> center;// ��������
	private List<List<float[]>> cluster; // ��
	private List<List<List<float[]>>> returnclu;
	private List<Float> jc;// ���ƽ���ͣ�kԽ�ӽ�dataSetLength�����ԽС
	private Random random;
	float minerror;//��С���ƽ����
	int minindex;//��С���ƽ���Ͷ�Ӧ��index

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
		System.out.println("��"+i+"�ε����ƽ����Ϊ��"+minerror);
		i++;
		while(M>1) {
			kmeans();
			returnclu.add(cluster);
			System.out.println("��"+i+"�ε����ƽ����Ϊ��"+jc.get(m));
			if(jc.get(m)<minerror) {
				minerror=jc.get(m);
				minindex=i;
				System.out.println("�������ƽ���͸�С������");
			}
			i++;
			M--;
		}
	}
		
		//Kmeans�㷨���Ĺ��̷���
			public void kmeans() {
				init();   //��ʼ�������ĵ㼯��center,��ʼ���ؼ���
				// ѭ�����飬ֱ������Ϊֹ
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
			System.out.println("����Ϊ�գ����������ݣ�������");
		}
		dataSetLength = dataSet.size();
		if (k > dataSetLength) {
			k = dataSetLength;
		}
		center = initCenters();
		cluster = initCluster();
		jc = new ArrayList<Float>();
	}
//��ʼ���������������ֳɶ��ٴؾ��ж��ٸ����ĵ�
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
			center.add(dataSet.get(randoms[i]));// ���ɳ�ʼ����������
		}
		return center;
	}
 //��ʼ���ؼ���
	private List<List<float[]>> initCluster() {
		List<List<float[]>> cluster = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			cluster.add(new ArrayList<float[]>());
		}
		return cluster;
	}


	//���ģ�����ǰԪ�طŵ���С����������صĴ���
	private void clusterSet() {
		float[] distance = new float[k];
		for (int i = 0; i < dataSetLength; i++) {
			for (int j = 0; j < k; j++) {
				distance[j] = ClusterSet.distance(dataSet.get(i), center.get(j));
			}
			int minLocation = minDistance(distance);
			cluster.get(minLocation).add(dataSet.get(i));// ���ģ�����ǰԪ�طŵ���С����������صĴ���
		}
	}
	private int minDistance(float[] distance) {
		float minDistance = distance[0];
		int minLocation = 0;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] < minDistance) {
				minDistance = distance[i];
				minLocation = i;
			} else if (distance[i] == minDistance) // �����ȣ��������һ��λ��
			{
				if (random.nextInt(10) < 5) {
					minLocation = i;
				}
			}
		}
		return minLocation;
	}

 // �������ƽ����׼��������
	private void countRule() {
		float jcF = 0;
		for (int i = 0; i < cluster.size(); i++) {
			for (int j = 0; j < cluster.get(i).size(); j++) {
				jcF += ClusterSet.errorSquare(cluster.get(i).get(j), center.get(i));
			}
		}
		jc.add(jcF);
	}

//�����µĴ����ķ���
	private void setNewCenter() {
		for (int i = 0; i < k; i++) {
			int n = cluster.get(i).size();
			if (n != 0) {
				float[] newCenter = { 0, 0 };
				for (int j = 0; j < n; j++) {
					newCenter[0] += cluster.get(i).get(j)[0];
					newCenter[1] += cluster.get(i).get(j)[1];
				}
				// ����һ��ƽ��ֵ
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
	// ��ȡ�������
	public List<List<float[]>> getCluster() {
		return returnclu.get(minindex);
	}
	/*public List<List<float[]>> getReturnClu() {
		return returnclu;
	}*/
}
