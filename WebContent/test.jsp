<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery_sign.js"></script>
<script>
$(document).ready(function(){	
	//使用签名时，只需调用SignShow方法，并传入签名的用户名    [sign_img为定义签名显示的位置]
	$('#button_sign').click( function () { $(this).sign('SignShow', 'admin');});	
});
</script>

<body bgcolor="#efefef">
	<table align="center" style="">
		<tr>
			<td>姓名:</td>
			<td><input type="text" name="uname" /></td>
		</tr>
		<tr>
			<td>生日:</td>
			<td><input type="text" name="birthday" /></td>
		</tr>
		<tr>
			<td>地址:</td>
			<td><input type="text" name="addr" /></td>
		</tr>
		<tr>
			<td>备注:</td>
			<td><textarea rows="5" cols="30" name="remark"></textarea></td>
		</tr>
		<tr>
			<td colspan="2"><div id="sign_img"></div></td>
		</tr>
		<tr>
			<td colspan="2">			
			<input type="button" value="签名" id="button_sign"/> 
			<input type="button" value="提交" /></td>
		</tr>
	</table>	
	
	
	</body>
    
</html>