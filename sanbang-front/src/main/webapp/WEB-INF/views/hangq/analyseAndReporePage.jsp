<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach var="item" items="${resultMap.Obj}">
    <div class="text_yzs" id="${item.id}">
      <h3>${item.name}（<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${item.addTime}" />）</h3>
      <div class="textOverflow_yzs">
        <p>${item.meta}</p>
      </div>
    </div>
</c:forEach>