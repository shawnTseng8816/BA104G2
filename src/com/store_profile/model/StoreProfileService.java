package com.store_profile.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Part;

import com.store_comment.model.StoreCommentVO;

public class StoreProfileService {
	private StoreProfileDAO_interface dao;
	
	public StoreProfileService(){
		dao = new StoreProfileDAO();
	}
	
//--------------------Peiiun----------------------------------
	public List<StoreProfileVO> getAllgeo(){
		return dao.getAllgeo();
	}
	
	public List<StoreProfileVO> getByKeyword(String keyword){
		return dao.search(keyword);
	}
	
	public List<StoreProfileVO> getNoKeyword(){
		return dao.search();
	}
	
	public StoreProfileVO getOneStoName(String sto_num){
		return dao.getOneByPrimary(sto_num);
	}
	
	public StoreProfileVO getOneByPrimary(String sto_num){
		return dao.getOneByPrimary(sto_num);
	}
//--------------------end Peiiun----------------------------------	

	
//---------------------Chi-------------------------------------
	
	public StoreProfileVO addStoreProfile(String sto_acc,    String sto_pwd,   String sto_name, 
			  String guy,        Integer uni_num,  String mobile,
			  String area,       String address,   String email,
			  Date set_time,     byte[] sto_logo,  String sto_introduce, 
			  String sto_status, Integer rem_point) throws IOException {

		StoreProfileVO storeprofileVO = new StoreProfileVO();
		
		storeprofileVO.setSto_acc(sto_acc);
		storeprofileVO.setSto_pwd(sto_pwd);
		storeprofileVO.setSto_name(sto_name);
		storeprofileVO.setGuy(guy);
		storeprofileVO.setUni_num(uni_num);
		storeprofileVO.setMobile(mobile);
		storeprofileVO.setArea(area);
		storeprofileVO.setAddress(address);
		storeprofileVO.setEmail(email);
		storeprofileVO.setSet_time(set_time);
		storeprofileVO.setSto_logo(sto_logo);
		//if (getFileNameFromPart(sto_logo) != null) {
		//storeprofileVO.setSto_logo(getPictureByteArray(sto_logo.getInputStream()));
		//} else {
		//storeprofileVO.setSto_logo(null);
		//}
		storeprofileVO.setSto_introduce(sto_introduce);
		storeprofileVO.setSto_status(sto_status);
		storeprofileVO.setRem_point(rem_point);
		
		dao.insert(storeprofileVO);
		return storeprofileVO;
	}
	
	public StoreProfileVO update(String sto_num, String sto_name, String sto_pwd, String mobile, String address, String email,
	byte[] sto_logo, String sto_introduce, String sto_status) throws IOException {
	
		StoreProfileVO storeprofileVO = new StoreProfileVO();
		
		storeprofileVO.setSto_num(sto_num);
		storeprofileVO.setSto_num(sto_name);
		storeprofileVO.setSto_pwd(sto_pwd);
		storeprofileVO.setMobile(mobile);
		storeprofileVO.setEmail(address);
		storeprofileVO.setAddress(email);
		storeprofileVO.setSto_logo(sto_logo);
		//if (getFileNameFromPart(sto_logo) != null) {
		//storeprofileVO.setSto_logo(getPictureByteArray(sto_logo.getInputStream()));
		//} else {
		//storeprofileVO.setSto_logo(null);
		//}
		
		storeprofileVO.setSto_introduce(sto_introduce);
		storeprofileVO.setSto_status(sto_status);
		
		dao.update(storeprofileVO);
		
		return storeprofileVO;
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
	
	public StoreProfileVO getMyProfile(String sto_num) {
	
		return dao.getStoreProfile(sto_num);
	}
	
	//登入確認
	public StoreProfileVO checkLogin(String sto_acc){
		return dao.checkLogin(sto_acc);
	
	}
	
	//public StoreProfileVO getStatus() {
	//String sto_status=null;
	//return dao.getStatus(sto_status);
	//}
	
	
	public List<StoreProfileVO> getStatus(String sto_status) {
	
		return dao.getStatus(sto_status);
	
	}
	
	public List<StoreProfileVO> getAll() {
		return dao.getAll();
	}
	
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	
	
	public StoreProfileVO changestatus(String sto_num, String sto_status){
	
		StoreProfileVO storeprofileVO = new StoreProfileVO();
		
		storeprofileVO.setSto_num(sto_num);
		
		storeprofileVO.setSto_status(sto_status);
		
		dao.changestatus(storeprofileVO);
		
		return storeprofileVO;
	}
	
//--------------------end Chi--------------------------------	
	
	
	
//-------------Shawn---------------
	
	public Set<StoreProfileVO> getStoreProfileImage(String sto_num) {
		return dao.getStoreProfileImage(sto_num) ;
	}
	public Set<StoreProfileVO> getStoreProfileBySto_num(String sto_num) {
		return dao.getStoreProfileBySto_num(sto_num);
	}
	
	public Set<StoreProfileVO> getStoreProfileAllName(String sto_name) {
		return dao.getStoreProfileAllName(sto_name);
	}
	
//	public List<StoreProfileVO> getAll() {
//		return dao.getAll();
//	}
	
	public StoreProfileVO getStoreProfile(String sto_num) {
		return dao.getStoreProfile(sto_num);
	}
	
	public StoreProfileVO updateStoreProfile(String sto_status, String sto_num) {

		StoreProfileVO storeProfileVO = new StoreProfileVO();

		storeProfileVO.setSto_status(sto_status);
		storeProfileVO.setSto_num(sto_num);

		dao.updateStoreProfile(storeProfileVO);

		return storeProfileVO;
	}
	
	
//------------end Shawn--------------	
	
//---------Eric---------------------

	public StoreProfileVO getOneStoInfo(String sto_num){
		return  dao.getOneStoInfo(sto_num);
		
	}
	
	public void updateStoStatus(String sto_num,String status){
		dao.updateStoStatus(sto_num, status);
	}
	

	public List<StoreCommentVO> getStoComment(String sto_num){
		return dao.getStoComment(sto_num);
	}
	
	public byte[] getLogo(String sto_num){
			return dao.getLogo(sto_num);
	}
	
	public void updateStoInfo(StoreProfileVO storeProfileVO){
		
		dao.updateStoInfo(storeProfileVO);
	}
	
	
//--------end Eric-----------------	
}
