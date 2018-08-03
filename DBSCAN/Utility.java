package DBSCAN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Utility {
   //计算两点之间的距离
   public static double getDistance(Point p,Point q){
     int dx=p.getX()-q.getX();
     int dy=p.getY()-q.getY();
     double distance=Math.sqrt(dx*dx+dy*dy);
     return distance;
   }
   
   
   //检测p点是不是核心点，tmpLst存储核心点的直达点
   public static List<Point> isKeyPoint(List<Point> lst,Point p,int e,int minp){
     int count=0;
     List<Point> tmpLst=new ArrayList<Point>();
     for(Iterator<Point> it=lst.iterator();it.hasNext();){
       Point q=it.next();
       if(getDistance(p,q)<=e){
        ++count;
         if(!tmpLst.contains(q)){
           tmpLst.add(q);//tmpLst中存储的是点p的r邻域内的数据点
        }
       }
     }
     if(count>=minp){
      p.setKey(true);
      return tmpLst;
     }
     return null;
   }
   
   
   //合并两个链表，前提是b中的核心点包含在a中
   public static boolean mergeList(List<Point> a,List<Point> b){
     boolean merge=false;
     if(a==null || b==null){
       return false;
     }
     for(int index=0;index<b.size();++index){
      Point p=b.get(index);
      if(p.isKey() && a.contains(p)){
         merge=true;
         break;
       }
     }
    if(merge){
       for(int index=0;index<b.size();++index){
         if(!a.contains(b.get(index))){
           a.add(b.get(index));
        }
       }
     }
     return merge;
   }
   
   
   //获取文本中的样本点集合
  public static List<Point> getPointsList() throws IOException{
     List<Point> lst=new ArrayList<Point>();
     String txtPath="F:\\\\.eclipse\\\\JAVA\\\\src\\\\DBSCAN\\\\dataDbscan.txt";
    BufferedReader br=new BufferedReader(new FileReader(txtPath));
     String str="";
     while((str=br.readLine())!=null && str!=""){
       lst.add(new Point(str));
     }
     br.close();
     return lst;
   }
   //显示聚类的结果
   public static void display(List<List<Point>> resultList){
     int index=1;
     for(Iterator<List<Point>> it=resultList.iterator();it.hasNext();){
      List<Point> lst=it.next();
       if(lst.isEmpty()){
       continue;
       }
       System.out.println("-----第"+index+"个聚类-----");
       for(Iterator<Point> it1=lst.iterator();it1.hasNext();){
         Point p=it1.next();
         System.out.println(p.print());
       }
       index++;
     }
  }
}
