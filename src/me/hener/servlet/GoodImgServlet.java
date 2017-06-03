package me.hener.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import me.hener.dao.UserDAO;
@WebServlet("/GoodImgServlet")
public class GoodImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String servletPath = request.getServletPath();
		String methodName = servletPath;
		
		methodName = methodName.split("\\.")[0].split("/")[1];
		
		Method method;
		try {
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			response.setHeader("Content-type", "text/html;charset=UTF-8");

			response.setCharacterEncoding("UTF-8");

			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将 四张图片存到指定目录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void saveGoodImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String img = request.getParameter("img");
		String time = request.getParameter("time");
		img=img.substring(23);
		time = time.substring(time.length()-6, time.length());
		String fileName = time+".jpg";
		
		int userid = (int)request.getSession().getAttribute("userid");
		String floder = "C:/img/goodimg/" + userid;
		//最终目录
		File dir = new File(floder);
		if(!dir.exists()){
			dir.mkdirs();
		}
		byte[] imgByte = Base64.getDecoder().decode(img);
		BufferedOutputStream bufferedOutputStream = null;
		FileOutputStream fileOutputStream = null;
		File file = new File(floder+"/"+fileName);
		fileOutputStream = new FileOutputStream(file);  
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);  
        bufferedOutputStream.write(imgByte);
        bufferedOutputStream.close();
        fileOutputStream.close();
        JSONObject json = new JSONObject();
        String src = "http://hener.me/img/goodimg/"+userid+"/"+fileName;
        json.put("src", src);
        json.put("result", true); 
        response.getWriter().write(json.toString());
	}
	/**
	 * 将 tbl 存到 指定目录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void saveGoodTbl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String img = request.getParameter("img");
		String time = request.getParameter("time");
		
		img=img.substring(23);
		time = time.substring(time.length()-6, time.length());
		String fileName = time+".jpg";
		int userid = (int)request.getSession().getAttribute("userid");
		String tblFloder = "C:/img/tbl/" + userid;
		//最终目录
		File dir = new File(tblFloder);
		if(!dir.exists()){
			dir.mkdirs();
		}
		byte[] tblByte = Base64.getDecoder().decode(img);
		BufferedOutputStream bufferedOutputStream = null;
		FileOutputStream fileOutputStream = null;
		File file = new File(tblFloder+"/"+fileName);
		fileOutputStream = new FileOutputStream(file);  
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);  
        bufferedOutputStream.write(tblByte);
        bufferedOutputStream.close();
        fileOutputStream.close();

        
        
        JSONObject json = new JSONObject();
        String src = "http://hener.me/img/tbl/"+userid+"/"+fileName;
        json.put("src", src);
        json.put("result", true); 
        response.getWriter().write(json.toString());
	}
	/**
	 * 同时删除  tbl 和 其他img
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void removeFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String src = request.getParameter("src");
		String[] srcs = src.split("/");
		String userid = srcs[5];
		String fileName = srcs[6];
		String tblPath = "C:/img/tbl/" + userid+"/"+fileName;
		String goodImgPath = "C:/img/goodimg/" + userid+"/"+fileName;      
		File file = new File(tblPath);   
		if (file.isFile() && file.exists()) {  
			file.delete();  
		}  
		file = new File(goodImgPath);
		if (file.isFile() && file.exists()) {  
			file.delete();  
		}
	}

	public void saveAvatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String img = request.getParameter("img");
		
		img=img.substring(22);
		int userid = (int)request.getSession().getAttribute("userid");
		String fileName = userid+".jpg";
		UserDAO udao = new UserDAO();
		udao.updateAvatar(userid, fileName);
		String avatarFloder = "C:/img/avatar/";
		//最终目录
		File dir = new File(avatarFloder);
		if(!dir.exists()){
			dir.mkdirs();
		}
		byte[] tblByte = Base64.getDecoder().decode(img);
		BufferedOutputStream bufferedOutputStream = null;
		FileOutputStream fileOutputStream = null;
		File file = new File(avatarFloder+"/"+fileName);
		fileOutputStream = new FileOutputStream(file);  
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);  
        bufferedOutputStream.write(tblByte);
        bufferedOutputStream.close();
        fileOutputStream.close();

        
        
        JSONObject json = new JSONObject();
        json.put("result", true); 
        response.getWriter().write(json.toString());
	}
}
