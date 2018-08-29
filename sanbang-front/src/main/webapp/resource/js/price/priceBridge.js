/** *init* */
$(function() {
	// APP点击
	var u = navigator.userAgent; // 获取用户设备
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	
	if (isIOS) {
		setupWebViewJavascriptBridge(function(bridge) {
			//筛选显示内容
			/*var data = {};
			pricetrend(str);
			//判断权限
			try {
				 WebViewJavascriptBridge.callHandler('isiosLogin', str, function(data) {
					 userk= data;
					 if(userk!=""){
						 $(".userkey").val(data);
						 //iscollcc(goodsid, data);
						 //getCartNum();
					 }
					});
				 
			} catch (e) { }*/
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
		//data = {"areaid":"452022","colorid":"75,16","formid":"15,89","pinleiid":"4"};
		//var str = JSON.stringify(data);
		//pricetrend(str);
		} else {
		//安卓侨联
		//首先获取移动端传回的data,在后要进行判断此品类是否可展示，而后进行筛选信息的展示
		//筛选显示内容
//		var data = {};
//		//var data = {"areaid":"452022","colorid":"75","formid":"15","pinleiid":"4"};
//		var str = JSON.stringify(data);
		//pricetrend(str);
		//判断权限
		 try {
			 userk= window.android.isAndroidLogin();
			 if(userk!=""){
				 $(".userkey").val(userk);
				 //iscollcc(goodsid, userk);
				 //getCartNum();
			 }
		} catch (e) {
		}
	};
});

function pricetrend(data){
	var str = JSON.stringify(data);
	if(str==null||str==''||str=='{}')
		return ;
	//var obj = data.parseJSON(); //由JSON字符串转换为JSON对象
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
	    	   $("#mainAll").css('display','none');//显示  
	    	   $("#mainAllLock").css('display','block');//隐藏   
	       }
	        myChart.hideLoading();
	        myChart.setOption({
	            xAxis: {
	                data: namey
	            },
	            yAxis: {
	                data: numo
	            },
	            series: [{
	            	name:classname,
	                type:'line',
	                smooth: true,
	                data: numo
	            }
	            ]
	        });
	        //初始化填充数据列表数据列表
	        showdataByPage($("input[name=goodClassId]").val(),"WEEK",1,areaId,colorId,formId);
	    },
	    error: function (errorMsg) {
	        myChart.hideLoading();
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
	        myChart.hideLoading();
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
			
		}else{
			html = html+ "<td><span><i class='lockyuip'></i></span></td>"+
			"<td><span><i class='lockyuip'></i></span></td>";
		}
		html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
	}
	$("tbody").append(html);
}