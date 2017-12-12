package com.member_profile.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Part;

public class MemberProfileService {

	private MemberProfileDAO_interface dao;

	public MemberProfileService() {
		dao = new MemberProfileDAO();
	}

	
//-----------------KAHN---------------------------------------	
	public MemberProfileVO editeMyProfile(String mem_name, String mem_pwd, String mobile, String email, String address,
			Part mem_pic, String mem_num, Integer rem_point) throws IOException {

		MemberProfileVO memberProfileVO = new MemberProfileVO();

		
		memberProfileVO.setMem_name(mem_name);
		memberProfileVO.setMem_pwd(mem_pwd);
		memberProfileVO.setMobile(mobile);
		memberProfileVO.setEmail(email);
		memberProfileVO.setAddress(address);
		if (mem_pic != null && getFileNameFromPart(mem_pic) != null) {
			memberProfileVO.setMem_img(getPictureByteArray(mem_pic.getInputStream()));
		} else {
			memberProfileVO.setMem_img(null);
		}
		memberProfileVO.setMem_num(mem_num);
		memberProfileVO.setRem_point(rem_point);

		dao.update(memberProfileVO);

		return memberProfileVO;
	}

	public MemberProfileVO getMyProfile(String mem_num) {

		MemberProfileVO memberProfileVO = new MemberProfileVO();

		memberProfileVO.setMem_num(mem_num);

		return dao.getMemProfile(memberProfileVO);
	}

	public static byte[] getPictureByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int i;
		while ((i = in.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		in.close();

		return baos.toByteArray();
	}

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	

//-----------------end KAHN---------------------------------------	
	
//----------------Chi---------------------------------------------
	public MemberProfileVO addMemberProfile(String mem_acc,     String mem_pwd, String mem_name, 
			String sex,         Integer age,    String mobile, 
			String cek_mobile,  String email,   String address, 
			Integer rem_point,  byte[] mem_img, String status) throws IOException {

		MemberProfileVO memberProfileVO = new MemberProfileVO();
		
		memberProfileVO.setMem_acc(mem_acc);
		memberProfileVO.setMem_pwd(mem_pwd);
		memberProfileVO.setMem_name(mem_name);
		memberProfileVO.setSex(sex);
		memberProfileVO.setAge(age);
		memberProfileVO.setMobile(mobile);
		memberProfileVO.setCek_mobile(cek_mobile);
		memberProfileVO.setEmail(email);
		memberProfileVO.setAddress(address);
		memberProfileVO.setRem_point(rem_point);
		memberProfileVO.setMem_img(mem_img);
		//if (getFileNameFromPart(mem_img) != null) {
		//memberProfileVO.setMem_img(getPictureByteArray(mem_img.getInputStream()));
		//} else {
		//memberProfileVO.setMem_img(mem_img);
		//}
		memberProfileVO.setStatus(status);
		dao.insert(memberProfileVO);
		
		return memberProfileVO;
	}
	
//	public MemberProfileVO editeMyProfile(String mem_name, String mem_pwd, String mobile, String email, String address,
//		Part mem_img, String mem_num) throws IOException {
//		
//		MemberProfileVO memberProfileVO = new MemberProfileVO();
//		
//		memberProfileVO.setMem_name(mem_name);
//		memberProfileVO.setMem_pwd(mem_pwd);
//		memberProfileVO.setMobile(mobile);
//		memberProfileVO.setEmail(email);
//		memberProfileVO.setAddress(address);
//		if (getFileNameFromPart(mem_img) != null) {
//			memberProfileVO.setMem_img(getPictureByteArray(mem_img.getInputStream()));
//		} else {
//			memberProfileVO.setMem_img(null);
//		}
//		memberProfileVO.setMem_num(mem_num);
//		
//		dao.update(memberProfileVO);
//		
//		return memberProfileVO;
//	}
	
	public MemberProfileVO getMyProfileChi(String mem_num) {
	
		return dao.getMemProfile( mem_num);
	}
	
	
	
	//登入確認
	public MemberProfileVO checkLogin(String mem_acc){
		return dao.checkLogin(mem_acc);
	
	}
	

	public List<String> getAllAccount(){
		return dao.getAllAccount();
	}

//----------------end Chi--------------------------------------	
	
	
//----------------Shawn--------------------------	
	public MemberProfileVO getMemberProfileByMem_num(String mem_num) {
		return dao.getMemberProfileByMem_num(mem_num) ;
	}
	
	public Set<MemberProfileVO> getMemberProfileAllName(String mem_name) {
		return dao.getMemberProfileAllName(mem_name) ;
	}
	
	public Set<MemberProfileVO> getMemberProfileImg(String mem_num) {
		return dao.getMemberProfileImg(mem_num) ;
	}
	
	public List<MemberProfileVO> getAll() {
		return dao.getAll();
	}
	
	public MemberProfileVO updateMemberProfile(String status, String mem_num) {

		MemberProfileVO memberProfileVO = new MemberProfileVO();

		memberProfileVO.setStatus(status);
		memberProfileVO.setMem_num(mem_num);

		dao.updateMemberProfile(memberProfileVO);

		return memberProfileVO;
	}
	
//--------------end Shawn-----------------------	
	
	
//-------------Eric---------------------------
	public MemberProfileVO getOneMemInfo(String mem_num){

		return dao.getOneMemInfo(mem_num);
		
	}
	
	public byte[] getPic(String mem_num){
		
		byte[] pic = dao.getPic(mem_num);
		
		return pic;
	
	}
	
//---------end Eric--------------------------	
}
