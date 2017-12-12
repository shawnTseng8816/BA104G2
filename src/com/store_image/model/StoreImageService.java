package com.store_image.model;

import java.util.List;

public class StoreImageService {
	
	private StoreImageDAO_interface dao;

	public StoreImageService() {
		dao = new StoreImageDAO();
	}
	
	public List<StoreImageVO> getStoreImageNum(String sto_num){
		return dao.getStoreImageNum(sto_num);
	}
	
	public StoreImageVO getStoreImage(String sto_num, String img_num){
		return dao.getStoreImage(sto_num, img_num);
	}
	
	public byte[] getStoreImage(String img_num){
		return dao.getStoreImage(img_num);
	}
	
	public void insertImg(List<StoreImageVO> list){
		dao.insertImg(list);
	}
	
	public void updateImg(StoreImageVO storeImageVO){
		dao.updateImg(storeImageVO);
	}
	
	

}
