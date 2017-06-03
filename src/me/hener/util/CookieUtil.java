package me.hener.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/**
	 * 10天
	 */
	public static final int COOKIE_TIME = 2592000;
	/**
	 * 根据姓名删除cookie
	 * @param response
	 * @param name
	 */
	public static void removeCookie(HttpServletResponse response,String name){
		Cookie cookie = new Cookie("pwd",null);
		cookie.setPath("/"); 
		cookie.setMaxAge(0);
		response.addCookie(cookie); //重新写入，将覆盖之前的

	}
	
	
	/**
	 * 添加Cookie
	 * @param response
	 * @param name
	 * @param value
	 * @return
	 */
	public static Cookie addCookie(HttpServletResponse response,String name,String value){
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
		cookie.setMaxAge(COOKIE_TIME);
		response.addCookie(cookie);
		return cookie;
	}
	/**
	 * 根据名字获取Cookie
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	/**
	 * 生成 cookieName-cookie的MAP
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
}
