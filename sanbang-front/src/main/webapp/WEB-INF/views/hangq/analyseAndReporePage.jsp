<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach var="item" items="${resultMap.Obj}">
      <!--数据列表-->
    <c:forEach var="item" items="${resultMap.Obj}">
    <div class="text_yzs" id="${item.id}">
      <h3>${item.name}</h3>
      <div class="textOverflow_yzs" style="-webkit-line-clamp: 3;">
        <p>${item.meta}</p>
      </div>
      <h5><fmt:formatDate pattern="yyyy-MM-dd" 
            value="${item.addTime}" /></h5>
    </div>
    </c:forEach>
</c:forEach>

<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.232/"; */
var type="${resultMap.kinds}";
var pagecount="${resultMap.Page.totalPageCount}";
$(document).ready(function(){
	mui('body').on('tap', '.text_yzs', function() {
		window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
	});
})
</script>