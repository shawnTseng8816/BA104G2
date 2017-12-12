package com.favorite_store.model;

import java.util.*;

import com.store_profile.model.StoreProfileVO;

public interface Favorite_StoreDAO_interface {
	
//----------KAHN-------------------
	public void update(FavoriteStoreVO favoritestoreVO);
	public List<StoreProfileVO> getFavoriteStore(FavoriteStoreVO favoritestoreVO);
//----------end KAHN-------------------
	
	
	
//----------Shawn-------------------
	public void insertFavoriteStore(FavoriteStoreVO favoriteStoreVO);
	public FavoriteStoreVO getOneFavoriteStore(String mem_num,String sto_num);
	public void updateFavoriteStore(FavoriteStoreVO favoriteStoreVO);
	
//----------end Shawn-------------------	
}
