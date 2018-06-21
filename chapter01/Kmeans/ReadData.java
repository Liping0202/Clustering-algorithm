package Kmeans;

import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList;  
  
public class ReadData {  
    //从文件中读取数据  
public ArrayList<float[]> read(String fileName){  
    ArrayList<float[]> arr=new ArrayList<float[]>();  
    try {  
        BufferedReader reader = new BufferedReader(new FileReader(fileName));  
        String line = null;  
        while((line=reader.readLine())!=null){  
            String str[] = line.split("\\s+");  
            //正则表达式  “//s+” /s匹配所有的空白字符，包括空格、制表符、回车符、换行符  +指定前面的子表达式出现一次或者多次
            float[][] point1 = new float[1][2];  
            point1[0][0]=Float.parseFloat(str[0].trim());  
            point1[0][1]=Float.parseFloat(str[1].trim());  
            arr.add(point1[0]);
            /*float[] point=new float[2];
            point[0]=Float.parseFloat(str[0].trim());
            point[1]=Float.parseFloat(str[1].trim());
            arr.add(point);*/
        }  
    }catch (FileNotFoundException e) {  
        e.printStackTrace();  
    }catch (IOException e) {  
        e.printStackTrace();  
    }  
          
    return arr;  
          
    }  
}
