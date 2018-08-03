package DBSCAN;

import java.io.IOException;
import java.util.*;
 
 public class Dbscan {
   private final static int e=2;//ε半径
   private final static int minp=4;//密度阈值
   private static List<Point> pointsList=new ArrayList<Point>();//存储原始样本点
   private static List<List<Point>> resultList=new ArrayList<>();//存储最后的聚类结果
   
   private static void applyDbscan() throws IOException{
     pointsList=Utility.getPointsList();//将数据集的原始样本点存入集合中
     for(int index=0;index<pointsList.size();++index){
       List<Point> tmpLst=new ArrayList<Point>();
       Point p=pointsList.get(index);
       if(p.isClassed())
         continue;
       tmpLst=Utility.isKeyPoint(pointsList, p, e, minp);
       if(tmpLst!=null){
         resultList.add(tmpLst);   //找出数据集中是核心对象的点，并把各核心对象对应的r邻域内的点的集合加入发哦resultList集合中
      }
     }
     int length=resultList.size();
    for(int i=0;i<length;++i){
       for(int j=0;j<length;++j){
         if(i!=j){
           if(Utility.mergeList(resultList.get(i), resultList.get(j))){
             resultList.get(j).clear();
           }
         }
       }
     }
   }
   public static void main(String[] args) {
	   
     try {
       //调用DBSCAN的实现算法
      applyDbscan();
       Utility.display(resultList);
    } catch (IOException e) {
    	
       e.printStackTrace();
    }
    
  }
 
 }