package cn.smallclover.complier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 格式验证
 * @author smallclover
 *
 */

@WebServlet("/test.action")
public class SecurityServlet extends HttpServlet{
	private Compiler compi = null;
	private BufferedWriter bw = null;
	private String className = null;//文件名
	private String result = null;//编译运行的结果
	private String realpathdir = null;//代码临时存储的路径
	private String batPathdir = null;//编译的脚本所在地
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		String code = req.getParameter("code");
		req.setCharacterEncoding("GBK");
		resp.setContentType("text/html;charset=GBK");
		System.out.println(code);
		PrintWriter out = resp.getWriter();
		
		
		realpathdir = req.getSession().getServletContext()  
				.getRealPath("/temp"); 
		System.out.println(realpathdir);
		
		batPathdir = req.getSession().getServletContext()  
				.getRealPath("/bat");
		System.out.println(batPathdir);
		
		
		try {		
			validate(code);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			System.out.println("complier&running");
			result = new ComplierVer2().ComplierCode(className, batPathdir,realpathdir);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(result);
	}
	
	/**
	 * 
	 * @param code 客户端编写的代码
	 * @return 格式初步的验证如果正确返回true，不正确返回false
	 * @throws IOException
	 */
	private boolean validate(String code) throws IOException{
		String classStr = null;
		String[] classStrArray = null;
	
		
		classStr = code.substring(code.indexOf("public class"), code.indexOf("{"));
		classStrArray = classStr.split("\\s{1,}");
		className = classStrArray[classStrArray.length - 1];
		System.out.println(className);
		
		File sourceFileJava = new File(realpathdir,className+".java");
		File sourceFileClass = new File(realpathdir,className+".class");
		if(sourceFileJava.exists()){
			sourceFileJava.delete();
			sourceFileClass.delete();
		}
        FileWriter fr = new FileWriter(sourceFileJava);
        bw = new BufferedWriter(fr);
        bw.write(code);
        bw.close();
        fr.close();
		return true;
	}
	
	
	
}