package Kmeans;

import java.util.ArrayList;  
import java.util.Scanner;  

public class main {  
  public  static void main(String[] args)    
  {    
      //��ʼ��һ��Kmean���󣬽�k��Ϊ3   
      System.out.println("���뽫�طֳɵ�����kΪ��");              
      int k=(new Scanner(System.in)).nextInt();  
      kmeans ks=new kmeans(k); //����kmeans�Ĺ��캯��   
      ArrayList<float[]> dataSet=new ArrayList<float[]>();      
      dataSet=new ReadData().read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\Kmeans\\\\data1.txt");  
      //����ԭʼ���ݼ�    
      ks.setDataSet(dataSet);    
      //ִ���㷨    
      ks.kmeans();  
      //�õ�������    
      ArrayList<ArrayList<float[]>> cluster=ks.getCluster();    
      //�鿴���    
      for(int i=0;i<cluster.size();i++)    
      {   
    	  System.out.println("�����ĵ�"+i+"����Ϊ��");
          ks.printDataArray(cluster.get(i));    
      }      
  }    
}
