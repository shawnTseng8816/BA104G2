package com.order_master.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.card.model.CardVO;

public interface OrderMasterDAO_interface {
	
	public String insertOrderMaster(OrderMasterVO orderMaster);
	
	public void updateOrderMaster(OrderMasterVO orderMaster);
	
	public OrderMasterVO getEditOrderMaster(OrderMasterVO orderMaster);
	
	public List<OrderMasterVO> getGroupBuyOrderMaster(String meror_num, String or_stanum);
	
	public List<OrderMasterVO> getGroupBuyOrderMasterWithoutPayMent(String meror_num);
	
//------------Eric----------------
	//取得店家目前上架卡片
		public CardVO getStoNowCard(String sto_num);
		
		public void upDateGame(String or_num,Integer value_cash);
			
		
		//複合查詢 訂單資訊
		public List<OrderMasterVO> getOrderInfo(Map<String,String[]> map);
		//回傳發起人會員編號
		public String getGroupMaster(String or_num);
		
		public Integer getPlay(String or_num);
		
		//取得 團購總點數
		public OrderDetailToolVO getGroupOrderPoint(String meror_num); 
		//取得訂單明細中文 團購####
		public List<OrderDetailToolVO> getGroupOrderDetailTool(String meror_num);
		//取得訂單明細中文####
		public List<OrderDetailToolVO> getOrderDetailTool(String or_num);
		
		public Map searchMemCard(String sto_num,String mem_num);
		
		//取得訂單總杯數
		public int howManyCups(OrderMasterVO orderMasterVO);
		
		//取得一張訂單明細
		public OrderMasterVO getOneOrder(String or_num);
		
		//完成交易沒有使用集點卡
		public void finishNoCard(List<Map> all);
		
		//訂單通過審核 團購
		public void groupOrderPass(String meror_num) ;
		
		//完成交易使用集點卡
		public void finish(List<Map> map);
		
		public List<OrderMasterVO> getOneStoFinishOrder(String sto_num);
		//取得店家待審核訂單
		public List<OrderMasterVO> getOneStoWaitModif(String sto_num);
		
		//取得店家處理中訂單
		public List<OrderMasterVO> getOneStoPassOrder(String sto_num);
			
		//訂單審核失敗
		public void orderCancel(OrderMasterVO orderMasterVO);
		
		
		//取得某店家所有訂單
		public List<OrderMasterVO> getOneStoAllOrder(String sto_num);
		
		//訂單通過審核
		public void orderPass(String or_num);
		
		//訂單準備好外送 或 待領取
		public void orderAlready(String or_num,String orsta_num);
		
		//取得某會員已完成交易訂單
		public List<OrderMasterVO> getOneMemFinishOrder(String mem_num);
		
		//某會員未完成交易訂單
		public List<OrderMasterVO> getOneMemNoFinishOrder(String mem_num);
			
		//訂單準備好外送 或 待領取 團購
		public void groupOrderAlready(String meror_num, String orsta_num);
		
		public  List<OrderMasterVO> getStoPointOrder(String sto_num);
		// 取得團購所有訂單 資訊
		public List<OrderMasterVO> getGroupAllOrderNumByMerorNum(String meror_num);
		
		//取得團購寄杯明細
		public List<OrderDetailToolVO> getGroupKeepDetailTool(String meror_num);
		
		public List<OrderDetailToolVO> getKeepDetailTool(String or_num);
	
		public List<OrderMasterVO> getPointOrder(String mem_num);
//--------end Eric----------------	
	
}
