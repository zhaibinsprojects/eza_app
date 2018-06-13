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
<script type="text/javascript" src="resource/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="resource/js/ezsm.js?v=1"></script>

</head>
<body style="background:#efefef;">
	<div class="blank70"></div>
	<div class="ezsm-normal-bottombtnB-box">
		<div class="ezsm-shopdetail-bottombtnbox">
			<div onclick="window.location.href='M06-采购单.html'"><img src="resource/img/micon_039.png"/><br>采购单</div>
			<div><img src="resource/img/micon_041.png"/><br>收藏</div>
			<div><img src="resource/img/micon_043.png"/><br>电话</div>
			<div><img src="resource/img/micon_045.png"/><br><a href="https://webchat.7moor.com/wapchat.html?accessId=77ad4f10-fa6f-11e7-b5e9-3f25a985904b&amp;fromUrl=&amp;urlTitle=" style="color: #333333;">客服</a></div>
		</div>
		<c:choose>
		<c:when test="${good.inventory eq 0}">
		<div class="ezsm-normal-bottombtnB2">预约预定</div>
		</c:when>
		<c:when test="${good.inventory gt 0 }">
		<div class="ezsm-normal-bottombtnB2" style="background: red;">立即购买</div>
		</c:when>
		</c:choose>
	</div>
</body>
</html>