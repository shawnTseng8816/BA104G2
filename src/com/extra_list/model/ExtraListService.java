package com.extra_list.model;

import java.util.List;
import java.util.Set;

import com.extra.model.ExtraVO;

public class ExtraListService {

	private ExtraListDAO_interface dao;

	public ExtraListService() {
		dao = new ExtraListDAO();
	}

//-------Peiiun---------------
	public ExtraListVO addExtraList(String ext_num, String ordet_num) {

		ExtraListVO extraListVO = new ExtraListVO();

		extraListVO.setExt_num(ext_num);
		extraListVO.setOrdet_num(ordet_num);

		dao.insert(extraListVO);

		return extraListVO;
	}

	public ExtraVO getExtra(String ext_num) {

		ExtraListVO extraListVO = new ExtraListVO();

		extraListVO.setExt_num(ext_num);

		return dao.getExtra(extraListVO);
	}
	
	public void deleteExtraList(String ordet_num){
		
		ExtraListVO extraListVO = new ExtraListVO();

		extraListVO.setOrdet_num(ordet_num);
		
		dao.deleteExtraList(extraListVO);
	}
	
	public List<ExtraListVO> getExtraNum(String OrdetNum){
		return dao.getExtraNum(OrdetNum);
	}
//----------end Peiiun-------------------------	
	
	
	
//---------shawn------------
	public Set<ExtraListVO> getExtNum(String ordet_num){
		return dao.getExtNum(ordet_num);
	}
//--------end shawn---------
	
	
//---------Eric---------------
	
	public List<ExtraListVO> getListByOdNum(String ordet_num){
		return dao.getListByOdNum(ordet_num);
	}
//	public ExtraListVO addExtraList(String ext_num, String ordet_num) {
//
//		ExtraListVO extraListVO = new ExtraListVO();
//
//		extraListVO.setExt_num(ext_num);
//		extraListVO.setOrdet_num(ordet_num);
//
//		dao.insert(extraListVO);
//
//		return extraListVO;
//	}
//
//	public ExtraVO getExtra(String ext_num) {
//
//		ExtraListVO extraListVO = new ExtraListVO();
//
//		extraListVO.setExt_num(ext_num);
//
//		return dao.getExtra(extraListVO);
//	}
//	
//	public void deleteExtraList(String ordet_num){
//		
//		ExtraListVO extraListVO = new ExtraListVO();
//
//		extraListVO.setOrdet_num(ordet_num);
//		
//		dao.deleteExtraList(extraListVO);
//	}
	
//--------end Eric------------	
	
}
