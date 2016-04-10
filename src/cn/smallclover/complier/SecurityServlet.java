package cn.smallclover.complier;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * @author smallclover
 *
 */

@WebServlet("/test.action")
public class SecurityServlet extends HttpServlet{
	public Compiler comp = null;
	public Compiler compi = null;
	BufferedWriter bw = null;
	String className = null;
	String classStr = null;
	String result = null;	
	String realpathdir = null;
	String batPathdir = null;
	
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
//			result = new Complier().ComplierCode(className, realpathdir);
			System.out.println("开始编译");
			result = new ComplierVer2().ComplierCode(className, batPathdir,realpathdir);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(result);
	}
	
	private boolean validate(String code) throws IOException{
		String classStr = null;
		String[] classStrArray = null;
		classStr = code.substring(code.indexOf("public class"), code.indexOf("{"));
		classStrArray = classStr.split("\\s{1,}");
		className = classStrArray[classStrArray.length - 1];
		System.out.println(className);
		
		File sourceFile = new File(realpathdir,className+".java");
		if(sourceFile.exists()){
			sourceFile.delete();
		}
        FileWriter fr = new FileWriter(sourceFile);
        bw = new BufferedWriter(fr);
        bw.write(code);
        bw.close();
        fr.close();
		return true;
	}
	
}