/** *init* */
var  userk="";
$(function() {
	// APP点击
	var u = navigator.userAgent; // 获取用户设备
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	
	if (isIOS) {
		setupWebViewJavascriptBridge(function(bridge) {
			//筛选显示内容
			var data = {};
			var str = JSON.stringify(data);
			//判断权限
			try {
				//调用IOS方法“isiosLogin”
				WebViewJavascriptBridge.callHandler('isiosLogin', str, function(data) {
					userk= data;
					if(userk!=""){
						//$(".userkey").val(data);
						$("input[name=token]").val(data);
					}
				});		 
			} catch (e) { }
		
			//注册一个"pricetrend"JS函数到IOS，供IOS调用
			bridge.registerHandler("pricetrend", function(data, responseCallback) {
				pricetrend(data);
				//responseCallback(responseData);
			});
		
		});
		// ios app 设备才执行
		//这段代码是固定的，必须要放到js中
		function setupWebViewJavascriptBridge(callback) {
		    if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
		    if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
		    window.WVJBCallbacks = [callback];
		    var WVJBIframe = document.createElement('iframe');
		    WVJBIframe.style.display = 'none';
		    WVJBIframe.src = 'https://__bridge_loaded__';
		    document.documentElement.appendChild(WVJBIframe);
		    setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
		}
        // 与OC交互的所有JS方法都要放在此处注册，才能调用通过JS调用OC或者让OC调用这里的JS
		var bridge =setupWebViewJavascriptBridge();
		} else {
		//安卓侨联
		 try {
			 userk= window.android.isAndroidLogin();
			 if(userk!=""){
				 //$(".userkey").val(userk);
				 $("input[name=token]").val(userk);
			 }
		} catch (e) { }
		doclick();
	};
});
/*点击列表项*/
function doclick(){
	$(".graybakhuio").unbind(); //移除之前所有绑定的事件
	//走势图锁
	$(".graybakhuio").on('tap',function(){
		var u = navigator.userAgent; // 获取用户设备
		var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
		if ("" == $("input[name=token]").val()) {
			if (isIOS) {
				iosnologin();
			} else {
				androidnologin();
			}
			return false;
		}else{
			//已登录 去订阅
			if (isIOS) {
				iostodingyue();
			} else {
				androidtodingyue();
			}
		}
	})
};
//列表锁-点击事件
function doclickTwo(){
	$(".lockyuip").unbind(); //移除之前所有绑定的事件
	//列表锁
	$(".lockyuip").on('tap',function(){
		var u = navigator.userAgent; // 获取用户设备
		var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
		if ("" == $("input[name=token]").val()) {
			if (isIOS) {
				iosnologin();
			} else {
				androidnologin();
			}
			return false;
		}else{
			//已登录 去订阅
			if (isIOS) {
				iostodingyue();
			} else {
				androidtodingyue();
			}
		}
	})
}

/*------------------------------------*/

var namey = [];
var numo = [];
var classname = "";
$(document).ready(function(){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceTrendDetail.htm',
	    data:{
	    	"goodClassId":$("input[name=goodClassId]").val(),
	    	"token":$("input[name=token]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	    	//页数
	    	$("input[name=pagecount]").val(Math.ceil(result.length/20));
	    	var isshow = 0;
	       $.each(result, function (index, item) {
	            namey.push(item.dealDate);     
	            numo.push(item.currentAVGPrice);
	            classname = item.goodClassName;
	            isshow = item.isshow;
	        });
	       if(isshow=='1'){
	    	   //展示走势图
	    	   $("#container").css('display','block');//显示  
	    	   $("#containerLock").css('display','none');//隐藏   
	       }else{
	    	   $("#container").css('display','none');//隐藏 
	    	   $("#containerLock").css('display','block');//显示   
	       }
	       	//heighchart
	       	echartInit(namey, classname, numo);   
	       	doclick();
	       	showdataByPageOne("WEEK",1);
	    	
	    },
	    error: function (errorMsg) {
	        //alert("图表数据请求失败!");
	    }

	});	
})

//初始化填充表格
function initTable(plist){
	$("tbody").empty();
	var html = "";	
	for(var i=0;i<plist.length;i++){
		html = html+"<tr><td><span>"+plist[i].goodClassName+"</span></td>"+
			"<td><span>"+plist[i].goodArea+"</span></td>";
		
		if(plist[i].isshow=='1'){
			html = html+"<td><span class='colrRed'>￥"+plist[i].currentAVGPrice+"</span></td>";
			
			if(plist[i].currentAVGPrice > plist[i].preAVGPrice){			
				html=html+"<td><span class='colrRed'>"+plist[i].sandByOne+"</span></td>";
			}else if(plist[i].currentAVGPrice < plist[i].preAVGPrice){
				html=html+"<td><span class='colGreen'>"+plist[i].sandByOne+"</span></td>";			
			}else {
				html=html+"<td><span>"+plist[i].sandByOne+"</span></td> ";
			} 
			$("#container").css('display','block');//显示
			$("#containerLock").css('display','none');//隐藏   
		}else{
			html = html+ "<td><span><i class='lockyuip'></i></span></td>"+
			"<td><span><i class='lockyuip'></i></span></td>";
		}
		html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
	}
	$("tbody").append(html);
	//doclick();
}

