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
      <h2><span>${title}</span></h2>
    </div>
    <div class="text_yzs" id="${show.id}">
      <h3>${show.name}（<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${show.addTime}" />）</h3>
      <div class="textOverflow_yzs">
        <p class="111">${show.content}</p>
      </div>
    </div>
  </section>
</div>
</body>

<script type="text/javascript">
var baseurl="${serurl}";
$(document).ready(function(){
	$(".MsoNormalTable").css("width","100%");
});

$(document).ready(function(){
	var imghref=$(".MsoNormal").find("img").eq(0).attr("src");
	if(imghref!="undefind"&&imghref!=""){
		$(".MsoNormal").find("img").eq(0).attr("src",'http://www.ezaisheng.com/'+imghref);
		$(".MsoNormal").find("img").eq(0).css("width","100%");
	}
})
</script>
</html>