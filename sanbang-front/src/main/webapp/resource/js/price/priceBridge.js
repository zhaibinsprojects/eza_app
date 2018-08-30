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
		doclick();
		//判断权限
		try {
			WebViewJavascriptBridge.callHandler('isiosLogin', str, function(data) {
				userk= data;
				if(userk!=""){
					//$(".userkey").val(data);
					$("input[name=token]").val(data);
				}
			});		 
		} catch (e) { }
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
	
	//参数暂存
	$("input[name=goodClassId]").val(goodClassId);
	$("input[name=areaId]").val(areaId);
	$("input[name=colorId]").val(colorId);
	$("input[name=formId]").val(formId);
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
	    //设置时间跨度区间恢复为 一周 WEEK
	    $("input[name=dateBetweenType]").val("WEEK");
	    $("input[name=channelchanged]").val("1");
	    $('ul li:first-child').css('class', 'active');
	    //$("ul li").removeClass("active");
	    //$("WEEK").removeClass("active");
	    $("MONTH").removeClass("active");
	    $("QUARTER").removeClass("active");
	    $("YEAR").removeClass("active");
		mui('#pullrefresh').pullRefresh().enablePullupToRefresh(true);
	    
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
	        //初始化填充数据列表数据列表
	        showdataByPage($("input[name=goodClassId]").val(),"WEEK",1,areaId,colorId,formId);
	    },
	    error: function (errorMsg) {
	    }
	});
	
}
//实时报价详情列表-分页展示 
function showdataByPage(goodClassId,dateBetweenType,currentPage,areaId,colorId,formId){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceTrendDetailPage.htm',
	    data:{
	    	"goodClassId":goodClassId,
	    	"dateBetweenType":dateBetweenType,
	    	"currentPage":currentPage,
	    	"areaId":areaId,
	    	"colorId":colorId,
	    	"formId":formId,
	    	"token":$("input[name=token]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	       //初始化填充数据列表数据列表
	       initTable(result);
	    },
	    error: function (errorMsg) {
	        //alert("图表数据请求失败!");
	    }
	});
}
//初始化填充表格
function initTable(plist){
	$("tbody").empty();
	var html = "";	
	//plist.length
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
				html=html+"<td><span class='colrRed'>"+plist[i].sandByOne+"</span></td> ";
			} 
			$("#container").css('display','block');//隐藏
			$("#containerLock").css('display','none');//显示   
		}else{
			html = html+ "<td><span><i class='lockyuip'></i></span></td>"+
			"<td><span><i class='lockyuip'></i></span></td>";
		}
		html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
	}
	$("tbody").append(html);
}

/*点击列表项*/
function doclick(){
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
};

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



