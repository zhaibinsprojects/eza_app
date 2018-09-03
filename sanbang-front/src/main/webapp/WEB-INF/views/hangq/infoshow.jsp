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
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
<link rel="stylesheet" href="front/resource/script/layer/mobile/need/layer.css" />
<script type="text/javascript" src="front/resource/js/hangq/hangqindex.js"></script>
<script type="text/javascript" src="front/resource/script/layer/mobile/layer.js" charset="utf-8"></script>
<style type="text/css">
/*设置文档中图片显示样式 */
.textOverflow_yzs img {
  width: 80%;
  height: 80%;
}
</style>
</head>
<body style="background:#efefef;width: 100%;/* background:#efefef;overflow-x: hidden; */">
	<div class="content_yzs">
  <!--价格评析-->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>${title}</span></h2>
    </div>
    <div class="text_yzs" id="${show.id}">
      <h3>${show.name}（<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${show.addTime}" />）</h3>
       <div class="textOverflow_yzs" onclick="">
        <p class="111">${show.content}</p>
      </div>     
      <div class="textOverflow_yzs">
       <p class="111">${show.meta}</p>
      </div>
    </div>
    
    <c:if test="${ not empty show.attachment}">
	    <div class="textOverflow_yzs">
	    <h5 style="margin-left: 70%;margin-top: 5%;">
	         <p class="download" name="${show.name}" style="color:orange;">在线查看报告</p>
	     </h5>    
	      </div>
    </c:if> 
  </section>
</div>
<div class="content_yzs">
      <!--研究报告--->
  <section class="secsty_yzs" style="margin-top: 60%;margin-bottom: -20%;">
     <c:if test="${not empty top}">
     <div class="text_yzs" id="${top.id}">
      <h3>上一篇：${top.name}（<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${top.addTime}" />）</h3>
      <div class="textOverflow_yzs">
      </div>
    </div>
     </c:if>
     <c:if test="${not empty button }">
    <div class="text_yzs" id="${button.id}">
      <h3>下一篇：${button.name}
      		（<fmt:formatDate pattern="yyyy-MM-dd"  value="${button.addTime}" />）
            </h3>
    </div>
     </c:if>
  </section>
	  <c:if test="${ not empty show.attachment}">
		  <form action="http://www.ezaisheng.com/contentDown.htm" method="post" id="theForm2">
				       <input type="hidden" name="fileName"  id="fileName"  value="" />
		  </form>	
	  </c:if>
</div>
</body>

<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl= "http://10.10.10.232/"; */
var  pdfname="${show.attachment}";
var pdfid="${show.id}"
$(document).ready(function(){
	$(".text_yzs").click(function(){
		//window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
		window.location.href=baseurl+"/front/app/menuhq/hangqShow.htm?id="+$(this).attr("id");
	})
	$(".MsoNormalTable").css("width","100%");
	
	/* $(".textOverflow_yzs").click(function(){
		$("#fileName").val(pdfname);
		$("#theForm2").submit();
	}); */
});
</script>
</html>