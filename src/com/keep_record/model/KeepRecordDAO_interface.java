package com.keep_record.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.order_master.model.OrderDetailToolVO;

public interface KeepRecordDAO_interface {
	
//---------------KAHN-------------------------	
	public String insertKeepRecord(KeepRecordVO keeprecordVO);
	
	public void deleteKeepRecord(KeepRecordVO keeprecordVO);
	
	public Set<KeepRecordVO> getKeepRecordByMem_num(String mem_num);
	
	public KeepRecordVO getKeepRecordByOrdetNum(String OrdetNum);
//--------------end KAHN---------------------------
	
//-------------Shawn------------------------------
//	public Set<KeepRecordVO> getKeepRecordByMem_num(String mem_num);

	public void updateKeepRecord(KeepRecordVO keepRecordVO);
	
	public Set<KeepRecordVO> getKeepRecordByStatus(String mem_num, String keep_status);
//------------end Shawn------------------------------
	
//------------Eric----------------------------
	//取得某店家所有未領取寄杯紀錄
		public List<KeepRecordVO> getOneStoKeepRecordNoFinish(String sto_num);
		//取得某店家所有已省核寄杯紀錄
		public List<KeepRecordVO> getOneStoKeepRecordFinish(String sto_num);
		
		//取得某店家所有已省核寄杯紀錄
		public void UpdateKeepRecord(String keep_num,String keep_status);
		
		List<OrderDetailToolVO> getOrderDetailTool(String ordet_num);
		
		public List<KeepRecordVO> getKeepInfoByMobile(String mobile,String sto_num);
		public List<KeepRecordVO> getKeepInfo(Map<String, String[]> map);
		
//------------end Eric----------------------------------		
}
