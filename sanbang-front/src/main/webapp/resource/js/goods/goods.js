
/** *init* */
var userk ="";
var goodsid="";
var u ="";

$(function() {
	// APP点击
	var u = navigator.userAgent; // 获取用户设备
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	
	if (isIOS) {
		 goodsid = $(".goodsid").val();
		setupWebViewJavascriptBridge(function(bridge) {
       		var data = {};
       		var str = JSON.stringify(data);
			// 查看购物车桥接
			iostocart();
			// 立即购买
			iostobuy();
			// 预约
			iostoyuyue();
			// 质检报告
			iosshowpdf();
			/*// 未登录 初始化不调用
			iosnologin();*/
			// 电话
			iostophone();
			// 试样
			iosshiyang();
			 try {
				 WebViewJavascriptBridge.callHandler('isiosLogin', str, function(data) {
					 userk= data;
					 if(userk!=""){
						 $(".userkey").val(data);
						 iscollcc(goodsid, data);
						 getCartNum();
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
		goodsid = $(".goodsid").val();
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
		
		//收藏
		 try {
			 userk= window.android.isAndroidLogin();
			 if(userk!=""){
				 $(".userkey").val(userk);
				 iscollcc(goodsid, userk);
				 getCartNum();
			 }
		} catch (e) {
		}
		
	
	};
	
	

	// 加入购物车
	$(".ezsm-normal-bottombtnB5").click(function(){
		userk = $(".userkey").val();
		goodsid = $(".goodsid").val();
		if ("" == userk) {
			var u = navigator.userAgent; // 获取用户设备
			var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
			if (isIOS) {
				iosnologin();
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
					cartsuccess();
					var u = navigator.userAgent; // 获取用户设备
					var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
					if (isIOS) {
						layer.open({
							content : data.msg,
							skin : 'msg',
							time : 2
						});
					} 
					getCartNum();
				} else {
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
						layer.open({
							content : data.msg,
							skin : 'msg',
							time : 2
						});
					}
					
				}
			},
			error : function(e) {

			}
		});
	});
	
	//商品收藏
	$(".collcc").click(function(){
		userk = $(".userkey").val();
		goodsid = $(".goodsid").val();
		if ("" == userk) {
			var u = navigator.userAgent; // 获取用户设备
			var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
			if (isIOS) {
				iosnologin();
			} else {
				androidnologin();
			}
		} else {
			cllislogin(goodsid, userk);
		}

	});

});

/** *安卓部分* */

// 查看购物车桥接
function androidtocart(e) {
	$(".tocart").click(function() {
		if ("" == $(".userkey").val()) {
			androidnologin();
		} else {
			androidlogintocart($(".userkey").val());
		}
	})
	return false;
}
// 登录去购物车桥接
function androidlogintocart(userk) {
	var data = {};
	var str = JSON.stringify(data);
	window.android.androidlogintocart(str);
	return false;
};

// 立即购买
function androidtobuy(e) {
	$(".ezsm-normal-bottombtnB2").click(function() {
		if ("" == $(".userkey").val()) {
			androidnologin();
		} else {
			androidlogintobuy(goodsid, $(".userkey").val());
		}
	})
	return false;
}
// 立即购买桥接
function androidlogintobuy(userk) {
	var data = {
		"goodsid" : goodsid,
		"isauth" : $(".toauth").val()
	};
	var str = JSON.stringify(data);
	window.android.androidlogintobuy(str);
	return false;
};

// 预约
function androidtoyuyue(e) {

	$(".ezsm-normal-bottombtnB4").click(function() {
		if ("" == $(".userkey").val()) {
			androidnologin();
		} else {
			androidlogintoyuyue(goodsid, $(".userkey").val());
		}
	})
	return false;
}

// 预约预定桥接
function androidlogintoyuyue(goodsid, userk) {
	var data = {"goodsid" : goodsid,
			"goodsName":$(".goodsName").val()/*,
			"isauth" : $(".toauth").val()*/
			};
	var str = JSON.stringify(data);
	window.android.androidlogintoyuyue(str);
	return false;
	};

// 试样
function shiyang(e) {
	$(".shiyang").click(function() {
		if ("" == $(".userkey").val()) {
			androidnologin();
		} else {
			androidshiyang(goodsid, $(".userkey").val());
		}
	})
	return false;
}

// 试样
function androidshiyang(goodsid, userk) {
	var data = {"goodsid" : goodsid};
	var str = JSON.stringify(data);
	window.android.androidshiyang(str);
	return false;
};

// 未登录桥接
function androidnologin() {
	window.android.androidnologin();
	return false;
};   

// 电话桥接
function androidtophone() {
	
	$(".tophone").click(function() {
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
		var data = {"goodsid" : goodsid};
		var str = JSON.stringify(data);
		window.android.androidshowpdf(str);
	})
}


/** *IOS部分* */
	// 查看购物车桥接
	function iostocart() {
		$(".tocart").click(function() {
			if ("" == $(".userkey").val()) {
				iosnologin();
			} else {
				ioslogintocart($(".userkey").val());
			}
		})
		return false;
	}
	// 登录去购物车桥接
	function ioslogintocart(userk) {
		var data = {};
		var str = JSON.stringify(data);
		WebViewJavascriptBridge.callHandler('iostocart', str, function() {
		});
		return false;
	};

	// 立即购买
	function iostobuy() {
		$(".ezsm-normal-bottombtnB2").click(function() {
			if ("" == $(".userkey").val()) {
				iosnologin();
			} else {
				ioslogintobuy(goodsid);
			}
		})
		return false;
	}
	// 立即购买桥接
	function ioslogintobuy(userk) {
		var data = {
				"goodsid" : goodsid,
				"isauth" : $(".toauth").val()
			}
		var str = JSON.stringify(data);
		WebViewJavascriptBridge.callHandler('iostobuy', str, function() {
		});
		return false;
};

	// 预约
	function iostoyuyue() {

		$(".ezsm-normal-bottombtnB4").click(function() {
			if ("" == $(".userkey").val()) {
				iosnologin();
			} else {
				ioslogintoyuyue(goodsid, $(".userkey").val());
			}
		})
		return false;
	}

	// 预约预定桥接
	function ioslogintoyuyue(goodsid, userk) {
		var data = {
				"goodsid" : goodsid,
				"goodsName":$(".goodsName").val()/*,
				"isauth" : $(".toauth").val()*/
				};
				var str = JSON.stringify(data);
				WebViewJavascriptBridge.callHandler('iostoyuyue', str, function() {
				});
				return false;
};

	// 试样
	function iosshiyang() {
		$(".shiyang").click(function() {
			if ("" == $(".userkey").val()) {
				iosnologin();
			} else {
				ioslogintoshiyang(goodsid, $(".userkey").val());
			}
		})
		return false;
	}

	// 试样
	function ioslogintoshiyang(goodsid, userk) {
		var data = {"goodsid" : goodsid}
		var str = JSON.stringify(data);
		WebViewJavascriptBridge.callHandler('iosshiyang', str, function() {
		});
		return false;
	};

	// 未登录桥接
	function iosnologin() {
		
		var data = {}
		var str = JSON.stringify(data);
		WebViewJavascriptBridge.callHandler('iosnologin', str, function() {
		});
		return false;
	};

	// 电话桥接
	function iostophone() {
		$(".tophone").click(function() {
			var data = {
					"telnum" : "400-6666-890"
				}
				var str = JSON.stringify(data);
				WebViewJavascriptBridge.callHandler('iostophone', str, function() {
				});
				return false;
		})
	}

	// 质检报告
	function iosshowpdf() {
		$(".colorgreen").click(function() {
			var data = {
				"goodsid" : goodsid
			}
			var str = JSON.stringify(data);
			WebViewJavascriptBridge.callHandler('iosshowpdf', str, function() {
			});
		})
	} 
	
	/**
	 * 添加购物车成功
	 */
	function cartsuccess(){
		var u = navigator.userAgent; // 获取用户设备
		var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
		if (isIOS) {
			var  str="";
			WebViewJavascriptBridge.callHandler('ioscart', str, function() {
			});
		}else{
			window.android.androidcart();	
		}
	}
	
	
	//显示收藏状态
	function iscollcc(goodsid,userk){
		$.ajax({
			type : "post",
			url : baseurl + "front/app/goods/iscollented.htm",
			data : {
				"id" : goodsid,
				"token" : userk
			},
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.success) {
					if(data.obj==0){
						$(".collcc").find("img").eq(0).attr("src",
								"front/resource/img/micon_041.png")
					}else{
						$(".collcc").find("img").eq(0).attr("src",
								"front/resource/img/micon_042.png")
					}
				} else {
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
						layer.open({
							content : data.msg,
							skin : 'msg',
							time : 2
						});
					}
					
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
	
	// 收藏操作
	function cllislogin(goodsid, userk) {
		if ("" == userk) {
			var u = navigator.userAgent; // 获取用户设备
			var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
			if (isIOS) {
				iosnologin();
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
							layer.open({
								content : data.msg,
								skin : 'msg',
								time : 2
							});
						}
						
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
	
	
	/**
	 * 购物车数量
	 * @returns {Boolean}
	 */
	function getCartNum(){
		$.ajax({
				type : "post",
				url : baseurl + "front/app/goods/getCartNum.htm",
				data : {
					"token" : $(".userkey").val()
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.success) {
						$(".cartnum").html("");
						$(".cartnum").html(data.obj.count);
						if(data.obj.count>0){
							$(".cartnum").show();
						}
						$(".toauth").val(data.obj.toauth);
					} else {
						$(".cartnum").hide();
					}
				},
				error : function(e) {
					$(".cartnum").hide();
				}
				});
	
	}
	
	var londing="";
	$(function(){
		if(status==1){
			londing=layer.open({
			    type: 2
			    ,time: 1
			    ,content: '小e努力加载中'
			  });
		}else{
			layer.open({
			    type: 2
			    ,content: '商品下架啦,去商城看看吧！'
			    ,shadeClose: false
			  });
		}
		 
	})
	
	
	
