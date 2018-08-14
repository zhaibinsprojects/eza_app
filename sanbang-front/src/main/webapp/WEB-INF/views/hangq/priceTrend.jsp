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
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="front/resource/css/ezsm.css?v=1" />
<script type="text/javascript" src="front/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="front/resource/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="front/resource/js/ezsm.js?v=1"></script>
<script type="text/javascript" src="front/resource/js/goods/mui.min.js"></script>
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
<link href="front/resource/css/pagercss/toPush.css" rel="stylesheet" type="text/css" />
<link href="front/resource/css/pagercss/mui.min.css" rel="stylesheet" >

<script src="front/resource/js/newAddjs/mui.picker.js"></script>
<script src="front/resource/js/newAddjs/mui.poppicker.js"></script>
<script src="front/resource/js/newAddjs/city.data.js" type="text/javascript"></script>
<link href="front/resource/css/newAddcss/mui.picker.css" rel="stylesheet" />
<link href="front/resource/css/newAddcss/mui.poppicker.css" rel="stylesheet" />
<script type="text/javascript" src="front/resource/js/newAddjs/pricezsDetail.js"></script>
 <script src="front/resource/js/highcharts.js"></script>
</head>
<body style="background:#efefef;width: 100%;/* background:#efefef;overflow-x: hidden; */">
  	<div class="hTwoFater_yzs">
    <h2><span>价格趋势</span></h2>
    <nav class="navAll_yzs" style="margin-top: 3%;/* margin-left: -5%; margin-right: -5%; */">
  	<ul class="nav_ulsty_yzs">
    	<li  id="showCityPicker" style="width: 25%;"><a id="cityResult" href="javascript:;">山东</a></li>
        <li id="showUserPicker" style="width: 25%;"><a id="userResult" href="javascript:;">品类</a></li>
        <li id="showColorPicker" style="width: 25%;"><a id="colorResult" href="javascript:;">颜色</a></li>
        <li id="showFormPicker" style="width: 25%;"><a id="formResult" href="javascript:;">形态</a></li>
    </ul>
  	</nav>
    </div>
  <!--实时报价--->
    <section class="secsty_yzs" style="height:50%;margin-top: 3%">
    <div id="container" class="nedHiCha_yzs" style="min-width: 95%;height:80%;">
    	<!-- <div id="main" style="width: 90%;height:40%;"></div> -->
    </div>
  </section>
  <section class="secpri_yzs">
  	<table class="tabl_yzs_ud" cellpadding="0" cellspacing="0">
    	<thead>
        	<tr>
            	<th><span>日期</span></th>
              	<th><span>均价</span></th>
                <th><span>跌涨</span></th>
                <th><span>幅度</span></th>
            </tr>
        </thead>
        <tbody>
        	
        </tbody>
    </table>
  </section>
  <form class="csubmit">
 	 <input type="hidden" value="${colorId}" name="colorId" class="colorId"/>
	 <input type="hidden" value="${kindId}" name="kindId" class="kindId"/>
	 <input type="hidden" value="${formId}" name="formId" class="formId"/>
	 <input type="hidden" value="${areaId}" name="areaId" class="areaId"/>
	 
	 <input type="hidden" value="${colorval}" name="colorval" class="colorval"/>
	 <input type="hidden" value="${kindval}" name="kindval" class="kindval"/>
	 <input type="hidden" value="${formval}" name="formval" class="formval"/>
	 <input type="hidden" value="${areaval}" name="areaval" class="areaval"/>
	 <input type="hidden" value="priceInTimeDetail" name="type" />
  </form>   
</body>
<script type="text/javascript">
var baseurl="${serurl}";
/* var baseurl="http://10.10.10.232/";*/
var goodClassId = "${kindId}";
var areaId = "${areaId}";
$(document).ready(function(){
	var  colorval=$(".colorval").val();
	if(colorval!=""){
		$("#colorResult").html(colorval);
	}
	
	var  kindval=$(".kindval").val();
	if(kindval!=""){
		$("#userResult").html(kindval);
	}
	
	var  formval=$(".formval").val();
	if(formval!=""){
		$("#formResult").html(formval);
	}
	
	var  areaval=$(".areaval").val();
	if(areaval!=""){
		$("#cityResult").html(areaval);
	}
});

$(".nav_ulsty_yzs li").click(function(){
	$(this).siblings().find("a").removeClass("active_yzs");
	$(this).children("a").addClass("active_yzs");
	});
$(".dy_yzsbtn").click(function(){
	$(".graybaks,.grayChren").show();
	});
$(".qx_yzsbtn").click(function(){
	$(".graybaks,.grayChren").hide();
	});	
</script>
<script type="text/javascript">
$(document).ready(function(){
	getdate(goodClassId);
});
function getdate(classid){
	var xdata="";
	var name="";
	var data="";
	var plist="";
	$.ajax({
		  type: 'POST',
		  url: baseurl+"/front/app/home/getPriceTrendcyShow.htm",
		  data: {
			  kindId:classid
		  },
		  success: function(result){
			//进行请求后要数据
		    //x轴显示
		    xdata =result.name;
		    //主体内容
		   	data = result.data.data;
		   	name=result.data.name;
		   	plist=result.plist;
		   	echartInit(xdata, name, data);
		   	initTable(plist);
		  },
		  error : function(errorMsg) {
	            //alert("无该品类实时成交数据!");
	      },
		  dataType : "json"
		});
}
function initTable(plist){
	$("tbody").empty();
	var html = "";	
	for(var i=0;i<plist.length;i++){
		html = html+"<tr><td><span>"+plist[i].dealDate+"</span></td>"+
					"<td><span>"+plist[i].currentAVGPrice+"</span></td>";
		if(plist[i].increaseValue > 0){			
			html=html+"<td><span class='sup'>涨</span></td><td><span class='pup'>"+plist[i].sandByOne+"</span></td>";
		}else if(plist[i].increaseValue < 0){
			html=html+"<td><span class='sdown'>跌</span></td><td><span class='pdown'>"+plist[i].sandByOne+"</span></td>";			
		}else {
			html=html+"<td><span class='spin'>平</span></td><td><span class='ppin'>"+plist[i].sandByOne+"</span></td> ";
		} 
		html=html+"</tr>";
	}
	$("tbody").append(html);
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