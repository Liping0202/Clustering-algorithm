package BisectingKmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
 * 二分k均值，实际上是对一个集合做多次的k=2的kmeans划分， 每次划分后会对sse值较大的簇再进行二分。
 *  最终使得分出来的簇的个数为k个则停止,这里利用之前kmeans的java实现作为基础类。
 */
public class BiKmeans {
	private int k;// 分成多少簇
	private List<float[]> dataSet;// 当前要被二分的簇
	private List<ClusterSet> cluster=new ArrayList<>(); // 簇集合
	int m;//试验次数m
	
	public BiKmeans(int k) {//构造函数
		if (k < 2) {
			k = 2;
		}
		this.k = k;
	}
	public void setDataSet(ArrayList<float[]> dataSet) {
		this.dataSet = dataSet;
	}
	
	public void init() {
		System.out.println("每次运行二分kmenas试验的次数m为：");              
	    m=(new Scanner(System.in)).nextInt();
	    int dataSetLength = dataSet.size();
		if (k > dataSetLength) {
			k = dataSetLength;
		}
	}
		 // 二分Kmeans算法核心过程
	private void BisectingKmeans() {
		init();
		if (k < 2) {
			// 小于2 则原样输出数据集被认为是只分了一个簇
			ClusterSet cs = new ClusterSet();
			cs.setClu(dataSet);
			cluster.add(cs);//向簇集合中添加一个ClusterSet簇
		}	
		
			while (cluster.size() < k) {
				List<ClusterSet> clu = kmeans(dataSet);	// 调用kmeans进行二分
				for (ClusterSet cl : clu) {
					cluster.add(cl);
				}
				if (cluster.size() == k)
					break;
				else// 顺序计算簇的误差平方和，找到误差平方和最大的簇
				{			
					float maxerr=0f;
					int maxindex=0;
					int i=0;
					for (ClusterSet tt:cluster) {
						//计算误差平方和并得出误差平方和最大的簇
						float err = ClusterSet.countRule(tt.getClu(), tt.getCenter());
						tt.setErro(err);
						if(maxerr<err){
							maxerr=err;
							maxindex=i;
						}
						i++;
					}
					dataSet=cluster.get(maxindex).getClu();//找到最大误差平方和对应的簇的数据集
					cluster.remove(maxindex);//从cluster中删除要继续一分为二的簇			
				}
			}
			int i=1;
			for(ClusterSet sc:cluster){
				System.out.println("第"+i+"个簇：");
				ClusterSet.printDataArray(sc.getClu());
				i++;
				}
			}

		// 调用kmeans得到两个簇。
		private List<ClusterSet> kmeans(List<float[]> dataSet) {
			Kmeans k = new Kmeans(2);
			// 设置原始数据集
			k.setDataSet(dataSet);
			// 执行算法
			k.execute(m);
		    //k.kmeans();
			// 将List<List<float[]>>类型的簇集合转化为List<Cluster>类型的簇集合
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
		System.out.println("输入将簇分成的类数k为：");              
	    int K=(new Scanner(System.in)).nextInt(); 
		BiKmeans bkm = new BiKmeans(K);
		
		ArrayList<float[]> dataSet = new ArrayList<>();
		ReadDataTest rd=new ReadDataTest();
		dataSet=rd.read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\BisectingKmeans\\\\data4.txt");

		bkm.setDataSet(dataSet);
		// 执行算法
		bkm. BisectingKmeans();
	}
}
