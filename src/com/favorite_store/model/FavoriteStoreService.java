package com.favorite_store.model;

import java.util.List;

import com.store_profile.model.StoreProfileVO;

public class FavoriteStoreService {

	private Favorite_StoreDAO_interface dao;

	public FavoriteStoreService() {
		dao = new FavoriteStoreDAO();
	}

	
//------------------KAHN-------------------------------	
	public FavoriteStoreVO delFavoriteStore(String mem_num, String sto_num) {

		FavoriteStoreVO favoritestoreVO = new FavoriteStoreVO();

		favoritestoreVO.setMem_num(mem_num);
		favoritestoreVO.setSto_num(sto_num);
		favoritestoreVO.setIs_favo("N");

		dao.update(favoritestoreVO);

		return favoritestoreVO;
	}

	public List<StoreProfileVO> getFavoStoreList(String mem_num) {

		FavoriteStoreVO favoritestoreVO = new FavoriteStoreVO();

		favoritestoreVO.setMem_num(mem_num);
		favoritestoreVO.setIs_favo("Y");

		return dao.getFavoriteStore(favoritestoreVO);
	}
//-------------------end KAHN-------------------------------
	
	
//--------------------Shawn-------------------------------------
	
	
	public FavoriteStoreVO addFavoriteStore(String mem_num, String sto_num, String is_favo, java.util.Date chang_time) {

		FavoriteStoreVO favoriteStoreVO = new FavoriteStoreVO();

		favoriteStoreVO.setMem_num(mem_num);
		favoriteStoreVO.setSto_num(sto_num);
		favoriteStoreVO.setIs_favo(is_favo);
		favoriteStoreVO.setChang_time(chang_time);

		dao.insertFavoriteStore(favoriteStoreVO);

		return favoriteStoreVO;
	}
	
	public FavoriteStoreVO updateFavoriteStore(String is_favo, java.util.Date chang_time, String mem_num, String sto_num) {

		FavoriteStoreVO favoriteStoreVO = new FavoriteStoreVO();

		favoriteStoreVO.setIs_favo(is_favo);
		favoriteStoreVO.setChang_time(chang_time);
		favoriteStoreVO.setMem_num(mem_num);
		favoriteStoreVO.setSto_num(sto_num);

		dao.updateFavoriteStore(favoriteStoreVO);

		return favoriteStoreVO;
	}
	
	public FavoriteStoreVO getOneFavoriteStore(String mem_num, String sto_num){
		return dao.getOneFavoriteStore(mem_num,sto_num);
	}

	
//------------------end Shawn-------------------------------------	
	
}
