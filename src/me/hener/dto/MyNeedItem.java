package me.hener.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MyNeedItem {
	private int userid;
	private int needid;
	private String category;
	private String needinfo;
	private boolean locked;
	private Timestamp uptime;
	private Timestamp lockedtime;
	private BigDecimal price;
	private int lockeduserid;
	private String lockedavatar;
	private String lockedname;
	private boolean lockedseeqq;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getNeedid() {
		return needid;
	}
	public void setNeedid(int needid) {
		this.needid = needid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getNeedinfo() {
		return needinfo;
	}
	public void setNeedinfo(String needinfo) {
		this.needinfo = needinfo;
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
	public boolean isLockedseeqq() {
		return lockedseeqq;
	}
	public void setLockedseeqq(boolean lockedseeqq) {
		this.lockedseeqq = lockedseeqq;
	}
	
	
}
