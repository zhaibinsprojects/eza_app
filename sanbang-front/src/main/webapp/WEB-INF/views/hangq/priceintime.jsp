<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${serurl}"/>
<!-- <base href="http://10.10.10.232/"/> -->
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="front/resource/css/ezsm.css?v=1" />
<script type="text/javascript" src="front/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="front/resource/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="front/resource/js/ezsm.js?v=1"></script>
<script type="text/javascript" src="front/resource/js/goods/mui.min.js"></script>
<script type="text/javascript" src="front/resource/js/goods/priceInTime.js"></script>
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
<link href="front/resource/css/pagercss/toPush.css" rel="stylesheet" type="text/css" />
<link href="front/resource/css/pagercss/mui.min.css" rel="stylesheet" > 
</head>
<body style="background:#efefef;width: 100%;/* background:#efefef;overflow-x: hidden; */">
  <!--实时报价--->
    <div class="hTwoFater_yzs">
      <h2><span>实时报价</span></h2>
    </div>
    <div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="margin-top: 15%;">
    <div class="mui-scroll">
    <section class="secNeiron mui-table-view mui-table-view-chevron">
		<div class="ghs_divgt">
		    <table class="tableOne_yzs" cellpadding="0" cellspacing="0">
		      <c:forEach items="${resultMap.Obj}" var="item">
		        <tr>
		          <td><label>地区</label></td>
		          <td><span>${item.goodArea}</span></td>
		        </tr>
		        <tr>
		          <td><label>品类</label></td>
		          <td><span>${item.goodClassName}</span></td>
		        </tr>
		        <tr>
		          <td><label>国内参考价</label></td>
		          <td>${item.price}</td>
		        </tr>
		       </c:forEach>
       		</table>
       </div>
        </section>
       </div>
       </div>
 
</body>
<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.232/"; */
var type="${resultMap.kinds}";
var goodClassId = "${resultMap.goodClassId}";
var areaId = "${resultMap.areaId}";
var pagecount="${resultMap.Page.totalPageCount}";
$(document).ready(function(){
	$(".text_yzs").click(function(){
		window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
	})
})
</script>
</html>