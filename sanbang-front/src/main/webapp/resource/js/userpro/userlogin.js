$(document).ready(function(){
	paramvali();
		$("#saveFtPrram").click(function(){
			saveFtPrram();
		});
		$(".sendFtCodeBut").click(function(){
			sendFtCode(this);
		});
		paramvali();
	});


function sendFtCode(oo){
	 $.ajax({
  		type: "POST",
  		url : baseurl+"userPro/sendFtCode.htm",
  		data : {"mobile":$("input[type='text'][name='mobile']").eq(0).val()},
  		dataType:"json",
  		async:false,
  		success :function(data){
  			if(data.code=="000"){
  				//$("div[name='phone']").eq(0).html("");
  				time(oo);
  			}else if(data.code=="888"){
  			//提示错误
  				$("#ftCodepage div").each(function(){
  					if($(this).attr("data-warn")!=null){
  						//$(this).html("");
	   					if($(this).attr("data-warn")=="phone"){
	   						layer.open({content :data["phone"],skin : 'msg',time : 2});
	   					}
  					}
  				});
  			}else{
  				layer.open({content :data.message,skin : 'msg',time : 2});
  			}
  		},
  		error:function(){
  			layer.open({content :"请求错误",skin : 'msg',time : 2});
  		}
      });
 }

var wait=60;
function time(o) { 
	if (wait == 0) { 
		$(o).attr("disabled",true); 
		$(o).html("获取验证码");
		wait = 60; 
	} else { 
		$(o).attr("disabled",true); 
		$(o).html(wait + "s后重新获取"); 
		$(o).css("color","#ec1414");
		wait--;
		setTimeout(function(){time(o)},1000)};
  }


function beforeSave(o){
	 var iphonnum = new RegExp("^[1][3578][0-9]{9}$");
	 if($(this).attr("name")=="mobile"&&$(this).val()==''||
			 ($(this).attr("name")=="mobile"&&!iphonnum.test($(this).val()))){
		 layer.open({content : '>格式有误，请输入正确的手机号码！',skin : 'msg',time : 2});
		 bb=false;
	 }
}

	function saveFtPrram(){
	  var datainfo={};
	  $("#ftCodepage").find("input[type='text']").each(function(){
	     if($(this).attr("name")!=null){
	          datainfo[$(this).attr("name")]=$(this).val();
		  }
		}
	  );
	  $.ajax({
	   		type: "POST",
	   		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	   		url : baseurl+"userPro/checkFtCode.htm",
	   		data : datainfo,
	   		async:false,
	   		dataType:"json",
	   		success :function(data){
	   			if(data.code=="000"){
	   				window.location.href=baseurl+"userPro/userinfo.htm";
	   			}else if(data.code=="888"){
	   			//提示错误
	  				$("#ftCodepage div").each(function(){
	  					if($(this).attr("data-warn")!=null&&data[$(this).attr("data-warn")]!=undefined){
		   					layer.open({content :data[$(this).attr("data-warn")],skin : 'msg',time : 2});
		   					return false;
	  					}
	  				});
						
	   			}else{
	   				layer.open({content :"???????",skin : 'msg',time : 2});	
	   			}
	   		},
	   		error:function(data){
	   			layer.open({content :"请求错误",skin : 'msg',time : 2});	
	   		}
	       });
	}
	
	
	function paramvali(){
		var password = new RegExp("^[0-9a-zA-Z]{6,20}$$");
		/*------验证空------*/
	    $(".rtyu11 input").focus(function(){
				$(this).parent(".regis_adres").removeAttr("style");
				$(this).parents(".regis_name").next(".login_tishi").find("p").remove();
				return false;
				}).blur(function(){
			var valnull=$(this).val();
			if(valnull==""||valnull==null){
				$(this).parent(".regis_adres").css("border","1px solid #ffb32e");
	        	$(this).parents(".regis_name").next(".login_tishi").append("<p>输入内容不能为空！</p>");
				return false;
				}
			});
	    /*--------验证密码是否合法-------*/
			$(".rtyu11 input").focus(function(){
				$(this).parent(".regis_adres").removeAttr("style");
				$(this).parents(".regis_name").next(".login_tishi").find("p").remove();
				return false;
				}).blur(function(){
			var valtxt=$(this).val();
			if(!password.test(valtxt)&&valtxt!=""){
				$(this).parent(".regis_adres").css("border","1px solid #ffb32e");
	        	$(this).parents(".regis_name").next(".login_tishi").append("<p>密码长度6-20位！</p>");
				return false;
	    		}
			});
			/*--------验证密码是否合法和是否一致-------*/
			$(".rtyu2 input").focus(function(){
				$(this).parent(".regis_adres").removeAttr("style");
				$(this).parents(".regis_name").next(".login_tishi").find("p").remove();
				return false;
				}).blur(function(){
				var valtxt=$(this).val();
				var beibj=$(".rtyu11 input").val();
				if(valtxt!=beibj){
					$(this).parent(".regis_adres").css("border","1px solid #ffb32e");
	        		$(this).parents(".regis_name").next(".login_tishi").append("<p>两次密码输入不一致！</p>");
					return false;
				}else{
					if(!password.test(valtxt)&&valtxt!=""){
					$(this).parent(".regis_adres").css("border","1px solid #ffb32e");
	        		$(this).parents(".regis_name").next(".login_tishi").append("<p>密码长度6-20位！</p>");
				return false;
	    		}}
			});
	}