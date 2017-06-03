package me.hener.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import me.hener.dto.GoodContact;
import me.hener.dto.GoodDetail;
import me.hener.dto.HomeGoodItem;
import me.hener.dto.HomeGoodResult;
import me.hener.dto.MyGoodItem;
import me.hener.dto.MyMsg;
import me.hener.dto.OtherGoodItem;
import me.hener.dto.PhoneMsg;
import me.hener.dto.PhoneQQ;
import me.hener.dto.Query;
import me.hener.dto.SaveGood;
import me.hener.util.JdbcUtil;

public class GoodDAO {
	
	private static Connection connection = null;
	private static PreparedStatement preparedStatment = null;
	private static ResultSet resultSet = null;
	
	private final String SAVA_GOOD = "insert into good (userid,category,price,info,tbl) values (?,?,?,?,?)";

	private final String GET_INSERT_ID = "select @@identity";
	private final String SAVA_PICTURE = "insert into pic (goodid,pic) values(?,?)";
	private final String SAVE_TAG = "insert into tag (goodid,tag) values(?,?)";
	
	private final String GET_ALLCOUNT = "select found_rows()";
	private final String GET_KEY_SEELOKED = "select SQL_CALC_FOUND_ROWS good.id ,good.info,price,tbl,userid,good.gmt_modified,name,avatar,locked,clicktimes from user,good,tag where(tag.tag like ? and good.id=tag.goodid and good.userid = user.id) order by %s %s limit ?,?;";
	private final String GET_KEY_NOLOKED = "select SQL_CALC_FOUND_ROWS good.id ,good.info,price,tbl,userid,good.gmt_modified, name,avatar,locked,clicktimes from user,good,tag where(tag.tag like ? and good.locked = 0 and good.id=tag.goodid and good.userid = user.id) order by %s %s limit ?,?;";
	private final String GET_C_SEELOKED = "select SQL_CALC_FOUND_ROWS good.id ,good.info,price,tbl,userid,good.gmt_modified,name,avatar,locked,clicktimes from user,good where(good.category = ? and user.id = good.userid) order by %s %s limit ?,?";
	private final String GET_C_NOLOKED = "select SQL_CALC_FOUND_ROWS good.id ,good.info,price,tbl,userid,good.gmt_modified,name,avatar,locked,clicktimes from user,good where(good.locked = 0 and good.category = ? and user.id = good.userid) order by %s %s limit ?,?";
	private final String GET_NO_SEELOKED = "select SQL_CALC_FOUND_ROWS good.id ,good.info,price,tbl,userid,good.gmt_modified,name,avatar,locked,clicktimes from user,good where (good.userid=user.id) order by %s %s limit ?,?";
	private final String GET_NO_NOLOKED = "select SQL_CALC_FOUND_ROWS good.id ,good.info,price,tbl,userid,good.gmt_modified,name,avatar,locked,clicktimes from user,good where (good.locked = 0 and good.userid=user.id) order by %s %s limit ?,?";
	private final String GET_K_C_NOLOKED = "select SQL_CALC_FOUND_ROWS good.id ,good.info,price,tbl,userid,good.gmt_modified,name,avatar,locked,clicktimes from user,good,tag where(good.locked = 0 and good.category = ? and tag.tag like ? and good.userid = user.id and tag.goodid=good.id) order by %s %s limit ?,?";
	private final String GET_K_C_SEELOKED = "select SQL_CALC_FOUND_ROWS good.id ,good.info,price,tbl,userid,good.gmt_modified,name,avatar,locked,clicktimes from user,good,tag where(good.category = ? and tag.tag like ? and good.userid = user.id and tag.goodid=good.id) order by %s %s limit ?,?";
	
