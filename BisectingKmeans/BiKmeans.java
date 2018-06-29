package BisectingKmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
 * ����k��ֵ��ʵ�����Ƕ�һ����������ε�k=2��kmeans���֣� ÿ�λ��ֺ���sseֵ�ϴ�Ĵ��ٽ��ж��֡�
 *  ����ʹ�÷ֳ����Ĵصĸ���Ϊk����ֹͣ,��������֮ǰkmeans��javaʵ����Ϊ�����ࡣ
 */
public class BiKmeans {
	private int k;// �ֳɶ��ٴ�
	private List<float[]> dataSet;// ��ǰҪ�����ֵĴ�
	private List<ClusterSet> cluster=new ArrayList<>(); // �ؼ���
	int m;//�������m
	
	public BiKmeans(int k) {//���캯��
		if (k < 2) {
			k = 2;
		}
		this.k = k;
	}
	public void setDataSet(ArrayList<float[]> dataSet) {
		this.dataSet = dataSet;
	}
	
	public void init() {
		System.out.println("ÿ�����ж���kmenas����Ĵ���mΪ��");              
	    m=(new Scanner(System.in)).nextInt();
	    int dataSetLength = dataSet.size();
		if (k > dataSetLength) {
			k = dataSetLength;
		}
	}
		 // ����Kmeans�㷨���Ĺ���
	private void BisectingKmeans() {
		init();
		if (k < 2) {
			// С��2 ��ԭ��������ݼ�����Ϊ��ֻ����һ����
			ClusterSet cs = new ClusterSet();
			cs.setClu(dataSet);
			cluster.add(cs);//��ؼ��������һ��ClusterSet��
		}	
		
			while (cluster.size() < k) {
				List<ClusterSet> clu = kmeans(dataSet);	// ����kmeans���ж���
				for (ClusterSet cl : clu) {
					cluster.add(cl);
				}
				if (cluster.size() == k)
					break;
				else// ˳�����ص����ƽ���ͣ��ҵ����ƽ�������Ĵ�
				{			
					float maxerr=0f;
					int maxindex=0;
					int i=0;
					for (ClusterSet tt:cluster) {
						//�������ƽ���Ͳ��ó����ƽ�������Ĵ�
						float err = ClusterSet.countRule(tt.getClu(), tt.getCenter());
						tt.setErro(err);
						if(maxerr<err){
							maxerr=err;
							maxindex=i;
						}
						i++;
					}
					dataSet=cluster.get(maxindex).getClu();//�ҵ�������ƽ���Ͷ�Ӧ�Ĵص����ݼ�
					cluster.remove(maxindex);//��cluster��ɾ��Ҫ����һ��Ϊ���Ĵ�			
				}
			}
			int i=1;
			for(ClusterSet sc:cluster){
				System.out.println("��"+i+"���أ�");
				ClusterSet.printDataArray(sc.getClu());
				i++;
				}
			}

		// ����kmeans�õ������ء�
		private List<ClusterSet> kmeans(List<float[]> dataSet) {
			Kmeans k = new Kmeans(2);
			// ����ԭʼ���ݼ�
			k.setDataSet(dataSet);
			// ִ���㷨
			k.execute(m);
		    //k.kmeans();
			// ��List<List<float[]>>���͵Ĵؼ���ת��ΪList<Cluster>���͵Ĵؼ���
			//List<List<float[]>> clus = k.getReturnClu();
			List<List<float[]>> clus = k.getCluster();
			List<ClusterSet> clusterset = new ArrayList<ClusterSet>();
			int i = 0;
			for (List<float[]> cl : clus) {
				ClusterSet cs = new ClusterSet();
				cs.setClu(cl);
				cs.setCenter(k.getCenter().get(i));
				clusterset.add(cs);
				i++;
			}
			return clusterset;
		}
		
	public static void main(String[] args) {
		System.out.println("���뽫�طֳɵ�����kΪ��");              
	    int K=(new Scanner(System.in)).nextInt(); 
		BiKmeans bkm = new BiKmeans(K);
		
		ArrayList<float[]> dataSet = new ArrayList<>();
		ReadDataTest rd=new ReadDataTest();
		dataSet=rd.read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\BisectingKmeans\\\\data4.txt");

		bkm.setDataSet(dataSet);
		// ִ���㷨
		bkm. BisectingKmeans();
	}
}
