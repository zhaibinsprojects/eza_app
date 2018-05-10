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
    <script type="text/javascript" src="resource/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="resource/js/ezsm.js?v=1"></script>
	<script type="text/javascript" src="resource/js/highcharts.js"></script>
	<script type="text/javascript" src="resource/js/userpro/userlogin.js"></script>
	<script type="text/javascript" src="resource/script/layer/mobile/layer.js" charset="utf-8"></script>
	<link rel="stylesheet" type="text/css"  href="resource/script/layer/mobile/need/layer.css" />
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=VXYbZobqzDrC9YuckYUD8Skl6N1WpGf4"></script>
	<style>
		html{background:#fff;}
	</style>
</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-index">
		<div class="ezsm-index-top">
			<div class="ezsm-index-local" id="sel_city"><span data-value="hebei" id="addressindex">北京市</span><img src="resource/img/micon_017.png"/></div>
			<div class="ezsm-index-search"><input type="text" placeholder="请输入您要找的关键字..."/></div>
			<div class="ezsm-index-msg"><img src="resource/img/micon_018.png"/><br>消息</div>
		</div>
		<div class="ezsm-index-banner">
			<img src="resource/img/banner_001.png"/>
		</div>
		<div class="ezsm-index-new">
			<div onclick="window.location.href='M01-移动工具-更多-行情分析.html'">行情分析</div>
			<div><span>周评</span>再生通用塑料PE本周价格本周本周价本周价本周价价格</div>
		</div>
		<div class="ezsm-index-type">
			<ul>
				<li><img src="resource/img/tpic_001.png"/>再生通用塑料</li>
				<li><img src="resource/img/tpic_002.png"/>再生工程塑料</li>
				<li><img src="resource/img/tpic_003.png"/>再生特种塑料</li>
				<a href="M03-首页-采购定制.html"><li><img src="resource/img/tpic_004.png"/>采购定制</li></a>
			</ul>
			<div class="blank0c"></div>
		</div>
		<div class="ezsm-index-banner2">
			<a href="M03-首页-我订阅的价格行情.html"><img src="resource/img/banner_002.png"/></a>
		</div>
		<div class="ezsm-index-menu">
			<ul>
				<a href="store/index.htm"><li><img src="resource/img/micon_001.png"/><br>自营商城</li></a>
				<a href="data/init.htm"><li><img src="resource/img/micon_002.png"/><br>价格行情</li></a>
				<a href="javascript:layer.open({content :'金融服务暂未上线,敬请期待',skin : 'msg',time : 2});"><li><img src="resource/img/micon_003.png"/><br>金融服务</li></a>
				<a href="M03-首页-行业资讯.html"><li><img src="resource/img/micon_004.png"/><br>行业资讯</li></a>
				<a href="M02-首页-供货合作.html"><li><img src="resource/img/micon_005.png"/><br>供货合作</li></a>
			</ul>
			<div class="blank0c"></div>
		</div>
		<div class="ezsm-index-tit">
			<img src="resource/img/tit_001.png"/>
		</div>
		<div class="ezsm-index-list">
			<ul>
				<a href="M04-品类-货品详情.html">
				<li>
					<div class="ezsm-index-list-name">复合料复合料复合料复合料复合料料复合料复合料</div>
					<div class="ezsm-index-list-price"><span>7800</span>元/吨</div>
					<div class="ezsm-index-list-num">库存：10000吨</div>
					<div class="ezsm-index-list-addr">河北-保定-蠡县</div>
				</li>
				</a>
				<a href="M04-品类-货品详情.html">
				<li>
					<div class="ezsm-index-list-name">复合料复合料复合料复合料复合料料复合料复合料</div>
					<div class="ezsm-index-list-price"><span>7800</span>元/吨</div>
					<div class="ezsm-index-list-num">库存：10000吨</div>
					<div class="ezsm-index-list-addr">河北-保定-蠡县</div>
				</li>
				</a>
				<a href="M04-品类-货品详情.html">
				<li>
					<div class="ezsm-index-list-name">复合料复合料复合料复合料复合料料复合料复合料</div>
					<div class="ezsm-index-list-price"><span>7800</span>元/吨</div>
					<div class="ezsm-index-list-num">库存：10000吨</div>
					<div class="ezsm-index-list-addr">河北-保定-蠡县</div>
				</li>
				</a>
				<a href="M04-品类-货品详情.html">
				<li>
					<div class="ezsm-index-list-name">复合料复合料复合料复合料复合料料复合料复合料</div>
					<div class="ezsm-index-list-price"><span>7800</span>元/吨</div>
					<div class="ezsm-index-list-num">库存：10000吨</div>
					<div class="ezsm-index-list-addr">河北-保定-蠡县</div>
				</li>
				</a>
				<a href="M04-品类-货品详情.html">
				<li>
					<div class="ezsm-index-list-name">复合料复合料复合料复合料复合料料复合料复合料</div>
					<div class="ezsm-index-list-price"><span>7800</span>元/吨</div>
					<div class="ezsm-index-list-num">库存：10000吨</div>
					<div class="ezsm-index-list-addr">河北-保定-蠡县</div>
				</li>
				</a>
				<a href="M04-品类-货品详情.html">
				<li>
					<div class="ezsm-index-list-name">复合料复合料复合料复合料复合料料复合料复合料</div>
					<div class="ezsm-index-list-price"><span>7800</span>元/吨</div>
					<div class="ezsm-index-list-num">库存：10000吨</div>
					<div class="ezsm-index-list-addr">河北-保定-蠡县</div>
				</li>
				</a>
			</ul>
		</div>
		<div class="ezsm-index-more">正在加载</div>
		<div class="blank10"></div>
		<div class="blank70"></div>
		<div class="ezsm-index-bottom">
			<ul>
				<a href="home/index.htm"><li class="ezsm-index-bottom-sel"><img src="resource/img/micon_007.png"/><br>首页</li></a>
				<a href="cata/init.htm"><li><img src="resource/img/micon_008.png"/><br>品类</li></a>
				<a href="data/init.htm"><li class="ezsm-index-bottom-big"><img src="resource/img/micon_010.png"/><br>价格</li></a>
				<a href="buyer/order.htm"><li><img src="resource/img/micon_012.png"/><br>采购单</li></a>
				<a href="user/index.htm"><li><img src="resource/img/micon_014.png"/><br>我的</li></a>
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
		<div id="allmap" style="display: none" ></div>
</body>

    <script type="text/javascript">
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
		});
	</script>
	
	<script type="text/javascript">
	
	$(document).ready(function(){
		handleSuccess();
	});
	
	//定位事件
	function handleSuccess(){ 
		var map = new BMap.Map("allmap");
	    var point = new BMap.Point(108.95,34.27);
	    map.centerAndZoom(point,12);
	 
	    var geolocation = new BMap.Geolocation();
	    geolocation.getCurrentPosition(function(r){
	        if(this.getStatus() == BMAP_STATUS_SUCCESS){
	            var mk = new BMap.Marker(r.point);
	            map.addOverlay(mk);//标出所在地
	            map.panTo(r.point);//地图中心移动
	            //alert('您的位置：'+r.point.lng+','+r.point.lat);
	            var point = new BMap.Point(r.point.lng,r.point.lat);//用所定位的经纬度查找所在地省市街道等信息
	          //alert('您的位置：'+r.point.lng+','+r.point.lat);
	            var gc = new BMap.Geocoder();
	            gc.getLocation(point, function(rs){
	              // alert(rs.address);//弹出所在地址
	               var addComp = rs.addressComponents;
	       		   var mapAddress = addComp.province;
	       		 document.getElementById("addressindex").innerHTML=mapAddress;
	            });
	        }else{
	        	handleError();
	        }
		}); 
		
	};
	
    
</script>
</html>