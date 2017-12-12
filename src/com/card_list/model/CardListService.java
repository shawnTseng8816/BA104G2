package com.card_list.model;

import java.util.List;

public class CardListService {

	private CardListDAO_interface dao;

	public CardListService() {

		dao = new CardListDAO();
	}

	// 查詢單個會員集點卡清單
	public List<CardListVO> getCardListByMem_num(String mem_num){
		return dao.getCardListByMem_num(mem_num);
	}

	

	// 後台查詢所有集點卡清單
	public List<CardListVO> getAll(){
		return dao.getAll();		
	}
	
	public List<CardListVO> getMyCard(String sto_num, String mem_num){
		
		CardListVO cardListVO = new CardListVO();
		
		cardListVO.setSto_num(sto_num);
		cardListVO.setMem_num(mem_num);
		cardListVO.setStatus("可使用");
		
		return dao.getCardListByMem_numAndSto_num(cardListVO);		
	}
	
	public CardListVO getCardInfo(String card_num){
		return dao.getCardInfo(card_num);
	}
	
	public void upDateMyCard(String card_num, String or_num){
		dao.updateMyCard(card_num, or_num);
	}
	
	
	
//----------Eric----------------------
	public int getCardCash(String or_num){
		return dao.getCardCash(or_num);
	}
	// 查詢單個會員集點卡清單
//	public List<CardListVO> getCardListByMem_num(String mem_num){
//		return dao.getCardListByMem_num(mem_num);
//	}
//
//	
//
//	// 後台查詢所有集點卡清單
//	public List<CardListVO> getAll(){
//		return dao.getAll();		
//	}
//----------end Eric-------------------	

}
