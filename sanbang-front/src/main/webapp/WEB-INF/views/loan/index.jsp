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

<style type="text/css">
	.hTwoFater_yzs h2{border-left: 5px solid #cdb57c;}
	.hTwoFater_yzs h2 span{color: #333333;}
.pj_section{margin-bottom:10px;    background-color:#fff;    border-bottom:1px solid #e5e5e5;    border-top:1px solid #e5e5e5;    height:auto;    width:100%;}
.pj_sec_con{color:#333333;font-size:14px;line-height:1.5;margin:2%;}
.pj_sec_liucheng{margin:7% 2% 7% 5%;}
.pj_sec_liucheng_i{background-color:#fbf9f5;font-size:18px;color:#333333;line-height:2;text-align:center;position:relative;}
.pj_sec_liucheng_i span{font-size:18px;color:#cdb57c;border:1px dashed #cdb57c;line-height:1.5;width:26px;border-radius:50%;position:absolute;left:-13px;top:3px;background-color:#fff;}
.pj_bottom_btn{background:url('front/resource/img/pj_jr_btn.png') center center;text-align:center;font-size:16px;color:#433e2f;position:fixed;bottom:0;width:100%;line-height:3;font-weight: bold;}
.pj_sec_lc_arrow{background:url('front/resource/img/pja_jr_01.png') center center no-repeat;background-size:60%;width:19px;height:30px;display:block;margin:5px auto;}
</style>

<script>
		$(function(){
			$(".ezsm-normal-tab2 ul li").click(function(){
				$(".ezsm-normal-tab2 ul li").removeClass("ezsm-normal-tab2-sel");
				$(this).addClass("ezsm-normal-tab2-sel");
				$(".ezsm-normal-content").css("display","none");
				$("#box_"+($(this).attr("data-value"))).css("display","block");
			});
			
			$(".ezsm-normal-bottombtn").click(function(){
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
									 window.location.href=baseurl+"/front/app/home/loan/loadalert.htm";
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
					    setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0);
					}
			        // 与OC交互的所有JS方法都要放在此处注册，才能调用通过JS调用OC或者让OC调用这里的JS
					
					var bridge =setupWebViewJavascriptBridge();
						
					} else {
					 try {
						 userk= window.android.isAndroidLogin();
						 if(userk!=""){
							 window.location.href=baseurl+"/front/app/home/loan/loadalert.htm";
						 }else{
							 window.android.androidnologin();
						 }
					} catch (e) {
					}
				};

				
				$(".aicon_yzs").click(function(){
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
	
	<div class="pj_supply_service">
  	<img src="front/resource/img/pj_supply_01.jpg" width="100% "/>
  	<section class="pj_section">
  		<div class="hTwoFater_yzs">
      <h2><span>业务介绍</span></h2>
    </div>
          <div class="pj_sec_con">易再生网货押业务是企业用户以自有库存货物（原料或产成品），或即将购买的货物作为质押物，平台对其进行货值和风险进行评估，以约定价格进行贸易采购，入库共同监管，用户可以在约定的期限内，以约定的价格随时赎回。</div>
  	</section>
  	<section class="pj_section">
  		<div class="hTwoFater_yzs">
      <h2><span>申请流程</span></h2>
    </div>
    <div class="pj_sec_liucheng">
    	<div class="pj_sec_liucheng_i">
    		<span>1</span>填写申请资料
    	</div>
    	<i class="pj_sec_lc_arrow"></i>
    	<div class="pj_sec_liucheng_i">
    		<span>2</span>评估货值以及额度
    	</div>
    	<i class="pj_sec_lc_arrow"></i>
    	<div class="pj_sec_liucheng_i">
    		<span>3</span>签署业务协议
    	</div>
    	<i class="pj_sec_lc_arrow"></i>
    	<div class="pj_sec_liucheng_i">
    		<span>4</span> 货物质检入库
    	</div>
    	<i class="pj_sec_lc_arrow"></i>
    	<div class="pj_sec_liucheng_i">
    		<span>5</span>货物回购
    	</div>
    	</div>
  	</section>
  </div>
   <div class="ezsm-normal-bottombtn">
 	立即申请
 </div>
	<input name="userkey" type="hidden" class="userkey" value="${userkey}"/>
</body>

<script type="text/javascript">
  var baseurl="${serurl}";
 /* var baseurl="http://10.10.10.232/"; */
</script>
</html>