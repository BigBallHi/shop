package me.hener.dto;

import org.json.JSONObject;

public class Query {
	private int pageSize;
	private String enter;
	private String key;
	private String category;
	private int pageNo;
	private boolean nokey;
	private boolean nolocked;
	private String order;
	private String sort;
	
	public Query(JSONObject json){
		this.pageSize = json.getInt("pageSize");
		this.enter = json.getString("enter");
		this.key = json.getString("key");
		this.category = json.getString("category");
		this.pageNo = json.getInt("pageNo");
		this.nokey = json.getBoolean("nokey");
		this.nolocked = json.getBoolean("nolocked");
		this.order = json.getString("order");
		this.sort = json.getString("sort");
	}
	public JSONObject toJSON(){
		return new JSONObject(this);
	}
	public String getEnter() {
		return enter;
	}
	public void setEnter(String enter) {
		this.enter = enter;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public boolean isNokey() {
		return nokey;
	}
	public void setNokey(boolean nokey) {
		this.nokey = nokey;
	}
	public boolean isNolocked() {
		return nolocked;
	}
	public void setNolocked(boolean nolocked) {
		this.nolocked = nolocked;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getPageSize() {
		return pageSize;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
}
