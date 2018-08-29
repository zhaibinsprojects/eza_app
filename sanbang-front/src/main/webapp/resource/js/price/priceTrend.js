//筛选条件
$(function(){
			$("#sel_city").click(function(){
				if($(".msg-local").css("display")=="none"){
					$(".msg-local").show();
					var height = window.screen.height;
					$(".msg-local").css({"height":(height-80)+"px","overflow-y":"scroll"});
				}else{
					$(".msg-local").hide();
				}
			});
			$(".msg-local-con li").click(function(){
				$(".msg-local-con li").removeClass("msg-local-con-li-sel");
				$(this).addClass("msg-local-con-li-sel");
				$("#sel_city span").html($(this).html());
				$("#sel_city span").attr("data-value",$(this).attr("data-value"));
				$(".msg-local").hide();
			});
			$("#sel_type").click(function(){
				if($(".msg-type").css("display")=="none"){
					$(".msg-type").show();
					var height = window.screen.height;
					$(".msg-type").css({"height":(height-80)+"px","overflow-y":"scroll"});
				}else{
					$(".msg-type").hide();
				}
			});
			$(".msg-type-con li").click(function(){
				$(".msg-type-con li").removeClass("msg-type-con-li-sel");
				$(this).addClass("msg-type-con-li-sel");
				$("#sel_type span").html($(this).html());
				$("#sel_type span").attr("data-value",$(this).attr("data-value"));
				$(".msg-type").hide();
			});
			//Select select
			$("#sel_select").click(function(){
				if($(".msg-select").css("display")=="none"){
					$(".msg-select").show();
					$(".msg-bg").show();
					var height = window.screen.height;
					console.log(height);
					$(".msg-select").css({"height":height+"px","overflow-y":"scroll"});
				}else{
					$(".msg-select").hide();
					$(".msg-bg").hide();
				}
			});
			$(".msg-select-con li").click(function(){
				$(this).parent().find("li").removeClass("msg-select-con-li-sel");
				$(this).addClass("msg-select-con-li-sel");
				//$("#sel_type span").html($(this).html());
				//$("#sel_type span").attr("data-value",$(this).attr("data-value"));
				//$(".msg-select").hide();
				//$(".msg-bg").hide();
			});
			$(".msg-select-btnA").click(function(){  //筛选 重置按钮
				$(".msg-select-con li").removeClass("msg-select-con-li-sel");
				$(".msg-select-con input").val("");
				$(".msg-select-con select").val("");
			});
			$(".msg-select-btnB").click(function(){  //筛选 确定按钮
				$(".msg-select").hide();
				$(".msg-bg").hide();
			});
		});

//初始化加载
$(document).ready(function(){
	//初始化加载一月内数据
	getdate(goodClassId,"WEEK");
});
//则线图：一周、一月、一季度、一年切换
function changeDates(type){
	getdate(goodClassId,type);
}

function getdate(classid,dateBetweenType){
	var xdata="";
	var name="";
	var data="";
	var plist="";
	$.ajax({
		  type: 'POST',
		  url: baseurl+"/front/app/home/getPriceTrendcyShow.htm",
		  data: {
			  kindId:classid,
			  dateBetweenType:dateBetweenType
		  },
		  success: function(result){
			//进行请求后要数据
		    //x轴显示
		    xdata =result.name;
		    //主体内容
		   	data = result.data.data;
		   	name=result.data.name;
		   	plist=result.plist;
		   	echartInit(xdata, name, data);
		   	initTable(plist);
		  },
		  error : function(errorMsg) {
	            //alert("无该品类实时成交数据!");
	      },
		  dataType : "json"
		});
}

function initTable(plist){
	$("tbody").empty();
	var html = "";	
	//plist.length
	for(var i=0;i<plist.length;i++){
		html = html+"<tr><td><span>"+plist[i].goodClassName+"</span></td>"+
			"<td><span>"+plist[i].goodArea+"</span></td>"+
			"<td><span>"+plist[i].currentAVGPrice+"</span></td>";
		if(plist[i].increaseValue > 0){			
			html=html+"<td><span class='pup'>"+plist[i].sandByOne+"</span></td>";
		}else if(plist[i].increaseValue < 0){
			html=html+"<td><span class='pdown'>"+plist[i].sandByOne+"</span></td>";			
		}else {
			html=html+"<td><span class='ppin'>"+plist[i].sandByOne+"</span></td> ";
		} 
		html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
	}
	$("tbody").append(html);
}

function echartInit(xdata,name,mydata){
	Highcharts.chart('container', {
	        chart: {
	            zoomType: 'x'
	        },
	        title: {
	            text: ''
	        },
	        subtitle: {
	            text: ''
	        },
			colors: ['#4cd4c8'],
	        xAxis: {
	        	 tickInterval: 8,
	        categories: xdata
	    },
			credits: {
	          enabled:false
	},
	       exporting: {
	            enabled:false
	},
	        yAxis: {
	        	allowDecimals:false,//tickPositions: [0, 5, 10, 15,20,25,30],
	        	title: {
	            	text: ''
	        	}
	    	},
	        legend: {
	            enabled: false
	        },
			
	        plotOptions: {
	            area: {
	                fillColor: {
	                    linearGradient: {
	                        x1: 0,
	                        y1: 0,
	                        x2: 0,
	                        y2: 1
	                    },
	                    stops: [
	                        [0, '#4cd4c8'],
	                        [1, Highcharts.Color('#4cd4c8').setOpacity(0).get('rgba')]
	                    ]
	                },
	                marker: {
	                    radius: 1,
						lineWidth: 1,
						fillColor: '#fff',//点填充色
						lineColor: '#4cd4c8',//点边框色
	                },
	                lineWidth: 1,
					
	                states: {
	                    hover: {
	                        lineWidth: 1
	                    }
	                },
	                threshold: null
	            }
	        },
	        series: [{
				type: 'area',
		        name: name,
		        data: mydata,
		        lineWidth:1
	    }]
	    });
};

//上拉加载更多
function addMoreTable(plist){
	var html = "";	
	for(var i=0;i<plist.length;i++){
		html = html+"<tr><td><span>"+plist[i].goodClassName+"</span></td>"+
			"<td><span>"+plist[i].goodArea+"</span></td>"+
			"<td><span>"+plist[i].currentAVGPrice+"</span></td>";
		if(plist[i].increaseValue > 0){			
			html=html+"<td><span class='pup'>"+plist[i].sandByOne+"</span></td>";
		}else if(plist[i].increaseValue < 0){
			html=html+"<td><span class='pdown'>"+plist[i].sandByOne+"</span></td>";			
		}else {
			html=html+"<td><span class='ppin'>"+plist[i].sandByOne+"</span></td> ";
		} 
		html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
	}
	$("tbody").append(html);
}