//选择显示周期：一周、一月、三月、一年
$(document).ready(function(){
	$(".section_roptab ul li").click(function(){
		$(this).addClass("active");
		$(this).siblings("li").removeClass("active")
		$("input[name=dateBetweenType]").val($(this).attr("name"));
		$("input[name=channelchanged]").val("1");
		//获取选定月份
		//加载表格数据列表，默认加载首页
		mui('#pullrefresh').pullRefresh().enablePullupToRefresh(true);
		showdataByPageOne($(this).attr("name"),1);
		//加载则线图数据
		showdatas($(this).attr("name"));
	});
})
function showdatas(dateBetweenType){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceTrendDetail.htm',
	    data:{
	    	"goodClassId":$("input[name=goodClassId]").val(),
	    	"dateBetweenType":dateBetweenType,
	    	"areaId":$("input[name=areaId]").val(),
	    	"colorId":$("input[name=colorId]").val(),
	    	"formId":$("input[name=formId]").val(),
	    	"token":$("input[name=token]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	    //清空数组
	    namey=[];
	    numo=[];
	    //页数
	    $("input[name=pagecount]").val(Math.ceil(result.length/20));
	       $.each(result, function (index, item) {
	            namey.push(item.dealDate);     
	            numo.push(item.currentAVGPrice);
	            classname = item.goodClassName;
	        });
	     //heighchart
	     echartInit(namey, classname, numo); 
	     doclick();
	    },
	    error: function (errorMsg) {
	    }
	});
}
//实时报价详情列表-分页展示 
function showdataByPageOne(dateBetweenType,currentPage){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceTrendDetailPage.htm',
	    data:{
	    	"goodClassId":$("input[name=goodClassId]").val(),
	    	"dateBetweenType":dateBetweenType,
	    	"currentPage":"1",
	    	"areaId":$("input[name=areaId]").val(),
	    	"colorId":$("input[name=colorId]").val(),
	    	"formId":$("input[name=formId]").val(),
	    	"token":$("input[name=token]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	       initTable(result);
	       //列表添加成功-添加事件
	       doclickTwo();
	    },
	    error: function (errorMsg) {
	    }
	});
}
//---------------------修改为highchart
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
	        	/*labels: {
		            formatter: function () {
		            	return this.value + "%";
		            }
		        }*/
	    	},
	        legend: {
	            enabled: false
	        	//enabled: true
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
	            },
	            tooltip: {
	            	formatter: function () {
	            	var s = "haha";
	            	return s;
	            	}
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


//移动端调用-筛选
function pricetrend(data){
	var str = JSON.stringify(data);
	if(str==null||str==''||str=='{}')
		return ;
	var obj = $.parseJSON(str);
	
	var areaId = obj.areaid;
	var colorId = obj.colorid;
	var formId = obj.formid;
	var goodClassId = obj.pinleiid;
	
	$("input[name=goodClassId]").val('');
	$("input[name=areaId]").val('');
	$("input[name=colorId]").val('');
	$("input[name=formId]").val('');
	
	//参数暂存
	$("input[name=goodClassId]").val(goodClassId);
	$("input[name=areaId]").val(areaId);
	$("input[name=colorId]").val(colorId);
	$("input[name=formId]").val(formId);
	
    //设置时间跨度区间恢复为 一周 WEEK
    $("input[name=dateBetweenType]").val("WEEK");
    $("input[name=channelchanged]").val("1");
    //默认选着一周
    $("ul li:eq(0)").addClass('active');
    //去掉兄弟节点样式
    $("ul li:eq(0)").siblings('li').removeClass('active');
	mui('#pullrefresh').pullRefresh().enablePullupToRefresh(true);
	
	//初始化填充数据列表数据列表
	showdataByPage(goodClassId,1,areaId,colorId,formId);
	
	var namey=[];
    var numo=[];
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceTrendDetail.htm',
	    data:{
	    	"goodClassId":goodClassId,
	    	"areaId":areaId,
	    	"colorId":colorId,
	    	"formId":formId,
	    	"token":$("input[name=token]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	    //页数
	    $("input[name=pagecount]").val(Math.ceil(result.length/20));
	    
	    var isshow = 0;
	       $.each(result, function (index, item) {
	            namey.push(item.dealDate);     
	            numo.push(item.currentAVGPrice);
	            classname = item.goodClassName;
	            isshow = item.isshow;
	        });
	       if(isshow=='1'){
	    	   //展示走势图
	    	   $("#mainAll").css('display','block');//显示  
	    	   $("#mainAllLock").css('display','none');//隐藏   
	       }else{
	    	   $("#mainAll").css('display','none');//隐藏   
	    	   $("#mainAllLock").css('display','block');// 显示
	       }
	        echartInit(namey, classname, numo);
	        //调用移动端函数，通知加载完成
	        window.android.affection();
	    },
	    error: function (errorMsg) {
	    	//调用移动端函数，通知加载完成
	    	window.android.affection();
	    }
	});
}
//实时报价详情列表-分页展示 
function showdataByPage(goodClassId,currentPage,areaId,colorId,formId){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceTrendDetailPage.htm',
	    data:{
	    	"goodClassId":goodClassId,
	    	"dateBetweenType":"WEEK",
	    	"currentPage":currentPage,
	    	"areaId":$("input[name=areaId]").val(),
	    	"colorId":$("input[name=colorId]").val(),
	    	"formId":$("input[name=formId]").val(),
	    	"token":$("input[name=token]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	       //初始化填充数据列表数据列表
	       initTable(result);
	       //列表添加成功-添加事件
	       doclickTwo();
	    },
	    error: function (errorMsg) {
	        //alert("图表数据请求失败!");
	    }
	});
}
/*android未登录*/
function androidnologin() {
	window.android.androidnologin();
	return false;
}; 
/*IOS未登录*/
function iosnologin() {
	var data = {}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iosnologin', str, function() { });
	return false;
};
/*android订阅*/
function androidtodingyue() {
	var data = {};
	var str = JSON.stringify(data);
	window.android.androidtodingyue();
	return false;
}

/*IOS订阅*/
function iostodingyue() {
	var data = {}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iostodingyue', "", function() { });
	return false;
}