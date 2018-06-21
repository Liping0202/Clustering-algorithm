package Kmeans;

import java.util.ArrayList;    
import java.util.Random;    
     
public class kmeans {    
    private int k;// 分成多少簇    
    private int m;// 迭代次数    
    private int dataSetLength;// 数据集元素个数，即数据集的长度    
    private ArrayList<float[]> dataSet;// 数据集链表    ArrayList集合中元素即float[]是一个点的坐标。
    private ArrayList<float[]> center;// 中心链表    
    private ArrayList<ArrayList<float[]>> cluster; // 簇    
    private ArrayList<Float> jc;// 误差平方和，k越接近dataSetLength，误差越小    
    private Random random;
    
    public kmeans(int k) {    
        //传入需要分成的簇数量  
        if (k <= 0) {    
            k = 1;    
        }    
        this.k = k;    
    }
     
    public void setDataSet(ArrayList<float[]> dataSet) {    
        //设置需分组的原始数据集  
        this.dataSet = dataSet;    
    } 
    
    private ArrayList<float[]> initCenters() {    
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
    }    
    
   
    private ArrayList<ArrayList<float[]>> initCluster() {    
        //初始化簇集合  
        ArrayList<ArrayList<float[]>> cluster = new ArrayList<ArrayList<float[]>>();    
        for (int i = 0; i < k; i++) {    
            cluster.add(new ArrayList<float[]>());    
        } //初始化簇，  产生k个簇，每个簇初始化是空的 
    
        return cluster;    
    } 

    private void init() {   
        //初始化  
        m = 0;//开始时迭代次数为0    
        random = new Random();    
        if (dataSet == null || dataSet.size() == 0) {     
            System.out.println("数据为空，请输入数据！！！！");  
        } else{  
            dataSetLength = dataSet.size();    
            if (k > dataSetLength) {    
                k = dataSetLength;    
            }    
            center = initCenters();    
            cluster = initCluster();    
            jc = new ArrayList<Float>();  //初始化误差平方和  
            }  
    }       
    
    private void clusterSet() {    
        //将当前元素放到最小距离中心相关的簇中  
        float[] distance = new float[k];  //原始数据集合中的一个点到每个k中心点的距离形成的数组  
        for (int i = 0; i < dataSetLength; i++) {    
            for (int j = 0; j < k; j++) {    
                distance[j] = distance(dataSet.get(i), center.get(j));    
            }    
            int minLocation = minDistance(distance);    
            cluster.get(minLocation).add(dataSet.get(i));  //将原始数据集合中一个点（i对应的点）添加到最小位置对应的簇中  
    
        }    
    }
    private float distance(float[] element, float[] center) {    
        //计算两个点之间的距离  
        float distance = 0.0f;    
        float x = element[0] - center[0];    
        float y = element[1] - center[1];    
        float z = x * x + y * y;    
        distance = (float) Math.sqrt(z);    
    
        return distance;    
    } 
    private int minDistance(float[] distance) {    
        //获取距离集合中最小距离的位置  
       float minDistance = distance[0];    
       int minLocation = 0;    
       for (int i = 1; i < distance.length; i++) {    
           if (distance[i] < minDistance) {    
               minDistance = distance[i];    
               minLocation = i;    
           } else if (distance[i] == minDistance) // 如果相等，随机返回一个位置    
           {    
               if (random.nextInt(10) < 5) {    
                   minLocation = i;    
               }    
           }    
       }    
   
       return minLocation;    
   } 
    
    private void countRule() {    
        //计算误差平方和准则函数方法  
        float jcF = 0;    
        for (int i = 0; i < cluster.size(); i++) {  //一共多少个簇  
            for (int j = 0; j < cluster.get(i).size(); j++) {  //每个簇中一共有多少个元素  
                jcF += errorSquare(cluster.get(i).get(j), center.get(i));//每个簇中元素到数据中心点的误差和    
            } //整个簇所有元素的误差平方和   
        }    
        jc.add(jcF);   //每一次迭代得到的误差平方和放入jc集合中，jc集合中一共m个元素 
    } 
     
    private float errorSquare(float[] element, float[] center) {  
        //求两点误差平方的方法   
        float x = element[0] - center[0];    
        float y = element[1] - center[1];    
    
        float errSquare = x * x + y * y;    
    
        return errSquare;    
    }    
    
    
   
    private void setNewCenter() {   
        //设置新的簇中心方法  
        for (int i = 0; i < k; i++) {    
            int n = cluster.get(i).size();    
            if (n != 0) {    
                float[] newCenter = { 0, 0 };    
                for (int j = 0; j < n; j++) {    
                    newCenter[0] += cluster.get(i).get(j)[0];    
                    newCenter[1] += cluster.get(i).get(j)[1];    
                }    
                // 设置一个平均值    
                newCenter[0] = newCenter[0] / n;    
                newCenter[1] = newCenter[1] / n;    
                center.set(i, newCenter); //Replaces the element at the specified position in this list with the specified element   
            }    
        }    
    }       
     
    public void kmeans() {    
        init();    
        // 循环分组，直到误差不变为止    
        while (true) {    
            clusterSet();    
            countRule();     
            // 误差不变了，分组完成    
            if (m != 0) {    
                if (jc.get(m) - jc.get(m - 1) == 0) {    
                    break;    
                }    
            }    
    
            setNewCenter();     
            m++;    
            cluster.clear();    
            cluster = initCluster();    
        }  
    } 
    public ArrayList<ArrayList<float[]>> getCluster() {    
        return cluster;    
    }
    public void printDataArray(ArrayList<float[]> dataArray) {   
        //打印数据  
        for (int i = 0; i < dataArray.size(); i++) {    
            System.out.println("簇中第"+i+"个元素为：(" + dataArray.get(i)[0] + "," + dataArray.get(i)[1]+")");    
        }    
        System.out.println("===================================");    
    } 
} 
