package me.hener.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import me.hener.dto.HomeNeedItem;
import me.hener.dto.HomeNeedResult;
import me.hener.dto.MyMsg;
import me.hener.dto.MyNeedItem;
import me.hener.dto.NeedContact;
import me.hener.dto.PhoneMsg;
import me.hener.dto.PhoneQQ;
import me.hener.dto.SaveNeed;
import me.hener.util.JdbcUtil;

public class NeedDAO {
	private static Connection connection = null;
	private static PreparedStatement preparedStatment = null;
	private static ResultSet resultSet = null;
	
	private final String SAVE_NEED = "insert into need(userid,info,price,category) values(?,?,?,?)";
	private final String GET_NEED = "select SQL_CALC_FOUND_ROWS need.id ,need.info,price,category,userid,need.gmt_modified,name,avatar,locked from user,need where (need.userid=user.id) order by gmt_modified limit ?,?";
	private final String GET_ALLCOUNT = "select found_rows()";
	

	public boolean saveNeed(SaveNeed sn) {
		try {
			connection = JdbcUtil.getConnection();
			
			preparedStatment = connection.prepareStatement(SAVE_NEED);
			preparedStatment.setInt(1, sn.getUserid());
			preparedStatment.setString(2, sn.getInfo());
			preparedStatment.setBigDecimal(3, sn.getPrice());
			preparedStatment.setString(4, sn.getCategory());
			
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public HomeNeedResult getNeed(int pageSize,int pageNo){
		HomeNeedResult homeNeedResult = new HomeNeedResult();
		LinkedList<HomeNeedItem> list = new LinkedList<HomeNeedItem>();
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_NEED);
			
			
			preparedStatment.setInt(1, (pageNo-1) * pageSize);
			preparedStatment.setInt(2, pageSize);
			resultSet = preparedStatment.executeQuery();
			
			while (resultSet.next()) {
				HomeNeedItem item = new HomeNeedItem();
				item.setNeedid(resultSet.getInt(1));
				item.setNeedinfo(resultSet.getString(2));
				item.setPrice(resultSet.getBigDecimal(3));
				item.setCategory(resultSet.getString(4));
				item.setUserid(resultSet.getInt(5));
				item.setTime(resultSet.getTimestamp(6));
				item.setName(resultSet.getString(7));
				item.setAvatar(resultSet.getString(8));
				
				list.add(item);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				homeNeedResult.setAllcount(resultSet.getInt(1));
				
			}
			homeNeedResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return homeNeedResult;
	}

	private final String LOCK_NEED = "update need set locked = true,lockeduserid=?,lockedtime=now() where id =?;";
	public void lockNeed(int needid,int lockeduserid){
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(LOCK_NEED);
			preparedStatment.setInt(1, lockeduserid);
			preparedStatment.setInt(2, needid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private final String GET_INFO = "select info from need where id=?";
	public String getInfo(int needid){
		String info = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_INFO);
			preparedStatment.setInt(1, needid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				info = resultSet.getString(1);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return info;
	}

	private final String GET_NEED_LOCKED = "select locked from need where id = ?";
	
	public boolean needLocked(int needid){
		boolean needLocked = false;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_NEED_LOCKED);
			preparedStatment.setInt(1, needid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				needLocked=resultSet.getBoolean(1);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return needLocked;
		
		
	}

	private final String GET_NEEDCONTACT = "select seephone,seeqq,phone,qq,name,avatar,user.id,need.id,need.info,need.locked,lockeduserid from user,need where (user.id = ? and need.id=? and need.userid=user.id)"; 
	public NeedContact getNeedContact(int taruserid,int needid){
		NeedContact nc = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_NEEDCONTACT);
			preparedStatment.setInt(1, taruserid);
			preparedStatment.setInt(2, needid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				nc =new NeedContact();
				nc.setSeePhone(resultSet.getBoolean(1));
				nc.setSeeQq(resultSet.getBoolean(2));
				nc.setPhone(resultSet.getString(3));
				nc.setQq(resultSet.getString(4));
				nc.setName(resultSet.getString(5));
				nc.setAvatar(resultSet.getString(6));
				nc.setUserid(resultSet.getInt(7));
				nc.setNeedid(resultSet.getInt(8));
				nc.setNeedInfo(resultSet.getString(9));
				nc.setLocked(resultSet.getBoolean(10));
				nc.setLockeduserid(resultSet.getInt(11));
			}
			resultSet.close();
			preparedStatment.close();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return nc;
	}
	private final String ADD_NEED_MSG = "insert into needmsg(srcuserid,taruserid,needid,msgcontent) values(?,?,?,?)";
	public void addNeedMsg(PhoneMsg pmsg){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(ADD_NEED_MSG);
			preparedStatment.setInt(1, pmsg.getSrcuserid());
			preparedStatment.setInt(2, pmsg.getTaruserid());
			preparedStatment.setInt(3, pmsg.getTarobjid());
			preparedStatment.setString(4, pmsg.getMsgcontent());
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void addNeedMsg(String msgcontent,int needid,int taruserid,int srcuserid){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(ADD_NEED_MSG);
			preparedStatment.setString(4, msgcontent);
			preparedStatment.setInt(3, needid);
			preparedStatment.setInt(2, taruserid);
			preparedStatment.setInt(1, srcuserid);
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private final String GET_MYNEED_ITEM= "select category,info,locked,gmt_modified,lockedtime,lockeduserid,price,id,userid from need where(userid = ?)";
	private final String GET_LOCKED_USER = "select name,avatar,info, seeqq from user where id = ?";
	
	public LinkedList<MyNeedItem> getMyNeedItem(int userid) {
		LinkedList<MyNeedItem> list = new LinkedList<MyNeedItem>();
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_MYNEED_ITEM);
			preparedStatment.setInt(1, userid);
			
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				
				MyNeedItem n = new MyNeedItem();
				n.setCategory(resultSet.getString(1));
				n.setNeedinfo(resultSet.getString(2));
				n.setLocked(resultSet.getBoolean(3));
				n.setUptime(resultSet.getTimestamp(4));
				n.setLockedtime(resultSet.getTimestamp(5));
				n.setLockeduserid(resultSet.getInt(6));
				n.setPrice(resultSet.getBigDecimal(7));
				n.setNeedid(resultSet.getInt(8));
				n.setUserid(resultSet.getInt(9));
				list.add(n);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public MyNeedItem getLockedUser(MyNeedItem n){
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_LOCKED_USER);
			preparedStatment.setInt(1, n.getLockeduserid());
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				n.setLockedname(resultSet.getString(1));
				n.setLockedavatar(resultSet.getString(2));
				n.setLockedseeqq(resultSet.getBoolean(4));
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return n;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return n;
		}
		
		
	}
	private final String REMOVE_NEED = "delete from need where id = ?";
	public void removeNeed(int needid) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(REMOVE_NEED);
			preparedStatment.setInt(1, needid);
			
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private final String GET_NEED_DETAIL = "select category,info,price from need where id = ?";
	public SaveNeed getNeedDetail(int needid) {
		SaveNeed sn = null;
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_NEED_DETAIL);
			preparedStatment.setInt(1, needid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				sn = new SaveNeed();
				sn.setCategory(resultSet.getString(1));
				sn.setInfo(resultSet.getString(2));
				sn.setPrice(resultSet.getBigDecimal(3));
				
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return sn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	private final String UPDATE_NEED = "update need set category=?,price=?,info=? where id = ?";
	public boolean modifyNeed(SaveNeed sn) {
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(UPDATE_NEED);
			preparedStatment.setString(1, sn.getCategory());
			preparedStatment.setBigDecimal(2, sn.getPrice());
			preparedStatment.setString(3, sn.getInfo());
			preparedStatment.setInt(4, sn.getNeedid());
			
			
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
	private final String GET_MYLOCK_NEED_ITEM= "select category,info,locked,gmt_modified,lockedtime,lockeduserid,price,id,userid from need where(lockeduserid = ? and locked=true)";
	public LinkedList<MyNeedItem> getMylockNeedItem(int userid) {
		LinkedList<MyNeedItem> list = new LinkedList<MyNeedItem>();
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_MYLOCK_NEED_ITEM);
			preparedStatment.setInt(1, userid);
			
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				
				MyNeedItem n = new MyNeedItem();
				n.setCategory(resultSet.getString(1));
				n.setNeedinfo(resultSet.getString(2));
				n.setLocked(resultSet.getBoolean(3));
				n.setUptime(resultSet.getTimestamp(4));
				n.setLockedtime(resultSet.getTimestamp(5));
				n.setLockeduserid(resultSet.getInt(6));
				n.setPrice(resultSet.getBigDecimal(7));
				n.setNeedid(resultSet.getInt(8));
				n.setUserid(resultSet.getInt(9));
				list.add(n);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private final String GET_PQ = "select phone,qq,seephone,seeqq,lockeduserid from user,need where (need.lockeduserid=user.id and lockeduserid = ? and need.id=?)";
	public PhoneQQ getPQ(int lockeduserid,int needid){
		PhoneQQ pq = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_PQ);
			preparedStatment.setInt(1,lockeduserid);
			preparedStatment.setInt(2, needid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				pq = new PhoneQQ();
				pq.setPhone(resultSet.getString(1));
				if(resultSet.getBoolean(4)){
					pq.setQq(resultSet.getString(2));
				}
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return pq;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	private final String CLEAR_LOCK = "update need set locked = false where id = ?";
	public void clearLock(int needid) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(CLEAR_LOCK);
			preparedStatment.setInt(1, needid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private final String BAN_LOCK = "insert into needcantlocked(userid,needid) values(?,?)";
	public void banLock(int userid,int needid) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(BAN_LOCK);
			preparedStatment.setInt(1, userid);
			preparedStatment.setInt(2, needid);
			
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String GET_BANLIST = "select userid from needcantlocked where needid = ?";
	public List<Integer> getBanList(int needid) {
		List<Integer> banList = new LinkedList<Integer>();
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_BANLIST);
			preparedStatment.setInt(1, needid);
			resultSet = preparedStatment.executeQuery();
			
			while(resultSet.next()){
				banList.add(resultSet.getInt(1));
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return banList;
		} catch (SQLException e) {
			e.printStackTrace();
			return banList;
		}
	}
	private final String GET_MYMSG = "select needmsg.id,needmsg.gmt_create,taruserid,needid,msgcontent,"
			+ "readed,need.info,name,avatar,srcuserid,phone,qq,seeqq,seephone from needmsg,need,user where "
			+ "(taruserid = ? and needmsg.needid=need.id and needmsg.srcuserid=user.id) ";
	public List<MyMsg> listMyNeedMsg(List<MyMsg> list,int taruserid){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_MYMSG);
			preparedStatment.setInt(1, taruserid);
			
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				MyMsg msg = new MyMsg();
				msg.setMsgid(resultSet.getInt(1));
				msg.setTime(resultSet.getTimestamp(2));
				msg.setTaruserid(resultSet.getInt(3));
				msg.setTarobjid(resultSet.getInt(4));
				msg.setMsgcontent(resultSet.getString(5));
				msg.setReaded(resultSet.getBoolean(6));
				msg.setInfo(resultSet.getString(7));
				msg.setSrcname(resultSet.getString(8));
				msg.setSrcavatar(resultSet.getString(9));
				msg.setSrcuserid(resultSet.getInt(10));
				msg.setSrcphone(resultSet.getString(11));
				msg.setSrcqq(resultSet.getString(12));
				msg.setSeeqq(resultSet.getBoolean(13));
				msg.setSeephone(resultSet.getBoolean(14));
				
				msg.setMsgtype("needmsg");
				list.add(msg);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return list;
		}
		
	}
	private final String DELETE_MSG = "delete from needmsg where id=?";
	public void deleteMsg(int msgid) {
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(DELETE_MSG);
			preparedStatment.setInt(1, msgid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private final String MSG_READ = "update needmsg set readed = true where taruserid= ?";
	public void msgReaad(int taruserid){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(MSG_READ);
			preparedStatment.setInt(1, taruserid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
