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
<meta name="share-description" content="${description}">
<title>${show.meta}</title>
<link rel="stylesheet" href="front/resource/css/ezsm.css?v=1" />
<link rel="stylesheet" href="front/resource/css/ezsm_newAdd.css?v=1" />
<script type="text/javascript" src="front/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="front/resource/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="front/resource/js/ezsm.js?v=1"></script>
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
<link rel="stylesheet" href="front/resource/script/layer/mobile/need/layer.css" />
<script type="text/javascript" src="front/resource/js/hangq/hangqindexnew.js"></script>
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
	<div class="content_hqzx">
  <!-- <header class="ezsm-collection-top">
    <div><a class="aicon_yzs" href="#"></a></div>
    <div>申请结果</div>
    <div></div>
  </header> -->
  <section class="hqzx_uiop">
  	<h2>${show.name}</h2>
  	<span><fmt:formatDate pattern="yyyy-MM-dd" 
            value="${show.addTime}" /></span>
  	    <div class="vuetpso">
	  	    <div class="contenttrue">
	      	 <p class="111">${show.meta}</p>
	      </div>
  	        <div class="contenttrue">
  	        <p >${show.content}</p>
  	        </div>
	  		<div class="contenttfalse">
	  		<p >为了拿到最前沿价格数据信息，易再生组建1000人数据采集团队，并与多家大型供货商合作，及时获取各地区市场一出最具权威性价格信息...</p>
	  		</div>
	  		<div class="gtayfffbak">
  		</div>
  		
  	</div>
  	<button class="djdigxuz">点击订阅</button>
  	
  	<c:if test="${ not empty show.attachment}">
	    <button class="download"  style="width: 100%;
			    height: 46px;
			    line-height: 46px;
			    border: none;
			    font-size: 18px;
			    color: #fff;
			    background-color: #009183;
			    text-align: center;
			    border-radius: 6px;
			    outline: none;"
    name="${show.name}">在线查看报告</button>
    </c:if> 
  	
  </section>
</div>
<footer class="vuesdxjd">
	<ul>
		<li>
			<a class="astyuiop" href="javascript:;"></a>
		</li>
		<li>
			<span class="xx_yuiop"></span>
		</li>
		<li>
			<span class="dz_uiop"></span>
		</li>
	</ul>
</footer>
<div class="artyuiosuccess">
	<img src="front/resource/img/cz_success.png" width="36px" height="36px" />
	<p>收藏成功</p>
</div>

<div class="artyuioerror">
	<img src="front/resource/img/cz_error.png" width="36px" height="36px" />
	<p>取消成功</p>
</div>
<div class="dzuiosuccess">
	<img src="front/resource/img/cz_success.png" width="36px" height="36px" />
	<p>点赞成功</p>
</div>

<div class="adzuioerror">
	<img src="front/resource/img/cz_error.png" width="36px" height="36px" />
	<p>取消成功</p>
</div>
<input type="hidden" name="userkey" class="userkey" id="userkey"/>
<input type="hidden" name="docid" class="docid" id="docid"  value="${docid}"/>
</body>
<c:if test="${ not empty show.attachment}">
		  <form action="http://www.ezaisheng.com/contentDown.htm" method="post" id="theForm2">
				       <input type="hidden" name="fileName"  id="fileName"  value="" />
		  </form>	
	  </c:if>
<script type="text/javascript">
 var baseurl="${serurl}"; 
/* var baseurl= "http://10.10.10.52/"; */
var  pdfname="${show.attachment}";
var pdfid="${show.id}";
var title="${show.meta}";
var description="${description}";


	$(document).ready(function(){
		$(".gtayfffbak").height($(".vuetpso p").height());
	});
</script>
</html>