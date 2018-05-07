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
	<link href="resource/css/newAddcss/mui.picker.css" rel="stylesheet" />
	<link href="resource/css/newAddcss/mui.poppicker.css" rel="stylesheet" />
    <link rel="stylesheet" href="resource/css/ezsm.css?v=1" />
    <script type="text/javascript" src="resource/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="resource/js/newAddjs/mui.min.js"></script>
	<script type="text/javascript" src="resource/js/newAddjs/mui.picker.js"></script>
	<script type="text/javascript" src="resource/js/newAddjs/mui.poppicker.js"></script>
	<script type="text/javascript" src="resource/js/newAddjs/city.data.js" type="text/javascript"></script>

    <script type="text/javascript" src="resource/js/ezsm.js?v=1"></script>
    <script type="text/javascript">
		$(function(){
			_init_EZS_Type_Shop();
		});
	</script>
</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-index">
		<div class="ezsm-index-top">
			<div onclick="javascript:history.go(-1);" style="float:left;width:20%;height:40px;background:url('resource/img/micon_019.png') no-repeat;background-size:28px;"></div>
			<div class="ezsm-index-search"><input type="text" placeholder="请输入您要找的关键字..."/></div>
			<div class="ezsm-index-msg"><img src="resource/img/micon_018.png"/><br>消息</div>
		</div>
		
		<nav class="navAll_yzs">
			<ul class="nav_ulsty_yzs">
				<li id="showCityPicker"><a id="cityResult" href="javascript:;">河北</a></li>
				<li id="showUserPicker"><a id="userResult" href="javascript:;">品类</a></li>
				<li id="showColorPicker"><a id="colorResult" href="javascript:;">颜色</a></li>
				<li id="showFormPicker"><a id="formResult" href="javascript:;">形态</a></li>
				<li><input class="submit_yzs" type="submit" value="筛选"/></li>
			</ul>
		</nav>
		<div class="ezsm-index-more">释放立即刷新</div>
		<div class="ezsm-shoplist-pannel">
			<ul>
				<li>
					<div class="ezsm-shoplist-box">
						<div class="ezsm-shoplist-img"><img src="images/shop_001.jpg"/></div>
						<div class="ezsm-shoplist-tit">PEEX复合料</div>
						<div class="ezsm-shoplist-price">7800元/吨<span>179吨</span></div>
					</div>
				</li>
				<li>
					<div class="ezsm-shoplist-box">
						<div class="ezsm-shoplist-img"><img src="images/shop_001.jpg"/></div>
						<div class="ezsm-shoplist-tit">PEEX复合料</div>
						<div class="ezsm-shoplist-price">7800元/吨<span>179吨</span></div>
					</div>
				</li>
				<li>
					<div class="ezsm-shoplist-box">
						<div class="ezsm-shoplist-img"><img src="images/shop_001.jpg"/></div>
						<div class="ezsm-shoplist-tit">PEEX复合料</div>
						<div class="ezsm-shoplist-price">7800元/吨<span>179吨</span></div>
					</div>
				</li>
				<li>
					<div class="ezsm-shoplist-box">
						<div class="ezsm-shoplist-img"><img src="images/shop_001.jpg"/></div>
						<div class="ezsm-shoplist-tit">PEEX复合料</div>
						<div class="ezsm-shoplist-price">7800元/吨<span>179吨</span></div>
					</div>
				</li>
			</ul>
			<div class="blank0c"></div>
		</div>
		
		<div class="ezsm-index-more">正在加载</div>
		<div class="blank70"></div>
	</div>
</body>
</html>