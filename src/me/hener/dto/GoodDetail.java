package me.hener.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.json.JSONObject;

public class GoodDetail {
	private int goodid;
	private String[] tags;
	private String[] pics;
	private String tbl;
	private String category;
	private String goodinfo;
	private boolean locked;
	private Timestamp time;
	private BigDecimal price;
	private int clicktimes;
	private String location;
	private int userid;
	private String avatar;
	private String name;
	private String userinfo;
	private boolean seephone;
	private boolean seeqq;
	
	
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public JSONObject toJosn(){
		return new JSONObject(this);
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

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getClicktimes() {
		return clicktimes;
	}

	public void setClicktimes(int clicktimes) {
		this.clicktimes = clicktimes;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	public String getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}
	
	
	
}
