package me.hener.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import me.hener.dao.GoodDAO;
import me.hener.dao.NeedDAO;
import me.hener.dao.UserDAO;
import me.hener.dto.MemberInfo;
import me.hener.dto.MyMsg;
import me.hener.dto.SignedInfo;
import me.hener.dto.UserModifyInfo;
import me.hener.util.Code;
import me.hener.util.CookieUtil;


@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
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
	public void sign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone = request.getParameter("phone");
		String pwd = request.getParameter("pwd");
		
		UserDAO udao = new UserDAO();
		SignedInfo si = udao.getSignInfo(phone, pwd);
		if(si==null){
			si = new SignedInfo();
			si.setSuccess(false);
			response.getWriter().write(si.toJson().toString());
			return;
		}
		si.setSuccess(true);
		si.setMsgCount(udao.getGoodMsgCount(si));
		si.setSysMsgCount(udao.getNeedMsgCount(si));
		
		HttpSession session = request.getSession();
		session.setAttribute("userid", si.getUserid());
		CookieUtil.addCookie(response, "phone", phone);
		CookieUtil.addCookie(response, "pwd", pwd);
		response.getWriter().write(si.toJson().toString());
		
	}
	public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone  = request.getParameter("phone");
		String pwd = request.getParameter("pwd");
		UserDAO udao = new UserDAO();
		boolean success = udao.saveUser(phone, pwd);
		JSONObject json = new JSONObject();
		json.put("success", success);
		response.getWriter().write(json.toString());
	}
	public void registed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone  = request.getParameter("phone");
		UserDAO udao = new UserDAO();
		boolean registed = udao.registed(phone);
		JSONObject json = new JSONObject();
		json.put("registed", registed);
		response.getWriter().write(json.toString());
	}
	public void changeName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	public void getMemberInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		UserDAO udao = new UserDAO();
		MemberInfo mi = udao.getMemberInfo(userid);
		if(mi == null){
			JSONObject json = new JSONObject();
			json.put("success", false);
			response.getWriter().write(json.toString());
			return;
		}
		mi.setUserid(userid);
		response.getWriter().write(mi.toJson().toString());
		
		
	}
	public void getSignedInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		UserDAO udao = new UserDAO();
		SignedInfo si = udao.getSignInfo(userid);
		if(si==null){
			si = new SignedInfo();
			si.setSuccess(false);
			response.getWriter().write(si.toJson().toString());
			return;
		}
		si.setSuccess(true);
		si.setMsgCount(udao.getGoodMsgCount(si));
		si.setSysMsgCount(udao.getNeedMsgCount(si));
		response.getWriter().write(si.toJson().toString());
		
	}
	public void isSigned(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession(false);
		JSONObject json = new JSONObject();
		if(session==null||session.getAttribute("userid")==null){
			json.put("success", false);
		}else{
			json.put("success", true);
		}
		response.getWriter().write(json.toString());
	}
	public void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		HttpSession session = request.getSession(false);
		if(session != null){
			session.removeAttribute("userid");
		}
		CookieUtil.removeCookie(response, "phone");
		CookieUtil.removeCookie(response, "pwd");
		response.sendRedirect("../home.jsp");
		
	}
	
	public void modifiUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		String info  = request.getParameter("info");
		JSONObject json = new JSONObject();
		UserDAO udao = new UserDAO();
		boolean success = udao.modifiUserInfo(userid, info);
		json.put("success", success);
		response.getWriter().write(json.toString());
	}

	public void getUserModifyInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		UserDAO udao = new UserDAO();
		UserModifyInfo u = udao.getUserModifyInfo(userid);
		if(u != null){
			response.getWriter().write(u.toJson().put("success", true).toString());
		}else{
			response.getWriter().write(new JSONObject().put("success", false).toString());
		}
		
	}
	public void saveExtraInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		String realname = request.getParameter("realname");
		
		String sex = request.getParameter("sex");
		String location = request.getParameter("location");
		UserDAO udao = new UserDAO();
		boolean b = udao.saveExtraInfo(qq, email, realname, sex, location, userid);
		response.getWriter().write(new JSONObject().put("success", b).toString());
	}
	public void setCansee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		String k = request.getParameter("k");
		boolean v = Boolean.parseBoolean(request.getParameter("v"));
		UserDAO udao = new UserDAO();
		boolean b = udao.setCansee(k, v, userid);
		response.getWriter().write(new JSONObject().put("success", b).toString());
	}
	public void setName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		String name = request.getParameter("name");
		UserDAO udao = new UserDAO();
		boolean b = udao.setName(name,userid);
		response.getWriter().write(new JSONObject().put("success", b).toString());
	}

	public void listMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int)(request.getSession().getAttribute("userid"));
		List<MyMsg> list = new LinkedList<MyMsg>();
		NeedDAO ndao = new NeedDAO();
		GoodDAO gdao = new GoodDAO();
		list = ndao.listMyNeedMsg(list, userid);
		list = gdao.listMyGoodMsg(list, userid);
		
		response.getWriter().write(new JSONObject().put("list", list).toString());
		
	}
	/**
	 * 这里强烈感觉用反射会很方便，目前还不知道用
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int msgid = Integer.parseInt(request.getParameter("msgid"));
		String msgtype = request.getParameter("msgtype");
		if(msgtype.equals("goodmsg")){
			GoodDAO gdao = new GoodDAO();
			gdao.deleteMsg(msgid);
		}else{
			NeedDAO ndao = new NeedDAO();
			ndao.deleteMsg(msgid);
		}
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
	public void msgRead(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("userid");
		
			GoodDAO gdao = new GoodDAO();
			gdao.msgReaad(userid);
		
			NeedDAO ndao = new NeedDAO();
			ndao.msgReaad(userid);
		
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
	public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String phone = request.getParameter("phone");
		UserDAO udao = new UserDAO();
		boolean registed = udao.registed(phone);
		
		if(!registed){
			JSONObject json = new JSONObject();
			json.put("registed", registed);
			response.getWriter().write(json.toString());
			return;
		}
		String code = Code.makeCode();
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		session.setAttribute("findPhone", phone);
		Code.sendCode(code, phone);
		response.getWriter().write(new JSONObject().put("success",true).put("registed", registed).toString());
	}
	public void setPwdByCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String phone = (String)request.getSession().getAttribute("findPhone");
		String code1 = (String)request.getSession().getAttribute("code");
		String code2 = request.getParameter("code");
		String pwd = request.getParameter("pwd");
		UserDAO udao = new UserDAO();
		if(code1.equals(code2)){
			if(udao.setPwd(phone,pwd)){
				response.getWriter().write(new JSONObject().put("success",true).toString());
			}else{
				response.getWriter().write(new JSONObject().put("success","exception").toString());
			}
		}else{
			response.getWriter().write(new JSONObject().put("success",false).toString());
		}
	}
	public void updatePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		UserDAO udao = new UserDAO();
		if(udao.pwdMatch(userid, oldpwd)){
			udao.setPwd(userid, newpwd);
			response.getWriter().write(new JSONObject().put("success", true).toString());
		}else{
			response.getWriter().write(new JSONObject().put("success", false).toString());
		}
	}
	
}
