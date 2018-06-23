package Kmedoids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  

public class TestMain {
    public static void main (String args[]){
        ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();      
        dataPoints=new ReadDataM().read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\Kmedoids\\\\data2.txt");
        System.out.println("输入将簇分成的类数k为：");              
        int K=(new Scanner(System.in)).nextInt();
        
        int iter=0;
        int dimNum=2;
        ClusterAnalysis ca=new ClusterAnalysis(K,iter,dataPoints,dimNum);//做一些初始化，初始化簇名，中心点为null,每个簇的数据为null 
        
        double[][] initCenters=new double[K][dimNum];        
        Random ran = new Random();
        int[] randoms = new int[K];    
        boolean flag; 
        int length=dataPoints.size();
        int temp = ran.nextInt(length);    
        randoms[0] = temp;  
        //整个循环是为了保证生成的随机数不相同，即得到的中心点不重合
        for (int i = 1; i < K; i++) {    
                flag = true;    
                while (flag) {    
                    temp = ran.nextInt(length);    
                    int j = 0;    
                    while (j < i) {    
                        if (temp == randoms[j]) {    
                            break;    
                        }    
                        j++;    
                    }    
                    if (j == i) {    
                        flag = false;    
                    }    
                }    
                randoms[i] = temp;    
            }     
            for (int i = 0; i < K; i++) {    
            	initCenters[i]=(dataPoints.get(randoms[i])).getDimensioin();  
            }    
       
       ca.startAnalysis(initCenters);//算法运行的关键环节

       ArrayList<DataPoint>[] v = ca.getClusterOutput();//仅仅起到一个输出作用
        for (int ii=0; ii<v.length; ii++){
            ArrayList tempV = v[ii];
            System.out.println("-----------Cluster"+ii+"---------");
            Iterator Iter = tempV.iterator();
            while(Iter.hasNext()){
                DataPoint dpTemp = (DataPoint)Iter.next(); 
                double[] dp=dpTemp.getDimensioin();
                System.out.print("("+dp[0]+","+dp[1]+")");
            }
            System.out.println();
        }
    }
}
  
class ReadDataM {  
public ArrayList<DataPoint> read(String fileName){  
    ArrayList<DataPoint> arr=new ArrayList<>();  
    try {  
        BufferedReader reader = new BufferedReader(new FileReader(fileName));  
        String line = null;  
        while((line=reader.readLine())!=null){  
            String str[] = line.split("\\s+");  
            double[] dimension=new double[2];  
            dimension[0]=Double.parseDouble(str[0].trim());  
            dimension[1]=Double.parseDouble(str[1].trim());  
            arr.add(new DataPoint(dimension));
        }  
    }catch (FileNotFoundException e) {  
        e.printStackTrace();  
    }catch (IOException e) {  
        e.printStackTrace();  
    }       
    return arr;       
    }  
}

