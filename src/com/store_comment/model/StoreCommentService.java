package com.store_comment.model;

import java.util.List;
import java.util.Map;

public class StoreCommentService {

	private StoreCommentDAO_interface dao;

	public StoreCommentService() {
		dao = new StoreCommentDAO();
	}

//------------KAHN-------------	
	public StoreCommentVO updateMyComment(String com_title, int Stars, String comment, String com_num) {

		StoreCommentVO stoCommentVO = new StoreCommentVO();

		stoCommentVO.setCom_title(com_title);
		stoCommentVO.setStars(Stars);
		stoCommentVO.setCommentt(comment);
		stoCommentVO.setStatus("一般");
		stoCommentVO.setCom_num(com_num);

		dao.update(stoCommentVO);
		
		return stoCommentVO;
	}

	public StoreCommentVO deleteMyComment(String com_num) {
		
		StoreCommentVO stoCommentVO = new StoreCommentVO();
		
		stoCommentVO.setStatus("刪除");
		stoCommentVO.setCom_num(com_num);

		dao.update(stoCommentVO);

		return stoCommentVO;
	}

	public List<List> getMyComments(String mem_num, int rows) {


		StoreCommentVO stoCommentVO = new StoreCommentVO();

		stoCommentVO.setMem_num(mem_num);
		stoCommentVO.setStatus("一般");

		return dao.getMyComment(stoCommentVO, rows);
	}
	
	
	
	public List<StoreCommentVO> geStoreCommentBySto_num(String sto_num) {
		return dao.geStoreCommentBySto_num(sto_num);
	}
	
	public Map<String, Integer> getStoreStars(){
		return dao.getStoreStars();
	}
//-------end KAHN----------------
	
	
//---------Shawn-------------------
	
//	public List<StoreCommentVO> geStoreCommentBySto_num(String sto_num) {
//		return dao.geStoreCommentBySto_num(sto_num);
//	}

	public StoreCommentVO getStoreComment(String com_num) {
		return dao.getStoreComment(com_num);
	}
	
	public List<StoreCommentVO> geStoreCommentBySto_numStatus(String sto_num, String status){
		return dao.geStoreCommentBySto_numStatus(sto_num, status);
	}
	
	
	public StoreCommentVO updateCommentProfile(String status, String com_num) {

		StoreCommentVO storeCommentVO = new StoreCommentVO();

		storeCommentVO.setStatus(status);
		storeCommentVO.setCom_num(com_num);

		dao.updateCommentProfile(storeCommentVO);

		return storeCommentVO;
	}
//-------end Shawn----------------	
	
//--------Eric----------------
//	public StoreCommentVO updateMyComment(String com_title, int Stars, String comment, String com_num) {
//
//		StoreCommentVO stoCommentVO = new StoreCommentVO();
//
//		stoCommentVO.setCom_title(com_title);
//		stoCommentVO.setStars(Stars);
//		stoCommentVO.setCommentt(comment);
//		stoCommentVO.setStatus("一般");
//		stoCommentVO.setCom_num(com_num);
//
//		dao.update(stoCommentVO);
//
//		return stoCommentVO;
//	}
//
//	public StoreCommentVO deleteMyComment(String com_num) {
//		
//		StoreCommentVO stoCommentVO = new StoreCommentVO();
//		
//		stoCommentVO.setStatus("刪除");
//		stoCommentVO.setCom_num(com_num);
//
//		dao.update(stoCommentVO);
//
//		return stoCommentVO;
//	}
//
//	public List<List> getMyComments(String mem_num, int rows) {
//
//		StoreCommentVO stoCommentVO = new StoreCommentVO();
//
//		stoCommentVO.setMem_num(mem_num);
//		stoCommentVO.setStatus("一般");
//
//		return dao.getMyComment(stoCommentVO, rows);
//	}
	
	public void insert(StoreCommentVO storeCommentVO){
		dao.insert(storeCommentVO);
	}
	public String checkComment(String or_num){
		return dao.checkComment(or_num);
	}
	
//--------end Eric---------------------------	
	
}
