package me.hener.dto;

import java.math.BigDecimal;

public class OtherGoodItem {
	private String tbl;
	private int goodid;
	private String info;
	private int clicktimes;
	private BigDecimal price;
	private int userid;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getTbl() {
		return tbl;
	}
	public void setTbl(String tbl) {
		this.tbl = tbl;
	}
	public int getGoodid() {
		return goodid;
	}
	public void setGoodid(int goodid) {
		this.goodid = goodid;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getClicktimes() {
		return clicktimes;
	}
	public void setClicktimes(int clicktimes) {
		this.clicktimes = clicktimes;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
