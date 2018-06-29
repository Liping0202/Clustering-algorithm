package BisectingKmeans;

import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList;  
  
public class ReadDataTest {  
    //���ļ��ж�ȡ����  
public ArrayList<float[]> read(String fileName){  
    ArrayList<float[]> arr=new ArrayList<float[]>();  
    try {  
        BufferedReader reader = new BufferedReader(new FileReader(fileName));  
        String line = null;  
        while((line=reader.readLine())!=null){  
            String str[] = line.split("\\s+");  
            //������ʽ  ��//s+�� /sƥ�����еĿհ��ַ��������ո��Ʊ�����س��������з�  +ָ��ǰ����ӱ��ʽ����һ�λ��߶��
            float[] point1 = new float[2];  
            point1[0]=Float.parseFloat(str[0].trim());  
            point1[1]=Float.parseFloat(str[1].trim());  
            arr.add(point1);
        }  
    }catch (FileNotFoundException e) {  
        e.printStackTrace();  
    }catch (IOException e) {  
        e.printStackTrace();  
    }       
    return arr;        
    }  
}