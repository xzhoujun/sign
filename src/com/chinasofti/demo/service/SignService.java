package com.chinasofti.demo.service;

import java.io.InputStream;

public interface SignService {

	/**
	 * 查询数据库验证用户名密码后，返回签名图像编号
	 * @param sign_user
	 * @param sign_pwd
	 * @return
	 */
	public abstract String checkSign(String sign_user, String sign_pwd);

	/**
	 * 从文件目录或数据库，返回签名图像
	 * @param sign_user
	 * @param sign_pwd
	 * @return
	 */
	public abstract InputStream getSignImg(String signid);

}