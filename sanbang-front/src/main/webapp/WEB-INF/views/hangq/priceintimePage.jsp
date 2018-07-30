<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:forEach items="${resultMap.Obj}" var="item">
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
</c:forEach>
<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.232/"; */
var type="${resultMap.kinds}";
var goodClassId = "${resultMap.goodClassId}";
var areaId = "${resultMap.areaId}";
var pagecount="${resultMap.Page.totalPageCount}";
$(document).ready(function(){
	$(".text_yzs").click(function(){
		window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
	})
})
</script>