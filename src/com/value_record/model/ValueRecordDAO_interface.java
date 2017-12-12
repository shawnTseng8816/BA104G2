package com.value_record.model;

import java.util.List;

import account.AccVO;

public interface ValueRecordDAO_interface {

	public void addValue(String mem_num, Integer value_cash);

	public List<ValueRecordVO> getAll();
	
	public List<ValueRecordVO> getMyRecord(String mem_num);
	

}
