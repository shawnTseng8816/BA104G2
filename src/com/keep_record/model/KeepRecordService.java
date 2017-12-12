package com.keep_record.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.order_master.model.OrderDetailToolVO;

public class KeepRecordService {

	private KeepRecordDAO_interface dao;

	public KeepRecordService() {
		dao = new KeepRecordDAO();
	}
	
//------------KHAN------------------------	
	public String insertKeepRecord(String mem_num, String sto_num, String com_num, String ordet_num){
		
		KeepRecordVO keepRecordVO = new KeepRecordVO();
		
		keepRecordVO.setMem_num(mem_num);
		keepRecordVO.setSto_num(sto_num);
		keepRecordVO.setCom_num(com_num);
		keepRecordVO.setOrdet_num(ordet_num);
		keepRecordVO.setKeep_status("確認中");
		
		return dao.insertKeepRecord(keepRecordVO);
	}

	public Set<KeepRecordVO> getKeepRecordByMem_num(String mem_num) {
		return dao.getKeepRecordByMem_num(mem_num);
	}
	
	public void deleteKeepRecord(String keep_num){
		
		KeepRecordVO keepRecordVO = new KeepRecordVO();
		
		keepRecordVO.setKeep_num(keep_num);
		
		dao.deleteKeepRecord(keepRecordVO);
	}
	
	public KeepRecordVO getKeepRecordByDetpNum(String deptNum) {
		return dao.getKeepRecordByOrdetNum(deptNum);
	}
//-------------end KHAN------------------------------------
	
	
//-------------Shawn---------------------------------------
//	public Set<KeepRecordVO> getKeepRecordByMem_num(String mem_num) {
//		return dao.getKeepRecordByMem_num(mem_num);
//	}
	
	public KeepRecordVO updateKeepRecord(String keep_status, String keep_num) {

		KeepRecordVO keepRecordVO = new KeepRecordVO();

		keepRecordVO.setKeep_status(keep_status);
		keepRecordVO.setKeep_num(keep_num);

		dao.updateKeepRecord(keepRecordVO);

		return keepRecordVO;
	}
	
	public Set<KeepRecordVO> getKeepRecordByStatus(String mem_num, String keep_status) {
		return dao.getKeepRecordByStatus(mem_num, keep_status);
	}
	
//----------------end Shawn-----------------------------------------	
	
//----------------Eric---------------------------------------------
	
	public List<KeepRecordVO> getOneStoKeepRecordNoFinish(String sto_num) {

		return dao.getOneStoKeepRecordNoFinish(sto_num);
	}

	public List<KeepRecordVO> getOneStoKeepRecordFinish(String sto_num) {

		return dao.getOneStoKeepRecordFinish(sto_num);
	}

	
	public List<KeepRecordVO> getKeepInfo(Map<String, String[]> map){
		return dao.getKeepInfo(map);
	}
	
	
	//更新寄杯狀態
	public void UpdateKeepRecord(String keep_num,String keep_status){
		dao.UpdateKeepRecord(keep_num, keep_status);
	}
	public List<KeepRecordVO> getKeepInfoByMobile(String mobile,String sto_num){
		return dao.getKeepInfoByMobile(mobile,sto_num);
	}
	
	public List<OrderDetailToolVO> getOrderDetailTool(String ordet_num) {
	
	return dao.getOrderDetailTool(ordet_num);
	}
	
//---------end Eric-----------------------------------------	
}
