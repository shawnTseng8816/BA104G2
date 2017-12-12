package com.member_profile.model;

import java.util.List;
import java.util.Set;

public interface MemberProfileDAO_interface {

	
//--------------------------KAHN---------------------------------		
	public void update(MemberProfileVO memberProfileVO);
	public MemberProfileVO getMemProfile(MemberProfileVO memberProfileVO);
//--------------------------end KAHN------------------------------
	
//-------------------------Chi----------------------------------
	public void insert(MemberProfileVO memberprofileVO);
	
	public void updateChi(MemberProfileVO memberProfileVO);

	public MemberProfileVO getMemProfile(String mem_num);
	
	public MemberProfileVO checkLogin(String mem_acc);
	
	public List<String> getAllAccount();
//------------------------end Chi-------------------------------	
	
//----------------------Shawn-----------------------------------
	public MemberProfileVO getMemberProfileByMem_num(String mem_num);
	public Set<MemberProfileVO> getMemberProfileAllName(String mem_name);//��ݷ|���޲z-�|���W����r�j�M
	public Set<MemberProfileVO> getMemberProfileImg(String mem_num);//��ݷ|���޲z-�|���W����r�j�M
	public void updateMemberProfile(MemberProfileVO memberProfileVO);
	public List<MemberProfileVO> getAll();
	
	
//----------------end Shawn------------------------------	
	
//---------------Eric------------------
public MemberProfileVO getOneMemInfo(String mem_num);
	
	public byte[] getPic(String mem_num);
	
//------------end Eric----------------	
}
