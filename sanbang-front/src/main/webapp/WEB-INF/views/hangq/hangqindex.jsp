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
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
<script type="text/javascript" src="front/resource/js/echarts.min.js"></script>
</head>
<body style="background:#efefef;width: 100%; /* background:#efefef;overflow-x: hidden; */" onload='echartInit()'>
	<div class="content_yzs">
  <!--价格评析-->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>价格评析</span><span id="priceAnalyse" style="float: right;color: orange;">更多</span></h2>
    </div>
    <c:forEach var="item" items="${jiage.Obj}">
    <div class="text_yzs" id="${item.id}">
      <h3>${item.name}（<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${item.addTime}" />）</h3>
      <div class="textOverflow_yzs">
        <p>${item.meta}</p>
      </div>
    </div>
    </c:forEach>
    
  </section>
  <!--研究报告--->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>研究报告</span><span id="report" style="float: right;color: orange;">更多</span></h2>
    </div>
    <c:forEach var="item" items="${baogao.Obj}">
    <div class="text_yzs" id="${item.id}">
      <h3>${item.name}（<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${item.addTime}" />）</h3>
      <div class="textOverflow_yzs">
        <p>${item.meta}</p>
      </div>
    </div>
    </c:forEach>
  </section>
  <!--实时报价--->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>实时报价</span><span id="priceInTime" style="float: right;color: orange;">更多</span></h2>
    </div>
    <div class="realTime_price">
      <table class="tableOne_yzs" cellpadding="0" cellspacing="0">
      
      <c:forEach items="${baojia.Obj}" var="item">
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
        <%-- <tr>
          <td><label>实时成交价</label></td>
          <td>${item.goodPrice}</td>
        </tr> --%>
       </c:forEach> 
        
      </table>
    </div>
  </section>
  <!---价格走势-->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>价格走势</span></h2>
    </div>
    <div id="container" class="nedHiCha_yzs" style="min-width: 310px;"> 
    	<!-- <div id="main" style="width: 700px;height:400px;"></div> -->
    	<div id="main" style="width: 90%;height:120%;"></div>
    </div>
  </section>
</div>
<input id="goodclassid"  class="pagecount" value="${baojia_goodclass}" style="display: none"/>
<input id="areaid"  class="pagecount" value="${baojia_areaId}" style="display: none"/>
</body>

<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.232/"; */
var baojia_goodclass = $('#goodclassid').val();
var baojia_areaId = $('#areaid').val();
$(document).ready(function(){
	$(".text_yzs").click(function(){
		window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
	})
	/* 更多 */
	$(".hTwoFater_yzs").find("span:eq(1)").click(function(){
		if($(this).attr("id")=="priceInTime")
			window.location.href=baseurl+"/front/app/home/analyseAndReport.htm?type="+$(this).attr("id")+"&currentPage=1&goodClassId="+baojia_goodclass+"&areaId="+baojia_areaId;
		else
			window.location.href=baseurl+"/front/app/home/analyseAndReport.htm?type="+$(this).attr("id")+"&currentPage=1";
	})
})
</script>
<script type="text/javascript">
function echartInit(){
	var option = {
	   	    tooltip: {
	   	        trigger: 'axis'
	   	    },
	   	    legend: {
	   	    	left: '3%',
	   	    	data:['价格走势']
	   	    },
	   	    grid: {
	   	    	top:'20%',
	   	        left: '2%',
	   	        right: '3%',
	   	        bottom: '10%',
	   	        containLabel: true
	   	    },
	   	    toolbox: {
	   	        feature: {
	   	            saveAsImage: {}
	   	        }
	   	    },
	   	    xAxis: {
	   	        type: 'category',
	   	        boundaryGap: false,
	   	        data: []
	   	    },
	   	    yAxis: {
	   	        type: 'value'
	   	    },
	   	    series:{
	   	       	type:'line',
	   	        stack: '价格',
	   	        itemStyle : { normal: {label : {show: false}}},
	   	        data:[]
	   	    }
	   	};
	$.ajax({
		  type: 'POST',
		  url: baseurl+"/front/app/home/getPriceTrendcyShow.htm",
		  data: {
			  goodclassId:baojia_goodclass
		  },
		  success: function(result){
			//进行请求后要数据
  		    //x轴显示
  		    option.xAxis.data = result.name;
  		    //主体内容
  		   	option.series.data = result.data.data;
  		  	option.series.name = result.data.name;
			// 基于准备好的dom，初始化echarts实例
			var myChart = echarts.init(document.getElementById('main'));
			myChart.hideLoading();
			// 使用刚指定的配置项和数据显示图表
			myChart.setOption(option,true);
			myChart.hideLoading();
		  },
		  error : function(errorMsg) {
	            alert("无该品类实时成交数据!");
	      },
		  dataType : "json"
		});
}
    </script>
</html>