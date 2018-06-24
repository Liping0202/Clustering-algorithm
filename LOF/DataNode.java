package LOF;

import java.util.ArrayList;   
 
public class DataNode { 
	//ÿ������������������ԣ�����������������ά�ȡ�k���롢k���򡢿ɴ��ܶȡ��ɴ���롢�ֲ���Ⱥ����
    private String nodeName; // ��������  
    private double[] dimensioin; // �������ά��  
    private double kDistance; // k-����  
    private ArrayList<DataNode> kNeighbor = new ArrayList<DataNode>();// k-����  
    private double distance; // ���������ŷ����þ���  
    private double reachDensity;// �ɴ��ܶ�  
    private double reachDis;// �ɴ����  
    private double lof;// �ֲ���Ⱥ����  
    
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