$(document).ready(function(){
	   paramvali();
	   $("#sendCodeBut").click(function(){
			sendCodeValue(this);
		});
		initimgrand();
		$("input[type='text'][name='code']").blur(function(){
			codeblur($(this));
	        });
		
		//无密登陆 检查手机号码
		$("#loginParamnonepd").find("input[type='text'][name='userName']").blur(function(){
			mobileblur($(this));
        });
	});
function mobileblur(oo){
	var iphonnum = new RegExp("^[1][3578][0-9]{9}$");
	var valtxt=oo.val();
	if(!iphonnum.test(valtxt)&&valtxt!=""){
		$("#loginParamnonepd").find("div[name='usernametip']").eq(0).html("<p>格式有误，请输入正确的手机号码！</p>");
		return false;
		}else{
			return true;
		}
}

function codeblur(codeoo){
	if(bBtn){
		return false;
	}
	var oo=codeoo;
	if(codeoo.val()!=null&&codeoo.val()!=""){
    	$.ajax({
	   		type: "POST",
	   		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	   		url : baseurl+"userPro/loginRandImgVali.do",
	   		data : {code:codeoo.parent().parent().parent().find("input[type='text'][name='code']").eq(0).val()},
	   		async:false,
	   		success :function(data){
	   			if(data.code=="000"){
	   				randcodestatus=true;
	   				oo.parent().parent().find("div[name='randimgtip']").eq(0).html('<img src="resource/images/login/read_redia.png">');
	   			}else if(data.code=="888"){
//	   				flushcodeimg();
	   				if(hxtp){
	   					oo.parent().parent().find("div[name='randimgtip']").eq(0).html('<img src="resource/images/login/check_error.gif"  title="'+data["randimgtip"]+'">');
	   				}
	   			}else if(data.code=="999"){
	   				flushcodeimg();
	   				alert(data.message);
	   			}
	   		},
	   		error:function(data){
	   			alert(data.message);
	   		}
	       });
	}
}

    //js函数
