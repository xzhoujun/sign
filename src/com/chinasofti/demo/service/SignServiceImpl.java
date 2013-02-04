package com.chinasofti.demo.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SignServiceImpl implements SignService {
	
	
	/**
	 * 查询数据库验证用户名密码后，返回签名图像编号
	 * @param sign_user
	 * @param sign_pwd
	 * @return
	 */
	public String checkSign(String sign_user,String sign_pwd){
		
		if ("admin".equals(sign_user) && "123456".equals(sign_pwd)) {			
			return "221100admin";
		}else{
			return null;
		}
	}
	
	/**
	 * 从文件目录或数据库，返回签名图像,采用png格式
	 * @param sign_user
	 * @param sign_pwd
	 * @return
	 */
	public InputStream getSignImg(String signid){
		
		try {
			return new FileInputStream(this.getClass().getResource(signid+".png").getFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
