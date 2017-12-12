package com.store_chatlog.model;

import java.util.List;

public class StoreChatlogService {
	private StoreChatlogDAO_interface dao ;
	
	public StoreChatlogService(){
		dao = new StoreChatlogDAO();
	}
	
	public StoreChatlogVO addChat(StoreChatlogVO storeChatlogVO){
		dao.insert(storeChatlogVO);
		return storeChatlogVO;
	}
	
	public List<StoreChatlogVO> stoGetAll(String sto_num){
		return dao.stoGetAll(sto_num);
	}
	
	public List<StoreChatlogVO> getAll(){
		return dao.getAll();
	}
}
