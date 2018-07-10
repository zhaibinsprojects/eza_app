
/** *init* */
$(function() {
	// APP点击
	var u = navigator.userAgent; // 获取用户设备
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	
	if (isIOS) {
		setupWebViewJavascriptBridge(function(bridge) {});
		
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
		iostophone();
		} else {
		// 电话
		androidtophone();
	};
	
});

/** *安卓部分* */

//电话桥接
function androidtophone() {
	
	$(".fottersty").click(function() {
		var data = {
			"telnum" : "400-6666-890"
		}
		var str = JSON.stringify(data);
		window.android.androidtophone(str);
		return false;
	})
}





/** *IOS部分* */
//电话桥接
function iostophone() {
	$(".fottersty").click(function() {
		var data = {
				"telnum" : "400-6666-890"
			}
			var str = JSON.stringify(data);
			WebViewJavascriptBridge.callHandler('iostophone', str, function() {
			});
			return false;
	})
}