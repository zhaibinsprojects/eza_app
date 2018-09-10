var  userk="";
/** *init* */
$(function() {
	// APP点击
	var u = navigator.userAgent; // 获取用户设备
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	var  docid=$(".docid").val();
	if (isIOS) {
		setupWebViewJavascriptBridge(function(bridge) {
			var data = {};
	   		var str = JSON.stringify(data);
	   		iostoshare();
			iosloadpdf();
			iostodingyue();
			dogive();
			dohouse();
	   		
			 try {
				 WebViewJavascriptBridge.callHandler('isiosLogin', str, function(data) {
					 userk= data;
					 if(userk!=""){
						 $(".userkey").val(data);
					 }
					 commondo(docid, data);
					});
				
				 
			} catch (e) {
				 commondo(docid, userk);
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
				 commondo(docid, userk);
			} catch (e) {
				 commondo(docid, userk);
			}
		// 电话
		androidtoshare();
		androidloadpdf();
		androidtodingyue();
		dogive();
		dohouse();
	};
	
});

/** *安卓部分* */
//未登录桥接
function androidnologin() {
	window.android.androidnologin();
	return false;
}; 

//分享桥接
function androidtoshare() {
	
	$(".astyuiop").click(function() {
		var data = {
			"docid" : $("docid").val()
		}
		var str = JSON.stringify(data);
		window.android.androidtoshare(str);
		return false;
	})
}
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

//下载pdf
function androidloadpdf() {
	
	$(".download").click(function() {
		if(pdfname==""||pdfname==null){
			return false;
		}

		var res=cppdf(pdfid);
		if(!res){
			layer.open({
				content : "查看失败",
				skin : 'msg',
				time : 2
			});
			return false;
		}
		
		var data = {
			"pdfurl" : "https://m.ezaisheng.com/upload/h5/ezsSubstance/"+pdfid+".pdf"
		}
		var str = JSON.stringify(data);
		/*layer.open({
			content : "回调开始",
			skin : 'msg',
			time : 2
		});*/
		window.android.androidloadpdf(str);
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
function iostoshare() {
	$(".astyuiop").click(function() {
		var data = {
				"sharurl":baseurl + "/front/app/menuhq/hangqShow.htm?id"+pdfid,
				"sharimg":baseurl +"/front/resource/img/shareimg.png",
				"title":title,
				"description":description.length>20?description:description.substring(0,20)
			}
			var str = JSON.stringify(data);
			WebViewJavascriptBridge.callHandler('iostoshare', str, function() {
			});
			return false;
	})
}

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

//下载pdf
function iosloadpdf() {
	
	$(".download").click(function() {
		if(pdfname==""||pdfname==null){
			return false;
		}
		$("#fileName").val(pdfname);
		$("#theForm2").submit();
		return false;
	})
}


//查看文件
function  cppdf(id){
	if(pdfname==""||pdfname==null){
		return false;
	}
	var res=false;
	$.ajax({
		type : "post",
		url : baseurl + "/front/app/home/getFileForHangq.htm",
		data : {
			"id" : parseInt(id),
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				res= true;
			} 
		},
		error : function(e) {
		}
	});
	return res;
}

/**
 * 点赞操作
 */
function dogive(){
	
	$(".dz_uiop").click(function(){
		
		if ("" == $(".userkey").val()) {
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
			url : baseurl + "front/app/menuhq/doGiveOrHouse.htm",
			data : {
				"token" : $(".userkey").val(),
				"docid":$(".docid").val(),
				 "reqtype":"give"
			},
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.success) {
					$(".dz_uiop").toggleClass("active");
					if($(".dz_uiop").hasClass("active")){
						$(".adzuioerror").hide();
						$(".dzuiosuccess").show().delay (1500).fadeOut ();;
					}else{
						$(".dzuiosuccess").hide();
						$(".adzuioerror").show().delay (1500).fadeOut ();;
					}
				
				}else{
					layer.open({
						content : data.msg,
						skin : 'msg',
						time : 2
					});
				}
			},
			error : function(e) {
				layer.open({
					content : "系统错误!",
					skin : 'msg',
					time : 2
				});
			}
			});
	
	});
};

/**
 * 收藏操作
 */
function dohouse(){
	$(".xx_yuiop").click(function(){
		
		if ("" == $(".userkey").val()) {
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
			url : baseurl + "front/app/menuhq/doGiveOrHouse.htm",
			data : {
				"token" : $(".userkey").val(),
				"docid":$(".docid").val(),
				 "reqtype":"house"
			},
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.success) {
					$(".xx_yuiop").toggleClass("active");
					if($(".xx_yuiop").hasClass("active")){
						$(".artyuioerror").hide();
						$(".artyuiosuccess").show().delay (1500).fadeOut ();;
					}else{
					$(".artyuiosuccess").hide();
					$(".artyuioerror").show().delay (1500).fadeOut ();;
					}
				}else{
					layer.open({
						content : data.msg,
						skin : 'msg',
						time : 2
					});
				}
			},
			error : function(e) {
				layer.open({
					content : "系统错误!",
					skin : 'msg',
					time : 2
				});
			}
			});
	
	});

	
};





/****公共部分****/
/**
 * 初始化显示
 * @param docid
 * @param data
 * @returns
 */
function commondo(docid, data){
	$.ajax({
		type : "post",
		url : baseurl + "front/app/menuhq/getDocStatusForUser.htm",
		data : {
			"token" : data,
			"docid":docid
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				var give=data.obj.give;
				var house=data.obj.house;
				var ispass=data.obj.ispass;
				if(house==1){$(".xx_yuiop").toggleClass("active");}
				if(give==1){$(".dz_uiop").toggleClass("active");}
				if(ispass){
					$(".contenttrue").show();
					$(".contenttfalse").hide();
					$(".djdigxuz").hide();
				}else{
					$(".contenttrue").hide();
					$(".contenttfalse").show();
					$(".vuetpso p").addClass("fontwithgry");
					$(".download").hide();
					
				}
			}else{
				$(".contenttrue").hide();
				$(".contenttfalse").show();
				$(".vuetpso p").addClass("fontwithgry");
				$(".download").hide();
				if(data.errorcode==110002){
					/*$(".userkey").val("");
					userk="";
					var u = navigator.userAgent; // 获取用户设备
					var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
					if (isIOS) {
						iosnologin();
					} else {
						androidnologin();
					}*/
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
				content : "系统错误!",
				skin : 'msg',
				time : 2
			});
		}
		});

}
