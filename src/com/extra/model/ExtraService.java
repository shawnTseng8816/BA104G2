package com.extra.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.member_profile.model.MemberProfileVO;

public class ExtraService {

	private ExtraDAO_interface dao;

	public ExtraService() {
		dao = new ExtraDAO();
	}
	
//----------Peiiun----------------------	
	public ExtraVO addExtra(ExtraVO extraVO) {
		String ext_num = dao.insert(extraVO);
		extraVO.setExt_num(ext_num);
		return extraVO;
	}

	public ExtraVO updateExtra(ExtraVO extraVO){
		dao.update(extraVO);
		return extraVO;		
	}

	public List<ExtraVO> getExtras(String sto_num) {
		return dao.getExtras(sto_num);
	}
	
	public ExtraVO getOneExtra(String ext_num){
		return dao.getOneExtra(ext_num);
	}
	
	

	public ExtraVO addExtra(String ext_name, Integer ext_amount, String sto_num) {

		ExtraVO extraVO = new ExtraVO();

		extraVO.setExt_name(ext_name);
		extraVO.setSto_num(sto_num);
		extraVO.setExt_amount(ext_amount);
		extraVO.setStatus("上架");

		return extraVO;
	}

	public ExtraVO takeOffExtra(String ext_num) {

		ExtraVO extraVO = new ExtraVO();

		extraVO.setStatus("下架");
		extraVO.setExt_num(ext_num);

		dao.update(extraVO);

		return extraVO;
	}

//	public ExtraVO delExtra(String ext_num) {
//
//		ExtraVO extraVO = new ExtraVO();
//
//		extraVO.setStatus("刪除");
//		extraVO.setExt_num(ext_num);
//
//		dao.update(extraVO);
//
//		return extraVO;
//	}

//	public List<ExtraVO> getExtras(String sto_num) {
//
//		ExtraVO extraVO = new ExtraVO();
//
//		extraVO.setSto_num(sto_num);
//		extraVO.setStatus("上架");
//
//		return dao.getExtras(extraVO);
//	}
	
	public ExtraVO getExtraAttr(String ext_num) {

		ExtraVO extraVO = new ExtraVO();

		extraVO.setExt_num(ext_num);;
		extraVO.setStatus("上架");

		return dao.getExtraAttr(extraVO);
	}
	
//----------end Peiiun----------------------	
	
	
	
	
	
	
	
	
//-------------Shawn----------------------------	
	public ExtraVO getExtName(String ext_num){
		return dao.getExtName(ext_num);
	}
//------------end Shawn-------------------------	


//----------Eric---------------------
//	public ExtraVO addExtra(String ext_name, Integer ext_amount, String sto_num) {
//
//		ExtraVO extraVO = new ExtraVO();
//
//		extraVO.setExt_name(ext_name);
//		extraVO.setSto_num(sto_num);
//		extraVO.setExt_amount(ext_amount);
//		extraVO.setStatus("上架");
//
//		return extraVO;
//	}
//
//	public ExtraVO takeOffExtra(String ext_num) {
//
//		ExtraVO extraVO = new ExtraVO();
//
//		extraVO.setStatus("下架");
//		extraVO.setExt_num(ext_num);
//
//		dao.update(extraVO);
//
//		return extraVO;
//	}

	public ExtraVO delExtra(String ext_num) {

		ExtraVO extraVO = new ExtraVO();

		extraVO.setStatus("刪除");
		extraVO.setExt_num(ext_num);

		dao.update(extraVO);

		return extraVO;
	}

	public List<ExtraVO> getExtrasEric(String sto_num) {

		ExtraVO extraVO = new ExtraVO();

		extraVO.setSto_num(sto_num);
		extraVO.setStatus("上架");

		return dao.getExtrasEric(extraVO);
	}
	
//	public ExtraVO getExtraAttr(String ext_num) {
//
//		ExtraVO extraVO = new ExtraVO();
//
//		extraVO.setExt_num(ext_num);;
//		extraVO.setStatus("上架");
//
//		return dao.getExtraAttr(extraVO);
//	}
	
//----------end Eric-----------------	
}
