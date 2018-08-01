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
   
<script type="text/javascript">
		$(function(){
			$(".ezsm-normal-top-nav li").click(function(){
				$(".ezsm-normal-top-nav li").removeClass("ezsm-normal-top-nav-sel");
				$(this).addClass("ezsm-normal-top-nav-sel");
			});
			
			$(".ezsm-shopdetail-picbox").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-visual").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image ul").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image li").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image li span").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-main-image li a").css("height",($("body").width())+"px");
			$(".ezsm-shopdetail-picbox-flicking-con").css("top",($("body").width()-30)+"px");
			
			//轮播图效果
			$(document).ready(function(){
				$(".ezsm-shopdetail-picbox-main-visual").hover(function(){ $("#btn_prev,#btn_next").fadeOut();
				},function(){ $("#btn_prev,#btn_next").fadeOut(); });
				$dragBln = false;
				$(".ezsm-shopdetail-picbox-main-image").touchSlider({
					flexible : true,
					speed : 200,
					btn_prev : $("#btn_prev"),
					btn_next : $("#btn_next"),
					paging : $(".ezsm-shopdetail-picbox-flicking-con a"),
					counter : function (e){ $(".ezsm-shopdetail-picbox-flicking-con a").removeClass("on").eq(e.current-1).addClass("on"); }
				});
				$(".ezsm-shopdetail-picbox-main-image").bind("mousedown", function() { $dragBln = false; });
				$(".ezsm-shopdetail-picbox-main-image").bind("dragstart", function() { $dragBln = true; });
				$(".ezsm-shopdetail-picbox-main-image a").click(function(){ if($dragBln) { return false; } });
				timer = setInterval(function(){ $("#btn_next").click(); }, 5000);
				$(".ezsm-shopdetail-picbox-main-visual").hover(function(){ clearInterval(timer);
				},function(){ timer = setInterval(function(){ $("#btn_next").click(); },5000); });
				
				$(".ezsm-shopdetail-picbox-main-image").bind("touchstart",function(){ clearInterval(timer);
				}).bind("touchend", function(){ timer = setInterval(function(){ $("#btn_next").click(); }, 5000); });
			}); 
		});
	</script>
</head>
<body style="background:#efefef;width: 100%;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<div class="ezsm-shopdetail-picbox">
		<div class="ezsm-shopdetail-picbox-main-visual">
			<div class="ezsm-shopdetail-picbox-flicking-con">
				<a href="#">1</a>
				<a href="#">2</a>
				<a href="#">3</a>
			</div>
			<div class="ezsm-shopdetail-picbox-main-image">
				<ul> 
				 <c:forEach items="${good.mainphoto}" var="mainphoto">
				 <li><img src="${mainphoto.path}"/></li>
				 </c:forEach>
				 <c:forEach items="${good.goods_photos}" var="goods_photos">
				 <li><img src="${goods_photos.photo.path}"/></li>
				 </c:forEach>
				</ul>
				<a href="javascript:;" id="btn_prev"></a>
				<a href="javascript:;" id="btn_next"></a>
			</div>
		</div>
	</div>
	<div class="ezsm-shopdetail-tit">${good.name}<!-- <div class="shiyang">试样</div> --></div>
	<div class="ezsm-shopdetail-param">
		<table cellspacing="0">
			<tr> <td>单价</td> <td><span class="colororange" style="font-weight:bold;font-size:15px;margin-right:20px;">¥${good.price}</span><span style="color:#999999;font-size:12px;">(含税，不含物流费用)</span></td> </tr>
			<tr> <td>库存</td> <td>${good.inventory}吨</td> </tr>
			<tr> <td>库存地</td> <td>${good.addess}</td> </tr>
			<tr> <td>有效期</td> <td>${good.validity}</td> </tr>
		</table>
	</div>
	<div class="ezsm-mem-box-tit1">采购订单</div>
	<div class="ezsm-shopdetail-param">
		<table cellspacing="0">
			<tr> <td><span>*</span>颜色</td> <td>${good.color.name}</td></tr>
			<tr> <td><span>*</span>形态</td> <td>${good.form.name}</td></tr>
			<tr> <td>来源</td> <td>${good.source}</td> </tr>
			<tr> <td>用途</td> <td>${good.purpose}</td> </tr>
			<c:choose>
    			<c:when test="${good.protection}">
        			<tr> <td>环保</td> <td>是</td> </tr>
    			</c:when>
    			<c:otherwise>
        			<tr> <td>环保</td> <td>否</td> </tr>
    			</c:otherwise>
			</c:choose>
			<tr> <td>水分</td> <td>${good.water}<span style="color:#999999;font-size:12px;"> %</span></td> </tr>
			<tr> <td>密度</td> <td>${good.density}<span style="color:#999999;font-size:12px;"> g/cm³</span></td> </tr>
			<tr> <td>熔融指数</td> <td>${good.lipolysis}<span style="color:#999999;font-size:12px;"> g/10min</span></td> </tr>
			<tr> <td>灰分</td> <td>${good.ash}<span style="color:#999999;font-size:12px;"> %</span></td> </tr>
			<tr> <td>悬臂梁缺口冲击</td> <td>${good.cantilever}<span style="color:#999999;font-size:12px;"> kj/m²</span></td> </tr>
			<tr> <td>简支梁缺口冲击</td> <td>${good.freely}<span style="color:#999999;font-size:12px;"> kj/m²</span></td> </tr>
			<tr> <td>拉伸强度</td> <td>${good.tensile}<span style="color:#999999;font-size:12px;"> Mpa</span></td> </tr>
			<tr> <td>断裂伸长率</td> <td>${good.crack}<span style="color:#999999;font-size:12px;"> %</span></td> </tr>
			<tr> <td>弯曲强度</td> <td>${good.bending}<span style="color:#999999;font-size:12px;"> Mpa</span></td> </tr>
			<tr> <td>弯曲模量</td> <td>${good.flexural}<span style="color:#999999;font-size:12px;"> Mpa</span></td> </tr>
			<tr> <td>燃烧等级</td> <td>${good.burning}</td> </tr>
		</table>
	</div>
	<div class="ezsm-mem-box-tit1">制作案例</div>
	<div class="ezsm-shopdetail-case">
		<c:forEach items="${good.cartographys}" var="cartographys">
			<div class="ezsm-shopdetail-casebox"><img src="${cartographys.photo.path}"></div>
		</c:forEach>
	</div>
	<div class="ezsm-mem-box-tit1">其他说明</div>
	<div class="ezsm-shopdetail-desc" style="color:#333333;font-size:15px;">该产品性能良好，可定制</div>
</body>

<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.232/"; */
$(document).ready(function(){
	var imghref=$(".ezsm-shopdetail-desc").find("img").eq(0).attr("src");
	if(imghref!="undefind"&&imghref!=""){
		$(".ezsm-shopdetail-desc").find("img").eq(0).attr("src",'http://www.ezaisheng.com/'+imghref);
		$(".ezsm-shopdetail-desc").find("img").eq(0).css("width","100%");
	}
})
</script>
</html>