package me.hener.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.hener.dto.MemberInfo;
import me.hener.dto.PhoneMsg;
import me.hener.dto.SignedInfo;
import me.hener.dto.UserModifyInfo;
import me.hener.util.JdbcUtil;

public class UserDAO {
	private static Connection connection = null;
	private static PreparedStatement preparedStatment = null;
	private static ResultSet resultSet = null;
	
	private final String GET_SIGN_INFO = "select user.id,name,avatar from user where(phone=? and pwd=?)";
	private final String GET_GOODMSG_COUNT = "select count(id) from goodmsg where taruserid = ? and readed = false";
	private final String GET_NEEDMSG_COUNT = "select count(id) from needmsg where taruserid = ? and readed = false";
	private final String SAVE_USER = "insert into user(phone,pwd) values(?,?)";
	private final String GET_MEMBER_INFO = "select name,avatar,info from user where id = ?";
	private final String GET_GOODCOUNT="select count(id) from good where userid = ?";
	private final String GET_NEEDCOUNT="select count(id) from need where userid = ?";
	private final String GET_LOCKEDCOUNT="select count(id) from good where (lockeduserid = ? and locked=true)";
	private final String GET_BELOCKEDCOUNT = "select count(id) from good where (userid = ? and locked=true)";
	
	private final String PWD_MATCH = "select id from user where id = ? and pwd=?";
	public boolean pwdMatch(int userid, String pwd){
		boolean match = false;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(PWD_MATCH);
			preparedStatment.setInt(1, userid);
			preparedStatment.setString(2, pwd);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				match = true;
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return match;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	private final String GET_SIGN_INFO_BYID = "select user.id,name,avatar from user where id = ?";
	public SignedInfo getSignInfo(int userid){
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_SIGN_INFO_BYID);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				SignedInfo si = new SignedInfo();
				si.setUserid(resultSet.getInt(1));
				si.setName(resultSet.getString(2));
				si.setAvatar(resultSet.getString(3));
				return si;
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
	}
	public SignedInfo getSignInfo(String phone, String pwd){
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_SIGN_INFO);
			preparedStatment.setString(1, phone);
			preparedStatment.setString(2, pwd);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				SignedInfo si = new SignedInfo();
				si.setUserid(resultSet.getInt(1));
				si.setName(resultSet.getString(2));
				si.setAvatar(resultSet.getString(3));
				return si;
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
	}
	
	public int getGoodMsgCount(SignedInfo si){
		int count = 0;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_GOODMSG_COUNT);
			preparedStatment.setInt(1, si.getUserid());
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				count = resultSet.getInt(1);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
		return count;
	}
	public int getNeedMsgCount(SignedInfo si){
		int count = 0;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_NEEDMSG_COUNT);
			preparedStatment.setInt(1, si.getUserid());
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				count = resultSet.getInt(1);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
		return count;
	}
	public boolean saveUser(String phone,String pwd){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SAVE_USER);
			preparedStatment.setString(1, phone);
			preparedStatment.setString(2, pwd);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public MemberInfo getMemberInfo(int userid){
		MemberInfo mi = null ;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_MEMBER_INFO);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				mi = new MemberInfo();
				mi.setUserid(userid);
				mi.setName(resultSet.getString(1));
				mi.setAvatar(resultSet.getString(2));
				mi.setInfo(resultSet.getString(3));
				
			}
			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_GOODCOUNT);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				mi.setGoodCount(resultSet.getInt(1));
			}
			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_NEEDCOUNT);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				mi.setNeedCount(resultSet.getInt(1));
			}
			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_LOCKEDCOUNT);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				mi.setLockedCount(resultSet.getInt(1));
			}
			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_BELOCKEDCOUNT);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				mi.setBelockedCount(resultSet.getInt(1));
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return mi;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private final String GET_SRC_USER = "select seephone,seeqq,phone,qq,name from user where id =?";
	private final String GET_TAR_USER = "select phone,name from user where id= ?";
	public PhoneMsg getPhoneMsg(int taruserid,int srcuserid){
		PhoneMsg pmsg = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_SRC_USER);
			preparedStatment.setInt(1, srcuserid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				pmsg =new PhoneMsg();
				pmsg.setSeephone(resultSet.getBoolean(1));
				pmsg.setSeeqq(resultSet.getBoolean(2));
				pmsg.setSrcphone(resultSet.getString(3));
				pmsg.setSrcqq(resultSet.getString(4));
				pmsg.setSrcname(resultSet.getString(5));
			}
			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_TAR_USER);
			preparedStatment.setInt(1, taruserid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				pmsg.setTarphone(resultSet.getString(1));
				pmsg.setTarname(resultSet.getString(2));
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		return pmsg;
	}
	
	private final String REGISTED = "select id from user where phone = ?";
	public boolean registed(String phone){
		boolean b;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(REGISTED);
			preparedStatment.setString(1, phone);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				b = true;
			}else{
				b = false;
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return b;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}

	private final String UPDATE_AVA = "update user set avatar = ? where id = ?";
	public boolean updateAvatar(int userid,String avatar){
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(UPDATE_AVA);
			preparedStatment.setString(1, avatar);
			preparedStatment.setInt(2, userid);
			preparedStatment.executeUpdate();
			
			preparedStatment.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	private final String M_USER_INFO = "update user set info = ? where id = ?";
	public boolean modifiUserInfo(int userid,String info){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(M_USER_INFO);
			preparedStatment.setString(1, info);
			preparedStatment.setInt(2, userid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	private final String GET_USER_MODIFY_INFO = "select id,avatar,name,info,gmt_create,qq,email,realname,sex,location,seephone,seeqq,seeelse from user where id = ?";
	public UserModifyInfo getUserModifyInfo(int userid){
		UserModifyInfo u = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_USER_MODIFY_INFO);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				u = new UserModifyInfo();
				u.setId(resultSet.getInt(1));
				u.setAvatar(resultSet.getString(2));
				u.setName(resultSet.getString(3));
				u.setInfo(resultSet.getString(4));
				u.setGmt_create(resultSet.getTimestamp(5));
				u.setQq(resultSet.getString(6));
				u.setEmail(resultSet.getString(7));
				u.setRealname(resultSet.getString(8));
				u.setSex(resultSet.getString(9));
				u.setLocation(resultSet.getString(10));
				u.setSeephone(resultSet.getBoolean(11));
				u.setSeeqq(resultSet.getBoolean(12));
				u.setSeeelse(resultSet.getBoolean(13));
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
		
		
	}
	
	private String SAVE_EXTRA_INFO = "update user set qq=?,email=?,realname=?,sex=?,location=? where id=?";
	public boolean saveExtraInfo(String qq,String email,String realname,String sex,String location,int id){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SAVE_EXTRA_INFO);
			preparedStatment.setString(1, qq);
			preparedStatment.setString(2, email);
			preparedStatment.setString(3, realname);
			preparedStatment.setString(4, sex);
			preparedStatment.setString(5, location);
			preparedStatment.setInt(6, id);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	private final String SET_CANSEE = "update user set %s = ? where id = ?";
	public boolean setCansee(String k,boolean v,int id){
		
		String SQL = String.format(SET_CANSEE, k);
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			preparedStatment.setBoolean(1, v);
			preparedStatment.setInt(2, id);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	private final String SET_NAME = "update user set name = ? where id = ?";
	public boolean setName(String name, int id) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SET_NAME);
			preparedStatment.setString(1, name);
			preparedStatment.setInt(2, id);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private final String SET_PWD_BY_CODE="update user set pwd = ? where phone=?";
	public boolean setPwd(String phone, String pwd) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SET_PWD_BY_CODE);
			preparedStatment.setString(1, pwd);
			preparedStatment.setString(2, phone);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	private final String SET_PWD="update user set pwd = ? where id=?";
	public boolean setPwd(int userid, String newpwd) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SET_PWD);
			preparedStatment.setString(1, newpwd);
			preparedStatment.setInt(2, userid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	

}
