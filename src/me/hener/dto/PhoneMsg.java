package me.hener.dto;

public class PhoneMsg {
	private int taruserid;
	private int srcuserid;
	private String tarname;
	private String tarphone;
	private String srcphone;
	private String srcqq;
	private String srcname;
	private boolean seephone;
	private boolean seeqq;
	private String msgcontent;
	private String msgtype;
	private int tarobjid;
	private String info;
	
	
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	public String getTarname() {
		return tarname;
	}
	public void setTarname(String tarname) {
		this.tarname = tarname;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
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
	public String getTarphone() {
		return tarphone;
	}
	public void setTarphone(String tarphone) {
		this.tarphone = tarphone;
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
	
}
