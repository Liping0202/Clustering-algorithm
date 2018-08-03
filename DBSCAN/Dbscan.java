package DBSCAN;

import java.io.IOException;
import java.util.*;
 
 public class Dbscan {
   private final static int e=2;//�Ű뾶
   private final static int minp=4;//�ܶ���ֵ
   private static List<Point> pointsList=new ArrayList<Point>();//�洢ԭʼ������
   private static List<List<Point>> resultList=new ArrayList<>();//�洢���ľ�����
   
   private static void applyDbscan() throws IOException{
     pointsList=Utility.getPointsList();//�����ݼ���ԭʼ��������뼯����
     for(int index=0;index<pointsList.size();++index){
       List<Point> tmpLst=new ArrayList<Point>();
       Point p=pointsList.get(index);
       if(p.isClassed())
         continue;
       tmpLst=Utility.isKeyPoint(pointsList, p, e, minp);
       if(tmpLst!=null){
         resultList.add(tmpLst);   //�ҳ����ݼ����Ǻ��Ķ���ĵ㣬���Ѹ����Ķ����Ӧ��r�����ڵĵ�ļ��ϼ��뷢ŶresultList������
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
       //����DBSCAN��ʵ���㷨
      applyDbscan();
       Utility.display(resultList);
    } catch (IOException e) {
    	
       e.printStackTrace();
    }
    
  }
 
 }