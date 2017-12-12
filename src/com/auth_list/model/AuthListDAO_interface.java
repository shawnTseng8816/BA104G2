package com.auth_list.model;

import java.util.List;
import java.util.Map;

public interface AuthListDAO_interface {
	
//------------------Peiiun---------------------
	public void insert(String bm_no, List<String>list);
	public void delete(String bm_no);
	public List<String> findByBm_no(String bm_no);
	public Map<String ,List<String>> getAll();
//------------------end Peiiun---------------------	
	
//--------------------chi----------------------------
	public AuthListVO findByPrimaryKey(String bm_no);
    public List<AuthListVO> getAllChi();
//--------------------end chi------------------------
}
