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
<script type="text/javascript" src="front/resource/js/loan/area.js"></script>
<script>
		$(function(){
			$(".ezsm-normal-tab2 ul li").click(function(){
				$(".ezsm-normal-tab2 ul li").removeClass("ezsm-normal-tab2-sel");
				$(this).addClass("ezsm-normal-tab2-sel");
				$(".ezsm-normal-content").css("display","none");
				$("#box_"+($(this).attr("data-value"))).css("display","block");
			});
			
			$("#btn_appley").click(function(){
				var userk ="";
				var u ="";
				// APP点击
				var u = navigator.userAgent; // 获取用户设备
				var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
				
				if (isIOS) {
					setupWebViewJavascriptBridge(function(bridge) {
			       		var data = {};
			       		var str = JSON.stringify(data);
						 try {
							 WebViewJavascriptBridge.callHandler('isiosLogin', str, function(data) {
								 userk= data;
								 if(userk!=""){
									 $(".userkey").val(data);
									 $(".msg-bg").css("display","block");
										$(".msg-box").css({"display":"block","height":"459px","margin-top":"40px"});
									 var con_str = "";
										$.ajax({
											type : "post",
											url : baseurl+"/front/app/home/loan/loadalert.htm",
											data : {
											},
											dataType : "html",
											async : false,
											success : function(data) {
												$(".msg-box").html(data);
											   }
										});
								 }else{
									var data = {}
									var str = JSON.stringify(data);
									WebViewJavascriptBridge.callHandler('iosnologin', str, function() {
									});
									}
								});
						} catch (e) {
						}
						
					});
					
					// ios app 设备才执行
					//这段代码是固定的，必须要放到js中
					function setupWebViewJavascriptBridge(callback) {
					    if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
					    if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
					    window.WVJBCallbacks = [callback];
					    var WVJBIframe = document.createElement('iframe');
					    WVJBIframe.style.display = 'none';
					    WVJBIframe.src = 'https://__bridge_loaded__';
					    document.documentElement.appendChild(WVJBIframe);
					    setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
					}
			        // 与OC交互的所有JS方法都要放在此处注册，才能调用通过JS调用OC或者让OC调用这里的JS
					
					var bridge =setupWebViewJavascriptBridge();
						
					} else {
					//收藏
					 try {
						 userk= window.android.isAndroidLogin();
						 if(userk!=""){
							 $(".userkey").val(userk);
							 $(".msg-bg").css("display","block");
								$(".msg-box").css({"display":"block","height":"459px","margin-top":"40px"});
							 var con_str = "";
								$.ajax({
									type : "post",
									url : baseurl+"/front/app/home/loan/loadalert.htm",
									data : {
									},
									dataType : "html",
									async : false,
									success : function(data) {
										$(".msg-box").html(data);
									   }
								});
						 }else{
							 window.android.androidnologin();
						 }
					} catch (e) {
					}
				};
				
				
				$(".msg-bg,.me-alert-close,#btn_cancel").click(function(){
					$(".msg-bg").css("display","none");
					$(".msg-box").css("display","none");
				});
			});
		});
		
	</script>
</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-normal-tab2">
		<ul>
			<li data-value="1" class="ezsm-normal-tab2-sel"><div>货押融资</div></li>
			<li data-value="2"><div>账期融资</div></li>
		</ul>
	</div>
	<div class="ezsm-normal-content" id="box_1">
		<img src="front/resource/images/file_001.jpg">
	</div>
	<div class="ezsm-normal-content" id="box_2" style="display:none;">
		<img src="front/resource/images/file_002.jpg">
	</div>
	<div class="blank50"></div>
	<div class="ezsm-normal-bottombtn" id="btn_appley">申请贷款</div>
	<input name="userkey" type="hidden" class="userkey" value="${userkey}"/>
</body>

<script type="text/javascript">
var baseurl="${serurl}";
</script>
</html>