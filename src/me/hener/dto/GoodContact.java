package me.hener.dto;

import org.json.JSONObject;

public class GoodContact {
	private int lockeduserid;
	private boolean locked;
	private boolean seePhone;
	private boolean seeQq;
	private String phone;
	private String qq;
	private String avatar;
	private String name;
	private int userid;
	private int goodid;
	private String needInfo;
	private String tbl;
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		json.put("locked", locked);
		json.put("seePhone", seePhone);
		json.put("seeQq", seeQq);
		json.put("phone", phone);
		json.put("qq", qq);
		json.put("avatar", avatar);
		json.put("name", name);
		json.put("userid", userid);
		json.put("goodid", goodid);
		json.put("goodInfo", needInfo);
		json.put("tbl", tbl);
		json.put("lockeduserid", lockeduserid);
		return json;
	}
	
	

	public int getLockeduserid() {
		return lockeduserid;
	}



	public void setLockeduserid(int lockeduserid) {
		this.lockeduserid = lockeduserid;
	}



	public String getTbl() {
		return tbl;
	}

	public void setTbl(String tbl) {
		this.tbl = tbl;
	}

	public String getNeedInfo() {
		return needInfo;
	}

	public void setNeedInfo(String needInfo) {
		this.needInfo = needInfo;
	}




	public int getGoodid() {
		return goodid;
	}

	public void setGoodid(int goodid) {
		this.goodid = goodid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}

	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean isSeePhone() {
		return seePhone;
	}
	public void setSeePhone(boolean seePhone) {
		this.seePhone = seePhone;
	}
	public boolean isSeeQq() {
		return seeQq;
	}
	public void setSeeQq(boolean seeQq) {
		this.seeQq = seeQq;
	}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
