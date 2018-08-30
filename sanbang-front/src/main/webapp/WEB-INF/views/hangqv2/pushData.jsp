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
<link rel="stylesheet" href="front/resource/css/ezsm_newAdd.css?v=1" />
</head>
<body>
<div class="content_dzself">
	<header class="ezsm-collection-top">
    <div><a class="aicon_yzs" href="#"></a></div>
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
  		<div class="vdutydiop">
  			<table border="0" cellspacing="0" cellpadding="0" width="100%">
  				<tr>
  					<th width="20%"><span>区域</span></th>
  					<th width="40%"><span>规格</span></th>
  					<th width="20%"><span>涨跌幅</span></th>
  					<th width="20%"><span>价格/吨</span></th>
  				</tr>
  				<tr>
  					<td><span>石家庄</span></td>
  					<td><span>再生塑料破碎料PS 杂色 片状</span></td>
  					<td><span class="greencolor">-5.62%</span></td>
  					<td><span class="redcolor">17890.78</span></td>
  				</tr>
  				<tr>
  					<td><span>石家庄</span></td>
  					<td><span>再生塑料破碎料PS 杂色 片状</span></td>
  					<td><span class="redcolor">5.62%</span></td>
  					<td><span class="redcolor">17890.78</span></td>
  				</tr>
  				<tr>
  					<td><span>石家庄</span></td>
  					<td><span>再生塑料破碎料PS 杂色 片状</span></td>
  					<td><span class="greencolor">-5.62%</span></td>
  					<td><span class="redcolor">17890.78</span></td>
  				</tr>
  				<tr>
  					<td><span>石家庄</span></td>
  					<td><span>再生塑料破碎料PS 杂色 片状</span></td>
  					<td><span>0%</span></td>
  					<td><span class="redcolor">17890.78</span></td>
  				</tr>
  			</table>
  		</div>
  		<h4>新料</h4>
  		<div class="vdutydiop">
  			<table border="0" cellspacing="0" cellpadding="0" width="100%">
  				<tr>
  					<th width="25%"><span>名称</span></th>
  					<th width="20%"><span>区域</span></th>
  					<th width="20%"><span>价格/吨</span></th>
  					<th width="20%"><span>涨跌幅</span></th>
  					<th width="15%"><span>时间</span></th>
  				</tr>
  				<tr>
  					<td><span>HDPE500S兰州石化</span></td>
  					<td><span>石家庄</span></td>
  					<td><span class="redcolor">17890.78</span></td>
  					<td><span class="greencolor">-5.62%</span></td>
  					<td><span>08-07</span></td>
  				</tr>
  				<tr>
  					<td><span>HDPE500S兰州石化</span></td>
  					<td><span>石家庄</span></td>
  					<td><span class="redcolor">17890.78</span></td>
  					<td><span>0%</span></td>
  					<td><span>08-07</span></td>
  				</tr>
  				<tr>
  					<td><span>HDPE500S兰州石化</span></td>
  					<td><span>石家庄</span></td>
  					<td><span class="redcolor">17890.78</span></td>
  					<td><span class="redcolor">5.62%</span></td>
  					<td><span>08-07</span></td>
  				</tr>
  			</table>
  		</div>
  	</div>
  </section>
  <section class="shdgjcueio">
  	<h3><span></span>价格走势订阅：</h3>
  	<div class="gtuceifop">
  		<h4>华北-再生工程塑料-PE</h4>
  		<div class="vdutydiop">
  			<div id="main" style="width: 100%; height: 150px;"></div>
  		</div>
  		<h4>华北-再生工程塑料-PE</h4>
  		<div class="vdutydiop">
  			<div id="mainTwoadd" style="width: 100%; height: 150px;"></div>
  		</div>
  	</div>
  </section><section class="shdgjcueio">
  	<h3><span></span>价格走势订阅：</h3>
  	<div class="gtuceifop">
  		<ul class="ukyuioghs">
  			<li><a href="javascript:;">[日评]2017年10月底，为贯彻落实为贯彻落实为贯彻落实为贯彻落实为贯彻落实</a></li>
  			<li><a href="javascript:;">[日评]2017年10月底，为贯彻落实为贯彻落实为贯彻落实为贯彻落实为贯彻落实</a></li>
  			<li><a href="javascript:;">[日评]2017年10月底，为贯彻落实为贯彻落实为贯彻落实为贯彻落实为贯彻落实</a></li>
  			<li><a href="javascript:;">[日评]2017年10月底，为贯彻落实为贯彻落实为贯彻落实为贯彻落实为贯彻落实</a></li>
  		</ul>
  	</div>
  </section>
</div>
</body>
<script type="text/javascript">
	mui.init();
			(function($) {
				//阻尼系数
				var deceleration = mui.os.ios?0.003:0.0009;
				$('.mui-scroll-wrapper').scroll({
					bounce: false,
					indicators: true, //是否显示滚动条
					deceleration:deceleration
				});
				$.ready(function() {
					//循环初始化所有下拉刷新，上拉加载。
					$.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, pullRefreshEl) {
						$(pullRefreshEl).pullToRefresh({
							down: {
								callback: function() {
									var self = this;
									setTimeout(function() {
										var ul = self.element.querySelector('.mui-table-view');
										ul.insertBefore(createFragment(ul, index, 5, true), ul.firstChild);
										self.endPullDownToRefresh();
									}, 1000);
								}
							},
							up: {
								callback: function() {
									var self = this;
									setTimeout(function() {
										var ul = self.element.querySelector('.mui-table-view');
										ul.appendChild(createFragment(ul, index, 5));
										self.endPullUpToRefresh();
									}, 1000);
								}
							}
						});
					});
					var createFragment = function(ul, index, count, reverse) {
						var length = ul.querySelectorAll('li').length;
						var fragment = document.createDocumentFragment();
						var li;
						for (var i = 0; i < count; i++) {
							li = document.createElement('li');
							li.className = 'mui-table-view-cell';
							li.innerHTML = '<div class="mui-table"><div class="mui-table-cell mui-col-xs-9"><h4 class="mui-ellipsis-2">第' + (index + 1) + '个选项卡子项的<br/>-xxx公司在设密马方面取得巨大成功呢</h4><p class="mui-h6 mui-ellipsis">2017-03-27 00:00:00</p></div></div>';							
							fragment.appendChild(li);
						}
						return fragment;
					};
				});
			})(mui);		
  </script> 
</html>