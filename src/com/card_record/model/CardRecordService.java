package com.card_record.model;

import java.util.List;

public class CardRecordService {

	private CardRecordDAO_interface dao;

	public CardRecordService() {
		dao = new CardRecordDAO();
	}
	
	//後台查詢所有集點紀錄清單
	public List<CardRecordVO> getAll() {
		return dao.getAll();
	}

	// 取得某個會員的集點歷史紀錄
	public List<CardRecordVO> getMemRecordByMemNum(String mem_num) {
		return dao.getMemRecordByMemNum(mem_num);

	}
}
