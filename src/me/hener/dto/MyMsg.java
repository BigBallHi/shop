package me.hener.dto;

import java.sql.Timestamp;

public class MyMsg {
	private int msgid;
	private int taruserid;
	private int srcuserid;
	private String srcphone;
	private String srcqq;
	private String srcname;
	private boolean seephone;
	private boolean seeqq;
	private String msgcontent;
	private String msgtype;
	private int tarobjid;
	private String info;
	private String tbl;
	private String srcavatar;
	private Timestamp time;
	private boolean readed;
	
	
	public boolean isReaded() {
		return readed;
	}
	public void setReaded(boolean readed) {
		this.readed = readed;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public int getTaruserid() {
		return taruserid;
	}
	public void setTaruserid(int taruserid) {
		this.taruserid = taruserid;
	}
	public int getSrcuserid() {
		return srcuserid;
	}
	public void setSrcuserid(int srcuserid) {
		this.srcuserid = srcuserid;
	}
	public String getSrcphone() {
		return srcphone;
	}
	public void setSrcphone(String srcphone) {
		this.srcphone = srcphone;
	}
	public String getSrcqq() {
		return srcqq;
	}
	public void setSrcqq(String srcqq) {
		this.srcqq = srcqq;
	}
	public String getSrcname() {
		return srcname;
	}
	public void setSrcname(String srcname) {
		this.srcname = srcname;
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
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public int getTarobjid() {
		return tarobjid;
	}
	public void setTarobjid(int tarobjid) {
		this.tarobjid = tarobjid;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getTbl() {
		return tbl;
	}
	public void setTbl(String tbl) {
		this.tbl = tbl;
	}
	public String getSrcavatar() {
		return srcavatar;
	}
	public void setSrcavatar(String srcavatar) {
		this.srcavatar = srcavatar;
	}
	
}
