package com.func_list.model;

import java.util.List;

public interface FuncListDAO_interface {
	
//-----------------Peiiun--------------------
	public List<FuncListVO> getAll();
	
//-----------------end Peiiun------------------
	
//-----------------Chi----------------------------	
	public FuncListVO findByPrimaryKey(String func_no);
    public List<FuncListVO> getAllChi();
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
    //public List<String func_noVO> getAll(Map<String, String[]> map); 
//----------------end Chi---------------------------------    
    public String getOneFuncNameByFuncNo(String func_no);
}
