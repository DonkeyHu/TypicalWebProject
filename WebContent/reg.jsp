<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/ajaxUtil.js"></script>


<script type="text/javascript">
		var nameCheckFlag=true;
	function checkForm(frm){
		var flag2=true;
		var uname_ok=checkFormField(frm.uname,$("unameMeg"),/^\w{5,20}$/,"用户名不能为空","请输入5-20位的字母数字下划线");
		var pwd_ok=checkFormField(frm.pwd,$("pwdMeg"),/^\w{5,20}$/,"密码不能为空","请输入5-20位的字母数字下划线");
		var pwd2=$("pwd2").value;
		var email_ok=checkFormField(frm.email,$("emailMeg"),/^\w+@[a-zA-Z0-9]+(\.[A-Za-z]{2,4}){1,2}$/,"邮件不能为空","请输入正确格式");
		var phone_ok=checkFormField(frm.phone,$("phoneMeg"),/^1[356789]\d{9}$/,"电话不能为空","请输入正确格式");
		var age_ok=checkFormField(frm.age,$("ageMeg"),/^\d{2}$/,"年龄不能为空","请输入正确格式");
		$("pwdMeg2").innerHTML="";//字写出去了，页面就会保存记录了
		if(pwd2!=frm.pwd.value){
			$("pwdMeg2").innerHTML="密码不一致，请重新输入";
			flag2=false;
		}
		return flag2&&uname_ok && pwd_ok&&email_ok&&phone_ok&&age_ok&&nameCheckFlag;
	}
	
	function testName(uname){
		
		handleXHRText("get", "UserServlet", "method=checkName&uname="+uname.value, true, function(result){
			if(result=="true"){
				$("unameMeg").innerHTML="恭喜你，这个用户可用!!!"
			}else{
				$("unameMeg").innerHTML="很抱歉，这个用户已存在!!!"
				nameCheckFlag=false;
			}
		})
	}
	
	
</script>
</head>
<body>
	<c:import url="head.jsp"></c:import>
	<form name="regFrm" method="get" action="UserServlet" onsubmit="return checkForm(this);">
		用户名:<input type="text" name="uname" onblur="testName(this);"/><span id="unameMeg" class="error"></span><br/>
		密码：<input type="password" name="pwd" /><span id="pwdMeg" class="error"></span><br/>
		确认密码:<input type="password" id="pwd2" /><span id="pwdMeg2" class="error"></span><br/>
		邮件:<input type="text" name="email" /><span id="emailMeg" class="error"></span><br/>
		电话:<input type="text" name="phone" /><span id="phoneMeg" class="error"></span><br/>
		年龄:<input type="text" name="age" /><span id="ageMeg" class="error"></span><br/>
		性别:<input type="radio" name="gender" value="0" checked />男
			<input type="radio" name="gender" value="1" />女<br/>
		地址:<input type="text" name="address" /><br/>
		学历:<select name="degree">
				<option value="0">高中</option>
				<option value="1">专科</option>
				<option value="2" selected>本科</option>
				<option value="3">硕士</option>
				<option value="4">博士</option>
			</select><br/>
			<!--不想在客户端显示出来，但又想把数据提交给数据库 -->
			<input type="hidden" name="method" value="reg" />
			<input type="hidden" name="formToBean" value="com.software.job.po.Users" />
			<%	
				session.setAttribute("token", new Date().getTime());
			%>
			<input type="hidden" name="token" value="${sessionScope.token}"/>
			<input type="submit" value="注册" />
	</form>
	<c:import url="foot.jsp"></c:import>
</body>
</html>