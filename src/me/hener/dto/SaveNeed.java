package me.hener.dto;

import java.math.BigDecimal;

import org.json.JSONObject;

public class SaveNeed {
	private int userid;
	private String info;
	private BigDecimal price;
	private String category;
	private int needid;
	public SaveNeed(JSONObject json){
		this.userid = json.getInt("userid");
		this.info = json.getString("info");
		this.price = json.getBigDecimal("price");
		this.category = json.getString("category");
	}
	public SaveNeed(){}
	public JSONObject toJson(){
		return new JSONObject(this);
	}
	

	public int getNeedid() {
		return needid;
	}
	public void setNeedid(int needid) {
		this.needid = needid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
