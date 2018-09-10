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
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
<script src="front/resource/js/highcharts.js"></script>
<script src="front/resource/js/newAddjs/exporting.js"></script>

</head>
<body style="background:#efefef;width: 100%; " >
	<div class="content_yzs">
  <!--价格评析-->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
     <h2><span>价格评析</span><span id="priceAnalyse" style="float: right;color: #9a9893;"><img style="height: 90%;" src="front/resource/img/right_icon.png"/></span></h2>
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
      <h2><span>研究报告</span><span id="report" style="float: right;color: #9a9893;"><img style="height: 90%;" src="front/resource/img/right_icon.png"/></span></h2>
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
      <h2><span>实时报价</span><span id="priceInTime" style="float: right;color: #9a9893;"><img style="height: 90%;" src="front/resource/img/right_icon.png"/></span></h2>
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
          <td>${item.price}元</td>
        </tr>
        <tr>
          <td><label>时间</label></td>
          <td><fmt:formatDate pattern="yyyy-MM-dd" 
            value="${item.addTime}" /></td>
        </tr>
          <tr>
         <td><span style="margin-top: 7%;"></span></td>
         <td></td>
      
         </tr>
       
       </c:forEach> 
      </table>
    </div>
  </section>
  <!---价格走势-->
  <section class="secsty_yzs">
    <div class="hTwoFater_yzs">
      <h2><span>价格走势</span><span id="priceTrend" style="float: right;color: #9a9893;"><img style="height: 90%;" src="front/resource/img/right_icon.png"/></span>
      <!-- <span id="priceAnalyse" style="float: right;color: #9a9893;"><img style="height: 90%;" src="front/resource/img/right_icon.png"/></span> -->
      </h2>
    </div>
    <div id="container" class="nedHiCha_yzs" style="    min-width: 95%;"> 
    	<!-- <div id="main" style="width: 700px;height:400px;"></div> -->
    	<div id="main" style="width: 90%;height:100%;"></div>
    </div>
  </section>
</div>
<input id="goodclassid"  class="pagecount" value="${baojia_goodclass}" style="display: none"/>
<input id="areaid"  class="pagecount" value="${baojia_areaId}" style="display: none"/>
</body>

<script type="text/javascript">
 var baseurl="${serurl}"; 
var baojia_goodclass = $('#goodclassid').val();
var baojia_areaId = $('#areaid').val();
$(document).ready(function(){
	$(".text_yzs").click(function(){
		window.location.href=baseurl+"/front/app/home/hangqShow.htm?id="+$(this).attr("id");
	})
	/* > */
	$(".hTwoFater_yzs").find("span:eq(1)").click(function(){
		if($(this).attr("id")=="priceInTime"){
			window.location.href=baseurl+"/front/app/home/analyseAndReport.htm?type="+$(this).attr("id")+"&currentPage=1&kindId="+baojia_goodclass+"&areaId="+baojia_areaId;
		}else if($(this).attr("id")=="getPriceMove"){
			window.location.href=baseurl+"/front/app/home/getPriceMove.htm";
		}else if($(this).attr("id")=="priceAnalyse"||$(this).attr("id")=="report"){
			window.location.href=baseurl+"/front/app/home/analyseAndReport.htm?type="+$(this).attr("id")+"&currentPage=1";}
		else if($(this).attr("id")=="priceTrend"){
			window.location.href=baseurl+"/front/app/home/turnToPriceMess.htm?kindId="+baojia_goodclass+"&areaId="+baojia_areaId;
		}
			
	})
})
</script>
<script type="text/javascript">
$(document).ready(function(){
	var baojia_goodclass = $('#goodclassid').val();
	getdate(baojia_goodclass);
});

function getdate(classid){
	var xdata="";
	var name="";
	var data="";
	$.ajax({
		  type: 'POST',
		  url: baseurl+"/front/app/home/getPriceTrendcyShow.htm",
		  data: {
			  goodclassId:classid
		  },
		  success: function(result){
			//进行请求后要数据
		    //x轴显示
		    xdata =result.name;
		    //主体内容
		   	data = result.data.data;
		   	name=result.data.name;
		   	echartInit(xdata, name, data);
		  },
		  error : function(errorMsg) {
	      },
		  dataType : "json"
		});
}

function echartInit(xdata,name,mydata){
	
	Highcharts.chart('container', {
	        chart: {
	            zoomType: 'x'
	        },
	        title: {
	            text: ''
	        },
	        subtitle: {
	            text: ''
	        },
			colors: ['#4cd4c8'],
	        xAxis: {
	        	 tickInterval: 8,
	        categories: xdata
	    },
			credits: {
	          enabled:false
	},
	       exporting: {
	            enabled:false
	},
	        yAxis: {
	        	allowDecimals:false,//tickPositions: [0, 5, 10, 15,20,25,30],
	        	title: {
	            	text: ''
	        	}
	    	},
	        legend: {
	            enabled: false
	        },
			
	        plotOptions: {
	            area: {
	                fillColor: {
	                    linearGradient: {
	                        x1: 0,
	                        y1: 0,
	                        x2: 0,
	                        y2: 1
	                    },
	                    stops: [
	                        [0, '#4cd4c8'],
	                        [1, Highcharts.Color('#4cd4c8').setOpacity(0).get('rgba')]
	                    ]
	                },
	                marker: {
	                    radius: 1,
						lineWidth: 1,
						fillColor: '#fff',//点填充色
						lineColor: '#4cd4c8',//点边框色
	                },
	                lineWidth: 1,
					
	                states: {
	                    hover: {
	                        lineWidth: 1
	                    }
	                },
	                threshold: null
	            }
	        },
	        series: [{
				type: 'area',
		        name: name,
		        data: mydata,
		        lineWidth:1
	    }]
	    });
};
    </script>
</html>