package Kmedoids;

import java.util.ArrayList;

public class DataPoint {
    private double dimension[]; //样本点的维度  表示一个数据点
    private String pointName; //样本点名字
    private Cluster cluster; //类簇
    private double euDt;//样本点到质点的距离

    public DataPoint(double dimension[]) {//构造函数
        this.dimension = dimension;
        this.cluster = null;
    }
    public DataPoint(double dimension[],String pointName) {//构造函数
        this.dimension = dimension;
        this.pointName = pointName;
        this.cluster = null;
    }

    public double[] getDimensioin() {
        return this.dimension;
    }
    public String getPointName() {
        return this.pointName;
    }
    public double getCurrentEuDt() {
        return this.euDt;
    }
    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    public Cluster getCluster() {
        return this.cluster;
    }
   
    public double calEuclideanDistanceSum() {
        double sum=0.0;
        Cluster cluster=this.getCluster();
        ArrayList<DataPoint> dataPoints=cluster.getDataPoints();
        for(int i=0;i<dataPoints.size();i++){
            double[] dims=dataPoints.get(i).getDimensioin();
            for(int j=0;j<dims.length;j++){
            	double temp=Math.pow((dims[j]-this.dimension[j]),2);
                 sum=sum+temp;
            }
        }
        return Math.sqrt(sum);
    }
    public double testEuclideanDistance(Medoid c) {//求出任意一个数据点到中心点的距离
        double sum=0.0;
        double[] cDim=c.getDimensioin();//就是中心点c
        for(int i=0;i<dimension.length;i++){
           double temp=Math.pow((dimension[i]-cDim[i]),2);
           sum=sum+temp;
        }
        return Math.sqrt(sum);
    }
}
