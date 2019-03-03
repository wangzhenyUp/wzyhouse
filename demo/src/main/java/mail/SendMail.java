package mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SendMail {
	public static String[][] txt2String(File file){
        List <String> list = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	if(s.length()>1)//
            	list.add(s);
            }
    		
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        String [] array = new String[4];
		String [][] str = new String[list.size()][];
		for(int i = 0;i<list.size()-1;i++){
			array = list.get(i+1).split(",");
			for(int j=0;j<array.length;j++){
				System.out.println(array[j]);
				str[i][j] = array[j];
			}
		}
        return str;
    }
    
    public static void main(String[] args){
        File file = new File("C:/Users/pc-1/Desktop/employee.txt");
        String [][] str = txt2String(file);
        if(str!=null)
        	System.out.println(str.toString());
    }
}
