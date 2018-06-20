package Kmeans;

import java.util.ArrayList;  
import java.util.Scanner;  

public class main {  
  public  static void main(String[] args)    
  {    
      //初始化一个Kmean对象，将k置为3   
      System.out.println("输入将簇分成的类数k为：");              
      int k=(new Scanner(System.in)).nextInt();  
      kmeans ks=new kmeans(k); //调用kmeans的构造函数   
      ArrayList<float[]> dataSet=new ArrayList<float[]>();      
      dataSet=new ReadData().read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\Kmeans\\\\data1.txt");  
      //设置原始数据集    
      ks.setDataSet(dataSet);    
      //执行算法    
      ks.kmeans();  
      //得到聚类结果    
      ArrayList<ArrayList<float[]>> cluster=ks.getCluster();    
      //查看结果    
      for(int i=0;i<cluster.size();i++)    
      {   
    	  System.out.println("分类后的第"+i+"个簇为：");
          ks.printDataArray(cluster.get(i));    
      }      
  }    
}
