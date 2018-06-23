package Kmedoids;

import java.util.ArrayList;
public class Medoid{
    private double dimension[]; // �ʵ��ά��
    private Cluster cluster; //�������
    private double etdDisSum;//Medoid������������е�ŷʽ����֮��

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
    public void calcMedoid() {// ȡ������С�ĵ�
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
 
    // �����Medoid��ͬ��������������ŷʽ�����
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
