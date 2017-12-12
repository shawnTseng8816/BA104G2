package com.value_record.model;

import java.sql.Date;
import java.util.List;

import account.AccVO;

public class ValueRecordService {
	
	private ValueRecordDAO_interface dao;
	
	
	public ValueRecordService(){
		dao = new ValueRecordDAO();
	}
	
	//查詢所有儲值明細
	public List<ValueRecordVO> getAll(){
		return dao.getAll();
	}
	
	//儲值點數
	public void addValue(String mem_num, Integer value_cash) {

		ValueRecordVO value_RecordVO = new ValueRecordVO();
		value_RecordVO.setMem_num(mem_num);
		value_RecordVO.setValue_cash(value_cash);
		dao.addValue(mem_num, value_cash);
		
	}
	
	//取得個人儲值資訊
	public List<ValueRecordVO> getMyRecord(String mem_num){
		return dao.getMyRecord(mem_num);
	}
	
}
