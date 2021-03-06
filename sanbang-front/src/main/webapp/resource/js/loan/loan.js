
function loansubmit(){
	
	var area_id=$(".areaId").val();
	if(area_id==0){
		layer.open({content : "请选择您所在城市",skin : 'msg',time : 2});
		return false;
	}
	
	$.ajax({
		type : "post",
		url : baseurl+"/front/app/home/loan/loansubmit.htm",
		data : {
			"applyType" : $(".applyType").find("option:selected").attr("class"),
			"mainBusiness" : $(".mainBusiness").find("option:selected").attr("class"),
			"companyName" : $(".companyName").val(),
			"contacts" : $(".contacts").val(),
			"telNum" : $(".telNum").val(),
			"area_id" : area_id,
			"address" : $(".address").val(),
			"loanAmount" : $(".loanAmount").val(),
			"email" : $(".email").val(),
			"token" : $(".userkey").val()
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.success){
				/*$(".msg-bg").css("display","none");
				$(".msg-box").css("display","none");
				layer.open({content : data.msg,skin : 'msg',time : 8});*/
				//提交成功
				window.location.href=baseurl+"/front/app/home/loan/getResultPage.htm?dealFlag=true";
			}else{
				if(userk!=""&&data.errorcode==110002){
					$(".userkey").val("");
					userk="";
					var u = navigator.userAgent; // 获取用户设备
					var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
					if (isIOS) {
						iosnologin();
					} else {
						androidnologin();
					}
				}else{
					/*layer.open({
						content : data.msg,
						skin : 'msg',
						time : 2
					});*/
					window.location.href=baseurl+"/front/app/home/loan/getResultPage.htm?dealFlag=false";
				}
				
			}
		}
	});

}



/** *init* */
var userk ="";
var u ="";
$(document).ready(function(){
	$(".me-alert-box-btn").click(function(){
		//window.location.href=baseurl+"/front/app/home/loan/getResultPage.htm?dealFlag=true";
		//TODO 检查登陆
		loansubmit();
	});
	// APP点击
	var u = navigator.userAgent; // 获取用户设备
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	if (isIOS) {
		setupWebViewJavascriptBridge(function(bridge) {
       		var data = {};
       		var str = JSON.stringify(data);
			 try {
				 WebViewJavascriptBridge.callHandler('isiosLogin', str, function(data) {
					 userk= data;
					 if(userk!=""){
						 $(".userkey").val(data);
					 }else{
						var data = {}
						var str = JSON.stringify(data);
						WebViewJavascriptBridge.callHandler('iosnologin', str, function() {
						});
						}
					});
			} catch (e) {
			}
			
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
		//收藏
		 try {
			 userk= window.android.isAndroidLogin();
			 if(userk!=""){
				 $(".userkey").val(userk);
			 }else{
				 window.android.androidnologin();
			 }
		} catch (e) {
		}
	};
})


// 未登录桥接
function androidnologin() {
	window.android.androidnologin();
	return false;
}; 

//未登录桥接
function iosnologin() {
	
	var data = {}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iosnologin', str, function() {
	});
	return false;
};