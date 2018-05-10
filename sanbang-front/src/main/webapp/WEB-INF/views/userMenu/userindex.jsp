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
    <script src="resource/js/jquery-1.9.1.min.js"></script>
    <script src="resource/js/ezsm.js?v=1"></script>
    <script>
		$(function(){});
	</script>
</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-index">
		<div class="ezsm-me-top">
			<div style="background:none"></div>
			<div></div>
			<div class="ezsm-index-msg"><img src="resource/img/micon_018.png"/><br>消息</div>
		</div>

		
		<div class="ezsm-me-banner" onclick="window.location.href='#'">
			<div class="ezsm-me-banner-icon"><img src="resource/img/micon_076.png"></div>
			<div class="ezsm-me-banner-name"><a href="userPro/toLoginPage.htm">登录</a> / <a href="userRegist/toRegist.htm">注册</a></div>
		</div>
		<div class="ezsm-me-tab">
			<a href="M07-买家会员中心-首页.html"><div><img src="resource/img/micon_080.png">买家会员中心</div></a>
			<a href="M07-供应商个人中心-首页.html"><div><img src="resource/img/micon_081.png">供应商个人中心</div></a>
		</div>

		<div class="blank10"></div>
		<a href="#"><div class="ezsm-me-main-box"><img src="resource/img/micon_082.png">行情报价<div></div></div></a>
		<a href="#"><div class="ezsm-me-main-box"><img src="resource/img/micon_083.png">资金管理<div></div></div></a>
		<a href="#"><div class="ezsm-me-main-box"><img src="resource/img/micon_084.png">金融服务<div></div></div></a>
		<div class="blank10"></div>
		<a href="M06-设置.html"><div class="ezsm-me-main-box"><img src="resource/img/micon_085.png">设置<div></div></div></a>
		<div class="blank10"></div>
		<div class="blank70"></div>
		<div class="ezsm-index-bottom">
			<ul>
				<a href="home/index.htm"><li><img src="resource/img/micon_006.png"/><br>首页</li></a>
				<a href="cata/init.htm"><li><img src="resource/img/micon_008.png"/><br>品类</li></a>
				<a href="cata/init.htm"><li class="ezsm-index-bottom-big"><img src="resource/img/micon_010.png"/><br>价格</li></a>
				<a href="buyer/order.htm"><li><img src=" resource/img/micon_012.png"/><br>采购单</li></a>
				<a href="user/index.htm"><li class="ezsm-index-bottom-sel"><img src="resource/img/micon_015.png"/><br>我的</li></a>
			</ul>
		</div>
	</div>
</body>
</html>