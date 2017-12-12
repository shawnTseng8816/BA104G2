package com.extra_list.model;

import java.util.List;
import java.util.Set;

import com.extra.model.ExtraVO;

public interface ExtraListDAO_interface {
	
//-----------Peiiun-------------	
	public void insert(ExtraListVO extraListVO);
	
	public void deleteExtraList(ExtraListVO extraListVO);

	public ExtraVO getExtra(ExtraListVO extraListVO);
	
	public List<ExtraListVO> getExtraNum(String OrdetNum);
//--------end Peiiun---------------
	
	
//-------Shawn------------------
	public Set<ExtraListVO> getExtNum(String ordet_num);
	
//------end Shawn--------------
	
//-----------Eric--------------
//public void insert(ExtraListVO extraListVO);
//	
//	public void deleteExtraList(ExtraListVO extraListVO);
//
//	public ExtraVO getExtra(ExtraListVO extraListVO);
	public List<ExtraListVO> getListByOdNum(String ordet_num);
	
//---------end Eric-----------	
}
