
//商品收藏
function collected(goodsid, userk) {
	if ("" == userk) {
		var u = navigator.userAgent; // 获取用户设备
		var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
		if (isIOS) {
			iosnologin(e);
		} else {
			androidnologin();
		}
	} else {
		cllislogin(goodsid, userk);
	}
};

// 收藏操作
function cllislogin(goodsid, userk) {
	if ("" == userk) {
		var u = navigator.userAgent; // 获取用户设备
		var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
		if (isIOS) {
			iosnologin(e);
		} else {
			androidnologin();
		}

		return false;
	}

	$.ajax({
				type : "post",
				url : baseurl + "front/app/goods/updateShare.htm",
				data : {
					"goodId" : goodsid,
					"token" : userk
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.success) {
						if ($(".collcc").find("img").eq(0).attr("src") == "front/resource/img/micon_041.png") {
							$(".collcc").find("img").eq(0).attr("src",
									"front/resource/img/micon_042.png")
						} else {
							$(".collcc").find("img").eq(0).attr("src",
									"front/resource/img/micon_041.png")
						}
					} else {
						layer.open({
							content : data.msg,
							skin : 'msg',
							time : 2
						});
					}
				},
				error : function(e) {
					layer.open({
						content : "系统错误",
						skin : 'msg',
						time : 2
					});
				}
			});
};

// 加入购物车
function addcart(goodsid, userk) {

	if ("" == userk) {
		
		var u = navigator.userAgent; // 获取用户设备
		var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
		if (isIOS) {
			iosnologin(e);
		} else {
			androidnologin();
		}

		return false;
	}

	$.ajax({
		type : "post",
		url : baseurl + "/front/app/goods/addToSelfGoodCar.htm",
		data : {
			"goodsId" : parseInt(goodsid),
			"token" : userk,
			"count" : 1
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				layer.open({
					content : data.msg,
					skin : 'msg',
					time : 2
				});
			} else {
				layer.open({
					content : data.msg,
					skin : 'msg',
					time : 2
				});
			}
		},
		error : function(e) {

		}
	});

}

/** *init* */
var userk ="";
var goodsid="";
var u ="";
$(document).ready(function(){
	 userk = $(".userkey").val();
	 goodsid = $(".goodsid").val();
	 u = navigator.userAgent; // 获取用户设备
})

// 判断当前访问设备
function isandroid() {
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	if (isIOS) {
		return false;
	} else {
		return true;
	}
}
$(function() {
	// APP点击
	var u = navigator.userAgent; // 获取用户设备
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	layer.open({
		content : u,
		skin : 'msg',
		time : 20
	});
	if (isIOS) { // ios app 设备才执行
		/*这段代码是固定的，必须要放到js中*/
         function setupWebViewJavascriptBridge(callback) {
	           if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
	           if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
	           window.WVJBCallbacks = [callback];
	           var WVJBIframe = document.createElement('iframe');
	           WVJBIframe.style.display = 'none';
	           WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
	           document.documentElement.appendChild(WVJBIframe);
	           setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0);
         }
		 
         /*与OC交互的所有JS方法都要放在此处注册，才能调用通过JS调用OC或者让OC调用这里的JS*/
         setupWebViewJavascriptBridge(function(bridge) {

 			/* JS给ObjC提供公开的API，在ObjC端可以手动调用JS的这个API。接收ObjC传过来的参数，且可以回调ObjC */
 			bridge.registerHandler('ioscall', function(e) {
 				// 查看购物车桥接
 				iostocart(e);
 				// 立即购买
 				iostobuy(e);
 				// 预约
 				iostoyuyue(e);
 				// 质检报告
 				iosshowpdf(e);
 				// 未登录
 				iosnologin(e);
 				// 电话
 				iostophone(e);
 				// 试样
 				iosshiyang(e);
 			});
 			
 		});
	} else {
		// 电话
		androidtophone();
		// 查看购物车桥接
		androidtocart("");
		// 立即购买
		androidtobuy("");
		// 预约
		androidtoyuyue("");
		// 试样
		shiyang("");
		// 质检报告
		androidshowpdf();
	}
});

/** *安卓部分* */

// 查看购物车桥接
function androidtocart(e) {
	$(".tocart").click(function() {
		if ("" == userk) {
			androidnologin();
		} else {
			androidlogintocart(userk);
		}
	})
	return false;
}
// 登录去购物车桥接
function androidlogintocart(userk) {
	
	// var data={"token":userk}
	var data = {};
	var str = JSON.stringify(data);
	window.android.androidlogintocart(str);
	return false;
};

