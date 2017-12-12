package com.backstage_management.model;

import java.util.*;

public interface BackstageManagementDAO_interface {
	
//---------------Peiiun-----------------------------------	
	public String insert(BackstageManagementVO bmVO);
	public void update(BackstageManagementVO bmVO);
	public int checkBm_num (String bm_num);
	public BackstageManagementVO findbyPrimaryKey(String bm_no);
	public List<BackstageManagementVO> getAll();
//---------------end Peiiun-------------------------------
	
//--------------Chi---------------------------------------
	public void insertChi(BackstageManagementVO backstagemanagementVO);
	// public void update(BackstageManagementVO backstagemanagementVO);
          
	public BackstageManagementVO findByPrimaryKey(String bm_no);

	public List<BackstageManagementVO> getAllChi();
	//萬用複合查詢(傳入參數型態Map)(回傳 List)
	//public List< BackstageManagementVO> getAll(Map<String, String[]> map); 
          
	public BackstageManagementVO checkLogin(String bm_num);
//--------------end Chi-----------------------------------
	
}
