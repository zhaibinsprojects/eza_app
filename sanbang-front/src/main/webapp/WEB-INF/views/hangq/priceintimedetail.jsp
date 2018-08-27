<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${serurl}"/>
<!-- <base href="http://10.10.10.52/"/> -->
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="front/resource/css/price/ezsm_newAdd.css"/>
<!--App自定义的css-->
<script src="front/resource/js/jquery-1.9.1.min.js"></script>
<script src="front/resource/js/price/echarts.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="front/resource/js/price/mui.min.js"></script>
<link rel="stylesheet" href="front/resource/css/ezsm.css?v=1" />
<script type="text/javascript" src="front/resource/js/jquery-1.9.1.min.js"></script>
</script><script type="text/javascript" src="front/resource/js/ezsm.js?v=1"></script>
<link href="front/resource/css/pagercss/toPush.css" rel="stylesheet" type="text/css" />
<link href="front/resource/css/pagercss/mui.min.css" rel="stylesheet" > 
<script type="text/javascript" src="front/resource/js/price/priceInTime.js"></script>
</head>
<body>
<div class="content_yzs">
  <header class="ezsm-collection-top">
    <div><a class="aicon_yzs" href="#"></a></div>
    <div>实时报价图表</div>
    <div></div>
  </header>
  <section class="echarts_inmgui">
  	<div class="section_roptab">
  		<ul>
  			<li name="WEEK" class="active"><a href="javascript:;">一周</a></li>
  			<li name="MONTH"><a href="javascript:;">一个月</a></li>
  			<li name="QUARTER"><a href="javascript:;">三个月</a></li>
  			<li name="YEAR"><a href="javascript:;">一年</a></li>
  		</ul>
  	</div>
  	<div class="echartHeight">
  		<div id="mainAll" style="height: 190px; width:100%;"></div>
  	</div>
  </section>
<!--   <section class="tabFater_tsi">
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
  			数据填充
  		</tbody>
  	</table>
  </section> -->
</div>
	  <div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="margin-top:75%;">
    <div class="mui-scroll"> 
    <section class="secNeiron mui-table-view mui-table-view-chevron">
	<div class="cont">
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
  			
  		</tbody>
  	</table>    
	</div>
    </section>
    </div>
    </div>
<input name="type" value="${type}" type="hidden">
<input name="priceId" value="${priceId}" type="hidden">
<input name="dateBetweenType" value="${dateBetweenType}" type="hidden">
<input name="channelchanged" value="0" type="hidden">
<input name="pagecount" value="" type="hidden">
</body>
<script src="front/resource/js/price/echarttaball.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
var type = $("input[name=type]").val();
var priceId = $("input[name=priceId]").val();
var dateBetweenType = $("input[name=dateBetweenType]").val();
var pagecount = $("input[name=pagecount]").val();
</script>
</html>