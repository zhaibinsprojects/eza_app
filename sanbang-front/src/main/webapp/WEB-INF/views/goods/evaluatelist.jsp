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

</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	<div class="ezsm-normal-top">
		<div onclick=""></div>
		<div>
			<ul class="ezsm-normal-top-nav">
				<li onclick="window.location.href='${baseurl}app/goods/toGoodsShow.htm?id=${good.id}'" ><span>货品</span></li>
				<li class="ezsm-normal-top-nav-sel" onclick="window.location.href='${baseurl}/app/buy/getEvaluateList.htm?goodsid=${good.id}&pageNo=1'"><span>评价</span></li></a>
			</ul>
		</div>
		<div></div>
	</div>
	<div class="ezsm-evaluate-pannel">
		<div class="ezsm-evaluate-tit">货品评价</div>
		<div class="ezsm-evaluate-desc">
			<div>好评率</div>
			<div>${good.highp}%</div>
			<div>共计<span>${good.allcount}</span>评价</div>
		</div>
	</div>
	<div class="blank10"></div>
	<div class="ezsm-evaluate-pannel">
		<div class="ezsm-evaluate-list">
			<ul>
			 <c:forEach items="${dvaluatelist}" var="dv">
				 <li>
						<div class="ezsm-evaluate-list-tit">${fn:substring(dv.user.name,0,3)}*** <span>的评价</span><span>${dv.addTime}</span></div>
						<div class="ezsm-evaluate-list-table">
							<table>
							<tr><td>综合评星</td><td>
								<c:choose>
								<c:when test="${dv.goodQuality eq 0}">
									<img src="resource/img/micon_047.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 1}">
									<img src="resource/img/micon_051.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 2}">
									<img src="resource/img/micon_050.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 3}">
									<img src="resource/img/micon_049.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 4}">
									<img src="resource/img/micon_048.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 5}">
									<img src="resource/img/micon_047.png"/>
								</c:when>
								</c:choose>
								</td>
								</tr>
								<tr><td>评定描述</td><td>${fn:substring(dv.conttent,0,10)}...</td></tr>
								<c:if test="${fn:length(dv.accessroys) eq 0 }">
								<tr><td>上传图片</td><td>无</td></tr>
								</c:if>
								<c:if test="${fn:length(dv.accessroys) gt 0 }">
								<c:forEach items="${dv.accessroys}" var="pic">
								<tr><td>上传图片</td><td><img style="width: 20%;height: 4%;" src="${pic.accessory.path}"/></td></tr>
								</c:forEach>
								</c:if>
							</table>
						</div>
					</li>
			 </c:forEach>
			</ul>
		</div>
	</div>
	
<%@ include file="../goodsfoot.jsp"%> 
</body>

<script type="text/javascript">
var baseurl="${serurl}";
</script>
</html>