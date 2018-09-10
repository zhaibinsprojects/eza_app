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
<script type="text/javascript" src="front/resource/js/jquery-1.9.1.min.js"></script>
<script src="front/resource/js/highcharts.js"></script>
<script src="front/resource/js/newAddjs/exporting.js"></script>
<script type="text/javascript" src="front/resource/js/price/mui.min.js"></script>
<script src="front/resource/js/newAddjs/mui.pullToRefresh.js"></script> 
<script src="front/resource/js/newAddjs/mui.pullToRefresh.material.js"></script> 
<script type="text/javascript" src="front/resource/js/hangq/hangqpushdata.js"></script>
<link rel="stylesheet" href="front/resource/css/ezsm_newAdd.css?v=1" />

<link rel="stylesheet" href="front/resource/script/layer/mobile/need/layer.css" />
<script type="text/javascript" src="front/resource/script/layer/mobile/layer.js" charset="utf-8"></script>
</head>
<body>
<div class="content_dzself">
	<header class="ezsm-collection-top">
    <div><a class="" href="javascript:void(0);"></a></div>
    <div>价格行情定制推送</div>
    <div></div>
  </header>
  <section>
  	<div id="grad">
  		<p>2017年12月1日 <span>星期五</span></p>
  	</div>
  </section>
  <section class="shdgjcueio">
  	<h3><span></span>实时报价订阅：</h3>
  	<div class="gtuceifop">
  		<h4>再生塑料</h4>
  		<div class="zsbj">
  		<div class="vdutydiop">
  		   <h5 class="dingzhi_show">再生塑料1</h5>
  			<table border="0" cellspacing="0" cellpadding="0" width="100%">
  				<tr>
  					<th width="20%"><span>区域</span></th>
  					<th width="40%"><span>规格</span></th>
  					<th width="20%"><span>涨跌幅</span></th>
  					<th width="20%"><span>价格/吨</span></th>
  				</tr>
  			</table>
  		</div>
  		</div>
  		<h4>新料</h4>
  		<div class="xlbj">
  		<div class="vdutydiop">
  			<table border="0" cellspacing="0" cellpadding="0" width="100%">
  				<tr>
  					<th width="25%"><span>名称</span></th>
  					<th width="20%"><span>区域</span></th>
  					<th width="20%"><span>价格/吨</span></th>
  					<th width="20%"><span>涨跌幅</span></th>
  					<th width="15%"><span>时间</span></th>
  				</tr>
  				
  			</table>
  		</div>
  		</div>
  		
  	</div>
  </section>
  <section class="shdgjcueio">
  	<h3><span></span>价格走势订阅：</h3>
  	<div class="zszs">
	  	<div class="gtuceifop">
	  		<h4>华北-再生工程塑料-PE</h4>
	  		<div class="vdutydiop" >
	  			<div id="main" style="width: 100%; height: 150px;"></div>
	  		</div>
	  		<h4>华北-再生工程塑料-PE</h4>
	  		<div class="vdutydiop">
	  			<div id="mainTwoadd" style="width: 100%; height: 150px;"></div>
	  		</div>
	  	</div>
  	</div>
  	
  </section><section class="shdgjcueio">
  	<h3><span></span>价格走势订阅：</h3>
  	<div class="gtuceifop">
  		<ul class="ukyuioghs">
  			<li><a href="javascript:;">[日评]2017年10月底，为贯彻落实为贯彻落实为贯彻落实为贯彻落实为贯彻落实</a></li>
  			
  		</ul>
  	</div>
  </section>
</div>
</body>
<script type="text/javascript">
	var  pushcode="${pushcode}";
	var baseurl="${serurl}";
	//var isshow="${result.obj.isshow}";
  </script> 
</html>