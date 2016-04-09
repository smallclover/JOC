package cn.smallclover.complier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test.action")
public class SecurityServlet extends HttpServlet{
	public Compiler comp = null;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String code = req.getParameter("code");
		req.setCharacterEncoding("GBK");
		resp.setContentType("text/html;charset=GBK");
		System.out.println(code);
		PrintWriter out = resp.getWriter();
		
		BufferedWriter bw = null;
		String className = null;//所编译文件的主类类名，即类文件的名字
		String classStr = null;//获取字符串public class className{
		String[] classStrArray = null;//将字符串根据空格切分成数组
		String result = null;
		classStr = code.substring(code.indexOf("public class"), code.indexOf("{"));
		classStrArray = classStr.split("\\s{1,}");//通过正则表达式来分割字符串，\s表示空格，{1,}表示匹配至少一个
		className = classStrArray[classStrArray.length - 1];
		System.out.println(className);
		
		
		String realpathdir = req.getSession().getServletContext()  
			     .getRealPath("/temp"); //获取文件存储的真实路径，这里用temp文件夹存储临时编译所用的文件夹
		System.out.println(realpathdir);
		
		File sourceFile = new File(realpathdir,className+".java");
		//如果文件存在
		if(sourceFile.exists()){
			sourceFile.delete();
		}
		FileWriter fr = new FileWriter(sourceFile);
		bw = new BufferedWriter(fr);
		bw.write(code);
		bw.close();
		fr.close();
		try {
			result = new Complier().ComplierCode(className, realpathdir);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		out.println(result);
	}
}
