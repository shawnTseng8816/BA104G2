package com.shop_ad.model;

import java.util.*;

public interface ShopAdDAO_interface {
//          public void insert(ShopAdVO shopadVO);
//          public void update(ShopAdVO shopadVO);

          
          public List<ShopAdVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
          //public List< ShopAdVO> getAll(Map<String, String[]> map); 
          
          public List<ShopAdVO> getAllExceptEdit();
		ShopAdVO insert(ShopAdVO shopadVO);
		
		void update(ShopAdVO shopadVO);
		
		List<ShopAdVO> getAllBySto_num(String sto_num);
		
		public List<ShopAdVO> getAllBySa_addmode(String sa_addmode);
		
		ShopAdVO findByPrimaryKey(String sa_no);
		
		List<ShopAdVO> findBySa_no(String sa_no);
}