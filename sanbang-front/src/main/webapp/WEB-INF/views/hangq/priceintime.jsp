<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${serurl}"/>
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

<script src="front/resource/js/newAddjs/mui.picker.js"></script>
<script src="front/resource/js/newAddjs/mui.poppicker.js"></script>
<script src="front/resource/js/newAddjs/city.data.js" type="text/javascript"></script>
<link href="front/resource/css/newAddcss/mui.picker.css" rel="stylesheet" />
<link href="front/resource/css/newAddcss/mui.poppicker.css" rel="stylesheet" />
 <script type="text/javascript" src="front/resource/js/newAddjs/pricezs.js"></script>
</head>
<body style="background:#efefef;width: 100%;/* background:#efefef;overflow-x: hidden; */">
  <!--实时报价--->
    <div class="hTwoFater_yzs">
      <h2><span>实时报价</span></h2>
    <nav class="navAll_yzs" style="margin-top: 3%;">
  	<ul class="nav_ulsty_yzs">
    	<li  id="showCityPicker" style="width: 25%;"><a id="cityResult" href="javascript:;">山东</a></li>
        <li id="showUserPicker" style="width: 25%;"><a id="userResult" href="javascript:;">品类</a></li>
        <li id="showColorPicker" style="width: 25%;"><a id="colorResult" href="javascript:;">颜色</a></li>
        <li id="showFormPicker" style="width: 25%;"><a id="formResult" href="javascript:;">形态</a></li>
    </ul>
  	</nav>
    </div>
    
    <div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="margin-top: 25%;">
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
		          <tr>
		          <td><label>时间</label></td>
		          <td><fmt:formatDate pattern="yyyy-MM-dd" 
		            value="${item.addTime}" /></td>
		        </tr>
		          <tr>
		         <td><span style="margin-top: 7%;"></span></td>
		         <td></td>
		      
		         </tr>
		       </c:forEach>
       		</table>
       </div>
        </section>
       </div>
       </div>
  <form action="front/app/home/analyseAndReport.htm" method="post" class="csubmit">
 	 <input type="hidden" value="${colorId}" name="colorId" class="colorId"/>
	 <input type="hidden" value="${kindId}" name="kindId" class="kindId"/>
	 <input type="hidden" value="${formId}" name="formId" class="formId"/>
	 <input type="hidden" value="${areaId}" name="areaId" class="areaId"/>
	 
	 <input type="hidden" value="${colorval}" name="colorval" class="colorval"/>
	 <input type="hidden" value="${kindval}" name="kindval" class="kindval"/>
	 <input type="hidden" value="${formval}" name="formval" class="formval"/>
	 <input type="hidden" value="${areaval}" name="areaval" class="areaval"/>
	 <input type="hidden" value="priceInTime" name="type" />
  </form>     
</body>

<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.232/"; */
var type="${resultMap.kinds}";
var goodClassId = "${resultMap.goodClassId}";
var areaId = "${resultMap.areaId}";
var pagecount="${resultMap.Page.totalPageCount}";
$(document).ready(function(){
	var  colorval=$(".colorval").val();
	if(colorval!=""){
		$("#colorResult").html(colorval);
	}
	
	var  kindval=$(".kindval").val();
	if(kindval!=""){
		$("#userResult").html(kindval);
	}
	
	var  formval=$(".formval").val();
	if(formval!=""){
		$("#formResult").html(formval);
	}
	
	var  areaval=$(".areaval").val();
	if(areaval!=""){
		$("#cityResult").html(areaval);
	}
});

$(".nav_ulsty_yzs li").click(function(){
	$(this).siblings().find("a").removeClass("active_yzs");
	$(this).children("a").addClass("active_yzs");
	});
$(".dy_yzsbtn").click(function(){
	$(".graybaks,.grayChren").show();
	});
$(".qx_yzsbtn").click(function(){
	$(".graybaks,.grayChren").hide();
	});	
</script>
</html>