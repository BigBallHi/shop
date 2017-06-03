package me.hener.dto;

import java.sql.Timestamp;

import org.json.JSONObject;

public class UserModifyInfo {
	private int id;
	private String avatar;
	private String name;
	private String info;
	private Timestamp gmt_create;
	private String qq;
	private String email;
	private String realname;
	private String sex;
	private String location;
	private boolean seephone;
	private boolean seeqq;
	private boolean seeelse;
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("avatar", avatar);
		json.put("name", name);
		json.put("info", info);
		json.put("gmt_create", gmt_create);
		json.put("qq", qq);
		json.put("email", email);
		json.put("realname", realname);
		json.put("sex", sex);
		json.put("location", location);
		json.put("seephone", seephone);
		json.put("seeqq", seeqq);
		json.put("seeelse", seeelse);
		return json;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public Timestamp getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Timestamp gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean isSeephone() {
		return seephone;
	}
	public void setSeephone(boolean seephone) {
		this.seephone = seephone;
	}
	public boolean isSeeqq() {
		return seeqq;
	}
	public void setSeeqq(boolean seeqq) {
		this.seeqq = seeqq;
	}
	public boolean isSeeelse() {
		return seeelse;
	}
	public void setSeeelse(boolean seeelse) {
		this.seeelse = seeelse;
	}

	
}
