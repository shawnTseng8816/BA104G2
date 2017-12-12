package com.extra.model;

import java.util.*;

public interface ExtraDAO_interface {
	

	
	
//-------------Peiiun-----------------
	public List<ExtraVO> getExtras(ExtraVO extraVO);
	
	public ExtraVO getExtraAttr(ExtraVO extraVO);
	
	
	public String insert(ExtraVO extraVO);
	
	public void update(ExtraVO extraVO);

	public List<ExtraVO> getExtras(String sto_num);
	
	public ExtraVO getOneExtra(String ext_num);
//-------------end Peiiun-----------------	
	
//-----------Shawn----------------
	public ExtraVO getExtName(String ext_num);
//-----------end Shawn----------------	
	
	
//----------Eric-----------------
	
//public void insert(ExtraVO extraVO);
//	
//	public void update(ExtraVO extraVO);

	public List<ExtraVO> getExtrasEric(ExtraVO extraVO);
	
//	public ExtraVO getExtraAttr(ExtraVO extraVO);
	
//---------end Eric------------	
	
}
