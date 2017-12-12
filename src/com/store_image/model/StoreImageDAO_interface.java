package com.store_image.model;

import java.util.List;

public interface StoreImageDAO_interface {
	public List<StoreImageVO> getStoreImageNum(String sto_num);//�s�����a�Ա�-���o���a�Ϥ��s��
	
	public StoreImageVO getStoreImage(String sto_num,String img_num);//�s�����a�Ա�-���o���a�Ϥ�
	public void insertImg(List<StoreImageVO> list);
	
	public byte[] getStoreImage(String img_num);
	
	public void updateImg(StoreImageVO storeImageVO);
}


