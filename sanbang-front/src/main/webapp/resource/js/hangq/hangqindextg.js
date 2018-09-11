var  userk="";
/** *init* */
$(function() {
	// APP点击
	var u = navigator.userAgent; // 获取用户设备
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	if (isIOS) {
		setupWebViewJavascriptBridge(function(bridge) {
			var data = {};
	   		var str = JSON.stringify(data);
			iostodingyue();
			iostophone();
			 try {
				 WebViewJavascriptBridge.callHandler('isiosLogin', str, function(data) {
					 userk= data;
					 if(userk!=""){
						 $(".userkey").val(data);
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
				 userk= window.android.isAndroidLogin();//"appq36LLF8RH7y6BgvrF2C2a30Lry3lS75W";//
				 if(userk!=""){
					 $(".userkey").val(userk);
				 }
				
			} catch (e) {
				
			}
		// 电话
		androidtodingyue();
		androidtophone();
	};
	
});

/** *安卓部分* */
//未登录桥接
function androidnologin() {
	window.android.androidnologin();
	return false;
}; 


//订阅
function androidtodingyue() {
	
	$(".djdigxuz").click(function() {
		userk=$(".userkey").val();
		if(userk==""){
			var u = navigator.userAgent; // 获取用户设备
			var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
			if (isIOS) {
				iosnologin();
			} else {
				androidnologin();
			}
			return false;
		}
		
		
		var data = {
		}
		var str = JSON.stringify(data);
		//订阅弃用 改为 开通试用 2018910
		//window.android.androidtodingyue();
		window.android.androidtoshiyong();
		return false;
	})
}


function androidtophone() {
	
	$("#fottersty").click(function() {
		var data = {
				"telnum" : "15910613890"
		}
		var str = JSON.stringify(data);
		window.android.androidtophone(str);
		return false;
	})
	
	$("#fottersty1").click(function() {
		var data = {
				"telnum" : "15110153537"
		}
		var str = JSON.stringify(data);
		window.android.androidtophone(str);
		return false;
	})
}

/** *IOS部分* */
//未登录桥接
function iosnologin() {
	
	var data = {}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iosnologin', str, function() {
	});
	return false;
};


/**
 * 订阅
 * @returns
 */
function iostodingyue() {
	$(".djdigxuz").click(function() {
		userk=$(".userkey").val();
		if(userk==""){
			var u = navigator.userAgent; // 获取用户设备
			var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
			if (isIOS) {
				iosnologin();
			} else {
				androidnologin();
			}
			return false;
		}
		var data = {
				
			}
			/*var str = JSON.stringify(data);
			WebViewJavascriptBridge.callHandler('iostodingyue', data, function() {
			});*/
		var str = JSON.stringify(data);
		WebViewJavascriptBridge.callHandler('iostoshiyong', data, function() {
		});
			return false;
	})
}

//电话桥接
function iostophone() {
	$("#fottersty").click(function() {
		var data = {
				"telnum" : "15910613890"
			}
			var str = JSON.stringify(data);
			WebViewJavascriptBridge.callHandler('iostophone', str, function() {
			});
			return false;
	});
	
	$("#fottersty1").click(function() {
		var data = {
				"telnum" : "15110153537"
			}
			var str = JSON.stringify(data);
			WebViewJavascriptBridge.callHandler('iostophone', str, function() {
			});
			return false;
	});
}



