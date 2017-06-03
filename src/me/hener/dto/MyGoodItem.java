package me.hener.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MyGoodItem {
	private int goodid;
	private String[] tags;
	private String[] pics;
	private String tbl;
	private String category;
	private String goodinfo;
	private boolean locked;
	private Timestamp uptime;
	private Timestamp lockedtime;
	private BigDecimal price;
	private int clicktimes;
	private int lockeduserid;
	private int userid;
	private String lockedavatar;
	private String lockedname;
	private boolean lockedseeqq;
	
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public boolean isLockedseeqq() {
		return lockedseeqq;
	}
	public void setLockedseeqq(boolean lockedseeqq) {
		this.lockedseeqq = lockedseeqq;
	}
	public int getGoodid() {
		return goodid;
	}
	public void setGoodid(int goodid) {
		this.goodid = goodid;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String[] getPics() {
		return pics;
	}
	public void setPics(String[] pics) {
		this.pics = pics;
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
	public String getGoodinfo() {
		return goodinfo;
	}
	public void setGoodinfo(String goodinfo) {
		this.goodinfo = goodinfo;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public Timestamp getUptime() {
		return uptime;
	}
	public void setUptime(Timestamp uptime) {
		this.uptime = uptime;
	}
	public Timestamp getLockedtime() {
		return lockedtime;
	}
	public void setLockedtime(Timestamp lockedtime) {
		this.lockedtime = lockedtime;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getClicktimes() {
		return clicktimes;
	}
	public void setClicktimes(int clicktimes) {
		this.clicktimes = clicktimes;
	}
	public int getLockeduserid() {
		return lockeduserid;
	}
	public void setLockeduserid(int lockeduserid) {
		this.lockeduserid = lockeduserid;
	}
	public String getLockedavatar() {
		return lockedavatar;
	}
	public void setLockedavatar(String lockedavatar) {
		this.lockedavatar = lockedavatar;
	}
	public String getLockedname() {
		return lockedname;
	}
	public void setLockedname(String lockedname) {
		this.lockedname = lockedname;
	}
	
}
