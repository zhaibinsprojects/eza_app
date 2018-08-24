<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<%-- <base href="${serurl}"/> --%>
<base href="http://10.10.10.52/"/>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="front/resource/css/price/ezsm_newAdd.css"/>
<!--App自定义的css-->
<script src="front/resource/js/jquery-1.9.1.min.js"></script>
<script src="front/resource/js/price/echarts.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="content_yzs">
  <header class="ezsm-collection-top">
    <div><a class="aicon_yzs" href="#"></a></div>
    <div>实时报价图表</div>
    <div></div>
  </header>
  <!-- <nav class="nav_style_jg">
  	<ul>
  		<li class="cliksuo"><a href="javascript:;">河北<i class="icon"></i></a></li>
  		<li class="clickProtuy"><a href="javascript:;">品类<i class="icon"></i></a></li>
  		<li class="sclicksxsd"><a href="javascript:;">筛选</a></li>
  	</ul>
  </nav> -->
  <!-- <div class="div_sx_jg"></div> -->
  <section class="echarts_inmgui">
  	<div class="section_roptab">
  		<ul>
  			<li class="active"><a href="javascript:;">一周</a></li>
  			<li><a href="javascript:;">一个月</a></li>
  			<li><a href="javascript:;">三个月</a></li>
  			<li><a href="javascript:;">一年</a></li>
  		</ul>
  	</div>
  	<div class="echartHeight">
  		<div id="mainAll" style="height: 190px; width:100%;"></div>
  	</div>
  </section>
  <section class="tabFater_tsi">
  	<table class="tabghuioy" border="0" cellspacing="0" cellpadding="0">
  		<thead>
  			<tr>
  				<th width="20%"><span>品类</span></th>
  				<th width="20%"><span>区域</span></th>
  				<th width="20%"><span>价格/吨</span></th>
  				<th width="20%"><span>涨跌幅</span></th>
  				<th width="20%"><span>时间</span></th>
  			</tr>
  		</thead>
  		<tbody>
  			<!-- 数据填充 -->
  		</tbody>
  	</table>
  </section>
</div>
<input name="type" value="${type}" type="hidden">
<input name="priceId" value="${priceId}" type="hidden">
<input name="dateBetweenType" value="${dateBetweenType}" type="hidden">
</body>
<script type="text/javascript" src="front/resource/js/price/echarttaball.js" charset="utf-8"></script>
<script type="text/javascript">
var type = $("imput[name=type]")
var priceId = ${priceId};
var dateBetweenType = ${dateBetweenType};
$(document).ready(function(){
	$(".section_roptab ul li").click(function(){
		$(this).addClass("active");
		$(this).siblings("li").removeClass("active")
		alert(priceId);
	});
})
</script>
</html>