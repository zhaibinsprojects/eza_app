
function getAddressInint(provinceid,cityid,countyid,provinceclass,cityclass,countyclass){
	   if(provinceid!=null){
		   getNamesList(provinceid,provinceclass);
	   }else{
		   $("#"+provinceclass).css("display","none")
	   }
	   if(cityid!=null){
		   getNamesList(cityid,cityclass);
	   }else{
		   $("#"+cityclass).css("display","none")
	   }
	   if(countyid!=null){
		   getNamesList(countyid,countyclass);
	   }else{
		   $("#"+countyclass).css("display","none")
	   }
	}


function getAddresscheck(provinceid,cityid,countyid,provinceclass,cityclass,countyclass){
	   if(provinceid!=null){
		   getNamesList(0,provinceclass,provinceid);
	   }else{
		   $("#"+provinceclass).css("display","none")
	   }
	   if(cityid!=null){
		   getNamesList(provinceid,cityclass,cityid);
	   }else{
		   $("#"+cityclass).css("display","none")
	   }
	   if(countyid!=null){
		   getNamesList(cityid,countyclass,countyid);
	   }else{
		   $("#"+countyclass).css("display","none")
	   }
	}

function getNamesList(parentid,classname){
	if(parentid==-1){
		return false;
	}
	$.ajax({
		type : "post",
		url : baseurl+"/front/area/getAreaListByParId.htm",
		data : {
			"areaid" : parentid
		},
		dataType : "json",
		async : false,
		success : function(data) {
			var htm='';
			htm+='<option id="-1" selected="selected" value="'+-1+'" >'+"请选择"+'</option>';
			$.each(data.obj,function(index,info){
					if(index==0){
						htm+='<option id="'+info.id+'"  value="'+info.id+'" >'+info.areaName+'</option>';	
					}else{
						htm+='<option id="'+info.id+'" value="'+info.id+'">'+info.areaName+'</option>';
					}
				})
				if(data.obj.length>0){
					$("#"+classname).css("display","inline")
					$("#"+classname).html("");
				    $("#"+classname).html(htm);
				}else{
					$("#"+classname).css("display","none")
				}
				
		   }
	});
}


function getNamesList(parentid,classname,selectid){
	if(parentid==-1){
		return false;
	}
	$.ajax({
		type : "post",
		url : baseurl+"/front/area/getAreaListByParId.htm",
		data : {
			"areaid" : parentid
		},
		dataType : "json",
		async : false,
		success : function(data) {
			var htm='';
			htm+='<option id="-1" selected="selected" value="'+-1+'" >'+"请选择"+'</option>';
			$.each(data.obj,function(index,info){
					if(info.id==selectid){
						htm+='<option id="'+info.id+'"  value="'+info.id+'" >'+info.areaName+'</option>';	
					}else{
						htm+='<option id="'+info.id+'" value="'+info.id+'">'+info.areaName+'</option>';
					}
				})
				if(data.obj.length>0){
					$("#"+classname).css("display","inline")
					$("#"+classname).html("");
				    $("#"+classname).html(htm);
				}else{
					$("#"+classname).css("display","none")
				}
				
		   }
	});
}

$(document).ready(function(){
	$(".addresschoose").change(function(){
		var thisclassname=$(this).attr("ID");
		var parentid=$(this).find("option:selected").eq(0).attr("id");
		var  changeclassname=null;
		
		if(thisclassname.indexOf("province")==0){
			var id=thisclassname.substring(0,thisclassname.indexOf("province"));
			changeclassname=id+"city";
			$("#"+id+"area").css("display","none")
			getNamesList(parentid, changeclassname);
		}else if(thisclassname.indexOf("city")==0){
			var id=thisclassname.substring(0,thisclassname.indexOf("city"));
			changeclassname=id+"area";
			getNamesList(parentid, changeclassname);
		}
		
	})
})



