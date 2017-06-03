package me.hener.dto;

import java.util.LinkedList;

import org.json.JSONObject;

public class HomeGoodResult {
	
	private LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
	private int pageSize;
	private int allcount;
	private int pageNo;
	public JSONObject toJSON(){
		JSONObject json = new JSONObject();
		json.put("list", this.list);
		json.put("allcount", this.allcount);
		json.put("pageNo", this.pageNo);
		json.put("pageSize", this.pageSize);
		return json;
	}
	public LinkedList<HomeGoodItem> getList() {
		return list;
	}
	public void setList(LinkedList<HomeGoodItem> list) {
		this.list = list;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getAllcount() {
		return allcount;
	}
	public void setAllcount(int allcount) {
		this.allcount = allcount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
	
	
	
}
