<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${serurl}" />
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="front/resource/css/ezsm.css?v=1" />
<script type="text/javascript"
	src="front/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="front/resource/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="front/resource/js/ezsm.js?v=1"></script>
<script type="text/javascript" src="front/resource/js/loan/area.js"></script>
<script type="text/javascript" src="front/resource/js/loan/loan.js"></script>
<script type="text/javascript" src="front/resource/script/layer/mobile/layer.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"  href="front/resource/script/layer/mobile/need/layer.css" />
</head>
<body style="background: #efefef;">
	
		<div class="me-alert-close">
			<span>×</span>
		</div>
		<div class="me-alert-box-tit">申请贷款</div>
		<table class="me-alert-box-table1">
			<tbody>
				<tr>
					<td><span>*</span>服务类型</td>
					<td><select class="applyType"><option class="-1">请选择</option>
							<option class="0">货押融资服务</option>
							<option class="1">账期融资服务</option></select></td>
				</tr>
				<tr>
					<td><span>*</span>经营类型</td>
					<td>
						<select class="mainBusiness">
							<option class="-1">请选择</option>
							<c:forEach items="${comtype}" var="com">
							<option class="${com.id}">${com.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><span>*</span>企业名称</td>
					<td><input class="companyName" type="text" placeholder="请填写企业名称"></td>
				</tr>
				<tr>
					<td><span>*</span>联系人</td>
					<td><input class="contacts" type="text" placeholder="请填写联系人"></td>
				</tr>
				<tr>
					<td><span>*</span>联系方式</td>
					<td><input class="telNum" type="text" placeholder="请填写联系电话"></td>
				</tr>
				<tr>
					<td>所在地</td>
					<td>
					<select id="province" name="province"  class="addresschoose"   style="width: 80px;">
					</select>
					<select id="city" name="city" class="addresschoose" style="width: 80px;">
					</select>
					<select id="area"  name="area" class="addresschoose" style="width: 80px;">
					</select>
					<script type="text/javascript">
					$(document).ready(function(){
						getAddressInint(0,null,null,'province','city','area');
					})
					</script>
					</td>
				</tr>
				<tr>
					<td>详细地址</td>
					<td><input class="address" type="text" placeholder="请填写详细地址"></td>
				</tr>
				<tr>
					<td><span>*</span>贷款金额</td>
					<td><input class="loanAmount" type="text" placeholder="请填写贷款金额"></td>
				</tr>
			</tbody>
		</table>
		<div class="me-alert-box-btn" id="btn_save">提交资料</div>
		<input name="userkey" type="hidden" class="userkey" value="${userkey}"/>
</body>
<script type="text/javascript">
	var baseurl = "${serurl}";
</script>
</html>