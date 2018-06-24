package LOF;  
  
import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Comparator;
import java.text.DecimalFormat;
    
public class OuterData {  
    private static int INT_K = 5;//��k�����е�kֵ   
    public ArrayList<DataNode> getOutlierNode(ArrayList<DataNode> allNodes) {  
        ArrayList<DataNode> kdAndKnList = getKDAndKN(allNodes);  
        calReachDis(kdAndKnList);  
        calReachDensity(kdAndKnList);  
        calLof(kdAndKnList);  
        //��������  
        Collections.sort(kdAndKnList, new LofComparator());  
        return kdAndKnList;  
    }  
  
     //������������������ŷ����þ���,���ҵ��������k-�����k���롣      
    private ArrayList<DataNode> getKDAndKN(ArrayList<DataNode> allNodes) {  
        ArrayList<DataNode> kdAndKnList = new ArrayList<DataNode>();  
        for (int i = 0; i < allNodes.size(); i++) { //���ݼ���ÿһ���㶼��Ӧһ��tempNodeList���� 
            ArrayList<DataNode> tempNodeList = new ArrayList<DataNode>();  
            DataNode nodeA = new DataNode(allNodes.get(i).getNodeName(), allNodes.get(i).getDimensioin());    
            for (int j = 0; j < allNodes.size(); j++) {  
                DataNode nodeB = new DataNode(allNodes.get(j).getNodeName(), allNodes.get(j).getDimensioin());    
                double tempDis = getDis(nodeA, nodeB);  
                nodeB.setDistance(tempDis);  
                tempNodeList.add(nodeB);  //��A��Ӧ�ļ���tempNodeList�д����nodeB,nodeB�����ԣ���¼ÿ��b�㵽��A�ľ���
            }    
            Collections.sort(tempNodeList, new DistComparator());  
            for (int k = 1; k < INT_K; k++) {    
                nodeA.getkNeighbor().add(tempNodeList.get(k));  
                if (k == INT_K - 1) {    
                    nodeA.setkDistance(tempNodeList.get(k).getDistance());  
                }  
            }  
            kdAndKnList.add(nodeA);  //kdAndKnList�����д����nodeA��ÿ��nodeA�������ԣ���A �ĵ�k����͵�k����
        }  
        return kdAndKnList;  
    }
    // ���������A��������B֮���ŷ����þ��롣  
    private double getDis(DataNode A, DataNode B) {  
        double dis = 0.0;  
        double[] dimA = A.getDimensioin();  
        double[] dimB = B.getDimensioin();  
        if (dimA.length == dimB.length) {  
            for (int i = 0; i < dimA.length; i++) {  
                double temp = Math.pow(dimA[i] - dimB[i], 2);  
                dis = dis + temp;  
            }  
            dis = Math.pow(dis, 0.5);  
        }  
        return dis;  
    } 
  //����һ���Ƚ��࣬���������㵽ָ����ľ���Ĵ�С������������ 
    class DistComparator implements Comparator<DataNode> {  
        public int compare(DataNode A, DataNode B) { 
        	if(A.getDistance() - B.getDistance()==0)
        		return 0;
        	else
        		return A.getDistance() - B.getDistance() < 0 ? -1 : 1;      
                
        }  
    } 
    
    //����ÿ����Ŀɴ����, reachdis(p,o)=max{ k-distance(o),d(p,o)}  
  private void calReachDis(ArrayList<DataNode> kdAndKnList) {  
      for (DataNode node : kdAndKnList) {  
          ArrayList<DataNode> tempNodes = node.getkNeighbor();  
          for (DataNode tempNode : tempNodes) {   
              double kDis = getKDis(tempNode.getNodeName(), kdAndKnList);  
              if (kDis < tempNode.getDistance()) {  
                  tempNode.setReachDis(tempNode.getDistance());  
              } else {  
                  tempNode.setReachDis(kDis);  
              }  
          }  
      }  
  }
  //��ȡĳ�����k���룬��kdAndKnList�е����ݵ���ж�Ӧ��k���룬���԰��յ�������Ҵ��Ӧ���k����  
 private double getKDis(String nodeName, ArrayList<DataNode> nodeList) {  
     double kDis = 0;  
     for (DataNode node : nodeList) {  
         if (nodeName.trim().equals(node.getNodeName().trim())) {  
             kDis = node.getkDistance();  
             break;  
         }  
     }  
     return kDis;  
 }
  
//����ÿ����Ŀɴ��ܶ�  
  private void calReachDensity(ArrayList<DataNode> kdAndKnList) {  
      for (DataNode node : kdAndKnList) {  
          ArrayList<DataNode> tempNodes = node.getkNeighbor();  
          double sum = 0.0;  
          double rd = 0.0;  
          for (DataNode tempNode : tempNodes) {  
              sum = tempNode.getReachDis() + sum;  
          }  
          rd = (double) INT_K / sum;  
          node.setReachDensity(rd);  
      }  
  }  
  
//ÿ����ľֲ���Ⱥ������ 
    private void calLof(ArrayList<DataNode> kdAndKnList) {  
        for (DataNode node : kdAndKnList) {  
            ArrayList<DataNode> tempNodes = node.getkNeighbor();  
            double sum = 0.0;  
            for (DataNode tempNode : tempNodes) {  
                double rd = getRD(tempNode.getNodeName(), kdAndKnList);  
                sum = rd / node.getReachDensity() + sum;  
            }  
            sum = sum / (double) INT_K;  
            node.setLof(sum);  
        }  
    }  
    //��ȡĳ����Ŀɴ��ܶ� 
    private double getRD(String nodeName, ArrayList<DataNode> nodeList) {  
        double kDis = 0;  
        for (DataNode node : nodeList) {  
            if (nodeName.trim().equals(node.getNodeName().trim())) {  
                kDis = node.getReachDensity();  
                break;  
            }  
        }  
        return kDis;  
    }  
      
     //��������   
    class LofComparator implements Comparator<DataNode> {  
        public int compare(DataNode A, DataNode B) { 
        	if(A.getLof() - B.getLof()==0)
        		return 0;
        	else return A.getLof() - B.getLof() < 0 ? 1 : -1;     
        }  
    }  
  
    public static void main(String[] args) {          
        DecimalFormat df=new DecimalFormat("#.####");  //����ͬ���͵����ֽ��и�ʽ��  
        ArrayList<DataNode> dpoints = new ArrayList<DataNode>();      
        dpoints=new ReadPoint().read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\LOF\\\\data3.txt");  
        OuterData lof = new OuterData();  
        ArrayList<DataNode> nodeList = lof.getOutlierNode(dpoints);  
        for (DataNode node : nodeList) {  
            System.out.println(node.getNodeName() + "  " + df.format(node.getLof())); 
            //Formats a double to produce a string.
        }  
    }  
}  