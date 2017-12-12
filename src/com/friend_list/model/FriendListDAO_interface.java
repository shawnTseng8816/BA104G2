package com.friend_list.model;

import java.util.*;

import com.member_profile.model.MemberProfileVO;

public interface FriendListDAO_interface {
	
//-------------------KAHN-------------------------------	
	public void insert(FriendListVO friendlistVO);

	public void update(FriendListVO friendlistVO);
	
	public List<MemberProfileVO> getFromInv(FriendListVO friendlistVO);
	
	public List<MemberProfileVO> getFromInvd(FriendListVO friendlistVO);
	
	public List<MemberProfileVO> getMembers(String name, FriendListVO friendlistVO);
//-------------------end KAHN-------------------------------	
	
//-------------------Shawn--------------------------------
	public FriendListVO getIsFriend(String inv_mem_num,String invd_mem_num);
	public void insertFriendList(FriendListVO friendListVO);
	public void updateFriendList(FriendListVO friendListVO);
	
//-----------------end Shawn------------------------------	
	
}
