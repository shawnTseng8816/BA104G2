package com.member_chatlog.model;

import java.util.List;

public interface MemberChatlogDAO_interface {
	public void insert(MemberChatlogVO memberChatlogVO);
	public List<MemberChatlogVO> memGetAll(String mem_num);
	public List<MemberChatlogVO> getAll();

}
