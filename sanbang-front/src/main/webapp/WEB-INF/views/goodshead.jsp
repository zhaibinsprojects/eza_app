<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${serurl}"/>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="front/resource/css/ezsm.css?v=1" />
<script type="text/javascript" src="front/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="front/resource/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="front/resource/js/ezsm.js?v=1"></script>
<script type="text/javascript" src="front/resource/js/goods/goods.js"></script>
<script type="text/javascript" src="front/resource/script/layer/mobile/layer.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"  href="front/resource/script/layer/mobile/need/layer.css" />
</head>
<div class="ezsm-normal-top">
		<div onclick="javascript:history.go(-1);"></div>
		<div>
			<ul class="ezsm-normal-top-nav">
				<li onclick="window.location.href='${baseurl}app/goods/toGoodsShow.htm?id=${good.id}'" class="ezsm-normal-top-nav-sel"><span>货品</span></li>
				<li onclick="window.location.href='${baseurl}/app/buy/getEvaluateList.htm?goodsid=${good.id}&pageNo=1'"><span>评价</span></li></a>
			</ul>
		</div>
		<div></div>
	</div>
</body>
</html>