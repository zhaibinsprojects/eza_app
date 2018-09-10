<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
   <!--数据列表-->
   <c:forEach var="item" items="${resultMap.Obj}">
    <div class="text_yzs" id="${item.id}" style="padding: 0px 0px 0px 0;">
    
    	<div class="textOverflow_yzs" style="-webkit-line-clamp: 1;">
		      	<h3 style="margin-top: 0px;margin-bottom: 0px;">${item.name}</h3>
		</div>
    
      
      <div class="textOverflow_yzs" style="-webkit-line-clamp: 3;">
        <p>${item.meta}</p>
      </div>
      <h3><fmt:formatDate pattern="yyyy-MM-dd" 
            value="${item.addTime}" /></h3>
    </div>
  </c:forEach>
<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.52/"; */
var type="${resultMap.kinds}";
var pagecount="${resultMap.Page.totalPageCount}";
$(document).ready(function(){
	mui('body').on('tap', '.text_yzs', function() {
		//window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
		window.location.href=baseurl+"/front/app/menuhq/hangqShow.htm?id="+$(this).attr("id");
	});
})
</script>