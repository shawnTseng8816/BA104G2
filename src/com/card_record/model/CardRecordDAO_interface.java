package com.card_record.model;

import java.util.List;


public interface CardRecordDAO_interface {

	
	//後台查詢所有集點紀錄清單
	public List<CardRecordVO> getAll();
	
	//取得某個會員的集點歷史紀錄
	public List<CardRecordVO> getMemRecordByMemNum(String mem_num);
	
}
