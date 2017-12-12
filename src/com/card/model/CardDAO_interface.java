package com.card.model;

import java.util.List;

public interface CardDAO_interface {
	
	//新增一張集點卡
	public void insertCard(CardVO cardVO);
	
	//上架集點卡
	public void upCard(String sto_num,String card_kinds);
	
	//下架集點卡
	public void downCard(String sto_num);
	
	//取得店家目前的集點卡資訊
	public CardVO getStoNowCrad(String sto_num);
	
	//後台取得所有集點卡資訊
	public List<CardVO> getall();
	
	//店家查詢自己所有集點卡
	public List<CardVO> getCardsBySto_num(String sto_num);
	
	
	public CardVO getOneCradInfo(String card_kinds);

}
