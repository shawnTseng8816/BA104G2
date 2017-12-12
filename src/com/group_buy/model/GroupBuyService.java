package com.group_buy.model;

import java.util.List;

import com.member_profile.model.MemberProfileVO;

public class GroupBuyService {

	private GroupBuyDAO_interface dao;

	public GroupBuyService() {
		dao = new GroupBuyDAO();
	}

	public GroupBuyVO addGroupBuy(String inv_mem_num, String invd_mem_num, String meror_num) {

		GroupBuyVO groupBuyVO = new GroupBuyVO();

		groupBuyVO.setInv_mem_num(inv_mem_num);
		groupBuyVO.setInvd_mem_num(invd_mem_num);
		groupBuyVO.setMeror_num(meror_num);
		groupBuyVO.setIsaccept("ONCONFIRM");

		dao.insert(groupBuyVO);

		return groupBuyVO;
	}

	public GroupBuyVO setIsAccept(String is_accept, String invd_mem_num, String meror_num) {

		GroupBuyVO groupBuyVO = new GroupBuyVO();

		groupBuyVO.setIsaccept(is_accept);
		groupBuyVO.setInvd_mem_num(invd_mem_num);
		groupBuyVO.setMeror_num(meror_num);

		dao.update(groupBuyVO);

		return groupBuyVO;
	}

	public List<GroupBuyVO> getGroupBuyMems(String inv_mem_num, String meror_num) {

		GroupBuyVO groupBuyVO = new GroupBuyVO();

		groupBuyVO.setInv_mem_num(inv_mem_num);
		groupBuyVO.setMeror_num(meror_num);

		return dao.getGroupBuyMem(groupBuyVO);
	}
	
	public List<GroupBuyVO> getMyGroupBuyInvite(String invd_mem_num) {

		GroupBuyVO groupBuyVO = new GroupBuyVO();

		groupBuyVO.setInvd_mem_num(invd_mem_num);
		groupBuyVO.setIsaccept("ONCONFIRM");

		return dao.getMyGroupBuyInvite(groupBuyVO);
	}

	public MemberProfileVO getInviter(String meror_num) {

		GroupBuyVO groupBuyVO = new GroupBuyVO();

		groupBuyVO.setMeror_num(meror_num);

		return dao.getInviter(groupBuyVO);
	}
}
