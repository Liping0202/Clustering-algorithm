package agnes;

import java.util.ArrayList;
import java.util.List;


public class Cluster {
    private List<DataPoint> dataPoints = new ArrayList<>(); // ����е�������
    private String clusterName;

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }
    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

}
