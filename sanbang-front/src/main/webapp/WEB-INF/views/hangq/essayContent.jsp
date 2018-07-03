<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台-文章报告</title>
<link rel="stylesheet" href="/front/resource/css/ezsm.css?v=1" />
<script type="text/javascript" src="/front/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/resource/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="/front/resource/js/ezsm.js?v=1"></script>
</head>
<body style="background:#efefef;width: 100%;">
	<div class="content_yzs">
  <header class="ezsm-collection-top">
    <div onclick="javascript:history.go(-1);"><a class="aicon_yzs" href="#"></a></div>
    <div>文章报告</div>
    <div></div>
  </header>
  <!--价格评析-->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>${essay.name}</span></h2>
    </div>
    <div class="text_yzs">
      <h3>${essay.author} ${essay.addTime}</h3>
      <div class="textOverflow_yzs">
        ${essay.content}
      </div>
    </div>
  </section>
</div>
</body>
<script type="text/javascript">
var baseurl="${serurl}";
</script>
</html>