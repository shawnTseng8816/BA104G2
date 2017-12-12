package com.order_master.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.card.model.CardVO;

public class OrderMasterService {
	
	private OrderMasterDAO_interface dao;

	public OrderMasterService() {
		dao = new OrderMasterDAO();
	}
	
	public List<OrderMasterVO> getPointOrder(String mem_num){
		return dao.getPointOrder(mem_num);
	}
	
	public String addNewOrderMaster(String mem_num, String sto_num, int or_amount, String meror_num) {

		OrderMasterVO orderMasterVO = new OrderMasterVO();

		orderMasterVO.setMem_num(mem_num);
		orderMasterVO.setSto_num(sto_num);
		orderMasterVO.setOr_stanum("修改中");
		orderMasterVO.setOr_amount(or_amount);
		orderMasterVO.setMeror_num(meror_num);

		return dao.insertOrderMaster(orderMasterVO);
	}

	
	public OrderMasterVO updateMyOrderMaster(String or_stanum, String rece, String pay_men, Integer or_amount, String meror_num, String address, String or_num) {

		OrderMasterVO orderMasterVO = new OrderMasterVO();

		orderMasterVO.setOr_stanum(or_stanum);
		orderMasterVO.setRece(rece);
		orderMasterVO.setPay_men(pay_men);
		orderMasterVO.setOr_num(or_num);
		orderMasterVO.setMeror_num(meror_num);
		orderMasterVO.setOr_amount(or_amount);
		orderMasterVO.setAddress(address);
		
		dao.updateOrderMaster(orderMasterVO);

		return orderMasterVO;
	}
	
	public OrderMasterVO deleteMyOrderMaster(String or_num) {

		OrderMasterVO orderMasterVO = new OrderMasterVO();

		orderMasterVO.setOr_num(or_num);

		dao.updateOrderMaster(orderMasterVO);

		return orderMasterVO;
	}
	
	public OrderMasterVO getMyOrderMaster(String or_num) {

		OrderMasterVO orderMasterVO = new OrderMasterVO();

		orderMasterVO.setOr_num(or_num);
		orderMasterVO.setOr_stanum("修改中");

		return dao.getEditOrderMaster(orderMasterVO);
	}
	
	public List<OrderMasterVO> getGroupBuyOrderMaster(String meror_num, String or_stanum) {
		return dao.getGroupBuyOrderMaster(meror_num, or_stanum);
	}
	
	public List<OrderMasterVO> getGroupBuyOrderMasterWithoutPayMent(String meror_num) {
		return dao.getGroupBuyOrderMasterWithoutPayMent(meror_num);
	}

	
//---------------Eric----------------------
	public List<OrderMasterVO> getOneStoFinishOrder(String sto_num){
		return dao.getOneStoFinishOrder(sto_num);
	}

	public void upDateGame(String or_num, Integer value_cash) {
		dao.upDateGame(or_num, value_cash);
	}

	public OrderMasterVO getOneOrder(String or_num) {
		return dao.getOneOrder(or_num);
	}

	public OrderDetailToolVO getGroupOrderPoint(String meror_num) {
		return dao.getGroupOrderPoint(meror_num);

	}

	public Integer getPlay(String or_num) {
		return dao.getPlay(or_num);
	}

	// 取得團購所有訂單 資訊
	public List<OrderMasterVO> getGroupAllOrderNumByMerorNum(String meror_num) {
		return dao.getGroupAllOrderNumByMerorNum(meror_num);
	}

	// 複合查詢訂單
	public List<OrderMasterVO> getOrderInfo(Map<String, String[]> map) {
		return dao.getOrderInfo(map);
	}

