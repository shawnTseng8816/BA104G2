package com.store_comment.model;

import java.util.List;
import java.util.Map;

public interface StoreCommentDAO_interface {
	
//--------KAHN-------------	
	public void update(StoreCommentVO storeCommentVO);
	
	public List<List> getMyComment(StoreCommentVO storeCommentVO, int rows);
	
	
public List<StoreCommentVO> geStoreCommentBySto_num(String sto_num);

	
	public Map<String, Integer> getStoreStars();
//------end KAHN--------------
	
//-----Shawn----------
	
//public List<StoreCommentVO> geStoreCommentBySto_num(String sto_num);
	
	public StoreCommentVO getStoreComment(String sto_num);
	
	public List<StoreCommentVO> geStoreCommentBySto_numStatus(String sto_num, String status);
	
	public void updateCommentProfile(StoreCommentVO storeCommentVO);
	
//-------end Shawn---------	

//-----Eric------------
//	public void update(StoreCommentVO storeCommentVO);

	public void insert(StoreCommentVO storeCommentVO);
//	public List<List> getMyComment(StoreCommentVO storeCommentVO, int rows);
	
	public String checkComment(String or_num);
	
//-------end Eric------	
}
