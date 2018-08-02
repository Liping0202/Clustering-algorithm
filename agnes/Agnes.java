package agnes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException; 
import java.io.IOException;

public class Agnes {
    public List<Cluster> startAnalysis(List<DataPoint> dataPoints,int ClusterNum){
        List<Cluster> finalClusters=new ArrayList<>();
        List<Cluster> originalClusters=initialCluster(dataPoints);
        finalClusters=originalClusters;
        while(finalClusters.size()>ClusterNum){
            double min=Double.MAX_VALUE;
            int mergeIndexA=0;
            int mergeIndexB=0;
            for(int i=0;i<finalClusters.size();i++){
                for(int j=0;j<finalClusters.size();j++){
                    if(i!=j){
                        Cluster clusterA=finalClusters.get(i);
                        Cluster clusterB=finalClusters.get(j);

                        List<DataPoint> dataPointsA=clusterA.getDataPoints();
                        List<DataPoint> dataPointsB=clusterB.getDataPoints();

                        for(int m=0;m<dataPointsA.size();m++){
                            for(int n=0;n<dataPointsB.size();n++){
                                double tempDis=getDistance(dataPointsA.get(m),dataPointsB.get(n));
                                if(tempDis<min){
                                    min=tempDis;
                                    mergeIndexA=i;
                                    mergeIndexB=j;
                                }
                            }
                        }
                    }
                } 
            }
            //合并cluster[mergeIndexA]和cluster[mergeIndexB]
            finalClusters=mergeCluster(finalClusters,mergeIndexA,mergeIndexB);
        }

        return finalClusters;
    }

    private List<Cluster> mergeCluster(List<Cluster> clusters,int mergeIndexA,int mergeIndexB){
        if (mergeIndexA != mergeIndexB) {
            // 将cluster[mergeIndexB]中的DataPoint加入到 cluster[mergeIndexA]
            Cluster clusterA = clusters.get(mergeIndexA);
            Cluster clusterB = clusters.get(mergeIndexB);

            List<DataPoint> dpA = clusterA.getDataPoints();
            List<DataPoint> dpB = clusterB.getDataPoints();

            for (DataPoint dp : dpB) {
                DataPoint tempDp = new DataPoint();
                tempDp = dp;
                tempDp.setCluster(clusterA);//将tempDp数据点设置为clusterA中元素
                dpA.add(tempDp);//将数据点tempDp添加到clusterA簇对应的List集合中
            }

            clusterA.setDataPoints(dpA);
            clusters.remove(mergeIndexB);
        }

        return clusters;
    }

    // 初始化类簇,每一个数据点形成一个类簇
    private List<Cluster> initialCluster(List<DataPoint> dataPoints){
        List<Cluster> originalClusters=new ArrayList<>();
        for(int i=0;i<dataPoints.size();i++){
            DataPoint tempDataPoint=dataPoints.get(i);
            List<DataPoint> tempDataPoints=new ArrayList<>();
            tempDataPoints.add(tempDataPoint);

            Cluster tempCluster=new Cluster();
            tempCluster.setClusterName("Cluster "+String.valueOf(i));
            tempCluster.setDataPoints(tempDataPoints);

            tempDataPoint.setCluster(tempCluster);
            originalClusters.add(tempCluster);
        }

        return originalClusters;
    }

    //计算两个样本点之间的欧几里得距离
    private double getDistance(DataPoint dpA, DataPoint dpB){
        double distance=0;
        double[] dimA = dpA.getDimensioin();
        double[] dimB = dpB.getDimensioin();

        if (dimA.length == dimB.length) {
            for (int i = 0; i < dimA.length; i++) {
                double temp=Math.pow((dimA[i]-dimB[i]),2);
                distance=distance+temp;
            }
            distance=Math.pow(distance, 0.5);
        }

        return distance;
    }

    public static void main(String[] args){
    	/*
    	 * 读取数据，并将数据添加到数据集合中
    	 */
        ArrayList<DataPoint> dpoints = new ArrayList<>();
        try {  
            BufferedReader reader = new BufferedReader(new FileReader("F:\\\\.eclipse\\\\JAVA\\\\src\\\\agnes\\\\dataagnes.txt"));  
            String line = null; 
            int i=0;
            while((line=reader.readLine())!=null){ 
            	i++;
                String str[] = line.split("\\s+");  
                double[] dimension=new double[2];  
                dimension[0]=Double.parseDouble(str[0].trim());  
                dimension[1]=Double.parseDouble(str[1].trim());  
                dpoints.add(new DataPoint(String.valueOf((char)(96+i)),dimension));
            }   
        }catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }catch (IOException e) {  
            e.printStackTrace();  
        }
        
        int clusterNum=3; //类簇数

        Agnes ca=new Agnes();
        List<Cluster> clusters=ca.startAnalysis(dpoints, clusterNum);

        for(Cluster cl:clusters){
            System.out.println("------"+cl.getClusterName()+"------");
            List<DataPoint> tempDps=cl.getDataPoints();
            for(DataPoint tempdp:tempDps){
                System.out.println(tempdp.getDataPointName());
            }
        }

    }
}