	// 訂單新增完成
	public void finishOrder(List<OrderMasterVO> list) {

		List<Map> all = new ArrayList();

	

		for (int i = 0; i < list.size(); i++) {

			OrderMasterVO orderMasterVO = list.get(i);

			CardVO cardVO = new CardVO();

			String mem_num = orderMasterVO.getMem_num();
			String sto_num = orderMasterVO.getSto_num();
			Map map = new HashMap();

			int addpoints = dao.howManyCups(orderMasterVO);

			int stoNowCardNeedPoints = 0;
			int memCardNeedPoints = 0;
			int exp_date = 0;
			int value = 0; // 最後點數值
			String card_num = null;
			String card_kinds = null;
			int howManyFullNewCards = 0;
			int newCardPoints = 0;
			int updateMemCard = 0;
			int usePoints = 0;

			if (orderMasterVO.getPay_men().equals("點數")) {
				System.out.println("點數");
				usePoints = 1;
			}

			// 此會員有這店家集點卡
			if (dao.searchMemCard(sto_num, mem_num) != null) {

				map = dao.searchMemCard(sto_num, mem_num);
				card_num = (String) map.get("card_num");
				value = (Integer) map.get("value");
				memCardNeedPoints = (Integer) map.get("points");

				System.out.println("//此會員有這店家集點卡 ");
				// 新增後 未集滿 測試OK
				if (memCardNeedPoints > (value + addpoints)) {

					System.out.println("新增後 未集滿 ");

					value += addpoints;
					map.put("addpoints", addpoints);
					map.put("value", value);
					map.put("status", "尚未集滿");
					map.put("updateMemCard", 1);
					map.put("OrderMasterVO", orderMasterVO);
					map.put("CardVO", cardVO);
					map.put("howManyFullNewCards", howManyFullNewCards);
					map.put("newCardPoints", newCardPoints);
					map.put("usePoints", usePoints);
					all.add(map);

					// 新增後剛好集滿 ok
				} else if (memCardNeedPoints == (value + addpoints)) {

					System.out.println("新增後剛好集滿 ");

					value = memCardNeedPoints;
					map.put("value", value);
					map.put("status", "可使用");
					map.put("updateMemCard", 1);
					map.put("OrderMasterVO", orderMasterVO);
					map.put("CardVO", cardVO);
					map.put("howManyFullNewCards", howManyFullNewCards);
					map.put("newCardPoints", newCardPoints);
					map.put("usePoints", usePoints);
					map.put("addpoints", addpoints);
					all.add(map);

					// 此店家沒有發集點卡 且 加的點數超過集點卡 ok
				} else if (memCardNeedPoints < (value + addpoints) && dao.getStoNowCard(sto_num) == null) {

					System.out.println("此店家沒有發集點卡 且 加的點數超過集點卡 ");

					map.put("value", memCardNeedPoints);
					map.put("status", "可使用");
					map.put("updateMemCard", 1);
					map.put("OrderMasterVO", orderMasterVO);
					map.put("CardVO", cardVO);
					map.put("howManyFullNewCards", howManyFullNewCards);
					map.put("newCardPoints", newCardPoints);
					map.put("usePoints", usePoints);
					map.put("addpoints", (memCardNeedPoints - value));
					all.add(map);

					// 店家有發集點卡 且 加的點數超過集點卡 測試OK
				} else if (memCardNeedPoints < (value + addpoints) && dao.getStoNowCard(sto_num) != null) {

					System.out.println("店家有發集點卡 且 加的點數超過集點卡 ");

					cardVO = dao.getStoNowCard(sto_num);
					stoNowCardNeedPoints = cardVO.getPoints();
					howManyFullNewCards = (addpoints - (memCardNeedPoints - value)) / stoNowCardNeedPoints;
					newCardPoints = (addpoints - (memCardNeedPoints - value)) % stoNowCardNeedPoints;
					addpoints = addpoints-newCardPoints-(stoNowCardNeedPoints*howManyFullNewCards) ;
					value = memCardNeedPoints;
					map.put("value", value);
					map.put("status", "可使用");
					map.put("updateMemCard", 1);
					map.put("OrderMasterVO", orderMasterVO);
					map.put("CardVO", cardVO);
					map.put("howManyFullNewCards", howManyFullNewCards);
					map.put("newCardPoints", newCardPoints);
					map.put("usePoints", usePoints);
					map.put("addpoints", addpoints);
					all.add(map);

				}
				// 此會員沒有這家集點卡
			} else {

				// 店家有發集點卡 測試OK
				if (dao.getStoNowCard(sto_num) != null) {

					System.out.println("//此會員沒有這家集點卡   //店家有發集點卡 ");

					cardVO = dao.getStoNowCard(sto_num);
					stoNowCardNeedPoints = cardVO.getPoints();
					howManyFullNewCards = addpoints / stoNowCardNeedPoints;
					newCardPoints = addpoints % stoNowCardNeedPoints;

					map.put("updateMemCard", 0);
					map.put("OrderMasterVO", orderMasterVO);
					map.put("CardVO", cardVO);
					map.put("howManyFullNewCards", howManyFullNewCards);
					map.put("newCardPoints", newCardPoints);
					map.put("usePoints", usePoints);
					all.add(map);

					// 店家沒有發集點卡
				} else {

					System.out.println(" //店家沒有發集點卡?????");
					map.put("OrderMasterVO", orderMasterVO);
					map.put("usePoints", usePoints);
					map.put("CardVO", cardVO);//////////////////////////////////////////
					map.put("noCrad", 1);
					all.add(map);

				}

			}
		}
		System.out.println("此次需更新訂單總筆數 :" + all.size());
		dao.finish(all);
	}

