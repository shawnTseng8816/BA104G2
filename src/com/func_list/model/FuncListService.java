package com.func_list.model;

import java.util.List;

public class FuncListService {
	private FuncListDAO_interface dao;
	
	public FuncListService (){
		dao = new FuncListDAO();
	}
	
	
//---------------------Peiiun--------------------	
	public List<FuncListVO> getAll(){
		return dao.getAll();
	}
//---------------------end Peiiun------------------
	
	
//-------------------Chi--------------------------
	
	public FuncListVO getOneFuncList(String func_no) {
		return dao.findByPrimaryKey(func_no);
	}

	public List<FuncListVO> getAllChi() {
		return dao.getAllChi();
	}
//-------------------end Chi----------------------	
	
	public String getOneFuncNameByFuncNo(String func_no){
		return dao.getOneFuncNameByFuncNo(func_no);
	}
}
