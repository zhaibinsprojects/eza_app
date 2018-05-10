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
   <script type="text/javascript">
		$(function(){
			ZES_BuyCar_Price();
			$(".ezsm-shop-numbox-sub").click(function(){
				var num = parseInt($(this).parent().find(".ezsm-shop-numbox-num input").val());
				if(num>1){
					$(this).parent().find(".ezsm-shop-numbox-num input").val(num-1);
				}
				ZES_BuyCar_Price();  //计算价格
			});
			$(".ezsm-shop-numbox-add").click(function(){
				var num = parseInt($(this).parent().find(".ezsm-shop-numbox-num input").val());
				$(this).parent().find(".ezsm-shop-numbox-num input").val(num+1);
				ZES_BuyCar_Price();  //计算价格
			});
			//顶部全选
			$(".ezsm-buycar-top-check").click(function(){
				if($(this).attr("data-check")=="0"){
					$(this).attr("data-check","1");
					$(this).addClass("ezsm-buycar-top-check-sel");
					$(".ezsm-buycar-check").parent().attr("data-check","1");
					$(".ezsm-buycar-check").addClass("ezsm-buycar-check-sel");
					$(".ezsm-buycar-bottom-check").attr("data-check","1");
					$(".ezsm-buycar-bottom-check").addClass("ezsm-buycar-bottom-check-sel");
				}else{
					$(this).attr("data-check","0");
					$(this).removeClass("ezsm-buycar-top-check-sel");
					$(".ezsm-buycar-check").parent().attr("data-check","0");
					$(".ezsm-buycar-check").removeClass("ezsm-buycar-check-sel");
					$(".ezsm-buycar-bottom-check").attr("data-check","0");
					$(".ezsm-buycar-bottom-check").removeClass("ezsm-buycar-bottom-check-sel");
				}
				ZES_BuyCar_Price();  //计算价格
			});
			//底部全选
			$(".ezsm-buycar-bottom-check").click(function(){
				if($(this).attr("data-check")=="0"){
					$(this).attr("data-check","1");
					$(this).addClass("ezsm-buycar-bottom-check-sel");
					$(".ezsm-buycar-check").parent().attr("data-check","1");
					$(".ezsm-buycar-check").addClass("ezsm-buycar-check-sel");
					$(".ezsm-buycar-top-check").attr("data-check","1");
					$(".ezsm-buycar-top-check").addClass("ezsm-buycar-top-check-sel");
				}else{
					$(this).attr("data-check","0");
					$(this).removeClass("ezsm-buycar-bottom-check-sel");
					$(".ezsm-buycar-check").parent().attr("data-check","0");
					$(".ezsm-buycar-check").removeClass("ezsm-buycar-check-sel");
					$(".ezsm-buycar-top-check").attr("data-check","0");
					$(".ezsm-buycar-top-check").removeClass("ezsm-buycar-top-check-sel");
				}
				ZES_BuyCar_Price();  //计算价格
			});
			//单选
			$(".ezsm-buycar-check").click(function(){
				if($(this).parent().attr("data-check")=="0"){
					$(this).parent().attr("data-check","1");
					$(this).addClass("ezsm-buycar-check-sel");
				}else{
					$(this).parent().attr("data-check","0");
					$(this).removeClass("ezsm-buycar-check-sel");
				}
				//判断是否触发全选
				var checkflag = true;
				$(".ezsm-buycar-list ul li").each(function(){
					if($(this).attr("data-check")=="0"){
						checkflag = false;
					}
				});
				if(checkflag){
					$(".ezsm-buycar-top-check").attr("data-check","1");
					$(".ezsm-buycar-top-check").addClass("ezsm-buycar-top-check-sel");
					$(".ezsm-buycar-bottom-check").attr("data-check","1");
					$(".ezsm-buycar-bottom-check").addClass("ezsm-buycar-bottom-check-sel");
				}else{
					$(".ezsm-buycar-top-check").attr("data-check","0");
					$(".ezsm-buycar-top-check").removeClass("ezsm-buycar-top-check-sel");
					$(".ezsm-buycar-bottom-check").attr("data-check","0");
					$(".ezsm-buycar-bottom-check").removeClass("ezsm-buycar-bottom-check-sel");
				}
				ZES_BuyCar_Price();  //计算价格
			});
		});
		//价格计算
		function ZES_BuyCar_Price(){
			var total_price = 0;
			$(".ezsm-buycar-list ul li").each(function(){
				if($(this).attr("data-check")=="1"){
					var price = $(this).find(".ezsm-shop-numbox-num input").attr("data-price");
					var num = $(this).find(".ezsm-shop-numbox-num input").val();
					total_price += price*num;
				}
			});
			total_price = total_price.toFixed(2);
			$("#total_price").html("¥"+total_price);
		}
	</script>
