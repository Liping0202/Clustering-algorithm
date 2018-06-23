package Kmedoids;

import java.util.ArrayList;
public class Cluster {
    private String clusterName; // 类簇名
    private Medoid medoid; // 类簇的质点
    private ArrayList<DataPoint> dataPoints; // 类簇中各样本点

    public Cluster(String clusterName) {
        this.clusterName = clusterName;
        this.medoid = null; // will be set by calling setCentroid()
        dataPoints = new ArrayList<DataPoint>();
    }
    public void setMedoid(Medoid c) {
        medoid = c;
    }
    public Medoid getMedoid() {
        return medoid;
    }
    public void addDataPoint(DataPoint dp) { // called from CAInstance
        dp.setCluster(this);// 标注该类簇属于某点,计算欧式距离
        this.dataPoints.add(dp);//该簇对应的数据集合中添加数据点dp
    }
    public void removeDataPoint(DataPoint dp) {
        this.dataPoints.remove(dp);
    }
    public int getNumDataPoints() {
        return this.dataPoints.size();
    }
    public DataPoint getDataPoint(int pos) {
        return (DataPoint) this.dataPoints.get(pos);
    }
    public String getName() {
        return this.clusterName;
    }
    public ArrayList<DataPoint> getDataPoints() {
        return this.dataPoints;
    }
}




