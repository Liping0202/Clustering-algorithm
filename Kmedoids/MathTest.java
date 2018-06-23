package Kmedoids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  

public class MathTest {
    public static void main (String args[]){
        ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();      
        dataPoints=new ReadData().read("F:\\\\.eclipse\\\\JAVA\\\\src\\\\Kmedoids\\\\data2.txt");
        System.out.println("输入将簇分成的类数k为：");              
        int K=(new Scanner(System.in)).nextInt();
        int iter=0;
        int dimNum=2;
        ClusterAnalysis ca=new ClusterAnalysis(K,iter,dataPoints,dimNum);//做一些初始化，初始化簇名，中心点为null,每个簇的数据为null 
        /*private ArrayList<float[]> initCenters() {    
            //初始化中心数据链表，分成多少簇就有多少个中心点  
            ArrayList<float[]> center = new ArrayList<float[]>();    
            int[] randoms = new int[k];    
            boolean flag;    
            int temp = random.nextInt(dataSetLength); //Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and the specified value (exclusive),
            //drawn from this random number generator's sequence   
            randoms[0] = temp;  
            //整个循环是为了保证生成的随机数不相同，即得到的中心点不重合
            for (int i = 1; i < k; i++) {    
                flag = true;    
                while (flag) {    
                    temp = random.nextInt(dataSetLength);    
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
            for (int i = 0; i < k; i++) {    
                center.add(dataSet.get(randoms[i]));// 生成初始化中心链表    
            }    
            return center;    
        }*/
        
       double[][] center={{2,3},{2,4},{7,20}}; //随机选取了三个数据点，g,h,i作为簇的中心点
       
       ca.startAnalysis(center);//算法运行的关键环节

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
  
class ReadData {  
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

