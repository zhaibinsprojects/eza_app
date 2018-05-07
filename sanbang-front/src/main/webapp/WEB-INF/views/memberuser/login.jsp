<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${baseurl}"/>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="resource/css/ezsm.css?v=1" />
<script type="text/javascript" src="resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="resource/js/ezsm.js?v=1"></script>
<script type="text/javascript" src="resource/js/highcharts.js"></script>
<script type="text/javascript">
	$(function() {
	});
</script>
<style>
html {
	background: #fff;
}
</style>
</head>
<body style="background: #fff;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>

	<div class="ezsm-m-login-top">
		<div onclick="javascript:history.go(-1);"></div>
		<div>登录</div>
		<div>忘记密码</div>
	</div>
	<div class="blank10"></div>
	<div class="ezsm-m-login-pannel">
		<div class="ezsm-m-login-tit">密码登录</div>
		<div class="ezsm-m-login-inputbox">
			<input type="text" placeholder="请输入手机号/登录名" />
		</div>
		<div class="ezsm-m-login-inputbox">
			<input type="text" placeholder="请输入密码" />
		</div>
		<a href="userPro/toPhoneLoginPage.htm"><div class="ezsm-m-login-href">用手机短信登录></div></a>
	</div>
	<div class="blank20"></div>
	<div class="ezsm-normal-centerbtn" id="btn_submit">登录</div>
</body>

</html>