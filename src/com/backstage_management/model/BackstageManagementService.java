package com.backstage_management.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.Part;

public class BackstageManagementService {
	
	private BackstageManagementDAO dao;
	
	public BackstageManagementService(){
		dao = new BackstageManagementDAO();
	}
	
	
//-------------------Peiiun--------------------------------	
	public BackstageManagementVO addStaff(BackstageManagementVO bmVO){
		String bm_no = dao.insert(bmVO);
		bmVO.setBm_no(bm_no);
		return bmVO;
	}
	
	public BackstageManagementVO updateStaff(BackstageManagementVO bmVO){
		dao.update(bmVO);
		return bmVO;
	}
	
	public BackstageManagementVO findbyPrimaryKey(String bm_no){
		return dao.findbyPrimaryKey(bm_no);
	}
	
	public List<BackstageManagementVO> getAll(){
		return dao.getAll();
	}
	
	public int checkBm_num (String bm_num){
		return dao.checkBm_num(bm_num);
	}
//-------------------end Peiiun--------------------------------		
	
	
//-------------------Chi-------------------------------------
	public BackstageManagementVO addBackstageManagement(String bm_name,    String bm_number, String bm_mail,
            String bm_banknum, byte[] bm_img,    String bm_num, 
            String bm_pwd,     String bm_jstatus) {

		BackstageManagementVO backstagemanagementVO = new BackstageManagementVO();	
	
		backstagemanagementVO.setBm_name(bm_name);
		backstagemanagementVO.setBm_number(bm_number);
		backstagemanagementVO.setBm_mail(bm_mail);
		backstagemanagementVO.setBm_banknum(bm_banknum);
		backstagemanagementVO.setBm_img(bm_img);
		//if (getFileNameFromPart(sto_logo) != null) {
		//storeprofileVO.setSto_logo(getPictureByteArray(sto_logo.getInputStream()));
		//} else {
		//storeprofileVO.setSto_logo(null);
		//}
		backstagemanagementVO.setBm_num(bm_num);
		backstagemanagementVO.setBm_pwd(bm_pwd);
		backstagemanagementVO.setBm_jstatus(bm_jstatus);
		
		dao.insertChi(backstagemanagementVO);
		return backstagemanagementVO;
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
	
	public BackstageManagementVO getOneBackstageManagement(String bm_no) {
		return dao.findByPrimaryKey(bm_no);
	}
	
	public List<BackstageManagementVO> getAllChi() {
		return dao.getAllChi();
	}
	
	public BackstageManagementVO checkLogin(String bm_num){
		return dao.checkLogin(bm_num);
	
	}
	
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
		return null;
		}
		return filename;
	}
	
	
//------------------end Chi----------------------------------
	
}
