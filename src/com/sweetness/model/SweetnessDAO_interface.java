package com.sweetness.model;

import java.util.List;
import java.util.Set;

import com.store_profile.model.StoreProfileVO;

public interface SweetnessDAO_interface {
	
//	public void insert(SweetnessVO sweetnessVO);
//	
//	public void update(SweetnessVO sweetnessVO);

//---------------KAHN-------------	
	public List<SweetnessVO> getSweetnessKAHN(SweetnessVO sweetnessVO);
	
	public SweetnessVO getSweetTypeKAHN(SweetnessVO sweetnessVO);
//----------end KAHN--------------
	
	
//-----------Peiiun----------------	
public String insert(SweetnessVO sweetnessVO);
	
	public void update(SweetnessVO sweetnessVO);

	public List<SweetnessVO> getSweetness(String sto_num);
	
	public SweetnessVO getOneSweetness(String sweet_num);
//----------end Peiiun---------------
	
//---------Shawn----------------
	
	public SweetnessVO getSweetName(String sweet_num);
	
//---------end Shawn------------------	
	
//---------Eric--------------------
public void insert_ERIC(SweetnessVO sweetnessVO);
	
	public void update_ERIC(SweetnessVO sweetnessVO);

	public List<SweetnessVO> getSweetness(SweetnessVO sweetnessVO);
	
	public SweetnessVO getSweetType(SweetnessVO sweetnessVO);
	
	
//--------end Eric---------	
}
