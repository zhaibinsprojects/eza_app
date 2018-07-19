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
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
</head>
<body style="background:#efefef;width: 100%;/* background:#efefef;overflow-x: hidden; */">
	<div class="content_yzs">
  <!--价格评析-->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>价格评析</span><span style="float: right;color: orange;">更多</span></h2>
    </div>
    <c:forEach var="item" items="${jiage.Obj}">
    <div class="text_yzs" id="${item.id}">
      <h3>${item.name}（<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${item.addTime}" />）</h3>
      <div class="textOverflow_yzs">
        <p>${item.meta}</p>
      </div>
    </div>
    </c:forEach>
    
  </section>
  <!--研究报告--->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>研究报告</span><span style="float: right;color: orange;">更多</span></h2>
    </div>
    <c:forEach var="item" items="${baogao.Obj}">
    <div class="text_yzs" id="${item.id}">
      <h3>${item.name}（<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${item.addTime}" />）</h3>
      <div class="textOverflow_yzs">
        <p>${item.meta}</p>
      </div>
    </div>
    </c:forEach>
  </section>
  <!--实时报价--->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>实时报价</span><span style="float: right;color: orange;">更多</span></h2>
    </div>
    <div class="realTime_price">
      <table class="tableOne_yzs" cellpadding="0" cellspacing="0">
      
      <c:forEach items="${baojia.Obj}" var="item">
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
        <%-- <tr>
          <td><label>实时成交价</label></td>
          <td>${item.goodPrice}</td>
        </tr> --%>
       </c:forEach> 
        
      </table>
    </div>
  </section>
  <!---价格走势-->
  <!-- <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>价格走势</span></h2>
    </div>
    <div id="container" class="nedHiCha_yzs" style="min-width: 310px;"> 
    
    </div>
  </section> -->
  <!---价格指数 -->
  <!-- <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>价格指数</span></h2>
    </div>
    <div id="containerbar" class="nedHiCha_yzs" style="min-width: 310px;">
    
    </div>
  </section> -->
  
</div>
</body>

<script type="text/javascript">
var baseurl="${serurl}";

$(document).ready(function(){
	$(".text_yzs").click(function(){
		window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
	})
})
</script>
</html>