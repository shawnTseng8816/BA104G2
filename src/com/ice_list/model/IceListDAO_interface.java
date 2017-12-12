package com.ice_list.model;

import java.util.List;

public interface IceListDAO_interface {
	
//-----------------KHAN--------------------------
	public void insertKHAN(IceListVO iceListVO);
	
	public void updateKHAN(IceListVO iceListVO);

	public List<IceListVO> getIceList(IceListVO iceListVO);
	
	public IceListVO getIceType(IceListVO iceListVO);
//-----------------end KHAN---------------------------
	
//------------------Peiiun--------------------------
	public String insert(IceListVO iceListVO);
	
	public void update(IceListVO iceListVO);

	public List<IceListVO> getIceList(String sto_num);
	
	public IceListVO getOneIce(String ice_num);
//------------------end Peiiun--------------------------
	
//------------------Shawn-----------------------------
	public IceListVO getIceName(String ice_num);
//------------------end Shawn-------------------------	

	
//---------------Eric-----------------
	
//public void insert(IceListVO iceListVO);
//	
//	public void update(IceListVO iceListVO);
//
//	public List<IceListVO> getIceList(IceListVO iceListVO);
//	
//	public IceListVO getIceType(IceListVO iceListVO);
	
//-------------end Eric-------------------	
}
