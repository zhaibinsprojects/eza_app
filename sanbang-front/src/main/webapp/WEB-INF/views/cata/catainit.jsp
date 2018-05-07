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
		$(function(){
			//Select location
			$("#sel_city").click(function(){
				if($(".msg-local").css("display")=="none"){
					$(".msg-local").show();
					var height = window.screen.height;
					$(".msg-local").css({"height":(height-80)+"px","overflow-y":"scroll"});
				}else{
					$(".msg-local").hide();
				}
			});
			$(".msg-local-con li").click(function(){
				$(".msg-local-con li").removeClass("msg-local-con-li-sel");
				$(this).addClass("msg-local-con-li-sel");
				$("#sel_city span").html($(this).html());
				$("#sel_city span").attr("data-value",$(this).attr("data-value"));
				$(".msg-local").hide();
			});
			
			//Change left li
			$(".ezsm-type-left li").click(function(){
				$(".ezsm-type-left li").removeClass("ezsm-type-left-li-sel");
				$(this).addClass("ezsm-type-left-li-sel");
			});
		});
	</script>
</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-index">
		<div class="ezsm-index-top">
			<div class="ezsm-index-local" id="sel_city"><span data-value="hebei">河北</span><img src="resource/img/micon_017.png"/></div>
			<div class="ezsm-index-search"><input type="text" placeholder="请输入您要找的关键字..."/></div>
			<div class="ezsm-index-msg"><img src="resource/img/micon_018.png"/><br>消息</div>
		</div>
		<div class="ezsm-type-pannel">
			<div class="ezsm-type-left">
				<ul>
					<li class="ezsm-type-left-li-sel">再生塑料</li>
					<li>XXXXX</li>
					<li>XXXXXX</li>
				</ul>
			</div>
			<div class="ezsm-type-right">
				<div class="ezsm-type-right-tit">再生通用塑料</div>
				<div class="ezsm-type-right-box">
					<table cellspacing="0">
						<tr> <td>PP</td> <td>LDPE</td> <td>HDPE</td> <td>LLDPE</td> </tr>
						<tr> <td>PVC</td> <td>GPPS</td> <td>EPS</td> <td>HIPS</td> </tr>
						<tr> <td>ABS</td> <td>AS</td> <td>K胶</td> <td>PMMA</td> </tr>
						<tr> <td>EVA</td> <td></td> <td></td> <td></td> </tr>
					</table>
				</div>

				<div class="ezsm-type-right-tit">再生工程塑料</div>
				<div class="ezsm-type-right-box">
					<table cellspacing="0">
						<tr> <td>PA</td> <td>PET</td> <td>PBT</td> <td>POM</td> </tr>
						<tr> <td>PP0</td> <td>PC</td> <td>其他</td> <td></td> </tr>
					</table>
				</div>
				
				<div class="ezsm-type-right-tit">再生特种塑料</div>
				<div class="ezsm-type-right-box">
					<table cellspacing="0">
						<tr> <td>PUS</td> <td>LCP</td> <td>PEEK</td> <td>PI</td> </tr>
						<tr> <td>PTFE</td> <td></td> <td></td> <td></td> </tr>
					</table>
				</div>
			</div>
		</div>
		
		<div class="blank10"></div>
		<div class="blank70"></div>
		<div class="ezsm-index-bottom">
		
			<ul>
				<a href="home/index.htm"><li><img src="resource/img/micon_006.png"/><br>首页</li></a>
				<a href="cata/init.htm"><li class="ezsm-index-bottom-sel"><img src="resource/img/micon_009.png"/><br>品类</li></a>
				<a href="data/init.htm"><li class="ezsm-index-bottom-big"><img src="resource/img/micon_010.png"/><br>价格</li></a>
				<a href="buyer/order.htm"><li><img src="resource/img/micon_012.png"/><br>采购单</li></a>
				<a href="M05-会员中心-会员登录-密码登录.html"><li><img src="resource/img/micon_014.png"/><br>我的</li></a>
			</ul>
		</div>
	</div>
		<!-- 选择城市 -->
		<div class="msg-local" style="top:40px;">
			<div class="msg-local-tit">全部地区</div>
			<div class="msg-local-now"><img src="resource/img/micon_026.png"/>您的位置：<span>河北</span></div>
			<div class="msg-local-tit">热门省市</div>
			<div class="msg-local-con">
				<ul><li data-value="beijing">北京</li><li data-value="shanghai">上海</li><li data-value="guangzhou">广州</li></ul>
				<div class="blank0c"></div>
			</div>
			<div class="msg-local-tit">A</div>
			<div class="msg-local-con">
				<ul>
					<li data-value="beijing">北京</li><li data-value="shanghai">上海</li><li data-value="guangzhou">广州</li>
					<li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li>
					<li data-value="guangzhou">广州</li><li>广州</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			<div class="msg-local-tit">B</div>
			<div class="msg-local-con">
				<ul>
					<li data-value="beijing">北京</li><li data-value="shanghai">上海</li><li data-value="guangzhou">广州</li>
					<li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li>
					<li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			<div class="blank0c"></div>
		</div>
</body>
</html>