	public List<OrderMasterVO> getOneStoAllOrder(String sto_num) {

		return dao.getOneStoAllOrder(sto_num);
	}

	public List<OrderMasterVO> getStoPointOrder(String sto_num) {
		return dao.getStoPointOrder(sto_num);

	}

	public List<OrderMasterVO> getOneStoWaitModif(String sto_num) {

		return dao.getOneStoWaitModif(sto_num);
	}

	public void orderCancel(String or_num) {

		List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();

		OrderMasterVO orderMasterVO;

		orderMasterVO = getOneOrder(or_num);

		
		String meror_num = orderMasterVO.getMeror_num();

		if (meror_num!=null) {
			list = getGroupAllOrderNumByMerorNum(meror_num);

			for (int i = 0; i < list.size(); i++) {
				orderMasterVO = new OrderMasterVO();
				orderMasterVO = list.get(i);
				dao.orderCancel(orderMasterVO);
			}
			

		} else {
			orderMasterVO = new OrderMasterVO();
			orderMasterVO = getOneOrder(or_num);
			dao.orderCancel(orderMasterVO);
			
		}

	}

	public void orderPass(String or_num) {
		dao.orderPass(or_num);
	}

	// 訂單通過審核 團購
	public void groupOrderPass(String meror_num) {
		dao.groupOrderPass(meror_num);
	}

	// 取得店家處理中訂單
	public List<OrderMasterVO> getOneStoPassOrder(String sto_num) {
		return dao.getOneStoPassOrder(sto_num);

	}

	public void orderDelivered(String or_num) {

		OrderMasterVO orderMasterVO = new OrderMasterVO();
		orderMasterVO = getOneOrder(or_num);
		String meror_num = orderMasterVO.getMeror_num();
		String orsta_num = "已交貨";

		if (meror_num == null || meror_num.trim().length() == 0) {

			dao.orderAlready(or_num, orsta_num);
		} else {
			dao.groupOrderAlready(meror_num, orsta_num);
		}
	}

	public void orderAlready(String or_num) {

		OrderMasterVO orderMasterVO = new OrderMasterVO();
		orderMasterVO = getOneOrder(or_num);
		String meror_num = orderMasterVO.getMeror_num();
		String rece = orderMasterVO.getRece();

		if (meror_num == null || meror_num.trim().length() == 0) {

			if (rece.equals("自取")) {
				String orsta_num = "待取貨";
				dao.orderAlready(or_num, orsta_num);
			} else if (rece.equals("外送")) {
				String orsta_num = "外送中";
				dao.orderAlready(or_num, orsta_num);

			}

		} else {

			if (rece.equals("自取")) {
				String orsta_num = "待取貨";
				dao.groupOrderAlready(meror_num, orsta_num);
			} else if (rece.equals("外送")) {
				String orsta_num = "外送中";
				dao.groupOrderAlready(meror_num, orsta_num);
			}

		}

	}

	// 回傳發起人會員編號
	public String getGroupMaster(String or_num) {
		return dao.getGroupMaster(or_num);

	}

	// 取得某會員已完成交易訂單
	public List<OrderMasterVO> getOneMemFinishOrder(String mem_num) {
		return dao.getOneMemFinishOrder(mem_num);
	}

	// 取得訂單明細 中文 單人 ##
	public List<OrderDetailToolVO> getOrderDetailTool(String or_num) {
		List<OrderDetailToolVO> detail = new Vector<OrderDetailToolVO>();
		List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();
		list = dao.getOrderDetailTool(or_num);
		for (int i = 0; i < list.size(); i++) {
			if (detail.contains(list.get(i))) {
				OrderDetailToolVO modif = detail.get(detail.indexOf(list.get(i)));
				modif.setAmount(modif.getAmount() + 1);
			} else {
				detail.add(list.get(i));
			}
		}
		return detail;
	}

