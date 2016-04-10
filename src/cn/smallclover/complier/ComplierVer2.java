package cn.smallclover.complier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;

public class ComplierVer2 {
	private Process process = null;
	private BufferedWriter bw = null;
	private StringBuffer sb = null;
    private String content = null;
	
	public String ComplierCode(String className, String batPath, String filePath) throws IOException, InterruptedException{
		Runtime runtime = Runtime.getRuntime();
		
		process = runtime.exec(batPath+"/build.bat");
		
		Thread.sleep(1*1000);
		
		bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		bw.write(filePath);
		bw.flush();
		bw.write(className);
		bw.flush();
		bw.close();
        SequenceInputStream sis = new SequenceInputStream (process.getInputStream (), process.getErrorStream ());
        InputStreamReader isr = new InputStreamReader (sis, "gbk");
        BufferedReader br = new BufferedReader (isr);
        

        sb = new StringBuffer();
        while((content = br.readLine()) != null){
        	sb.append(content+"\n");
        }
        System.out.println(sb);
        content = sb.toString();
		return content;
	}
}