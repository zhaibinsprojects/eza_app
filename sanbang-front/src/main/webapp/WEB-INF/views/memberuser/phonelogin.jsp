<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${baseurl}" />
<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>易再生-中国再生资源交易平台</title>
    <link rel="stylesheet" href="resource/css/ezsm.css?v=1" />
    <script type="text/javascript" src="resource/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="resource/js/ezsm.js?v=1"></script>
	<script type="text/javascript" src="resource/js/highcharts.js"></script>
	<script type="text/javascript" src="resource/js/userpro/userlogin.js"></script>
	<script type="text/javascript" src="resource/script/layer/mobile/layer.js" charset="utf-8"></script>
	<link rel="stylesheet" type="text/css"  href="resource/script/layer/mobile/need/layer.css" />
	<style>
		html{background:#fff;}
	</style>
</head>
<body style="background:#fff;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-m-login-top">
		<div onclick="javascript:history.go(-1);"></div>
		<div class="login-href">登录</div>
		<div class="login-regist">新用户注册</div>
	</div>
	<div class="blank10"></div>
	<div class="ezsm-m-login-pannel" id="ftCodepage">
		<div class="ezsm-m-login-tit">手机号登录</div>  
		<div class="ezsm-m-login-inputbox" data-warn="phone"><input type="text"  class="mobile" name="mobile" placeholder="请输入手机号" /></div>
		<div class="ezsm-m-login-inputbox"  data-warn="ftcode"><input class="tyuio" type="text" name="code"  placeholder="请输入校验码" style="width:60%;" />
		<div style="cursor: pointer" class="sendFtCodeBut">获取验证码</div>
		</div>
		<a href="userPro/toLoginPage.htm"><div class="ezsm-m-login-href">用账号密码登录 > </div></a>
	</div>
	<div class="blank20"></div>
	<div class="ezsm-normal-centerbtn" id="saveFtPrram">登录</div>
	<script type="text/javascript">
	var sendButStatus=false;
	var baseurl="${baseurl}";
		$(function(){
			$(document).ready(function(){
				$(".login-href").click(function(){ window.location.href="${baseurl}/userPro/toLoginPage.htm"});
				$(".login-regist").click(function(){ window.location.href="${baseurl}/userPro/toRegPage.htm"});
			})
		});
	</script>
	
	
</body>
</html>