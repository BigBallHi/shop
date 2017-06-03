package me.hener.servlet;

import java.io.File;
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

import me.hener.dao.GoodDAO;
import me.hener.dao.UserDAO;
import me.hener.dto.GoodContact;
import me.hener.dto.GoodDetail;
import me.hener.dto.HomeGoodResult;
import me.hener.dto.MyGoodItem;
import me.hener.dto.OtherGoodItem;
import me.hener.dto.PhoneMsg;
import me.hener.dto.PhoneQQ;
import me.hener.dto.Query;
import me.hener.dto.SaveGood;
import me.hener.manage.GoodManager;
import me.hener.util.SendMsg;


@WebServlet("/GoodServlet")
public class GoodServlet extends HttpServlet {
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
	
	public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject(request.getParameter("json"));
		json.put("userid", (int)request.getSession().getAttribute("userid"));
		SaveGood sg = new SaveGood(json);
		GoodDAO gdao = new GoodDAO();
		int goodid = gdao.saveGood(sg);
		if(goodid == 0){
			json.put("success", false);
			response.getWriter().write(json.toString());
			return;
		}
		//插入图片,标签
		sg.setGoodid(goodid);
		gdao.saveTags(sg);
		gdao.savePictures(sg);
		
		json.put("success", true);
		response.getWriter().write(json.toString());
	}
	public void updateGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject(request.getParameter("json"));
		json.put("userid", (int)request.getSession().getAttribute("userid"));
		int goodid = json.getInt("goodid");
		SaveGood sg = new SaveGood(json);
		GoodDAO gdao = new GoodDAO();
		sg.setGoodid(goodid);
		gdao.updateGood(sg);
		gdao.removePics(sg.getGoodid());
		gdao.removeTags(sg.getGoodid());
		gdao.saveTags(sg);
		gdao.savePictures(sg);
		json.put("success", true);
		response.getWriter().write(json.toString());
	}
	public void getHomeGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Query query = new Query(new JSONObject(request.getParameter("json")));
		
		GoodManager goodManager = new GoodManager();
		HomeGoodResult resultDTO = goodManager.getHomeGoodResult(query);
		response.getWriter().write(resultDTO.toJSON().toString());
	}

	public void getGoodDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int goodid = Integer.parseInt(request.getParameter("goodid"));
		GoodDAO gdao = new GoodDAO();
		gdao.addClicktimes(goodid);
		GoodDetail gd = gdao.getGoodDetail(goodid);
		
		if(gd==null){
			return;
		}
		gd.setGoodid(goodid);
		gd.setPics(gdao.getPicString(goodid).split("-"));
		gd.setTags(gdao.getTagString(goodid).split("-"));
		response.getWriter().write(gd.toJosn().toString());
		
	}


	public void getGoodContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taruserid = Integer.parseInt(request.getParameter("userid"));
		int srcuserid = (int)request.getSession().getAttribute("userid");
		int goodid = Integer.parseInt(request.getParameter("goodid"));
		
		GoodDAO gdao = new GoodDAO();
		List<Integer> banList = gdao.getBanList(goodid);
		if(banList.contains(srcuserid)){
			response.getWriter().write(new JSONObject().put("ban", true).toString());
			return;
		}
		GoodContact gc = new GoodContact();
		
		UserDAO udao = new UserDAO();
		gc = gdao.getGoodContact(taruserid,goodid);
		
		if(gc.isLocked()){
			if(gc.getLockeduserid() == srcuserid){
				if((!gc.isSeeQq()||gc.getQq().equals(""))&&!gc.isSeePhone()){
					gc.setQq("");
					gc.setPhone("");
					response.getWriter().write(gc.toJson().put("ilocked",true).toString());
					return;
				}else{
					if(!gc.isSeeQq()||gc.getQq().equals("")){
						gc.setQq("");
					}
					if(!gc.isSeePhone()){
						gc.setPhone("");
					}
					response.getWriter().write(gc.toJson().put("ilocked",true).toString());
					return;
				}
			}else{
				response.getWriter().write(new JSONObject().put("otherlocked",true).toString());
				return ;
			}
			
		}else{
			if((!gc.isSeeQq()||gc.getQq().equals(""))&&!gc.isSeePhone()){
				gc.setQq("");
				gc.setPhone("");
				response.getWriter().write(gc.toJson().toString());
				return;
			}else{
				if(!gc.isSeeQq()||gc.getQq().equals("")){
					gc.setQq("");
				}
				if(!gc.isSeePhone()){
					gc.setPhone("");
				}
				response.getWriter().write(gc.toJson().toString());
				PhoneMsg pmsg = udao.getPhoneMsg(taruserid, srcuserid);
				pmsg.setMsgtype("goodmsg");
				pmsg.setTarobjid(goodid);
				pmsg.setInfo(gdao.getInfo(goodid));
				pmsg.setMsgcontent("我锁定了你的商品");
				pmsg.setTaruserid(taruserid);
				pmsg.setSrcuserid(srcuserid);
				gdao.addGoodMsg(pmsg);
				SendMsg.sendLockedMsg(pmsg.getTarphone());
				gdao.lockGood(goodid, srcuserid);
			}
		}
		
	}


	public void sendMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taruserid = Integer.parseInt(request.getParameter("taruserid"));
		int srcuserid = (int)request.getSession().getAttribute("userid");
		
		int goodid = Integer.parseInt(request.getParameter("goodid"));
		String msgcontent = request.getParameter("msgcontent");
		UserDAO udao = new UserDAO();
		GoodDAO gdao = new GoodDAO();
		PhoneMsg pmsg = udao.getPhoneMsg(taruserid, srcuserid);
		pmsg.setMsgtype("goodcontentmsg");
		pmsg.setInfo(gdao.getInfo(goodid));
		pmsg.setMsgcontent(msgcontent);
		pmsg.setTarobjid(goodid);
		GoodContact gc = new GoodContact();
		gc = gdao.getGoodContact(taruserid,goodid);
		if(!gc.isLocked()||(gc.getLockeduserid() != srcuserid&&gc.isLocked())){
			SendMsg.sendLockedMsg(pmsg.getTarphone());
		}
		pmsg.setTaruserid(taruserid);
		pmsg.setSrcuserid(srcuserid);
		gdao.addGoodMsg(pmsg);
		gdao.lockGood(goodid, srcuserid);
		response.getWriter().write(new JSONObject().put("success", true).toString());
		
	}
	public void listMyGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		GoodDAO gdao = new GoodDAO();
		LinkedList<MyGoodItem> list = gdao.getMyGoodItem(userid);
		if(list==null){
			response.getWriter().write(new JSONObject().put("success", false).toString());
			return;
		}
		for(MyGoodItem g : list){
			g.setTags(gdao.getTagString(g.getGoodid()).split("-"));
			g.setPics(gdao.getPicString(g.getGoodid()).split("-"));
			if(g.isLocked()){
				gdao.getLockedUser(g);
			}
		}
		JSONObject json = new JSONObject().put("list", list);
		response.getWriter().write(json.toString());
		
		
	}

	public void removeGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		int goodid = Integer.parseInt(request.getParameter("goodid"));
		GoodDAO gdao = new GoodDAO();
		String pics = gdao.getPicString(goodid);
		String tbl = gdao.getTbl(goodid);
		removeGoodPics(pics,tbl,userid);
		gdao.removeGood(goodid);
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
	public void removeGoodPics(String pics,String tbl,int userid) {
		String tblPath = "C:/img/tbl/" + userid+"/"+tbl;
		File file = new File(tblPath);   
		if (file.isFile() && file.exists()) {  
			file.delete();  
		}  
		String[] picArray = pics.split("-");
		for(String p : picArray){
			String goodImgPath = "C:/img/goodimg/" + userid+"/"+p;
			file = new File(goodImgPath);
			if (file.isFile() && file.exists()) {  
				file.delete();  
			}
		}
	}
	public void listMylockGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = (int)request.getSession().getAttribute("userid");
		GoodDAO gdao = new GoodDAO();
		LinkedList<MyGoodItem> list = gdao.getMyLockGoodItem(userid);
		if(list==null){
			response.getWriter().write(new JSONObject().put("success", false).toString());
			return;
		}
		for(MyGoodItem g : list){
			g.setTags(gdao.getTagString(g.getGoodid()).split("-"));
			g.setPics(gdao.getPicString(g.getGoodid()).split("-"));
		}
		JSONObject json = new JSONObject().put("list", list);
		response.getWriter().write(json.toString());
		
		
	}

	
	public void getPQ(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int selfid = (int)request.getSession().getAttribute("userid");
		if(selfid==0){
			return;
		}
		int userid = Integer.parseInt(request.getParameter("lockeduserid"));
		int goodid = Integer.parseInt(request.getParameter("goodid"));
		GoodDAO gdao = new GoodDAO();
		PhoneQQ	pq = gdao.getPQ(userid, goodid);
		response.getWriter().write(new JSONObject(pq).toString());
		
		
	}
	public void clearLock(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int goodid = Integer.parseInt(request.getParameter("goodid"));
		GoodDAO gdao = new GoodDAO();
		gdao.clearLock(goodid);
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
	public void banLock(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int goodid = Integer.parseInt(request.getParameter("goodid"));
		int lockeduserid = Integer.parseInt(request.getParameter("lockeduserid"));
		GoodDAO gdao = new GoodDAO();
		gdao.banLock(lockeduserid,goodid);
		response.getWriter().write(new JSONObject().put("success", true).toString());
	}
	
	public void getOtherGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = Integer.parseInt(request.getParameter("userid"));
		String category = request.getParameter("category");
		List<OtherGoodItem> list = new LinkedList<OtherGoodItem>();
		GoodDAO gdao = new GoodDAO();
		list = gdao.getUserOthersGood(userid,list);
		if(list.size()>1){
			response.getWriter().write(new JSONObject().put("list", list).put("type", "userOthers").toString());
			return;
		}
		list.clear();
		list = gdao.getCategoryOthersGood(category, list);
		response.getWriter().write(new JSONObject().put("list", list).put("type", "categoryOthers").toString());
	}
}
