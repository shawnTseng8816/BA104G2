package com.ice_list.model;

import java.util.List;

public class IceListService {

	private IceListDAO_interface dao;

	public IceListService() {
		dao = new IceListDAO();
	}

//----------KHAN-----------------------------
	public IceListVO insertIceList(String sto_num, String ice_type) {

		IceListVO iceListVO = new IceListVO();

		iceListVO.setSto_num(sto_num);
		iceListVO.setIce_type(ice_type);
		iceListVO.setStatus("上架");

		dao.insertKHAN(iceListVO);

		return iceListVO;
	}

	public IceListVO updateIceList(String ice_num, String Status) {

		IceListVO iceListVO = new IceListVO();

		iceListVO.setIce_num(ice_num);
		iceListVO.setStatus(Status);

		dao.updateKHAN(iceListVO);

		return iceListVO;
	}

	public List<IceListVO> getIceList(String sto_num) {

		IceListVO iceListVO = new IceListVO();

		iceListVO.setSto_num(sto_num);

		return dao.getIceList(iceListVO);
	}
	
	public IceListVO getIceType(String ice_num) {

		IceListVO iceListVO = new IceListVO();

		iceListVO.setIce_num(ice_num);

		return dao.getIceType(iceListVO);
	}
	
	
//----------end KHAN-----------------------------
	
//----------Peiiun----------------------------------
	public IceListVO insertIceList(IceListVO iceListVO) {
		String ice_num = dao.insert(iceListVO);
		iceListVO.setIce_num(ice_num);
		return iceListVO;
	}

	public IceListVO updateIceList(IceListVO iceListVO) {
		dao.update(iceListVO);
		return iceListVO;
	}

	public List<IceListVO> getIceListPeiiun(String sto_num) {
		return dao.getIceList(sto_num);
	}
	
	public IceListVO getOneIce(String ice_num){
		return dao.getOneIce(ice_num);
	}
//----------end Peiiun----------------------------------

//-------------Shawn---------------------------------
	
	public IceListVO getIceName(String ice_num){
		return dao.getIceName(ice_num);
	}
	
//-------------------end Shawn----------------------	
	
	
//--------------Eric------------------------------
//	public IceListVO insertIceList(String sto_num, String ice_type) {
//
//		IceListVO iceListVO = new IceListVO();
//
//		iceListVO.setSto_num(sto_num);
//		iceListVO.setIce_type(ice_type);
//		iceListVO.setStatus("上架");
//
//		dao.insert(iceListVO);
//
//		return iceListVO;
//	}
//
//	public IceListVO updateIceList(String ice_num, String Status) {
//
//		IceListVO iceListVO = new IceListVO();
//
//		iceListVO.setIce_num(ice_num);
//		iceListVO.setStatus(Status);
//
//		dao.update(iceListVO);
//
//		return iceListVO;
//	}
//
//	public List<IceListVO> getIceList(String sto_num) {
//
//		IceListVO iceListVO = new IceListVO();
//
//		iceListVO.setSto_num(sto_num);
//
//		return dao.getIceList(iceListVO);
//	}
//	
//	public IceListVO getIceType(String ice_num) {
//
//		IceListVO iceListVO = new IceListVO();
//
//		iceListVO.setIce_num(ice_num);
//
//		return dao.getIceType(iceListVO);
//	}
	
	
//----------end Eric-------------------------------	
}
