package me.hener.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class HomeGoodItem {
	private int goodid;
	private int userid;
	private String info;
	private BigDecimal price;
	private String tbl;
	private Timestamp gmt_modified;
	private String name;
	private String avatar;
	private boolean locked;
	private int clicktimes;
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public int getGoodid() {
		return goodid;
	}

	public int getClicktimes() {
		return clicktimes;
	}
	public void setClicktimes(int clicktimes) {
		this.clicktimes = clicktimes;
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
	public String getTbl() {
		return tbl;
	}
	public void setTbl(String tbl) {
		this.tbl = tbl;
	}
	public Timestamp getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(Timestamp gmt_modified) {
		this.gmt_modified = gmt_modified;
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
	
}
