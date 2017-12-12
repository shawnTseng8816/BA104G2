package com.rem_record.model;

import java.util.List;


public interface RemRecordDAO_interface {
	//查詢尚未審核匯款清單
	 public List<RemRecordVO> getApply();
	 //查詢已審核匯款清單
	 public List<RemRecordVO> getFinish();
	 
	 //匯款審核通過
	 public void pass(RemRecordVO remvo);
	 
	 //匯款審核不通過
	 public void noPass(RemRecordVO remvo);
	 
	//取得店家剩餘點數
	public Integer getStoRemPoint(String sto_num);
	
	//新增一筆店家匯款資料
	public void insertRem(RemRecordVO remVO);
	
	public List<RemRecordVO> getOneStoRemInfo(String sto_num);
}
