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
<script type="text/javascript">
		$(function(){
			$(".ezsm-normal-top-nav li").click(function(){
				$(".ezsm-normal-top-nav li").removeClass("ezsm-normal-top-nav-sel");
				$(this).addClass("ezsm-normal-top-nav-sel");
			});
			
			$(".ezsm-shopdetail-picbox").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-visual").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image ul").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image li").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image li span").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image li a").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-flicking-con").css("top",($("body").width()-30)+"px");
			
			//轮播图效果
			$(document).ready(function(){
				$(".ezsm-shopdetail-picbox-main-visual").hover(function(){ $("#btn_prev,#btn_next").fadeOut();
				},function(){ $("#btn_prev,#btn_next").fadeOut(); });
				$dragBln = false;
				$(".ezsm-shopdetail-picbox-main-image").touchSlider({
					flexible : true,
					speed : 200,
					btn_prev : $("#btn_prev"),
					btn_next : $("#btn_next"),
					paging : $(".ezsm-shopdetail-picbox-flicking-con a"),
					counter : function (e){ $(".ezsm-shopdetail-picbox-flicking-con a").removeClass("on").eq(e.current-1).addClass("on"); }
				});
				$(".ezsm-shopdetail-picbox-main-image").bind("mousedown", function() { $dragBln = false; });
				$(".ezsm-shopdetail-picbox-main-image").bind("dragstart", function() { $dragBln = true; });
				$(".ezsm-shopdetail-picbox-main-image a").click(function(){ if($dragBln) { return false; } });
				timer = setInterval(function(){ $("#btn_next").click(); }, 5000);
				$(".ezsm-shopdetail-picbox-main-visual").hover(function(){ clearInterval(timer);
				},function(){ timer = setInterval(function(){ $("#btn_next").click(); },5000); });
				
				$(".ezsm-shopdetail-picbox-main-image").bind("touchstart",function(){ clearInterval(timer);
				}).bind("touchend", function(){ timer = setInterval(function(){ $("#btn_next").click(); }, 5000); });
			});
		});
	</script>
</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-normal-top">
		<div onclick="javascript:history.go(-1);"></div>
		<div>
			<ul class="ezsm-normal-top-nav">
				<li onclick="window.location.href='${baseurl}app/goods/toGoodsShow.htm?id=${good.id}'" class="ezsm-normal-top-nav-sel"><span>货品</span></li>
				<li onclick="window.location.href='M04-品类-评价.html'"><span>评价</span></li></a>
			</ul>
		</div>
		<div></div>
	</div>
	
	<div class="ezsm-shopdetail-picbox">
		<div class="ezsm-shopdetail-picbox-main-visual">
			<div class="ezsm-shopdetail-picbox-flicking-con">
				<a href="#">1</a>
				<a href="#">2</a>
				<a href="#">3</a>
			</div>
			<div class="ezsm-shopdetail-picbox-main-image">
				<ul> 
				 <c:forEach items="${good.mainphoto}" var="mainphoto">
				 <li><img src="${mainphoto.photo.path}"/></li>
				 </c:forEach>
				 <c:forEach items="${good.goods_photos}" var="goods_photos">
				 <li><img src="${goods_photos.photo.path}"/></li>
				 </c:forEach>
				</ul>
				<a href="javascript:;" id="btn_prev"></a>
				<a href="javascript:;" id="btn_next"></a>
			</div>
		</div>
	</div>
	<div class="ezsm-shopdetail-tit">${good.name}<div onclick="window.location.href='M06-品类-立即购买-试样.html'">试样</div></div>
	<div class="ezsm-shopdetail-param">
		<table cellspacing="0">
			<tr> <td>单价</td> <td><span class="colororange" style="font-weight:bold;font-size:15px;margin-right:20px;">¥${good.price}</span><span style="color:#999999;font-size:12px;">(含税，不含物流费用)</span></td> </tr>
			<tr> <td>库存</td> <td>${good.inventory}吨</td> </tr>
			<tr> <td>库存地</td> <td>${good.addess}</td> </tr>
			<tr> <td>颜色</td> <td>${good.color.name}</td> </tr>
			<tr> <td>形态</td> <td>${good.form.name}</td> </tr>
			<tr> <td>原料来源</td> <td>${good.source}</td> </tr>
			<tr> <td>用途</td> <td>${good.purpose}</td> </tr>
			<tr> <td>物流方式</td> <td>${good.logistics.name}</td> </tr>
			<tr> <td>上批货品质检结果</td> <td><span class="colorgreen" style="margin-right:20px;">高分子质检报告</span><span class="colorgreen">含杂率质检报告</span></td> </tr>
			<tr> <td>推荐度</td> <td><span class="colororange" style="font-size:15px;"> 98%</span></td> </tr>
		</table>
	</div>
	<div class="blank10"></div>
	<div class="ezsm-shopdetail-desctit">
		描述说明<a href="M04-品类-描述说明.html"><div></div></a>
	</div>
	<div class="ezsm-shopdetail-desc">${good.content}</div>
	
	<div class="blank70"></div>
	<div class="ezsm-normal-bottombtnB-box">
		<div class="ezsm-shopdetail-bottombtnbox">
			<div onclick="window.location.href='M06-采购单.html'"><img src="resource/img/micon_039.png"/><br>采购单</div>
			<div><img src="resource/img/micon_041.png"/><br>收藏</div>
			<div><img src="resource/img/micon_043.png"/><br>电话</div>
			<div><img src="resource/img/micon_045.png"/><br><a href="https://webchat.7moor.com/wapchat.html?accessId=77ad4f10-fa6f-11e7-b5e9-3f25a985904b&amp;fromUrl=&amp;urlTitle=" style="color: #333333;">客服</a></div>
		</div>
		<c:choose>
		
		</c:choose>
		<div class="ezsm-normal-bottombtnB2">预约预定</div>
		<div class="ezsm-normal-bottombtnB1">预约预定</div>
	</div>
</body>
</html>