package LOF;

import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList; 
  
public class ReadPoint {  
    //从文件中读取数据  
public ArrayList<DataNode> read(String fileName){  
    ArrayList<DataNode> arr=new ArrayList<>();  
    try {  
        BufferedReader reader = new BufferedReader(new FileReader(fileName));  
        String line = null; 
        int i=0;
        while((line=reader.readLine())!=null){ 
        	i++;
            String str[] = line.split("\\s+");  
            double[] dimension=new double[2];  
            dimension[0]=Double.parseDouble(str[0].trim());  
            dimension[1]=Double.parseDouble(str[1].trim());  
            arr.add(new DataNode(String.valueOf((char)(96+i)),dimension));
        }  
    }catch (FileNotFoundException e) {  
        e.printStackTrace();  
    }catch (IOException e) {  
        e.printStackTrace();  
    }       
    return arr;        
    }  
}

