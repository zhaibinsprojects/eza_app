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
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	<%@ include file="/WEB-INF/views/goodshead.jsp"%> 
	<div class="ezsm-evaluate-pannel">
		<div class="ezsm-evaluate-tit">货品评价</div>
		<div class="ezsm-evaluate-desc">
			<div>好评率</div>
			<div>100%</div>
			<div>共计<span>2</span>评价</div>
		</div>
	</div>
	<div class="blank10"></div>
	<div class="ezsm-evaluate-pannel">
		<div class="ezsm-evaluate-list">
			<ul>
				<li>
					<div class="ezsm-evaluate-list-tit">ge***n <span>的评价</span><span>2018-04-14</span></div>
					<div class="ezsm-evaluate-list-table">
						<table>
							<tr><td>综合评星</td><td><img src="img/micon_047.png"/></td></tr>
							<tr><td>评定描述</td><td>未输入评单描述</td></tr>
							<tr><td>上传图片</td><td>无</td></tr>
						</table>
					</div>
				</li>
				<li>
					<div class="ezsm-evaluate-list-tit">ge***n <span>的评价</span><span>2018-04-14</span></div>
					<div class="ezsm-evaluate-list-table">
						<table>
							<tr><td>综合评星</td><td><img src="img/micon_048.png"/></td></tr>
							<tr><td>评定描述</td><td>未输入评单描述</td></tr>
							<tr><td>上传图片</td><td>无</td></tr>
						</table>
					</div>
				</li>
				<li>
					<div class="ezsm-evaluate-list-tit">ge***n <span>的评价</span><span>2018-04-14</span></div>
					<div class="ezsm-evaluate-list-table">
						<table>
							<tr><td>综合评星</td><td><img src="img/micon_049.png"/></td></tr>
							<tr><td>评定描述</td><td>未输入评单描述</td></tr>
							<tr><td>上传图片</td><td>无</td></tr>
						</table>
					</div>
				</li>
			</ul>
		</div>
	</div>
	
<%@ include file="/WEB-INF/views/goodsfoot.jsp"%> 
</body>
</html>