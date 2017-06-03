package me.hener.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class HomeNeedItem {
	private int needid;
	private int userid;
	private String needinfo;
	private Timestamp time;
	private BigDecimal price;
	private String category;
	private String name;
	private String avatar;
	public int getNeedid() {
		return needid;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getNeedinfo() {
		return needinfo;
	}
	public void setNeedinfo(String needinfo) {
		this.needinfo = needinfo;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
}
