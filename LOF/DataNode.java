package LOF;

import java.util.ArrayList;   
 
public class DataNode { 
	//每个样本点具有如下属性：样本点名、样本点维度、k距离、k邻域、可达密度、可达距离、局部利群因子
    private String nodeName; // 样本点名  
    private double[] dimensioin; // 样本点的维度  
    private double kDistance; // k-距离  
    private ArrayList<DataNode> kNeighbor = new ArrayList<DataNode>();// k-领域  
    private double distance; // 到给定点的欧几里得距离  
    private double reachDensity;// 可达密度  
    private double reachDis;// 可达距离  
    private double lof;// 局部离群因子  
    
    public DataNode(double[] dimensioin) { 
    	this.dimensioin = dimensioin;
    }  
    public DataNode(String nodeName, double[] dimensioin) {  
        this.nodeName = nodeName;  
        this.dimensioin = dimensioin;  
    }  
  
    public void setNodeName(String nodeName) {  
        this.nodeName = nodeName;  
    }
    public String getNodeName() {  
        return nodeName;  
    }  
  
    public void setDimensioin(double[] dimensioin) {  
        this.dimensioin = dimensioin;  
    }   
    public double[] getDimensioin() {  
        return dimensioin;  
    }  
  
    public void setkDistance(double kDistance) {  
        this.kDistance = kDistance;  
    }  
    public double getkDistance() {  
        return kDistance;  
    }  
  
    public void setkNeighbor(ArrayList<DataNode> kNeighbor) {  
        this.kNeighbor = kNeighbor;  
    }   
    public ArrayList<DataNode> getkNeighbor() {  
        return kNeighbor;  
    }  
  
    public void setDistance(double distance) {  
        this.distance = distance;  
    }  
    public double getDistance() {  
        return distance;  
    }  
  
    public void setReachDensity(double reachDensity) {  
        this.reachDensity = reachDensity;  
    }  
    public double getReachDensity() {  
        return reachDensity;  
    }  
  
    public void setReachDis(double reachDis) {  
        this.reachDis = reachDis;  
    }  
    public double getReachDis() {  
        return reachDis;  
    }  
  
    public void setLof(double lof) {  
        this.lof = lof;  
    }  
    public double getLof() {  
        return lof;  
    }  
}  