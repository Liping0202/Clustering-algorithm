package agnes;

public class DataPoint {
    String dataPointName; // ��������
    Cluster cluster; // �������������
    private double dimensioin[]; // �������ά��

    public DataPoint() {

    }

    public DataPoint(String dataPointName,double[] dimensioin) {
        this.dataPointName = dataPointName;
        this.dimensioin = dimensioin;
    }

    public double[] getDimensioin() {
        return dimensioin;
    }

    public void setDimensioin(double[] dimensioin) {
        this.dimensioin = dimensioin;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public String getDataPointName() {
        return dataPointName;
    }

    public void setDataPointName(String dataPointName) {
        this.dataPointName = dataPointName;
    }
}
