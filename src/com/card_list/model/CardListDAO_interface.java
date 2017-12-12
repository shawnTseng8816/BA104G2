package com.card_list.model;

import java.util.List;

public interface CardListDAO_interface {
	
	//查詢單個會員集點卡清單
	public List<CardListVO> getCardListByMem_num(String mem_num);
	
	
	//後台查詢所有集點卡清單
	public List<CardListVO> getAll();
	
	public void newCard();
	
	public void updateMyCard(String card_num, String or_num);
	
	public List<CardListVO> getCardListByMem_numAndSto_num(CardListVO cardListVO);
	
	public CardListVO getCardInfo(String card_num);
	
	
//----------Eric-------------	
	//查詢單個會員集點卡清單
//	public List<CardListVO> getCardListByMem_num(String mem_num);
		
		
		//後台查詢所有集點卡清單
//	public List<CardListVO> getAll();
		
//	public void newCard();
	
	public int getCardCash(String or_num);
//------end Eric---------------		
		
}
