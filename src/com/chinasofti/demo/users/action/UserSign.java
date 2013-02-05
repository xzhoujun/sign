package com.chinasofti.demo.users.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.chinasofti.demo.service.SignService;
import com.chinasofti.demo.service.SignServiceImpl;


public class UserSign {
	private String sign_user;
	private String sign_pwd;
	private String signid;
	private String img_content; 
	private SignService userService = new SignServiceImpl();
	public String getSign_pwd() {
		return sign_pwd;
	}
	public void setSign_pwd(String sign_pwd) {
		this.sign_pwd = sign_pwd;
	}

	public String getSign_user() {
		return sign_user;
	}

	public void setSign_user(String sign_user) {
		this.sign_user = sign_user;
	}
	public String getSignid() {
		return signid;
	}
	public void setSignid(String signid) {
		this.signid = signid;
	}

	public String getImg_content() {
		return img_content;
	}
	public void setImg_content(String img_content) {
		this.img_content = img_content;
	}
	/**
	 * 显示签名
	 * 
	 * @return
	 * @throws IOException
	 */
	public String sign() throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		
		signid = userService.checkSign(sign_user, sign_pwd);
		if (!StringUtils.isBlank(signid)) {
			result.put("sign_img", "<img src='UserSign!getImage.action?signid="+signid+"'/>");
			result.put("success", "1");
		} else {
			result.put("message", "签名验证错误！");
			result.put("success", "0");
		}

		JSONObject obj = JSONObject.fromObject(result);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(obj.toString());
		out.close();

		return null;
	}
	
	
	/**
	 * 获取签名图片
	 * @return
	 * @throws Exception
	 */
	public String getImage() throws Exception {		
		InputStream in = userService.getSignImg(signid);
		HttpServletResponse response = ServletActionContext.getResponse();
		byte[] buf = new byte[1];
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		
		int len = 0;
		while ((len = in.read(buf)) != -1) {
			os.write(buf, 0, len);
		}
		os.close();
		in.close();
		return null;
	}
	
	
	/**
	 * 显示签名(Base64编码方式)
	 * 
	 * @return
	 * @throws IOException
	 */
	public String signBase64() throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		
		signid = userService.checkSign(sign_user, sign_pwd);
		if (!StringUtils.isBlank(signid)) {
			InputStream in = userService.getSignImg(signid);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			String img_content = new sun.misc.BASE64Encoder().encode(bytes); // 具体的编码方法
			result.put("sign_img", "<img src='data:image/png;base64,"+img_content+"' onerror=\"src='mhtml:http://'+document.location.href+'!mysign.png';\"/>");
			result.put("success", "1");
		} else {
			result.put("message", "签名验证错误！");
			result.put("success", "0");
		}

		JSONObject obj = JSONObject.fromObject(result);

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(obj.toString());
		out.close();

		return null;
	}
	/**
	 * 获取签名图片(Base64编码方式)
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getImageBase64() throws IOException {
		
		InputStream in = userService.getSignImg(signid);
				
		byte[] bytes = new byte[in.available()];
		in.read(bytes);
		String img_content = new sun.misc.BASE64Encoder().encode(bytes); // 具体的编码方法
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(img_content);
		out.close();

		return null;
	}
}
