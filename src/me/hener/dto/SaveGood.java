package me.hener.dto;

import java.math.BigDecimal;

import org.json.JSONObject;

public class SaveGood {
	private int userid;
	private int goodid;
	private String info;
	private BigDecimal price;
	private String tagString;
	private String picString;
	private String tbl;
	private String category;
	private String[] tags;
	private String[] pics;
	public String[] getTags(){
		return this.tagString.split("-");
	}
	public String[] getPics(){
		return this.picString.split("-");
	}
	public void setTags(){
		this.tags = this.tagString.split("-");
	}
	public void setPics(){
		this.pics = this.picString.split("-");
	}
	public SaveGood(JSONObject json){
		
		this.setUserid(json.getInt("userid"));
		this.setInfo(json.getString("info"));
		this.setCategory(json.getString("category"));
		this.setPrice(json.getBigDecimal("price"));
		this.setPicString(json.getString("picString"));
		this.setTagString(json.getString("tagString"));
		this.setTbl(json.getString("tbl"));
		this.setPics();
		this.setTags();
	}
	public JSONObject toJson(){
		JSONObject json = new JSONObject(this);
		return json;
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
	public String getTagString() {
		return tagString;
	}
	public void setTagString(String tagString) {
		this.tagString = tagString;
	}
	public String getPicString() {
		return picString;
	}
	public void setPicString(String picString) {
		this.picString = picString;
	}
	public String getTbl() {
		return tbl;
	}
	public void setTbl(String tbl) {
		this.tbl = tbl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getGoodid() {
		return goodid;
	}
	public void setGoodid(int goodid) {
		this.goodid = goodid;
	}
	
	
	
}
