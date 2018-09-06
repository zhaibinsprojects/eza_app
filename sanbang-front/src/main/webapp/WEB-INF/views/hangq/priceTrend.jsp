<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html class="page-login">
<head>
<base href="${serurl}"/>
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
<script type="text/javascript" src="front/resource/js/price/priceTrend.js"></script>
<script type="text/javascript" src="front/resource/js/price/priceTrendPull.js"></script>
<script src="front/resource/js/highcharts.js"></script>
</head>
<body style="background:#efefef;width: 100%;/* background:#efefef;overflow-x: hidden; */">
  	<div class="hTwoFater_yzs">
    <h2><span>价格趋势</span></h2>
    <!-- <nav class="navAll_yzs" style="margin-top: 3%; width: 100%;/* margin-left: -5%; margin-right: -5%; */">
  	<ul class="nav_ulsty_yzs" style="width: 100%;">
    	<li id="showCityPicker" style="width: 25%;"><a id="cityResult" href="javascript:;">山东</a></li>
        <li id="showUserPicker" style="width: 25%;"><a id="userResult" href="javascript:;">品类</a></li>
        <li id="showColorPicker" style="width: 25%;"><a id="colorResult" href="javascript:;">颜色</a></li>
        <li id="showFormPicker" style="width: 25%;"><a id="formResult" href="javascript:;">形态</a></li>
    </ul>
  	</nav> -->
    </div>
    <nav class="navAll_yzs" style="width: 100%;">
		<ul class="nav_ulsty_yzs">
			<li id="sel_city" style="width: 33%;"><span>地区</span></li>
			<li id="sel_type" style="width: 34%;"><span>品类</span></li>
			<li id="sel_select" style="width: 33%;"><span>筛选</span></li>
		</ul>
	</nav>
    <nav class="navAll_yzs" style="width: 100%;">
    	<ul class="nav_ulsty_yzs">
			<li id="weekbut" style="width: 25%;" onclick="changeDates('WEEK')"><span>一周</span></li>
			<li id="monthbut" style="width: 25%;" onclick="changeDates('MONTH')"><span>一月</span></li>
			<li id="quarterbut" style="width: 25%;" onclick="changeDates('QUARTER')"><span>三月</span></li>
			<li id="yearbut" style="width: 25%;" onclick="changeDates('YEAR')"><span>一年</span></li>
		</ul>
    </nav>
  <!--价格趋势折线图--->
  <section class="secsty_yzs" style="height:45%; margin-bottom:0px">
  <div id="container" class="nedHiCha_yzs" style="min-width: 95%;height:90%;"></div>
  </section>
  
  
  
  <!-- <div id="pullrefresh" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll"> 
    <section class="secNeiron mui-table-view mui-table-view-chevron">
    	<table class="tabl_yzs_ud" cellpadding="0" cellspacing="0">
    	<thead>
        	<tr>
                <th><span>品类</span></th>
              	<th><span>地区</span></th>
                <th><span>均价</span></th>
                <th><span>涨跌幅</span></th>
                <th><span>时间</span></th>
            </tr>
        </thead>
        <tbody>
        	<div class="cont">
		  
		  
		  
		  
		  
		    
			</div>
        </tbody>
    </table>
        
    </section>
    </div>
   </div> -->
  
  
  
  
  <section class="secpri_yzs">
  	<table class="tabl_yzs_ud" cellpadding="0" cellspacing="0">
    	<thead>
        	<tr>
                <th><span>品类</span></th>
              	<th><span>地区</span></th>
                <th><span>均价</span></th>
                <th><span>涨跌幅</span></th>
                <th><span>时间</span></th>
            </tr>
        </thead>
        <tbody>
        
        </tbody>
    </table>
  </section>
  
  		<!-- 选中城市 -->
		<div class="msg-local" style="width: 100%">
			<div class="msg-local-tit">全部地区</div>
			<div class="msg-local-now"><img src="img/micon_026.png"/>您的位置：<span>河北</span></div>
			<div class="msg-local-tit">热门省市</div>
			<div class="msg-local-con">
				<ul><li data-value="beijing">北京</li><li data-value="shanghai">上海</li><li data-value="guangzhou">广州</li></ul>
				<div class="blank0c"></div>
			</div>
			<div class="msg-local-tit">A</div>
			<div class="msg-local-con">
				<ul>
					<li data-value="beijing">北京</li><li data-value="shanghai">上海</li><li data-value="guangzhou">广州</li>
					<li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li>
					<li data-value="guangzhou">广州</li><li>广州</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			<div class="msg-local-tit">B</div>
			<div class="msg-local-con">
				<ul>
					<li data-value="beijing">北京</li><li data-value="shanghai">上海</li><li data-value="guangzhou">广州</li>
					<li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li>
					<li data-value="guangzhou">广州</li><li data-value="guangzhou">广州</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			<div class="blank0c"></div>
		</div>
  		<!-- 选择品类 -->
		<div class="msg-type" style="width: 100%">
			<div class="msg-type-con">
				<ul>
					<li data-value="all">全部品类</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			<!-- <div class="msg-type-tit">热门品类</div>
			<div class="msg-type-con msg-type-con1">
				<ul>
					<li data-value="PE">再生通用塑料-PE</li>
					<li data-value="ABS">再生通用塑料-ABS</li>
				</ul>
				<div class="blank0c"></div>
			</div> -->
			<div class="msg-type-tit">再生通用塑料</div>
			<div class="msg-type-con">
				<ul>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			
			<div class="msg-type-tit">再生塑料破碎料</div>
			<div class="msg-type-con">
				<ul>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			
			<div class="msg-type-tit">再生工程塑料</div>
			<div class="msg-type-con">
				<ul>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
					<li data-value="LDPE">LDPE</li> <li data-value="HDPE">HDPE</li> <li data-value="LLDPE">LLDPE</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			<div class="blank0c"></div>
		</div>
		<!--筛选其他-->
		<div class="msg-select">
			<div class="msg-select-tit">颜色</div>
			<div class="msg-select-con">
				<ul>
					<li>本色</li> <li>白色</li> <li>黑色</li>
					<li>灰色</li> <li>红色</li> <li>黄色</li>
					<li>杂色</li> <li>透明</li> <li>半透</li>
					<li>绿色</li> <li>蓝色</li> <li>橙色</li>
					<li>紫色</li> <li>其他</li> 
				</ul>
				<div class="blank0c"></div>
			</div>
			<div class="msg-select-tit">形态</div>
			<div class="msg-select-con">
				<ul>
					<li>颗粒</li> <li>片状</li> <li>颗粒</li> <li>粉末</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			<div class="msg-select-tit">来源</div>
			<div class="msg-select-con">
				<input type="text" placeholder="输入关键字"/>
			</div>
			<div class="msg-select-tit">用途</div>
			<div class="msg-select-con">
				<input type="text" placeholder="输入关键字"/>
			</div>
			<div class="msg-select-tit">价格</div>
			<div class="msg-select-con">
				<input type="text" style="width:41%;" placeholder="最低价"/> 至 <input type="text" style="width:41%;" placeholder="最高价"/>
			</div>
			<div class="msg-select-tit">重要参数</div>
			<div class="msg-select-con">
				<select><option>请选择</option></select>
				<input type="text" style="width:41%;" placeholder="最低价"/> 至 <input type="text" style="width:41%;" placeholder="最高价"/>
			</div>
			<div class="msg-select-tit">燃烧等级</div>
			<div class="msg-select-con">
				<input type="text" placeholder="输入关键字"/>
			</div>
			<div class="msg-select-tit">是否环保</div>
			<div class="msg-select-con">
				<ul>
					<li>是</li> <li>否</li>
				</ul>
				<div class="blank0c"></div>
			</div>
			<div class="blank70"></div>
			<div class="msg-select-btnbox">
				<div class="msg-select-btnA">重置</div>
				<div class="msg-select-btnB">确定</div>
			</div>
		</div>
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
	 <input type="hidden" value="WEEK" name="dateBetweenType" />
  </form>   
</body>
<script type="text/javascript">
var baseurl="${serurl}"; 
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
</html>