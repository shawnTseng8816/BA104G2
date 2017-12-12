package com.card.model;

import java.util.List;

public class CardService {
	CardDAO_interface dao;
	
	public CardService(){
		dao = new CardDAO();
	}
	// 新增一張集點卡
	public void insertCard(CardVO cardVO) {
		dao.insertCard(cardVO);
	}

	// 上架集點卡
	public void upCard(String sto_num,String card_kinds){
		dao.upCard(sto_num, card_kinds);
	}
	
	// 下架集點卡
		public void downCard(String sto_num){
			dao.downCard(sto_num);
		}

	// 後台取得所有集點卡資訊
	public List<CardVO> getall() {
		return null;
	}

	// 店家查詢自己所有集點卡
	public List<CardVO> getCardsBySto_num(String sto_num) {
		return dao.getCardsBySto_num(sto_num) ;
	}
	
	
	
	//取得店家目前的集點卡資訊
	public CardVO getStoNowCrad(String sto_num){
		return dao.getStoNowCrad(sto_num);
	}
	
	//取得單張集點卡資訊
	public CardVO getOneCradInfo(String card_kinds) {
		return dao.getOneCradInfo(card_kinds);
	}
}
