package com.member_chatlog.model;

import java.util.List;

public class MemberChatlogService {
	private MemberChatlogDAO_interface dao;
	
	public MemberChatlogService(){
		dao = new MemberChatlogDAO();
	}
	public MemberChatlogVO addChat(MemberChatlogVO memberChatlogVO){
		dao.insert(memberChatlogVO);
		return memberChatlogVO;
	}
	
	public List<MemberChatlogVO> memGetAll(String mem_num){
		return dao.memGetAll(mem_num);		
	}
	
	public List<MemberChatlogVO> getAll(){
		return dao.getAll();
	}
}
