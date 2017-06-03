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

import org.json.JSONObject;

import me.hener.dao.NeedDAO;
import me.hener.dao.UserDAO;
import me.hener.dto.HomeNeedResult;
import me.hener.dto.MyNeedItem;
import me.hener.dto.NeedContact;
import me.hener.dto.PhoneMsg;
import me.hener.dto.PhoneQQ;
import me.hener.dto.SaveNeed;
import me.hener.util.SendMsg;

@WebServlet("/NeedServlet")
public class NeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
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
	
	public void save (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject(request.getParameter("json"));
		json.put("userid", (int)request.getSession().getAttribute("userid"));
		SaveNeed sn = new SaveNeed(json);
		NeedDAO ndao = new NeedDAO();
		json.put("success", ndao.saveNeed(sn));
		response.getWriter().write(json.toString());
	}
	public void modifyNeed (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject(request.getParameter("json"));
		json.put("userid", (int)request.getSession().getAttribute("userid"));
		SaveNeed sn = new SaveNeed(json);
		sn.setNeedid(json.getInt("needid"));
		NeedDAO ndao = new NeedDAO();
		json.put("success", ndao.modifyNeed(sn));
		response.getWriter().write(json.toString());
	}
	public void getNeeds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		NeedDAO ndao = new NeedDAO();
		HomeNeedResult result = ndao.getNeed(pageSize, pageNo);
		result.setPageNo(pageNo);
		result.setPageSize(pageSize);
		response.getWriter().write(result.toJSON().toString());
	}
	public void getNeedContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taruserid = Integer.parseInt(request.getParameter("userid"));
		int srcuserid = (int)request.getSession().getAttribute("userid");
		int needid = Integer.parseInt(request.getParameter("needid"));
		NeedDAO ndao = new NeedDAO();
		List<Integer> banList = ndao.getBanList(needid);
		if(banList.contains(srcuserid)){
			response.getWriter().write(new JSONObject().put("ban", true).toString());
			return;
		}
		NeedContact nc = new NeedContact();
		
		UserDAO udao = new UserDAO();
		
		nc = ndao.getNeedContact(taruserid,needid);
		
		if(nc.isLocked()){
			if(nc.getLockeduserid() == srcuserid){
				if((!nc.isSeeQq()||nc.getQq().equals(""))&&!nc.isSeePhone()){
					nc.setQq("");
					nc.setPhone("");
					response.getWriter().write(nc.toJson().put("ilocked",true).toString());
					return;
				}else{
					if(!nc.isSeeQq()||nc.getQq().equals("")){
						nc.setQq("");
					}
					if(!nc.isSeePhone()){
						nc.setPhone("");
					}
					response.getWriter().write(nc.toJson().put("ilocked",true).toString());
					return;
				}
			}else{
				response.getWriter().write(new JSONObject().put("otherlocked", true).toString());
				return ;
			}
		}else{
			if((!nc.isSeeQq()||nc.getQq().equals(""))&&!nc.isSeePhone()){
				nc.setQq("");
				nc.setPhone("");
				response.getWriter().write(nc.toJson().toString());
				return;
			}else{
				if(!nc.isSeeQq()||nc.getQq().equals("")){
					nc.setQq("");
				}
				if(!nc.isSeePhone()){
					nc.setPhone("");
				}
				response.getWriter().write(nc.toJson().toString());
				
				//发送消息
				PhoneMsg pmsg = udao.getPhoneMsg(taruserid, srcuserid);
				pmsg.setMsgtype("goodmsg");
				pmsg.setTarobjid(needid);
				pmsg.setInfo(ndao.getInfo(needid));
				
				pmsg.setMsgcontent("我锁定了你的需求");
				pmsg.setTaruserid(taruserid);
				pmsg.setSrcuserid(srcuserid);
				SendMsg.sendLockedMsg(pmsg.getTarphone());
				ndao.addNeedMsg(pmsg);
				ndao.lockNeed(needid, srcuserid);
			}
		}
		
	}
	public void sendMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taruserid = Integer.parseInt(request.getParameter("taruserid"));
		int srcuserid = (int)request.getSession().getAttribute("userid");
		int needid = Integer.parseInt(request.getParameter("needid"));
		String msgcontent = request.getParameter("msgcontent");
		UserDAO udao = new UserDAO();
		NeedDAO ndao = new NeedDAO();
		PhoneMsg pmsg = udao.getPhoneMsg(taruserid, srcuserid);
		pmsg.setMsgtype("needcontentmsg");
		pmsg.setInfo(ndao.getInfo(needid));
		pmsg.setMsgcontent(msgcontent);
		pmsg.setTaruserid(taruserid);
		pmsg.setSrcuserid(srcuserid);
		pmsg.setTarobjid(needid);
		
		NeedContact nc = new NeedContact();
		nc = ndao.getNeedContact(taruserid,needid);
		if(!nc.isLocked()||(nc.getLockeduserid() != srcuserid&&nc.isLocked())){
			SendMsg.sendLockedMsg(pmsg.getTarphone());
		}
		ndao.addNeedMsg(pmsg);
		ndao.lockNeed(needid, srcuserid);
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
	public void listMyNeed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		NeedDAO ndao = new NeedDAO();
		LinkedList<MyNeedItem> list = ndao.getMyNeedItem(userid);
		if(list==null){
			response.getWriter().write(new JSONObject().put("success", false).toString());
			return;
		}
		for(MyNeedItem n : list){
			if(n.isLocked()){
				ndao.getLockedUser(n);
			}
		}
		JSONObject json = new JSONObject().put("list", list);
		response.getWriter().write(json.toString());
		
		
	}
	public void listMyLockNeed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		NeedDAO ndao = new NeedDAO();
		LinkedList<MyNeedItem> list = ndao.getMylockNeedItem(userid);
		if(list==null){
			response.getWriter().write(new JSONObject().put("success", false).toString());
			return;
		}
		JSONObject json = new JSONObject().put("list", list);
		response.getWriter().write(json.toString());
		
		
	}
	public void removeNeed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		if(userid==0){
			return;
		}
		int needid = Integer.parseInt(request.getParameter("needid"));
		NeedDAO ndao = new NeedDAO();
		ndao.removeNeed(needid);
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
	public void getNeedDetai(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int needid = Integer.parseInt(request.getParameter("needid"));
		NeedDAO ndao = new NeedDAO();
		SaveNeed sn = ndao.getNeedDetail(needid);
		
		if(sn==null){
			return;
		}
		sn.setNeedid(needid);
		response.getWriter().write(new JSONObject(sn).toString());
	}
	public void getPQ(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int selfid = (int)request.getSession().getAttribute("userid");
		if(selfid==0){
			return;
		}
		int userid = Integer.parseInt(request.getParameter("lockeduserid"));
		int needid = Integer.parseInt(request.getParameter("needid"));
		NeedDAO ndao = new NeedDAO();
		PhoneQQ	pq = ndao.getPQ(userid, needid);
		response.getWriter().write(new JSONObject(pq).toString());
		
		
	}
	public void clearLock(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int needid = Integer.parseInt(request.getParameter("needid"));
		NeedDAO ndao = new NeedDAO();
		ndao.clearLock(needid);
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
	public void banLock(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int needid = Integer.parseInt(request.getParameter("needid"));
		int lockeduserid = Integer.parseInt(request.getParameter("lockeduserid"));
		
		NeedDAO ndao = new NeedDAO();
		ndao.banLock(lockeduserid,needid);
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
}
