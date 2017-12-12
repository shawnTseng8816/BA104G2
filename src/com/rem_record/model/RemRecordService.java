package com.rem_record.model;

import java.sql.Date;
import java.util.List;

import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

import account.AccVO;

public class RemRecordService {
	private RemRecordDAO_interface dao;
	
	
	public RemRecordService(){
		dao = new RemRecordDAO();
	}
	
	//查詢尚未省核清單
	public List<RemRecordVO> getApply(){
		return dao.getApply();
	}
	
	//查詢已省核清單
	public List<RemRecordVO> getFinish(){
		return dao.getFinish();
	}
	
	//通過省核
	public void pass(String rem_num,String rem_status){
		
	
		
		
		RemRecordVO remVO = new RemRecordVO();
		remVO.setRem_num(rem_num);
		remVO.setRem_status(rem_status);
		dao.pass(remVO);
		
	}
	
	//未通過省核
	public void noPass(RemRecordVO remVO){


		
		dao.noPass(remVO);
		
	}
	public List<RemRecordVO> getOneStoRemInfo(String sto_num){
		return dao.getOneStoRemInfo(sto_num);
	}
	
	//新增一筆申請匯款紀錄
	public void insertRem(RemRecordVO remVO){
		
//		StoreProfileService storeProfileSvc = new StoreProfileService();
//		StoreProfileVO storeProfileVO = storeProfileSvc.getOneStoInfo(remVO.getSto_num());
//		String to = storeProfileVO.getEmail();
//		String guy = storeProfileVO.getGuy();
//		String subject ="提款申請通知 ";
//		String acc = String.valueOf(remVO.getRem_account());
//		String acc2 = acc.substring(acc.length()-5);

		dao.insertRem(remVO);
//		
//		String messageText = "親愛的 " + guy +" 先生／小姐，您好 ：   "
//						   + " 我們已經收到您的提款申請， 金額為新台幣 " + remVO.getRem_cash() +" 元整 ，"
//						   + " 收款帳號後五碼為 : " + acc2 +" 如有疑問請與我們聯絡 。";
//		
//		MailService.sendMail(to, subject, messageText);
		
		
	}
	
	//查詢店家剩餘點數
	public Integer getStoRemPoint(String sto_num){
		
		return dao.getStoRemPoint(sto_num);
		}
	
	
}
