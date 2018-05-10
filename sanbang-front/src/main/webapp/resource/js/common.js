//通用校验 input
	//<input data-value="required/norequired"    class="registdata" 
	//title="手机号" name="phone" type="text" placeholder="请输入手机号" />
	function generalcheck(parclassname){
		var  state=true;
		var needrequireds=$("."+parclassname).find("input[data-value=required]");
		$.each(needrequireds,function(index,req){
			var title=$(req).attr("title");
			var value=$(req).val();
			if(""==value){
				layer.open({content :title+"不能为空",skin : 'msg',time : 2});
				state=false;
				return false;
			}
		});
		
		return state;
	};
	
	
	
	//post请求
	function  ajaxpost(url,data){
		$.ajax({
			url:baseurl+url,
			type:"post",
			data:data,
			dataType:"json",
			async:false,
			success:function(dat){
				return data;
			},
			error:function(e){
				return ""
			}
		})
	};