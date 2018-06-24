package LOF;  
  
import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Comparator;
import java.text.DecimalFormat;
    
public class OuterData {  
    private static int INT_K = 5;//第k距离中的k值   
    public ArrayList<DataNode> getOutlierNode(ArrayList<DataNode> allNodes) {  
        ArrayList<DataNode> kdAndKnList = getKDAndKN(allNodes);  
        calReachDis(kdAndKnList);  
        calReachDensity(kdAndKnList);  
        calLof(kdAndKnList);  
        //降序排序  
        Collections.sort(kdAndKnList, new LofComparator());  
        return kdAndKnList;  
    }  
  
     //计算给定点与其他点的欧几里得距离,并找到给定点的k-领域和k距离。      
    private ArrayList<DataNode> getKDAndKN(ArrayList<DataNode> allNodes) {  
        ArrayList<DataNode> kdAndKnList = new ArrayList<DataNode>();  
        for (int i = 0; i < allNodes.size(); i++) { //数据集中每一个点都对应一个tempNodeList集合 
            ArrayList<DataNode> tempNodeList = new ArrayList<DataNode>();  
            DataNode nodeA = new DataNode(allNodes.get(i).getNodeName(), allNodes.get(i).getDimensioin());    
            for (int j = 0; j < allNodes.size(); j++) {  
                DataNode nodeB = new DataNode(allNodes.get(j).getNodeName(), allNodes.get(j).getDimensioin());    
                double tempDis = getDis(nodeA, nodeB);  
                nodeB.setDistance(tempDis);  
                tempNodeList.add(nodeB);  //点A对应的集合tempNodeList中存放着nodeB,nodeB的属性：记录每个b点到点A的距离
            }    
            Collections.sort(tempNodeList, new DistComparator());  
            for (int k = 1; k < INT_K; k++) {    
                nodeA.getkNeighbor().add(tempNodeList.get(k));  
                if (k == INT_K - 1) {    
                    nodeA.setkDistance(tempNodeList.get(k).getDistance());  
                }  
            }  
            kdAndKnList.add(nodeA);  //kdAndKnList集合中存放着nodeA，每个nodeA具有属性：点A 的第k距离和第k邻域
        }  
        return kdAndKnList;  
    }
    // 计算给定点A与其他点B之间的欧几里得距离。  
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
  //定义一个比较类，根据两个点到指定点的距离的大小进行升序排序 
    class DistComparator implements Comparator<DataNode> {  
        public int compare(DataNode A, DataNode B) { 
        	if(A.getDistance() - B.getDistance()==0)
        		return 0;
        	else
        		return A.getDistance() - B.getDistance() < 0 ? -1 : 1;      
                
        }  
    } 
    
    //计算每个点的可达距离, reachdis(p,o)=max{ k-distance(o),d(p,o)}  
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
  //获取某个点的k距离，因kdAndKnList中的数据点均有对应的k距离，所以按照点的名字找打对应点的k距离  
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
  
//计算每个点的可达密度  
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
  
//每个点的局部离群点因子 
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
    //获取某个点的可达密度 
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
      
     //降序排序   
    class LofComparator implements Comparator<DataNode> {  
        public int compare(DataNode A, DataNode B) { 
        	if(A.getLof() - B.getLof()==0)
        		return 0;
        	else return A.getLof() - B.getLof() < 0 ? 1 : -1;     
        }  
    }  
  
    public static void main(String[] args) {          
        DecimalFormat df=new DecimalFormat("#.####");  //将不同类型的数字进行格式化  
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