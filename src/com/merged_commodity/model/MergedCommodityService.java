package com.merged_commodity.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.Part;

import com.member_profile.model.MemberProfileVO;

public class MergedCommodityService {

	private MergedCommodityDAO_interface dao;

	public MergedCommodityService() {
		dao = new MergedCommodityDAO();
	}

	public MergedCommodityVO addMergedCommodity(String com_num) {

		MergedCommodityVO mergedCommodityVO = new MergedCommodityVO();

		mergedCommodityVO.setCom_num(com_num);

		dao.insert(mergedCommodityVO);

		return mergedCommodityVO;
	}
	
	
	public String addMergedCommodity(List<String> list) {
		String mercom_num = dao.insert(list);		
		return mercom_num;
	}
	
	public void delete(String mercom_num){
		dao.delete(mercom_num);
	}
	
	public List<MergedCommodityVO> getMerList(String mercom_num){
		return dao.getMerList(mercom_num);		
	}
	
	
//------------Eric-----------------
//	public MergedCommodityVO addMergedCommodity(String com_num) {
//
//		MergedCommodityVO mergedCommodityVO = new MergedCommodityVO();
//
//		mergedCommodityVO.setCom_num(com_num);
//
//		dao.insert(mergedCommodityVO);
//
//		return mergedCommodityVO;
//	}
//------------end Eric-----------------	
	
	
}