// 立即购买
function androidtobuy(e) {
	$(".ezsm-normal-bottombtnB2").click(function() {
		if ("" == userk) {
			androidnologin();
		} else {
			androidlogintobuy(goodsid, userk);
		}
	})
	return false;
}
// 立即购买桥接
function androidlogintobuy(userk) {
	
	// var data={"token":userk,"goodsid":goodsid}
	var data = {
		"goodsid" : goodsid
	};
	var str = JSON.stringify(data);
	window.android.androidlogintobuy(str);
	return false;
};

// 预约
function androidtoyuyue(e) {

	$(".ezsm-normal-bottombtnB4").click(function() {
		if ("" == userk) {
			androidnologin();
		} else {
			androidlogintoyuyue(goodsid, userk);
		}
	})
	return false;
}

// 预约预定桥接
function androidlogintoyuyue(goodsid, userk) {
	
	// var data={"goodsid":goodsid, "token":userk};
	var data = {
		"goodsid" : goodsid
	};
	var str = JSON.stringify(data);
	window.android.androidlogintoyuyue(str);
	return false;
};

// 试样
function shiyang(e) {
	$(".shiyang").click(function() {
		if ("" == userk) {
			androidnologin();
		} else {
			androidshiyang(goodsid, userk);
		}
	})
	return false;
}

// 试样
function androidshiyang(goodsid, userk) {
	
	// var data={"goodsid":goodsid, "token":userk}
	var data = {
		"goodsid" : goodsid
	};
	var str = JSON.stringify(data);
	window.android.androidshiyang(str);
	return false;
};

// 未登录桥接
function androidnologin() {
	layer.open({
		content : "androidnologin1",
		skin : 'msg',
		time : 2
	});
	window.android.androidnologin();
	
	return false;
};

// 电话桥接
function androidtophone() {
	
	$(".tophone").click(function() {
		// var data={"telnum":"400-6666-890","token":userk};
		var data = {
			"telnum" : "400-6666-890"
		}
		var str = JSON.stringify(data);
		window.android.androidtophone(str);
		return false;
	})
}

// 质检报告
function androidshowpdf() {
	$(".colorgreen").click(function() {
		// var data={"goodsid":goodsid,"token":userk}
		var data = {
			"goodsid" : goodsid
		};
		var str = JSON.stringify(data);
		window.android.androidshowpdf(str);
	})
}

/** *IOS部分* */

// 查看购物车桥接
function iostocart(e) {
	$(".tocart").click(function() {
		if ("" == userk) {
			iosnologin(e);
		} else {
			ioslogintocart(userk);
		}
	})
	return false;
}
// 登录去购物车桥接
function ioslogintocart(userk) {
	
	// var data={"token":userk}
	var data = {};
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iostocart', str, function() {
	});
	return false;
};

// 立即购买
function iostobuy(e) {
	$(".ezsm-normal-bottombtnB2").click(function() {
		if ("" == userk) {
			iosnologin(e);
		} else {
			ioslogintobuy(goodsid, userk);
		}
	})
	return false;
}
// 立即购买桥接
function ioslogintobuy(userk) {
	
	// var data={"token":userk,"goodsid":goodsid}
	var data = {
		"goodsid" : goodsid
	}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iostobuy', str, function() {
	});
	return false;
};

// 预约
function iostoyuyue(e) {

	$(".ezsm-normal-bottombtnB4").click(function() {
		if ("" == userk) {
			iosnologin(e);
		} else {
			ioslogintoyuyue(goodsid, userk);
		}
	})
	return false;
}

// 预约预定桥接
function ioslogintoyuyue(goodsid, userk) {
	// var data={"goodsid":goodsid, "token":userk}
	var data = {
		"goodsid" : goodsid
	}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iostoyuyue', str, function() {
	});
	return false;
};

// 试样
function iosshiyang(e) {
	$(".shiyang").click(function() {
		if ("" == userk) {
			iosnologin();
		} else {
			ioslogintoshiyang(goodsid, userk);
		}
	})
	return false;
}

// 试样
function ioslogintoshiyang(goodsid, userk) {
	
	// var data={"goodsid":goodsid, "token":userk}
	var data = {
		"goodsid" : goodsid
	}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iosshiyang', str, function() {
	});
	return false;
};

// 未登录桥接
function iosnologin(e) {
	
	var data = {}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iosnologin', str, function() {
	});
	return false;
};

// 电话桥接
function iostophone(e) {
	
	// var data={"telnum":"400-6666-890", "token":userk}
	var data = {
		"telnum" : "400-6666-890"
	}
	var str = JSON.stringify(data);
	WebViewJavascriptBridge.callHandler('iostophone', str, function() {
	});
	return false;
}

// 质检报告
function iosshowpdf(e) {
	$(".colorgreen").click(function() {
		
		// var data={"goodsid":goodsid, "token":userk}
		var data = {
			"goodsid" : goodsid
		}
		var str = JSON.stringify(data);
		WebViewJavascriptBridge.callHandler('iosshowpdf', str, function() {
		});
	})
}
