package Kmedoids;

import java.util.ArrayList;
public class Medoid{
    private double dimension[]; // 质点的维度
    private Cluster cluster; //所属类簇
    private double etdDisSum;//Medoid到本类簇中所有的欧式距离之和

    public Medoid(double dimension[]) {
        this.dimension = dimension;
    }
    public double[] getDimensioin() {
        return this.dimension;
    }
    public void setCluster(Cluster c) {
        this.cluster = c;
    }
    public Cluster getCluster() {
        return this.cluster;
    }
    public void calcMedoid() {// 取代价最小的点
        calcEtdDisSum();
        double minEucDisSum = this.etdDisSum;
        ArrayList<DataPoint> dps = this.cluster.getDataPoints();
        for (int i = 0; i < dps.size(); i++) {
            double tempeucDisSum = dps.get(i).calEuclideanDistanceSum();
            if (tempeucDisSum < minEucDisSum) {
                dimension = dps.get(i).getDimensioin();
                minEucDisSum=tempeucDisSum;
            }
        }
    }
 
    // 计算该Medoid到同类簇所有样本点的欧式距离和
    private void calcEtdDisSum() {
        double sum=0.0;
        Cluster cluster=this.getCluster();
        ArrayList<DataPoint> dataPoints=cluster.getDataPoints();
        for(int i=0;i<dataPoints.size();i++){
            double[] dims=dataPoints.get(i).getDimensioin();
            for(int j=0;j<dims.length;j++){
                 double temp=Math.abs(dims[j]-this.dimension[j]);
                 sum=sum+temp;
            }
        }
        etdDisSum= sum;
    }
}