	// 取得訂單明細 中文 團購 ##
	public List<OrderDetailToolVO> getGroupOrderDetailTool(String meror_num) {
		List<OrderDetailToolVO> detail = new Vector<OrderDetailToolVO>();
		List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();
		list = dao.getGroupOrderDetailTool(meror_num);
		for (int i = 0; i < list.size(); i++) {
			if (detail.contains(list.get(i))) {
				OrderDetailToolVO modif = detail.get(detail.indexOf(list.get(i)));
				modif.setAmount(modif.getAmount() + 1);
			} else {
				detail.add(list.get(i));
			}
		}
		return detail;
	}

	// 取得團購寄杯明細
	public List<OrderDetailToolVO> getGroupKeepDetailTool(String meror_num) {
		List<OrderDetailToolVO> detail = new Vector<OrderDetailToolVO>();
		List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();
		list = dao.getGroupKeepDetailTool(meror_num);
		for (int i = 0; i < list.size(); i++) {
			if (detail.contains(list.get(i))) {
				OrderDetailToolVO modif = detail.get(detail.indexOf(list.get(i)));
				modif.setAmount(modif.getAmount() + 1);
			} else {
				detail.add(list.get(i));
			}
		}
		return detail;

	}

	// 取得團購非寄杯明細
	public List<OrderDetailToolVO> getGroupNotKeepDetailTool(String meror_num) {

		List<OrderDetailToolVO> detail = new Vector<OrderDetailToolVO>();
		List<OrderDetailToolVO> keep = new ArrayList<OrderDetailToolVO>();
		List<OrderDetailToolVO> notKeep = new ArrayList<OrderDetailToolVO>();

		notKeep = dao.getGroupOrderDetailTool(meror_num);
		keep = dao.getGroupKeepDetailTool(meror_num);

		for (int i = 0; i < keep.size(); i++) {
			for (int j = 0; j < notKeep.size(); j++) {
				if (keep.get(i).getOrdet_num().equals(notKeep.get(j).getOrdet_num())) {
					notKeep.remove(j);
				}
			}
		}
		for (int i = 0; i < notKeep.size(); i++) {
			if (detail.contains(notKeep.get(i))) {
				OrderDetailToolVO modif = detail.get(detail.indexOf(notKeep.get(i)));
				modif.setAmount(modif.getAmount() + 1);
			} else {
				detail.add(notKeep.get(i));
			}

		}

		return detail;

	}

	// 取得單人寄杯明細
	public List<OrderDetailToolVO> getKeepDetailTool(String or_num) {

		List<OrderDetailToolVO> detail = new Vector<OrderDetailToolVO>();
		List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();
		list = dao.getKeepDetailTool(or_num);
		for (int i = 0; i < list.size(); i++) {
			if (detail.contains(list.get(i))) {
				OrderDetailToolVO modif = detail.get(detail.indexOf(list.get(i)));
				modif.setAmount(modif.getAmount() + 1);
			} else {
				detail.add(list.get(i));
			}
		}
		return detail;

	}

	public List<OrderDetailToolVO> getNotKeepDetailTool(String or_num) {

		List<OrderDetailToolVO> detail = new ArrayList<OrderDetailToolVO>();
		List<OrderDetailToolVO> keep = new ArrayList<OrderDetailToolVO>();
		List<OrderDetailToolVO> notKeep = new ArrayList<OrderDetailToolVO>();

		notKeep = dao.getOrderDetailTool(or_num);
		keep = dao.getKeepDetailTool(or_num);

		// 取得非寄杯的飲料
		for (int i = 0; i < keep.size(); i++) {
			// 全部杯數

			for (int j = 0; j < notKeep.size(); j++) {

				if (keep.get(i).getOrdet_num().equals(notKeep.get(j).getOrdet_num())) {
					notKeep.remove(j);
				}
			}
		}

		// 如有相同則合併
		for (int i = 0; i < notKeep.size(); i++) {
			//System.out.println("nokeep= " + notKeep.size());
			if (detail.contains(notKeep.get(i))) {
				OrderDetailToolVO modif = detail.get(detail.indexOf(notKeep.get(i)));
				modif.setAmount(modif.getAmount() + 1);
			} else {
				detail.add(notKeep.get(i));
			}

		}

		return detail;

	}

	// 某會員未完成交易訂單
	public List<OrderMasterVO> getOneMemNoFinishOrder(String mem_num) {
		return dao.getOneMemNoFinishOrder(mem_num);
	}
	
//---------------end Eric------------------	
	
}
