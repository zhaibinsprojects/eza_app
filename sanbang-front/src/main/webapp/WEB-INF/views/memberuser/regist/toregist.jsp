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
<script type="text/javascript" src="resource/js/common.js"></script>
<script type="text/javascript" src="resource/script/layer/mobile/layer.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"  href="resource/script/layer/mobile/need/layer.css" />


<script type="text/javascript">
		$(function(){
			$("#sel_xieyi").click(function(){
				if($(this).attr("class")=="" || typeof($(this).attr("class"))=='undefined'){
					$(this).addClass("ezsm-m-reg-xieyi-sel");
					$("#btn_getcode").css("background-color","#00897c");
					$("#btn_getcode").attr("onclick","EZSM_GetCode()");
				}else{
					$(this).removeClass("ezsm-m-reg-xieyi-sel");
					$("#btn_getcode").css("background-color","#ccc");
					$("#btn_getcode").attr("onclick","");
				}
			});
		});
	</script>
	<style>
		html{background:#fff;}
	</style>
</head>
<body style="background:#fff;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-m-login-top">
		<div onclick="javascript:history.go(-1);"></div>
		<div>注册</div>
		<div></div>
	</div>
	<div class="exsm-m-reg-step">
		<ul>
			<li class="exsm-m-reg-step-li-sel">1.输入手机号</li>
			<li>2.设置密码</li>
			<li>3.填写账号信息</li>
		</ul>
	</div>
	<div class="ezsm-m-login-pannel">
		<div class="ezsm-m-login-inputbox"><input data-value="required"    class="registdata"  title="手机号" name="phone" type="text" placeholder="请输入手机号" /></div>
		<div class="ezsm-m-login-inputbox"><input data-value="norequired"  class="registdata"  title="分享码"  name="sharecode" type="text" placeholder="请输入推荐码" style="width:60%;"/></div>
	</div>
	<div class="blank20"></div>
	<div id="btn_getcode" class="ezsm-normal-centerbtn" style="background-color:#ccc;">发送验证码</div>
	<div class="ezsm-m-reg-xieyi">
		<div id="sel_xieyi"></div> 我已阅读并同意<span style="float:none;color:#00897c;">《易再生网注册协议》</span>
	</div>
</body>

<script type="text/javascript">
$(document).ready(function(){
	var baseurl="${baseurl}";
	$("#btn_getcode").click(function(){
		var isnull=generalcheck("ezsm-m-login-pannel");
		if(!isnull){
			return false;
		}
		//检验手机号是否存在
		var array=new Array();
		array["phone"]="123456789";
		var returndate=ajaxpost("userRegist/checkMobile.htm",array);
		alert(returndate);
	})

	
})

</script>
</html>