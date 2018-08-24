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
<script type="text/javascript" src="front/resource/js/goods/goods.js"></script>

</head>
<body style="background:#efefef;">
	<div class="msg-bg"></div>
	<div class="msg-box"></div>
	
	<%@ include file="../goodshead.jsp"%> 
	
<div class="ezsm-shopdesc-pannel">
		<div class="ezsm-shopdesc-tit">重要参数</div>
		<div class="ezsm-shopdesc-table">
			<table cellspacing="0">
				<tr> <td>密度<br><span>(g/m³)</span></td> <td>${goodinfo.density}~${goodinfo.density}</td> </tr>
				<tr> <td>悬臂梁缺口冲击<br><span>(KJ/m²)</span></td> <td>${goodinfo.cantilever}~${goodinfo.cantilever}</td> </tr>
				<tr> <td>简支梁缺口冲击<br><span>(KJ/m²)</span></td> <td>${goodinfo.freely}~${goodinfo.freely}</td> </tr>
				<tr> <td>熔融指数<br><span>(g/10min)</span></td> <td>${goodinfo.lipolysis}~${goodinfo.lipolysis}</td> </tr>
				<tr> <td>灰分<br><span>(%)</span></td> <td>${goodinfo.ash}~${goodinfo.ash}</td> </tr>
				<tr> <td>水分<br><span>(%)</span></td> <td>${goodinfo.water}~${goodinfo.water}</td> </tr>
				<tr> <td>拉伸强度<br><span>(MPa)</span></td> <td>${goodinfo.tensile}~${goodinfo.tensile}</td> </tr>
				<tr> <td>断裂伸长率<br><span>(%)</span></td> <td>${goodinfo.crack}~${goodinfo.crack}</td> </tr>
				<tr> <td>弯曲强度<br><span>(MPa)</span></td> <td>${goodinfo.bending}~${goodinfo.bending}</td> </tr>
				<tr> <td>弯曲模量<br><span>(MPa)</span></td> <td>${goodinfo.flexural}~${goodinfo.flexural2}</td> </tr>
				<tr> <td>燃烧等级</td> <td>${good.burning}~${goodinfo.burning}</td> </tr>
				<tr> <td>是否环保</td> <td>
				<c:choose>
    			<c:when test="${good.protection eq 1}">
        			是
    			</c:when>
    			<c:when test="${good.protection eq 2}">
        			未检测
    			</c:when>
    			<c:otherwise>
        			否
    			</c:otherwise>
			</c:choose>
				</td> </tr>
			</table>
		</div>
	</div>
	<div class="blank10"></div>
	<div class="ezsm-shopdesc-pannel">
		<!-- <div class="ezsm-shopdesc-tit">详细描述</div> -->
		<%-- <div class="ezsm-shopdesc-info">
			${good.content}
		</div> --%>
	</div>
	
	<div class="ezsm-orderres-tit"><img src="front/resource/img/tit_002.png" /></div>
	<div class="ezsm-sameshop-pannel">
		<table cellspacing="0">
			<c:forEach items="${catalist}" var="cg" varStatus="status" >
				<c:if test="${(status.index)%2 eq 0}">
				<tr> 
				</c:if>
					<td style=" height: 200px;">
						<div class="ezsm-sameshop-box">
							<div class="ezsm-sameshop-img" onclick="window.location.href='${baseurl}app/goods/toGoodsShow.htm?id=${cg.id}'">
							<img style="height: 70%;" src="${cg.path}"/>
							</div>
							<div class="ezsm-sameshop-tit">${cg.name}</div>
							<div class="ezsm-sameshop-price">${cg.price}元/吨<span>${cg.inventory}</span></div>
						</div>
					</td>
				<c:if test="${(status.index)%2 eq 1}">
					</tr>
				</c:if>
			</c:forEach>
			
			
		</table>
	</div>
	<div class="ezsm-index-more">我也是有底线的</div>
	<%@ include file="../goodsfoot.jsp"%> 
	<input name="toauth" type="hidden" class="toauth" value="${toauth}"/>
</body>

<script type="text/javascript">
var baseurl="${serurl}";
</script>
</html>