</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-normal-top">
		<div style="background:none;"></div>
		<div>采购单</div>
		<div><img src="resource/img/micon_053.png"/><br>删除</div>
	</div>
	
	<div class="ezsm-buycar-box">
		<div class="ezsm-buycar-top">
			<div class="ezsm-buycar-top-check" data-check="0"></div>
			<div class="ezsm-buycar-top-info">全选</div>
			<div class="ezsm-buycar-top-more">继续选货</div>
		</div>
		<div class="ezsm-buycar-list">
			<ul>
				<li data-check="0">
					<div class="ezsm-buycar-check"></div>
					<div class="ezsm-buycar-right">
						<div class="ezsm-buycar-img"></div>
						<div class="ezsm-buycar-info">
							<div class="ezsm-buycar-tit">彩色xxxxxxxxxxxxxxx..</div>
							<div class="ezsm-buycar-desc"><div>单价</div><span>¥2000</span></div>
							<div class="ezsm-buycar-desc"><div>库存</div>1吨</div>
							<div class="ezsm-buycar-desc"><div>库存地</div>河北-保定-蠡县-1号库</div>
						</div>
						<div class="blank0c"></div>
						<div class="ezsm-buycar-num">购买量：
							<div class="ezsm-shop-numbox" id="buy_shop_num">
								<div class="ezsm-shop-numbox-sub">-</div>
								<div class="ezsm-shop-numbox-num"><input type="text" data-price="2000" value="0"/></div>
								<div class="ezsm-shop-numbox-add">+</div>
							</div>
						</div>
					</div>
					<div class="blank0c"></div>
				</li>
				<li data-check="0">
					<div class="ezsm-buycar-check"></div>
					<div class="ezsm-buycar-right">
						<div class="ezsm-buycar-img"></div>
						<div class="ezsm-buycar-info">
							<div class="ezsm-buycar-tit">彩色彩色彩色彩色彩色彩色彩色彩色彩色彩色彩色彩色彩色彩色彩色彩色彩色</div>
							<div class="ezsm-buycar-desc"><div>单价</div><span>¥3000</span></div>
							<div class="ezsm-buycar-desc"><div>库存</div>1吨</div>
							<div class="ezsm-buycar-desc"><div>库存地</div>河北-保定-蠡县-1号库</div>
						</div>
						<div class="blank0c"></div>
						<div class="ezsm-buycar-num">购买量：
							<div class="ezsm-shop-numbox" id="buy_shop_num">
								<div class="ezsm-shop-numbox-sub">-</div>
								<div class="ezsm-shop-numbox-num"><input type="text" data-price="3000" value="0"/></div>
								<div class="ezsm-shop-numbox-add">+</div>
							</div>
						</div>
					</div>
					<div class="blank0c"></div>
					<div class="ezsm-buycar-pre">
						<div class="ezsm-buycar-pre-btn" onclick="window.location.href='M04-品类-预约预定.html'">预约预定</div>
					</div>
				</li>
				<li data-check="0">
					<div class="ezsm-buycar-check"></div>
					<div class="ezsm-buycar-right">
						<div class="ezsm-buycar-img"></div>
						<div class="ezsm-buycar-info">
							<div class="ezsm-buycar-tit">彩色xxxxxxxxxxxxxxx..</div>
							<div class="ezsm-buycar-desc"><div>单价</div><span>¥1000</span></div>
							<div class="ezsm-buycar-desc"><div>库存</div>1吨</div>
							<div class="ezsm-buycar-desc"><div>库存地</div>河北-保定-蠡县-1号库</div>
						</div>
						<div class="blank0c"></div>
						<div class="ezsm-buycar-num">购买量：
							<div class="ezsm-shop-numbox" id="buy_shop_num">
								<div class="ezsm-shop-numbox-sub">-</div>
								<div class="ezsm-shop-numbox-num"><input type="text" data-price="1000" value="0"/></div>
								<div class="ezsm-shop-numbox-add">+</div>
							</div>
						</div>
					</div>
					<div class="blank0c"></div>
				</li>
			</ul>
		</div>
	</div>

	<div class="blank70"></div>
	<div class="blank70"></div>
	<div class="ezsm-buycar-bottom">
		<div class="ezsm-buycar-bottom-check" data-check="0"></div>
		<div class="ezsm-buycar-bottom-info">全选 &nbsp; 总计：<span id="total_price">¥2,300.00</span></div>
		<a href="M06-采购单-去结算（提交订单）.html"><div class="ezsm-buycar-bottom-btn">结算（<span>3</span>）</div></a>
	</div>
	<div class="ezsm-index-bottom">
	
		<ul>
		<a href="home/index.htm"><li><img src="resource/img/micon_006.png"/><br>首页</li></a>
			<a href="home/index.htm"><li><img src="resource/img/micon_008.png"/><br>品类</li></a>
			<a href="data/init.htm"><li class="ezsm-index-bottom-big"><img src="resource/img/micon_010.png"/><br>价格</li></a>
			<a href="buyer/order.htm"><li class="ezsm-index-bottom-sel"><img src="resource/img/micon_013.png"/><br>采购单</li></a>
			<a href="user/index.htm"><li><img src="resource/img/micon_014.png"/><br>我的</li></a>
		</ul>
	</div>
	<script type="text/javascript">
		$(window).load(function(){
			var width = document.body.clientWidth;
			$(".ezsm-buycar-right").css("width",(width-40)+"px");
			$(".ezsm-buycar-info").css("width",(width-40-100)+"px");
		});
	</script>
</body>
</html>