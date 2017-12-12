package com.sweetness.model;

import java.util.List;
import java.util.Set;

import com.store_profile.model.StoreProfileVO;

public class SweetnessService {

	private SweetnessDAO_interface dao;

	public SweetnessService() {
		dao = new SweetnessDAO();
	}

//	public SweetnessVO insertSweetness(String sto_num, String sweet_type) {
//
//		SweetnessVO sweetnessVO = new SweetnessVO();
//
//		sweetnessVO.setSto_num(sto_num);
//		sweetnessVO.setSweet_type(sweet_type);
//		sweetnessVO.setStatus("上架");
//
//		dao.insert(sweetnessVO);
//
//		return sweetnessVO;
//	}
//
//	public SweetnessVO updateSweetness(String sweet_num, String Status) {
//
//		SweetnessVO sweetnessVO = new SweetnessVO();
//
//		sweetnessVO.setSweet_num(sweet_num);
//		sweetnessVO.setStatus(Status);
//
//		dao.update(sweetnessVO);
//
//		return sweetnessVO;
//	}

//---------------KAHN------------------------------------	
	public List<SweetnessVO> getSweetnessKAHN(String sto_num) {

		SweetnessVO sweetnessVO = new SweetnessVO();

		sweetnessVO.setSto_num(sto_num);
		
		return dao.getSweetnessKAHN(sweetnessVO);
	}
	
	public SweetnessVO getSweetTypeKAHN(String sweet_num) {

		SweetnessVO sweetnessVO = new SweetnessVO();

		sweetnessVO.setSweet_num(sweet_num);;
		
		return dao.getSweetTypeKAHN(sweetnessVO);
	}
//----------end KAHN-----------------------------
	
	
//-----------Peiiun------------------------------	
	public SweetnessVO insertSweetness(SweetnessVO sweetnessVO) {
		String sweet_num = dao.insert(sweetnessVO);
		sweetnessVO.setSweet_num(sweet_num);
		return sweetnessVO;
	}

	public SweetnessVO updateSweetness(SweetnessVO sweetnessVO) {
		dao.update(sweetnessVO);
		return sweetnessVO;
	}

	public List<SweetnessVO> getSweetness(String sto_num) {
		return dao.getSweetness(sto_num);
	}

	public SweetnessVO getOneSweetness(String sweet_num){
		return dao.getOneSweetness(sweet_num);
	}
//-----------end Peiiun------------------------------
	
//-------------Shawn------------------------------
	
	public SweetnessVO getSweetName(String sweet_num){
		return dao.getSweetName(sweet_num);
	}
	
	
//----------------------end Shawn-------------------------------	
	
//----------Eric--------------------
	public SweetnessVO insertSweetness(String sto_num, String sweet_type) {

		SweetnessVO sweetnessVO = new SweetnessVO();

		sweetnessVO.setSto_num(sto_num);
		sweetnessVO.setSweet_type(sweet_type);
		sweetnessVO.setStatus("上架");

		dao.insert_ERIC(sweetnessVO);

		return sweetnessVO;
	}

	public SweetnessVO updateSweetness(String sweet_num, String Status) {

		SweetnessVO sweetnessVO = new SweetnessVO();

		sweetnessVO.setSweet_num(sweet_num);
		sweetnessVO.setStatus(Status);

		dao.update_ERIC(sweetnessVO);

		return sweetnessVO;
	}

	public List<SweetnessVO> getSweetnessEric(String sto_num) {

		SweetnessVO sweetnessVO = new SweetnessVO();

		sweetnessVO.setSto_num(sto_num);
		
		return dao.getSweetness(sweetnessVO);
	}
	
	public SweetnessVO getSweetType(String sweet_num) {

		SweetnessVO sweetnessVO = new SweetnessVO();

		sweetnessVO.setSweet_num(sweet_num);;
		
		return dao.getSweetType(sweetnessVO);
	}
	
	
//--------end Eric------------------	
}
