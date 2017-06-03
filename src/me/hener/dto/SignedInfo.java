package me.hener.dto;

import org.json.JSONObject;

public class SignedInfo {
	private boolean success;
	private int userid;
	private String name;
	private String avatar;
	private int msgCount;
	private int sysMsgCount;
 	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		json.put("success", this.success);
		json.put("userid",this.userid);
		json.put("name",this.name);
		json.put("avatar",this.avatar);
		json.put("msgCount",this.msgCount);
		json.put("sysMsgCount",this.sysMsgCount);
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
	
}
