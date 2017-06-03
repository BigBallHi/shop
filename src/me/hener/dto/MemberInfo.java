package me.hener.dto;

import org.json.JSONObject;

public class MemberInfo {
	private boolean success;
	private int userid;
	private String name;
	private String avatar;
	
	private int msgCount;
	private int sysMsgCount;
	private String info;
	private int goodCount;
	private int needCount;
	private int selledCount;
	private int lockedCount;
	private int belockedCount;
	private int historyCount;
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		json.put("success", this.success);
		json.put("userid", this.userid);
		json.put("name", this.name);
		json.put("avatar", this.avatar);
		
		json.put("msgCount", this.msgCount);
		
		json.put("sysMsgCount", this.sysMsgCount);
		
		json.put("info", this.info);
		json.put("goodCount", this.goodCount);
		
		json.put("needCount", this.needCount);
		json.put("selledCount", this.selledCount);
		
		json.put("lockedCount", this.lockedCount);
		json.put("belockedCount", this.belockedCount);
		json.put("historyCount", this.historyCount);
		return json;
		
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}
	public int getSysMsgCount() {
		return sysMsgCount;
	}
	public void setSysMsgCount(int sysMsgCount) {
		this.sysMsgCount = sysMsgCount;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}
	public int getNeedCount() {
		return needCount;
	}
	public void setNeedCount(int needCount) {
		this.needCount = needCount;
	}
	public int getSelledCount() {
		return selledCount;
	}
	public void setSelledCount(int selledCount) {
		this.selledCount = selledCount;
	}
	public int getLockedCount() {
		return lockedCount;
	}
	public void setLockedCount(int lockedCount) {
		this.lockedCount = lockedCount;
	}
	public int getBelockedCount() {
		return belockedCount;
	}
	public void setBelockedCount(int belockedCount) {
		this.belockedCount = belockedCount;
	}
	public int getHistoryCount() {
		return historyCount;
	}
	public void setHistoryCount(int historyCount) {
		this.historyCount = historyCount;
	}

	
	
}

