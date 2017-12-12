package com.auth_list.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AuthListService {
	
	private AuthListDAO_interface dao;
	
	public AuthListService(){
		dao = new AuthListDAO();
	}
		
//-------------Peiiun-----------------------------------------	
	public void insert(String bm_no, List<String>list){
		dao.insert(bm_no, list);
	}
	
	public void delete(String bm_no){
		dao.delete(bm_no);
	}
	
	public List<String> findByBm_no(String bm_no){
		return dao.findByBm_no(bm_no);
	}
	
	public Map<String ,List<String>> getAll(){
		return dao.getAll();
	}
	
//---------------end Peiiun---------------------------------------
	
//--------------chi--------------------------------------------
	
	public AuthListVO getOneAuthList(String bm_no) {
		return dao.findByPrimaryKey(bm_no);
	}

	public List<AuthListVO> getAllChi() {
		return dao.getAllChi();
	}
//--------------end chi-----------------------------------------	
	
	
}
