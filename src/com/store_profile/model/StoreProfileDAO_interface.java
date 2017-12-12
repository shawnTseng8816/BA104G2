package com.store_profile.model;

import java.util.List;
import java.util.Set;

import com.store_comment.model.StoreCommentVO;

public interface StoreProfileDAO_interface {
	
//------------------Peiiun-------------------	
	public List<StoreProfileVO> getAllgeo();
	public StoreProfileVO getOneByPrimary(String sto_num);
	
	public List<StoreProfileVO> search(String keyword);
	public List<StoreProfileVO> search();
//----------------end Peiiun-------------------
	
	
	
//-----------------Chi------------------------
	public void insert(StoreProfileVO storeprofileVO);
	
	public void update(StoreProfileVO storeprofileVO);

	public StoreProfileVO getStoreProfile(String sto_num);
	
	public StoreProfileVO checkLogin(String sto_acc);
	
	public List<StoreProfileVO> getStatus(String sto_status);

	List<StoreProfileVO> getAll();

	void changestatus(StoreProfileVO storeprofileVO);
	
	
//----------------end Chi---------------------	
	
	
//---------------Shawn------------------
	
	public Set<StoreProfileVO> getStoreProfileBySto_num(String sto_num);

	public Set<StoreProfileVO> getStoreProfileImage(String sto_num);
	public Set<StoreProfileVO> getStoreProfileAllName(String sto_name);
	public void updateStoreProfile(StoreProfileVO storeProfileVO);
//	public List<StoreProfileVO> getAll();
//	public StoreProfileVO getStoreProfile(String sto_num);
	
	
//----------------end Shawn------------------	
	
	
//----------------Eric---------------------------------------------------------
	public StoreProfileVO getOneStoInfo(String sto_num);
	
	public void updateStoStatus(String sto_num,String status);
	
	public void updateStoInfo(StoreProfileVO storeProfileVO);
	
	public List<StoreCommentVO> getStoComment(String sto_num);		
	
	public byte[] getLogo(String sto_num);
	
//------------end Eric---------------------	
}
