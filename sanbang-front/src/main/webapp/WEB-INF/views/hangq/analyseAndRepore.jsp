<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${serurl}"/>
<!-- <base href="http://10.10.10.52/"/> -->
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
<link href="front/resource/css/pagercss/toPush.css" rel="stylesheet" type="text/css"/>
<link href="front/resource/css/pagercss/mui.min.css" rel="stylesheet" >
<style type="text/css">
h3{
    margin-top: 0px;
    margin-bottom: 0px;
}
.text_yzs{
	padding: 0px 0px 0px 0;
}
</style>
</head>
<div class="mui-fullscreen">
  <!--价格评析-->
    <div class="hTwoFater_yzs">
      <h2 style="margin-top: 0px;">
      <c:if test="${resultMap.kinds=='priceAnalyse'}">
      	<span style="margin-top: 0px;">价格评析</span>      
      </c:if>
      <c:if test="${resultMap.kinds=='report'}">
      	<span style="margin-top: 0px;">研究报告</span>
      </c:if>
      </h2>
    </div> 
    <!-- 显示标题 -->
    <div class="ezsm-normal-tab" style="z-index:auto;width: 100% ;">
		<ul style="width: 100%;">
			<li id="0" class="ezsm-normal-tab-sel">全部</li>
			<c:choose>
				<c:when test="${resultMap.kinds=='priceAnalyse'}">
					<c:forEach var="item" items="${resultMap.columnList}" varStatus="i">
						<c:if test="${i.index < 2}">
							<li id="${item.id}">${item.name}</li>
						</c:if>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" items="${resultMap.columnList}" varStatus="i">
						<li id="${item.id}">${item.name}</li>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	
  <div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="margin-top:21%;">
    <div class="mui-scroll"> 
      <!--数据列表-->
    <section class="secNeiron mui-table-view mui-table-view-chevron">
		<div class="cont">
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
		  </div>
    </section>
    </div>
    </div>
</body>
<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.52/"; */
var type="${resultMap.kinds}";
var pagecount="${resultMap.Page.totalPageCount}";
$(document).ready(function(){
	mui('body').on('tap', '.text_yzs', function() {
		//window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
		window.location.href=baseurl+"/front/app/menuhq/hangqShow.htm?id="+$(this).attr("id");
	});
})
$(function(){
			
			$(".ezsm-normal-tab ul li").click(function(){
				$(".ezsm-normal-tab ul li").removeClass("ezsm-normal-tab-sel");
				$(this).addClass("ezsm-normal-tab-sel");
				$(".mui-table-view").empty();
				$.ajax({
					type : "post",
					url : baseurl+"/front/app/home/analyseAndReportPage.htm",
					data : {
						 "currentPage":1,
						  "type":type,
						  "ecId":$(this).attr("id")
					},
					dataType : "html",
					async : false,
					success : function(data) {
						var table = document.body.querySelector('.mui-table-view');
						var li = document.createElement('div');
						var html=pagertemp(count);
						li.innerHTML = data;
						table.appendChild(li);
					},
					error:function(e){
						html=e;
						}
				});
			});
});
</script>
</html>