	private final String UPDATE_GOOD = "update good set category=?,price=?,info=?,tbl=? where id = ?";
	public void updateGood(SaveGood sg){
		try {

			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(UPDATE_GOOD);
			preparedStatment.setString(1, sg.getCategory());
			preparedStatment.setBigDecimal(2, sg.getPrice());
			preparedStatment.setString(3, sg.getInfo());
			preparedStatment.setString(4, sg.getTbl());
			preparedStatment.setInt(5, sg.getGoodid());
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 插入商品的基本信息，并返回刚插入商品的ID,ID为0表示插入失败
	 * 
	 * @param good
	 * @return
	 */
	public int saveGood(SaveGood sg) {
		int id = 0;
		try {

			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SAVA_GOOD);
			preparedStatment.setInt(1, sg.getUserid());
			preparedStatment.setString(2, sg.getCategory());
			preparedStatment.setBigDecimal(3, sg.getPrice());
			preparedStatment.setString(4, sg.getInfo());
			preparedStatment.setString(5, sg.getTbl());
			preparedStatment.executeUpdate();
			preparedStatment.close();

			preparedStatment = connection.prepareStatement(GET_INSERT_ID);
			resultSet = preparedStatment.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getInt(1);

			}
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			return id;
		}
		return id;
	}

	/**
	 * 一次存储多张图片（用list做参数比单个字符串好，只要打开一次链接）---->才发现 原来 用 Batch 就行了
	 * 
	 * @param goodId
	 * @param pictures
	 */
	public void savePictures(SaveGood sg) {

		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SAVA_PICTURE);
			for (String picture : sg.getPics()) {
				preparedStatment.setInt(1, sg.getGoodid());
				preparedStatment.setString(2, picture);
				preparedStatment.addBatch();
			}
			preparedStatment.executeBatch();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 存储标签
	 * 
	 * @param goodId
	 * @param tags
	 */
	public void saveTags(SaveGood sg) {

		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SAVE_TAG);
			for (String tag : sg.getTags()) {
				preparedStatment.setInt(1, sg.getGoodid());
				preparedStatment.setString(2, tag);
				preparedStatment.addBatch();
			}
			preparedStatment.executeBatch();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public HomeGoodResult getGoodsByKeySeelocked(Query Query){
		HomeGoodResult HomeGoodResult = new HomeGoodResult();
		LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
		String SQL = String.format(GET_KEY_SEELOKED, Query.getOrder(),Query.getSort());
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			preparedStatment.setString(1, "%" + Query.getKey() + "%");
			preparedStatment.setInt(2, (Query.getPageNo()-1) * Query.getPageSize());
			preparedStatment.setInt(3, Query.getPageNo());
			resultSet = preparedStatment.executeQuery();
			/**** 巨大天坑 *******************/
			/**
			 * 如果 把 resultItem 的初始化写在注释的位置，ls.add（resultItem） 永远添加的是相同的内容
			 * 原因：整个过程只产生了一个 resultItem的内存，list里面装的是指针，内存的内容永远只有一个，即使有四个指针
			 */
			// ResultItem resultItem = new ResultItem();
			while (resultSet.next()) {
				HomeGoodItem HomeGoodItem = new HomeGoodItem();
				HomeGoodItem.setGoodid(resultSet.getInt(1));
				HomeGoodItem.setInfo(resultSet.getString(2));
				HomeGoodItem.setPrice(resultSet.getBigDecimal(3));
				HomeGoodItem.setTbl(resultSet.getString(4));
				HomeGoodItem.setUserid(resultSet.getInt(5));
				HomeGoodItem.setGmt_modified(resultSet.getTimestamp(6));
				HomeGoodItem.setName(resultSet.getString(7));
				HomeGoodItem.setAvatar(resultSet.getString(8));
				HomeGoodItem.setLocked(resultSet.getBoolean(9));
				HomeGoodItem.setClicktimes(resultSet.getInt(10));
				list.add(HomeGoodItem);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodResult.setAllcount(resultSet.getInt(1));
			}
			HomeGoodResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return HomeGoodResult;
	}
	
