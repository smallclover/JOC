package cn.smallclover.complier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;

public class Complier {
	private Process process = null;
	private BufferedWriter bw = null;
	private StringBuffer sb = null;
	private StringBuffer sbResult = null;
	public String ComplierCode(String className, String path) throws IOException, InterruptedException{
		Runtime runtime = Runtime.getRuntime();
		
		process = runtime.exec("cmd");
		
		Thread.sleep(1*1000);
		//往控制台注入命令
		bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		bw.write("c: \n");
		bw.write("cd " + path+" \n");
		bw.write("javac " + className + ".java\n");
		bw.flush();
		bw.write("java " + className + " \n");
		bw.close();
        SequenceInputStream sis = new SequenceInputStream (process.getInputStream (), process.getErrorStream ());
        InputStreamReader isr = new InputStreamReader (sis, "gbk");
        BufferedReader br = new BufferedReader (isr);
        
        
        String content = null;
        sb = new StringBuffer();
        sbResult = new StringBuffer();
        
            while((content = br.readLine()) != null){
            	sb.append(content+"\n");
            }
            System.out.println(sb);
            String str2 = sb.toString();
            String [] resultValue= sb.toString().split("\n{1,}");
            if(str2.indexOf("Exception in thread 'main'") < 0){
            	for(int i = 0; i < resultValue.length; i++){
            		if(i <= 5 ||(i == resultValue.length - 1)){
            			continue;
            		}
            		sbResult.append(resultValue[i]+"\n");
            	}                   	
            	return sbResult.toString();
            }
            if(str2.indexOf("Exception in thread 'main'") > 0){
            	return str2.substring(str2.indexOf("Exception"));
            }
		return "";
	}
}
