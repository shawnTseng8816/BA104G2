package com.store_chatlog.model;

import java.util.List;


public interface StoreChatlogDAO_interface {
	
	public void insert(StoreChatlogVO storeChatlogVO); 
	public List<StoreChatlogVO> stoGetAll(String sto_num);
	public List<StoreChatlogVO> getAll();
 	
}
