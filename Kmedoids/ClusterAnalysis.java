package Kmedoids;

import java.util.ArrayList;
public class ClusterAnalysis {
    private Cluster[] clusters;// 所有类簇
    private int iter;// 迭代次数
    private ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();// 所有样本点
    private int dimNum;//维度
    //构造函数
    public ClusterAnalysis(int k, int iter, ArrayList<DataPoint> dataPoints,int dimNum) {
        clusters = new Cluster[k];// 簇种类数
        for (int i = 0; i < k; i++) {
            clusters[i] = new Cluster("Cluster:" + i);
        } 
        this.iter = iter;
        this.dataPoints = dataPoints;
        this.dimNum=dimNum;
    }
    public int getIterations() {
        return iter;
    }
    public ArrayList<DataPoint>[] getClusterOutput() {
        ArrayList<DataPoint> v[] = new ArrayList[clusters.length];
        for (int i = 0; i < clusters.length; i++) {
            v[i] = clusters[i].getDataPoints();
        }
        return v;
    }
    private void setInitialMedoids(double[][] medoids) {
        for (int n = 0; n < clusters.length; n++) {//对于k个簇，每个簇进行遍历一次
            Medoid medoid = new Medoid(medoids[n]);//将一个随机点设置为中心点,输入一个中心点构成一个Medoid对象
            clusters[n].setMedoid(medoid);//将簇n的中心点设置为medoid
            medoid.setCluster(clusters[n]);//将中心点medoid的簇对应到簇n
        }
    }
    public void startAnalysis(double[][] medoids) {
        setInitialMedoids(medoids);//设置初始簇中心点
        double[][] newMedoids=medoids;
        double[][] oldMedoids=new double[medoids.length][this.dimNum];
        while(!isEqual(oldMedoids,newMedoids)){//两个集合不相等时，即中心点发生了改变
            for(int m = 0; m < clusters.length; m++){//每次迭代开始情况各类簇的点初始化
                clusters[m].getDataPoints().clear();
            }
            for (int j = 0; j < dataPoints.size(); j++) {//将所有样本点划分到距离最近的簇中
                int clusterIndex=0;
                double minDistance=Double.MAX_VALUE;
                for (int k = 0; k < clusters.length; k++) {//判断样本点属于哪个类簇
                    double eucDistance=dataPoints.get(j).testEuclideanDistance(clusters[k].getMedoid());
                    if(eucDistance<minDistance){
                        minDistance=eucDistance;
                        clusterIndex=k;
                    }
                }
                clusters[clusterIndex].addDataPoint(dataPoints.get(j)); //将该样本点添加到该类簇
            }
            for(int m = 0; m < clusters.length; m++){
                clusters[m].getMedoid().calcMedoid();//重新计算各类簇的质点
            }
            for(int i=0;i<medoids.length;i++){
                for(int j=0;j<this.dimNum;j++){
                    oldMedoids[i][j]=newMedoids[i][j];//之前的新中心点被更新为旧中心点
                }
            }
            for(int n=0;n<clusters.length;n++){
                newMedoids[n]=clusters[n].getMedoid().getDimensioin();//新的中心点由前面重新计算的簇的质点得到
            }
            this.iter++;
        }
    }
   
    private boolean isEqual(double[][] oldMedoids,double[][] newMedoids){
        boolean flag=false;
        for(int i=0;i<oldMedoids.length;i++){
            for(int j=0;j<oldMedoids[i].length;j++){
                if(oldMedoids[i][j]!=newMedoids[i][j]){
                    return flag;
                }
            }
        }
        flag=true;
        return flag;//有一个点不相等，则两个中心点集合不相等，只有所有中心点集合中的元素都相等，才返回true。
    }
}
