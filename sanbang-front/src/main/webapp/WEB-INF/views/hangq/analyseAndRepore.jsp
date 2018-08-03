<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<%-- <base href="${serurl}"/> --%>
<base href="http://10.10.10.232/"/>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="front/resource/css/ezsm.css?v=1" />
<script type="text/javascript" src="front/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="front/resource/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="front/resource/js/ezsm.js?v=1"></script>
<script type="text/javascript" src="front/resource/js/goods/mui.min.js"></script>
<script type="text/javascript" src="front/resource/js/goods/analyseAndRepore.js"></script>
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
<link href="front/resource/css/pagercss/toPush.css" rel="stylesheet" type="text/css" />
<link href="front/resource/css/pagercss/mui.min.css" rel="stylesheet" >
<style type="text/css">

</style>
</head>
<body style="background:#efefef;width: 100%;">
  <!--价格评析-->
    <div class="hTwoFater_yzs">
      <h2>
      <c:if test="${resultMap.kinds=='priceAnalyse'}">
      	<span>价格评析</span>      
      </c:if>
      <c:if test="${resultMap.kinds=='report'}">
      	<span>研究报告</span>
      </c:if>
      </h2>
    </div> 
    <!-- 显示标题 -->
    <div class="ezsm-normal-tab" style="z-index:auto;margin-top:4%;">
		<ul>
			<li class="ezsm-normal-tab-sel">全部</li>
			<c:forEach var="item" items="${resultMap.columnList}">
				<li id="${item.id}">${item.name}</li>
			</c:forEach>
		</ul>
	</div>
  <div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="margin-top: 22%;">
    <div class="mui-scroll"> 
      <!--数据列表-->
    <section class="secNeiron mui-table-view mui-table-view-chevron">
		<!-- <div class="ghs_divgt"> -->
		    <c:forEach var="item" items="${resultMap.Obj}">
		    <div class="text_yzs" id="${item.id}">
		      <h3>${item.name}</h3>
		      <div class="textOverflow_yzs" style="-webkit-line-clamp: 3;">
		        <p>${item.meta}</p>
		      </div>
		      <h3><fmt:formatDate pattern="yyyy-MM-dd" 
		            value="${item.addTime}" /></h3>
		    </div>
		    </c:forEach>
		  <!-- </div> -->
    </section>
     </section>
    </div>
    </div>
</body>
<script type="text/javascript">
/* var baseurl="${serurl}"; */
var baseurl="http://10.10.10.232/";
var type="${resultMap.kinds}";
var pagecount="${resultMap.Page.totalPageCount}";
$(document).ready(function(){
	mui('body').on('tap', '.text_yzs', function() {
		window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
	});
})
</script>
<script>
		$(function(){
			$(".ezsm-normal-tab ul li").each(function(){
				var width_li = $(this).width()+36;
				var width_ul = $(".ezsm-normal-tab ul").width();
				$(".ezsm-normal-tab ul").css("width",(width_li+width_ul)+"px")
			});
			$(".ezsm-normal-tab ul li").click(function(){
				$(".ezsm-normal-tab ul li").removeClass("ezsm-normal-tab-sel");
				$(this).addClass("ezsm-normal-tab-sel");
				//window.location.href=baseurl+"/front/app/home/analyseAndReport.htm?id="++"ecId="+$(this).attr("id");
			});
		});
	</script>
</html>