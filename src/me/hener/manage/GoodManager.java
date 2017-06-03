package me.hener.manage;

import me.hener.dao.GoodDAO;
import me.hener.dto.Query;
import me.hener.dto.HomeGoodResult;

public class GoodManager {

	
	public HomeGoodResult getHomeGoodResult(Query query){

		GoodDAO goodDAO = new GoodDAO();
		HomeGoodResult homeGoodResult = null;
		if(!query.getEnter().equals("pageitem")){
			query.setPageNo(1);
		}
		if(query.getEnter().equals("key")&&!query.getKey().equals("all")||(!query.getKey().equals("all")&&query.getCategory().equals("all"))){
			if(query.isNolocked()){
				homeGoodResult = goodDAO.getGoodsByKeyNolocked(query);
				
			}else{
				homeGoodResult = goodDAO.getGoodsByKeySeelocked(query);
			}
		}
		if(!query.getEnter().equals("key")&&!query.getCategory().equals("all")&&(query.isNokey()||query.getKey().equals("all"))){
			if(query.isNolocked()){
			
				homeGoodResult = goodDAO.getGoodsByCategoryNolocked(query);
			}else{
				
				homeGoodResult = goodDAO.getGoodsByCategorySeelocked(query);
			}
		}
		if(!query.getEnter().equals("key")&&(!query.getCategory().equals("all")&&!query.isNokey()&&!query.getKey().equals("all"))){
			
			if(query.isNolocked()){
				
				homeGoodResult = goodDAO.getGoodsByKeyAndCategoryNolocked(query);
			}else{
				
				homeGoodResult = goodDAO.getGoodsByKeyAndCategorySeelocked(query);
			}
		}
		if((query.getCategory().equals("all")&&query.isNokey())||(query.getEnter().equals("key")&&query.getKey().equals("all"))||(query.getKey().equals("all")&&query.getCategory().equals("all"))){
			
			if(query.isNolocked()){
				
				homeGoodResult = goodDAO.getGoodsByNullNolocked(query);
			}else{

				homeGoodResult = goodDAO.getGoodsByNullSeelocked(query);
			}
		}
		homeGoodResult.setPageNo(query.getPageNo());
		homeGoodResult.setPageSize(query.getPageSize());
		return homeGoodResult;
	}
	
}