function sendCodeValue(o){
	if(mobileblur($("#loginParamnonepd").find("input[name='userName']").eq(0))){
		var randcode=$("#"+dividflag).find("input[type='text']").eq(1).val();
		if(randcode==""){
			$("#"+dividflag).find("input[type='text']").eq(1).parent().parent().next("div").html("<p>输入内容不能为空！</p>");
			return ;
		}
		
		if(!randcodestatus){
			codeblur($("#loginParamnonepd").find("input[name='code']").eq(0));
			if(!randcodestatus){
				$("#"+dividflag).find("input[type='text']").eq(1).parent().parent().next("div").html("<p>请重新输入验证码</p>");
				return ;
			}
		}
	  var datainfo={};
	  datainfo["mobile"]=$("#loginParamnonepd").find("input[name='userName']").eq(0).val();
	  datainfo["imgcode"]=$("#loginParamnonepd").find("input[name='code']").eq(0).val();
	  datainfo["flag"]=1;
	  $.ajax({
	   		type: "POST",
	   		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	   		url : baseurl+"userPro/sendCode.do",
	   		data : datainfo,
	   		async:false,
	   		success :function(data){
	   			if(data.code=="000"){
//		   				$("div[name='mobile']").eq(0).html('<img src="resource/images/login/read_redia.png">');
	   				time(o);
	   			}else if(data.code=="888"){
	   				if(data.phone=="验证码已发送多次，请查收短信"){
	   					alert("今日获取动态登陆码的次数已用完！明日可继续使用");
	   				}else{
	   					alert(data.phone);
	   				}
//	   				$("#loginParamnonepd").find("div[name='usernametip']").eq(0).html('<p>'+data.phone+'</p>');
	   			}else if(data.code=="999"){
	   				alert(data.message);
	   			}else{
	   				alert(data.imgcode);
	   			}
	   		},
	   		error:function(data){
	   			alert(data.message);
	   		}
	       });
	}else{
		$("#loginParamnonepd").find("div[name='usernametip']").eq(0).html('<p>格式有误，请输入正确的手机号码！</p>');
	}
}
function time(o) { 
	if (wait == 0) { 
		o.removeAttribute("disabled"); 
		o.value="获取验证码";
		$(".tyuio").removeAttr("style"); 
		wait = 60; 
	} else { 
		o.setAttribute("disabled", true);
		$(".tyuio").css("color","#999");
		o.value=wait + "s后重新获取"; 
		wait--;
		setTimeout(function(){time(o)},1000)};
  }
	function saveLoginPrram(){
	  var datainfo={};
	  $("#"+dividflag).find("input[type='text']").each(function(){
	     if($(this).attr("name")!=null){
	           datainfo[$(this).attr("name")]=$(this).val();
		  }
		});
	  if(dividflag=="loginParam"){
		  datainfo[$("#"+dividflag).find("input[type='password']").eq(0).attr("name")]=hex_md5(hex_md5($("#"+dividflag).find("input[type='password']").eq(0).val()));
	  }else{
		  datainfo["flag"]="1";
	  }
	  $.ajax({
	   		type: "POST",
	   		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	   		url : baseurl+"userPro/userLogin.do",
	   		data : datainfo,
	   		async:false,
	   		success :loginCallback,
	   		error:loginError
	       });
	}
	function loginCallback(data){
		if(data.code=="000"){
			hxtp=false;
			$("#"+dividflag).find("div[name='randimgtip']").eq(0).html('<img src="resource/images/login/read_redia.png">');
			if(data.tourl!=null&&data.tourl!=''){
					window.location.href=baseurl+data.tourl;  
			}else if(data.tostaticurl!=null&&data.tostaticurl!=''){
					window.location.href=serurl+data.tostaticurl;
			}else{
				//跳转 到首页
				if(data.userType!=2){
					window.location.href=baseurl+"mallOrder/toframe.do?ordertype=buyer";
				}else{
					window.location.href=baseurl+"mallOrder/toframe.do?ordertype=seller";
				}
			}
		}else if(data.code=="111"){
			hxtp=true;
			window.location.href=baseurl+"mallOrder/toframe.do?ordertype=buyer&toget=linkman";
		}else if(data.code="888"){
			flushcodeimg();
			hxtp=true;
			//提示错误
			$("#"+dividflag).find("div").each(function(){
				if($(this).attr("data-warn")!=null){
					$(this).html("");
					if(data[$(this).attr("data-warn")]!=null){
						if($(this).attr("data-warn")=="randimgtip"){
							$("#"+dividflag).find("div[name='randimgtip']").eq(0).html('<img src="resource/images/login/check_error.gif" >');
						}else{
							 $(this).append("<p>"+data[$(this).attr("data-warn")]+"</p>");
						}
					}
				}
			});
		}else{
			alert("???????");
		}
	}
	function loginError(data){
		alert(data.message);
	}
	function GetRandomNum(Min,Max)
	{   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));
	}   
	function flushcodeimg(){
		var num = GetRandomNum(1,10)+"";  
		for(var i=0;i<10;i++){
			num+=GetRandomNum(1,10);
		};
		$("#"+dividflag).find("img").eq(0).attr("src","randImg/randCode.do"+"?"+num);
	}
    function paramvali(){
    	var namereg =new RegExp("^[0-9a-zA-Z]{3,20}$");
    	var password = new RegExp("^.{6,20}$$");
    	/*------通用验证空------*/
        $(".add_lognyz input,.valida_all .login_valida input,.login_adres_dynamic input").focus(function(){
    			$(this).parent(".add_lognyz").removeAttr("style");
    			$(this).parents(".login_valida").removeAttr("style");
    			$(this).parents(".add_lognyz").next(".login_tishi").find("p").remove();
    			 return false;
    			}).blur(function(){
    		var valnull=$(this).val();
    		if(valnull==""||valnull==null){
    			$(this).parent(".add_lognyz").css("border","1px solid #ffb32e");
    			$(this).parent(".login_valida").css("border","1px solid #ffb32e");
    			$(this).parents(".add_lognyz").next(".login_tishi").append("<p>输入内容不能为空！</p>");
    			return false;
    			}
    		});
    		/*-------验证用户名是否匹配------*/
    	$(".login_num input").focus(function(){
    			$(this).parent("div").removeAttr("style");
    			$(this).parent("div").next(".login_tishi").find("p").remove();
    			return false;
    			}).blur(function(){
    		var valtxt=$(this).val();
    		if(!namereg.test(valtxt)&&valtxt!=""){
    			$(this).parent("div").css("border","1px solid #ffb32e");
            	$(this).parent("div").next(".login_tishi").append("<p>请输入20位以内数字或字母！</p>");
    			return false;
        		}
    		});
    		/*--------验证密码是否合法-------*/
    		$(".login_adres input").focus(function(){
    			$(this).parent("div").removeAttr("style");
    			$(this).parent("div").next(".login_tishi").find("p").remove();
    			return false;
    			}).blur(function(){
    		var valtxt=$(this).val();
    		if(!password.test(valtxt)&&valtxt!=""){
    			$(this).parent("div").css("border","1px solid #ffb32e");
            	$(this).parent("div").next(".login_tishi").append("<p>密码长度6-20位！</p>");
    			return false;
        		}
    		});
    }
    
    function initimgrand(){
    	flushcodeimg();
    	$("#"+dividflag).find("input[type='button']").eq(0).click(function(){
			saveLoginPrram();
		});
    	$("#"+dividflag).find("img").eq(0).click(function(){
    		flushcodeimg();
    	});
    	dividflag="loginParamnonepd";
//    	flushcodeimg();
    	$("#"+dividflag).find("input[type='button']").eq(1).click(function(){
			saveLoginPrram();
		});
    	$("#"+dividflag).find("img").eq(0).click(function(){
    		flushcodeimg();
    	});
    	dividflag="loginParam";
    }
    
    function switchpiece(flag){
    	if(flag=="0"){
    		dividflag="loginParam";
    	}else{
    		dividflag="loginParamnonepd";
    	}
    	flushcodeimg();
    	$("#"+dividflag).siblings("div").hide();
    	$("#"+dividflag).show();
    }