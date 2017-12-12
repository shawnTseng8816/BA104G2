package com.group_buy.model;

import java.util.List;

import com.member_profile.model.MemberProfileVO;

public interface GroupBuyDAO_interface {
	
	public void insert(GroupBuyVO groupBuyVO);

	public void update(GroupBuyVO groupBuyVO);

	public List<GroupBuyVO> getGroupBuyMem(GroupBuyVO groupBuyVO);
	
	public List<GroupBuyVO> getMyGroupBuyInvite(GroupBuyVO groupBuyVO);
	
	public MemberProfileVO getInviter(GroupBuyVO groupBuyVO);
}