	public HomeGoodResult getGoodsByKeyNolocked(Query Query){
		HomeGoodResult HomeGoodResult = new HomeGoodResult();
		LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
		String SQL = String.format(GET_KEY_NOLOKED, Query.getOrder(),Query.getSort());
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			preparedStatment.setString(1, "%" + Query.getKey() + "%");
			
			preparedStatment.setInt(2, (Query.getPageNo()-1) * Query.getPageSize());
			preparedStatment.setInt(3, Query.getPageSize());
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodItem HomeGoodItem = new HomeGoodItem();
				HomeGoodItem.setGoodid(resultSet.getInt(1));
				HomeGoodItem.setInfo(resultSet.getString(2));
				HomeGoodItem.setPrice(resultSet.getBigDecimal(3));
				HomeGoodItem.setTbl(resultSet.getString(4));
				HomeGoodItem.setUserid(resultSet.getInt(5));
				HomeGoodItem.setGmt_modified(resultSet.getTimestamp(6));
				HomeGoodItem.setName(resultSet.getString(7));
				HomeGoodItem.setAvatar(resultSet.getString(8));
				HomeGoodItem.setLocked(resultSet.getBoolean(9));
				HomeGoodItem.setClicktimes(resultSet.getInt(10));
				list.add(HomeGoodItem);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodResult.setAllcount(resultSet.getInt(1));
			}
			HomeGoodResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return HomeGoodResult;
	}

	
	
	public HomeGoodResult getGoodsByCategorySeelocked(Query Query){
		HomeGoodResult HomeGoodResult = new HomeGoodResult();
		LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
		String SQL = String.format(GET_C_SEELOKED, Query.getOrder(),Query.getSort());
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			preparedStatment.setString(1, Query.getCategory());
		
			preparedStatment.setInt(2, (Query.getPageNo()-1) * Query.getPageSize());
			preparedStatment.setInt(3, Query.getPageSize());
			resultSet = preparedStatment.executeQuery();
			/**** 巨大天坑 *******************/
			/**
			 * 如果 把 resultItem 的初始化写在注释的位置，ls.add（resultItem） 永远添加的是相同的内容
			 * 原因：整个过程只产生了一个 resultItem的内存，list里面装的是指针，内存的内容永远只有一个，即使有四个指针
			 */
			// ResultItem resultItem = new ResultItem();
			while (resultSet.next()) {
				HomeGoodItem HomeGoodItem = new HomeGoodItem();
				HomeGoodItem.setGoodid(resultSet.getInt(1));
				HomeGoodItem.setInfo(resultSet.getString(2));
				HomeGoodItem.setPrice(resultSet.getBigDecimal(3));
				HomeGoodItem.setTbl(resultSet.getString(4));
				HomeGoodItem.setUserid(resultSet.getInt(5));
				HomeGoodItem.setGmt_modified(resultSet.getTimestamp(6));
				HomeGoodItem.setName(resultSet.getString(7));
				HomeGoodItem.setAvatar(resultSet.getString(8));
				HomeGoodItem.setLocked(resultSet.getBoolean(9));
				HomeGoodItem.setClicktimes(resultSet.getInt(10));
				list.add(HomeGoodItem);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodResult.setAllcount(resultSet.getInt(1));
			}
			HomeGoodResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return HomeGoodResult;
	}

	
	public HomeGoodResult getGoodsByCategoryNolocked(Query Query){
		HomeGoodResult HomeGoodResult = new HomeGoodResult();
		LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
		String SQL = String.format(GET_C_NOLOKED, Query.getOrder(),Query.getSort());
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			preparedStatment.setString(1, Query.getCategory());
			
			preparedStatment.setInt(2, (Query.getPageNo()-1) * Query.getPageSize());
			preparedStatment.setInt(3, Query.getPageSize());
			resultSet = preparedStatment.executeQuery();
			/**** 巨大天坑 *******************/
			/**
			 * 如果 把 resultItem 的初始化写在注释的位置，ls.add（resultItem） 永远添加的是相同的内容
			 * 原因：整个过程只产生了一个 resultItem的内存，list里面装的是指针，内存的内容永远只有一个，即使有四个指针
			 */
			// ResultItem resultItem = new ResultItem();
			while (resultSet.next()) {
				HomeGoodItem HomeGoodItem = new HomeGoodItem();
				HomeGoodItem.setGoodid(resultSet.getInt(1));
				HomeGoodItem.setInfo(resultSet.getString(2));
				HomeGoodItem.setPrice(resultSet.getBigDecimal(3));
				HomeGoodItem.setTbl(resultSet.getString(4));
				HomeGoodItem.setUserid(resultSet.getInt(5));
				HomeGoodItem.setGmt_modified(resultSet.getTimestamp(6));
				HomeGoodItem.setName(resultSet.getString(7));
				HomeGoodItem.setAvatar(resultSet.getString(8));
				HomeGoodItem.setLocked(resultSet.getBoolean(9));
				HomeGoodItem.setClicktimes(resultSet.getInt(10));
				list.add(HomeGoodItem);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodResult.setAllcount(resultSet.getInt(1));
			}
			HomeGoodResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return HomeGoodResult;
	}

	public HomeGoodResult getGoodsByNullSeelocked(Query Query){
		HomeGoodResult HomeGoodResult = new HomeGoodResult();
		LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
		String SQL = String.format(GET_NO_SEELOKED, Query.getOrder(),Query.getSort());
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			
			
			preparedStatment.setInt(1, (Query.getPageNo()-1) * Query.getPageSize());
			preparedStatment.setInt(2, Query.getPageSize());
			resultSet = preparedStatment.executeQuery();
			/**** 巨大天坑 *******************/
			/**
			 * 如果 把 resultItem 的初始化写在注释的位置，ls.add（resultItem） 永远添加的是相同的内容
			 * 原因：整个过程只产生了一个 resultItem的内存，list里面装的是指针，内存的内容永远只有一个，即使有四个指针
			 */
			// ResultItem resultItem = new ResultItem();
			while (resultSet.next()) {
				HomeGoodItem HomeGoodItem = new HomeGoodItem();
				HomeGoodItem.setGoodid(resultSet.getInt(1));
				HomeGoodItem.setInfo(resultSet.getString(2));
				HomeGoodItem.setPrice(resultSet.getBigDecimal(3));
				HomeGoodItem.setTbl(resultSet.getString(4));
				HomeGoodItem.setUserid(resultSet.getInt(5));
				HomeGoodItem.setGmt_modified(resultSet.getTimestamp(6));
				HomeGoodItem.setName(resultSet.getString(7));
				HomeGoodItem.setAvatar(resultSet.getString(8));
				HomeGoodItem.setLocked(resultSet.getBoolean(9));
				HomeGoodItem.setClicktimes(resultSet.getInt(10));
				list.add(HomeGoodItem);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodResult.setAllcount(resultSet.getInt(1));
			}
			HomeGoodResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return HomeGoodResult;
	}

	public HomeGoodResult getGoodsByNullNolocked(Query Query){
		HomeGoodResult HomeGoodResult = new HomeGoodResult();
		LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
		String SQL = String.format(GET_NO_NOLOKED, Query.getOrder(),Query.getSort());
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			
			
			preparedStatment.setInt(1, (Query.getPageNo()-1) * Query.getPageSize());
			preparedStatment.setInt(2, Query.getPageSize());
			
			resultSet = preparedStatment.executeQuery();
			/**** 巨大天坑 *******************/
			/**
			 * 如果 把 resultItem 的初始化写在注释的位置，ls.add（resultItem） 永远添加的是相同的内容
			 * 原因：整个过程只产生了一个 resultItem的内存，list里面装的是指针，内存的内容永远只有一个，即使有四个指针
			 */
			// ResultItem resultItem = new ResultItem();
			
			while (resultSet.next()) {
				HomeGoodItem HomeGoodItem = new HomeGoodItem();
				HomeGoodItem.setGoodid(resultSet.getInt(1));
				HomeGoodItem.setInfo(resultSet.getString(2));
				HomeGoodItem.setPrice(resultSet.getBigDecimal(3));
				HomeGoodItem.setTbl(resultSet.getString(4));
				HomeGoodItem.setUserid(resultSet.getInt(5));
				HomeGoodItem.setGmt_modified(resultSet.getTimestamp(6));
				HomeGoodItem.setName(resultSet.getString(7));
				HomeGoodItem.setAvatar(resultSet.getString(8));
				HomeGoodItem.setLocked(resultSet.getBoolean(9));
				HomeGoodItem.setClicktimes(resultSet.getInt(10));
				list.add(HomeGoodItem);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodResult.setAllcount(resultSet.getInt(1));
			}
			HomeGoodResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return HomeGoodResult;
	}

	public HomeGoodResult getGoodsByKeyAndCategorySeelocked(Query Query){
		HomeGoodResult HomeGoodResult = new HomeGoodResult();
		LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
		String SQL = String.format(GET_K_C_SEELOKED, Query.getOrder(),Query.getSort());
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			preparedStatment.setString(1, Query.getCategory());
			preparedStatment.setString(2, "%" + Query.getKey() + "%");
			
			preparedStatment.setInt(3, (Query.getPageNo()-1) * Query.getPageSize());
			preparedStatment.setInt(4, Query.getPageSize());
			
			resultSet = preparedStatment.executeQuery();
			/**** 巨大天坑 *******************/
			/**
			 * 如果 把 resultItem 的初始化写在注释的位置，ls.add（resultItem） 永远添加的是相同的内容
			 * 原因：整个过程只产生了一个 resultItem的内存，list里面装的是指针，内存的内容永远只有一个，即使有四个指针
			 */
			// ResultItem resultItem = new ResultItem();
			
			while (resultSet.next()) {
				HomeGoodItem HomeGoodItem = new HomeGoodItem();
				HomeGoodItem.setGoodid(resultSet.getInt(1));
				HomeGoodItem.setInfo(resultSet.getString(2));
				HomeGoodItem.setPrice(resultSet.getBigDecimal(3));
				HomeGoodItem.setTbl(resultSet.getString(4));
				HomeGoodItem.setUserid(resultSet.getInt(5));
				HomeGoodItem.setGmt_modified(resultSet.getTimestamp(6));
				HomeGoodItem.setName(resultSet.getString(7));
				HomeGoodItem.setAvatar(resultSet.getString(8));
				HomeGoodItem.setLocked(resultSet.getBoolean(9));
				HomeGoodItem.setClicktimes(resultSet.getInt(10));
				list.add(HomeGoodItem);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodResult.setAllcount(resultSet.getInt(1));
			}
			HomeGoodResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return HomeGoodResult;
	}

	public HomeGoodResult getGoodsByKeyAndCategoryNolocked(Query Query){
		HomeGoodResult HomeGoodResult = new HomeGoodResult();
		LinkedList<HomeGoodItem> list = new LinkedList<HomeGoodItem>();
		String SQL = String.format(GET_K_C_NOLOKED, Query.getOrder(),Query.getSort());
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(SQL);
			preparedStatment.setString(1, Query.getCategory());
			preparedStatment.setString(2, "%" + Query.getKey() + "%");
			
			preparedStatment.setInt(3, (Query.getPageNo()-1) * Query.getPageSize());
			preparedStatment.setInt(4, Query.getPageSize());
			
			resultSet = preparedStatment.executeQuery();
			/**** 巨大天坑 *******************/
			/**
			 * 如果 把 resultItem 的初始化写在注释的位置，ls.add（resultItem） 永远添加的是相同的内容
			 * 原因：整个过程只产生了一个 resultItem的内存，list里面装的是指针，内存的内容永远只有一个，即使有四个指针
			 */
			// ResultItem resultItem = new ResultItem();
			
			while (resultSet.next()) {
				HomeGoodItem HomeGoodItem = new HomeGoodItem();
				HomeGoodItem.setGoodid(resultSet.getInt(1));
				HomeGoodItem.setInfo(resultSet.getString(2));
				HomeGoodItem.setPrice(resultSet.getBigDecimal(3));
				HomeGoodItem.setTbl(resultSet.getString(4));
				HomeGoodItem.setUserid(resultSet.getInt(5));
				HomeGoodItem.setGmt_modified(resultSet.getTimestamp(6));
				HomeGoodItem.setName(resultSet.getString(7));
				HomeGoodItem.setAvatar(resultSet.getString(8));
				HomeGoodItem.setLocked(resultSet.getBoolean(9));
				HomeGoodItem.setClicktimes(resultSet.getInt(10));
				list.add(HomeGoodItem);
			}

			resultSet.close();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(GET_ALLCOUNT);
			resultSet = preparedStatment.executeQuery();
			while (resultSet.next()) {
				HomeGoodResult.setAllcount(resultSet.getInt(1));
			}
			HomeGoodResult.setList(list);
			resultSet.close();
			preparedStatment.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

		return HomeGoodResult;
	}

	

	private final String GET_GOOD_DETAIL = "select tbl,category,good.info,locked,good.gmt_modified,clicktimes,location,userid,avatar,name,user.info,price,seephone,seeqq from user,good where(user.id=good.userid and good.id = ?)";
	private final String GET_TAG = "select tag from tag where goodid = ?";
	private final String GET_PIC = "select pic from pic where goodid = ?";
	
	
	public GoodDetail getGoodDetail(int goodid){
		GoodDetail gd = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_GOOD_DETAIL);
			preparedStatment.setInt(1, goodid);
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				gd = new GoodDetail();
				gd.setTbl(resultSet.getString(1));
				gd.setCategory(resultSet.getString(2));
				gd.setGoodinfo(resultSet.getString(3));
				gd.setLocked(resultSet.getBoolean(4));
				gd.setTime(resultSet.getTimestamp(5));
				gd.setClicktimes(resultSet.getInt(6));
				gd.setLocation(resultSet.getString(7));
				gd.setUserid(resultSet.getInt(8));
				gd.setAvatar(resultSet.getString(9));
				gd.setName(resultSet.getString(10));
				gd.setUserinfo(resultSet.getString(11));
				gd.setPrice(resultSet.getBigDecimal(12));
				gd.setSeephone(resultSet.getBoolean(13));
				gd.setSeeqq(resultSet.getBoolean(14));
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return gd;
		
	}

	public String getTagString(int goodid){
		StringBuffer tags = new StringBuffer();
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_TAG);
			preparedStatment.setInt(1, goodid);
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				tags.append(resultSet.getString(1)).append("-");
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return tags.toString();
	}
	public String getPicString(int goodid){
		StringBuffer pics = new StringBuffer();
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_PIC);
			preparedStatment.setInt(1, goodid);
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				pics.append(resultSet.getString(1)).append("-");
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return pics.toString();
	}

	private final String ADD_CLICKTIMES = "update good set clicktimes=clicktimes+1 where id = ?";
	public void addClicktimes(int goodid){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(ADD_CLICKTIMES);
			preparedStatment.setInt(1, goodid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}
	private final String LOCKE_GOOD = "update good set locked = true,lockeduserid=?,lockedtime=now() where id =?;";
	public void lockGood(int goodid,int lockeduserid){
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(LOCKE_GOOD);
			preparedStatment.setInt(1, lockeduserid);
			preparedStatment.setInt(2, goodid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private final String GET_INFO = "select info from good where id=?";
	public String getInfo(int goodid){
		String info = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_INFO);
			preparedStatment.setInt(1, goodid);
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

	private final String GET_GOOD_LOCKED = "select locked from good where id = ?";
	public boolean goodLocked(int goodid){
		boolean goodLocked = false;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_GOOD_LOCKED);
			preparedStatment.setInt(1, goodid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				goodLocked=resultSet.getBoolean(1);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goodLocked;
		
		
	}

	private final String GET_GOODCONTACT = "select seephone,seeqq,phone,qq,name,avatar,user.id,good.id,good.info,good.locked,good.tbl,lockeduserid from user,good where (user.id = ? and good.id=? and good.userid=user.id)"; 
	public GoodContact getGoodContact(int taruserid,int goodid){
		GoodContact gc = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_GOODCONTACT);
			preparedStatment.setInt(1, taruserid);
			preparedStatment.setInt(2, goodid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				gc =new GoodContact();
				gc.setSeePhone(resultSet.getBoolean(1));
				gc.setSeeQq(resultSet.getBoolean(2));
				gc.setPhone(resultSet.getString(3));
				gc.setQq(resultSet.getString(4));
				gc.setName(resultSet.getString(5));
				gc.setAvatar(resultSet.getString(6));
				gc.setUserid(resultSet.getInt(7));
				gc.setGoodid(resultSet.getInt(8));
				gc.setNeedInfo(resultSet.getString(9));
				gc.setLocked(resultSet.getBoolean(10));
				gc.setTbl(resultSet.getString(11));
				gc.setLockeduserid(resultSet.getInt(12));
			}
			resultSet.close();
			preparedStatment.close();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return gc;
	}
	private final String ADD_GOOD_MSG = "insert into goodmsg(srcuserid,taruserid,goodid,msgcontent,type) values(?,?,?,?,'锁定')";
	public void addGoodMsg(PhoneMsg pmsg){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(ADD_GOOD_MSG);
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
	public void addGoodMsg(String msgcontent,int goodid,int taruserid,int srcuserid){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(ADD_GOOD_MSG);
			preparedStatment.setString(4, msgcontent);
			preparedStatment.setInt(3, goodid);
			preparedStatment.setInt(2, taruserid);
			preparedStatment.setInt(1, srcuserid);
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private final String GET_MYGOOD_ITEM= "select tbl,category,info,locked,gmt_modified,lockedtime,clicktimes,lockeduserid,price,id,userid from good where(userid = ?)";
	private final String GET_LOCKED_USER = "select name,avatar,info, seeqq from user where id = ?";
	public LinkedList<MyGoodItem> getMyGoodItem(int userid){
		LinkedList<MyGoodItem> list = new LinkedList<MyGoodItem>();
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_MYGOOD_ITEM);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				MyGoodItem g = new MyGoodItem();
				g.setTbl(resultSet.getString(1));
				g.setCategory(resultSet.getString(2));
				g.setGoodinfo(resultSet.getString(3));
				g.setLocked(resultSet.getBoolean(4));
				g.setUptime(resultSet.getTimestamp(5));
				g.setLockedtime(resultSet.getTimestamp(6));
				g.setClicktimes(resultSet.getInt(7));
				g.setLockeduserid(resultSet.getInt(8));
				g.setPrice(resultSet.getBigDecimal(9));
				g.setGoodid(resultSet.getInt(10));
				g.setUserid(resultSet.getInt(11));
				list.add(g);
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
	public MyGoodItem getLockedUser(MyGoodItem g){
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_LOCKED_USER);
			preparedStatment.setInt(1, g.getLockeduserid());
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				g.setLockedname(resultSet.getString(1));
				g.setLockedavatar(resultSet.getString(2));
				g.setLockedseeqq(resultSet.getBoolean(4));
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return g;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return g;
		}
		
		
	}
	private final String GET_LOCKED_USER_ID = "select lockeduserid from good where id =?";
	public int getLockedUserId(int goodid){
		int userid=0;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_LOCKED_USER_ID);
			preparedStatment.setInt(1, goodid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				userid = resultSet.getInt(1);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return userid;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	private final String REMOVE_GOOD = "delete from good where id = ?";
	private final String REMOVE_GOOD_PICS = "delete from pic where goodid=?";
	private final String REMOVE_GOOD_TAGS = "delete from tag where goodid=?";
	public void removeGood(int goodid){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(REMOVE_GOOD_TAGS);
			preparedStatment.setInt(1, goodid);
			
			preparedStatment.executeUpdate();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(REMOVE_GOOD_PICS);
			preparedStatment.setInt(1, goodid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			preparedStatment = connection.prepareStatement(REMOVE_GOOD);
			preparedStatment.setInt(1, goodid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void removePics(int goodid){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(REMOVE_GOOD_PICS);
			preparedStatment.setInt(1, goodid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void removeTags(int goodid){
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(REMOVE_GOOD_TAGS);
			preparedStatment.setInt(1, goodid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private final String GET_TBL = "select tbl from good where id = ?";
	public String getTbl(int goodid){
		String tbl = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_TBL);
			preparedStatment.setInt(1, goodid);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()){
				tbl = resultSet.getString(1);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return tbl;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	private final String GET_MYLOCK_GOOD_ITEM= "select tbl,category,info,locked,gmt_modified,lockedtime,clicktimes,lockeduserid,price,id,userid from good where(lockeduserid = ? and locked=true)";
	public LinkedList<MyGoodItem> getMyLockGoodItem(int userid) {
		LinkedList<MyGoodItem> list = new LinkedList<MyGoodItem>();
		
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_MYLOCK_GOOD_ITEM);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				MyGoodItem g = new MyGoodItem();
				g.setTbl(resultSet.getString(1));
				g.setCategory(resultSet.getString(2));
				g.setGoodinfo(resultSet.getString(3));
				g.setLocked(resultSet.getBoolean(4));
				g.setUptime(resultSet.getTimestamp(5));
				g.setLockedtime(resultSet.getTimestamp(6));
				g.setClicktimes(resultSet.getInt(7));
				g.setLockeduserid(resultSet.getInt(8));
				g.setPrice(resultSet.getBigDecimal(9));
				g.setGoodid(resultSet.getInt(10));
				g.setUserid(resultSet.getInt(11));
				list.add(g);
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
	
	private final String GET_PQ = "select phone,qq,seephone,seeqq,lockeduserid from user,good where (good.lockeduserid=user.id and lockeduserid = ? and good.id=?)";
	public PhoneQQ getPQ(int lockeduserid,int goodid){
		PhoneQQ pq = null;
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_PQ);
			preparedStatment.setInt(1,lockeduserid);
			preparedStatment.setInt(2, goodid);
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
	private final String CLEAR_LOCK = "update good set locked = false where id = ?";
	public void clearLock(int goodid) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(CLEAR_LOCK);
			preparedStatment.setInt(1, goodid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private final String BAN_LOCK = "insert into goodcantlocked(userid,goodid) values(?,?)";
	public void banLock(int userid,int goodid) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(BAN_LOCK);
			preparedStatment.setInt(1, userid);
			preparedStatment.setInt(2, goodid);
			preparedStatment.executeUpdate();
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String GET_BANLIST = "select userid from goodcantlocked where goodid = ?";
	public List<Integer> getBanList(int goodid) {
		List<Integer> banList = new LinkedList<Integer>();
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_BANLIST);
			preparedStatment.setInt(1, goodid);
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
	private final String GET_MYMSG = "select goodmsg.id,goodmsg.gmt_create,taruserid,goodid,msgcontent,readed,tbl,name,avatar,srcuserid,phone,qq,seeqq,seephone from goodmsg,good,user where (taruserid = ? and goodmsg.goodid=good.id and user.id = goodmsg.srcuserid)";
	public List<MyMsg> listMyGoodMsg(List<MyMsg> list,int taruserid){
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
				msg.setTbl(resultSet.getString(7));
				msg.setSrcname(resultSet.getString(8));
				msg.setSrcavatar(resultSet.getString(9));
				msg.setSrcuserid(resultSet.getInt(10));
				msg.setSrcphone(resultSet.getString(11));
				msg.setSrcqq(resultSet.getString(12));
				msg.setSeeqq(resultSet.getBoolean(13));
				msg.setSeephone(resultSet.getBoolean(14));
				msg.setMsgtype("goodmsg");
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
	private final String DELETE_MSG = "delete from goodmsg where id=?";
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
	private final String MSG_READ = "update goodmsg set readed = true where taruserid= ?";
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
	
	private final String GET_OTHER_USER_GOODS="select tbl,id,info,clicktimes,price,userid from good where userid = ?";
	public List<OtherGoodItem> getUserOthersGood(int userid,List<OtherGoodItem> list) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_OTHER_USER_GOODS);
			preparedStatment.setInt(1, userid);
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				OtherGoodItem g = new OtherGoodItem();
				g.setTbl(resultSet.getString(1));
				g.setGoodid(resultSet.getInt(2));
				g.setInfo(resultSet.getString(3));
				g.setClicktimes(resultSet.getInt(4));
				g.setPrice(resultSet.getBigDecimal(5));
				g.setUserid(resultSet.getInt(6));
				list.add(g);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
	}
	private final String GET_OTHER_CATEGORY_GOODS="select tbl,id,info,clicktimes,price,userid from good where category = ? order by gmt_modified desc limit 0,3";
	public List<OtherGoodItem> getCategoryOthersGood(String category,List<OtherGoodItem> list) {
		try {
			connection = JdbcUtil.getConnection();
			preparedStatment = connection.prepareStatement(GET_OTHER_CATEGORY_GOODS);
			preparedStatment.setString(1, category);
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()){
				OtherGoodItem g = new OtherGoodItem();
				g.setTbl(resultSet.getString(1));
				g.setGoodid(resultSet.getInt(2));
				g.setInfo(resultSet.getString(3));
				g.setClicktimes(resultSet.getInt(4));
				g.setPrice(resultSet.getBigDecimal(5));
				g.setUserid(resultSet.getInt(6));
				list.add(g);
			}
			resultSet.close();
			preparedStatment.close();
			connection.close();
			return list;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
	}
}
