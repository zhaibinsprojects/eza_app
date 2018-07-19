<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

    <c:forEach items="${dvaluatelist}" var="dv">
				 <li>
						<div class="ezsm-evaluate-list-tit">${fn:substring(dv.user.name,0,3)}*** <span>的评价</span>
						<span><fmt:formatDate value="${dv.addTime}" type="date" pattern="yyyy-MM-dd HH:mm"/></span></div>
						<div class="ezsm-evaluate-list-table">
							<table>
							<tr><td>综合评星</td><td>
								<c:choose>
								<c:when test="${dv.goodQuality eq 0}">
									<img src="front/resource/img/micon_047.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 1}">
									<img src="front/resource//img/micon_051.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 2}">
									<img src="front/resource/img/micon_050.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 3}">
									<img src="front/resource/img/micon_049.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 4}">
									<img src="front/resource/img/micon_048.png"/>
								</c:when>
								<c:when test="${dv.goodQuality eq 5}">
									<img src="front/resource/img/micon_047.png"/>
								</c:when>
								</c:choose>
								</td>
								</tr>
								<tr><td>评定描述</td><td>${fn:substring(dv.conttent,0,10)}...</td></tr>
								<c:if test="${fn:length(dv.accessroys) eq 0 }">
								<tr><td>上传图片</td><td>无</td></tr>
								</c:if>
								<c:if test="${fn:length(dv.accessroys) gt 0 }">
								<c:forEach items="${dv.accessroys}" var="pic">
								<tr><td>上传图片</td><td><img style="width: 20%;height: 4%;" src="${pic.accessory.path}"/></td></tr>
								</c:forEach>
								</c:if>
							</table>
						</div>
					</li>
			 </c:forEach>

<!--总页数 -->
   <input type="hidden" id="${pageCount}" class="pagecount" value="${pageCount}"/> 

<script type="text/javascript">
var baseurl="${serurl}";
var goodsid="${good.id}";
</script>
