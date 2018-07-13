<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-Hans">
<head>
<base href="${serurl}" />
<!-- <base href="http://10.10.10.232/" /> -->
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>易再生-中国再生资源交易平台</title>
<link rel="stylesheet" href="front/resource/css/ezsm.css?v=1" />
<link rel="stylesheet" href="front/resource/css/newAddcss/ezsm_newAdd.css?v=2"/>
<style type="text/css">
html, body, .content_yzs{height:100%;box-sizing:border-box;}
.pj_mess{margin:0 2%;text-align:center;position:absolute;    top:50%;    margin-top:-150px;    width:96%;}
.pj_mess .pj_h3{color:#333333;font-size:16px;font-weight:bold;margin-bottom:10px;}
.pj_mess .pj_desc{color:#333333;font-size:14px;margin-bottom:60px;}
.pj_mess .pj_btn{color:#fff;font-size:16px;background-color:#00897c;border-radius:8px;    line-height:1.42;    padding:15px 0;}
.pj_mess  .pj_img{width:15%;margin-bottom:30px;}	
</style>
</head>
<body>
<div class="content_yzs">
  <header class="ezsm-collection-top">
    <div><a href="javascript:void(0);"></a></div>
    <div>提交申请</div>
    <div></div>
  </header>
  <div class="pj_mess">
  	<img src="front/resource/img/pj_success.png" class="pj_img"/>
  	<div class="pj_h3">提交成功，等待客服审核</div>
  	<div class="pj_desc">工作人员将在2个工作日内与您联系，请耐心等待！<br>如有疑问，请致电400-666-890.</div>
  	<div class="pj_btn">确 定</div>
  </div>
</div>
</body>
<script type="text/javascript">
var baseurl = "${serurl}";
/* var baseurl = "http://10.10.10.232/"; */
$(function(){
	$(".pj_btn").click(function(){
		window.location.href=baseurl+"/front/app/home/loan/index.htm";
	})
})
</script>
</html>