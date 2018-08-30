var namey = [];
var numo = [];
var classname = "";
$.ajax({
    type: 'post',
    url: 'front/app/hangq/priceTrendDetail.htm',
    data:{
    	"goodClassId":$("input[name=goodClassId]").val(),
    	"token":$("input[name=token]").val()
    },
    dataType: "json",
    success: function (result) {
    	//页数
    	$("input[name=pagecount]").val(Math.ceil(result.length/20));
    	var isshow = 0;
       $.each(result, function (index, item) {
            namey.push(item.dealDate);     
            numo.push(item.currentAVGPrice);
            classname = item.goodClassName;
            isshow = item.isshow;
        });
       if(isshow=='1'){
    	   //展示走势图
    	   $("#container").css('display','block');//显示  
    	   $("#containerLock").css('display','none');//隐藏   
       }else{
    	   $("#container").css('display','none');//隐藏 
    	   $("#containerLock").css('display','block');//显示   
       }
       	//heighchart
       	echartInit(namey, classname, numo);   
       	showdataByPage($("input[name=goodClassId]").val(),"WEEK",1);
    	
    },
    error: function (errorMsg) {
        //alert("图表数据请求失败!");
    }

});

//初始化填充表格
function initTable(plist){
	$("tbody").empty();
	var html = "";	
	for(var i=0;i<plist.length;i++){
		html = html+"<tr><td><span>"+plist[i].goodClassName+"</span></td>"+
			"<td><span>"+plist[i].goodArea+"</span></td>";
		
		if(plist[i].isshow=='1'){
			html = html+"<td><span class='colrRed'>￥"+plist[i].currentAVGPrice+"</span></td>";
			
			if(plist[i].currentAVGPrice > plist[i].preAVGPrice){			
				html=html+"<td><span class='colrRed'>"+plist[i].sandByOne+"</span></td>";
			}else if(plist[i].currentAVGPrice < plist[i].preAVGPrice){
				html=html+"<td><span class='colGreen'>"+plist[i].sandByOne+"</span></td>";			
			}else {
				html=html+"<td><span>"+plist[i].sandByOne+"</span></td> ";
			} 
			$("#container").css('display','block');//隐藏
			$("#containerLock").css('display','none');//显示   
		}else{
			html = html+ "<td><span><i class='lockyuip'></i></span></td>"+
			"<td><span><i class='lockyuip'></i></span></td>";
		}
		html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
	}
	$("tbody").append(html);
	doclick();
}

//选择显示周期：一周、一月、三月、一年
$(document).ready(function(){
	$(".section_roptab ul li").click(function(){
		$(this).addClass("active");
		$(this).siblings("li").removeClass("active")
		$("input[name=dateBetweenType]").val($(this).attr("name"));
		$("input[name=channelchanged]").val("1");
		//获取选定月份
		//加载则线图数据
		showdatas(goodClassId,$(this).attr("name"));
		//加载表格数据列表，默认加载首页
		showdataByPage(goodClassId,$(this).attr("name"),1);
		mui('#pullrefresh').pullRefresh().enablePullupToRefresh(true);
	});
})
function showdatas(goodClassId,dateBetweenType){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceTrendDetail.htm',
	    data:{
	    	"goodClassId":goodClassId,
	    	"dateBetweenType":dateBetweenType,
	    	"token":$("input[name=token]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	    //清空数组
	    namey=[];
	    numo=[];
	    //页数
	    $("input[name=pagecount]").val(Math.ceil(result.length/20));
	       $.each(result, function (index, item) {
	            namey.push(item.dealDate);     
	            numo.push(item.currentAVGPrice);
	            classname = item.goodClassName;
	        });
	     //heighchart
	     echartInit(namey, classname, numo);  
	    },
	    error: function (errorMsg) {
	    }
	});
}
//实时报价详情列表-分页展示 
function showdataByPage(goodClassId,dateBetweenType,currentPage){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceTrendDetailPage.htm',
	    data:{
	    	"goodClassId":goodClassId,
	    	"dateBetweenType":dateBetweenType,
	    	"currentPage":currentPage,
	    	"areaId":$("input[name=areaId]").val(),
	    	"colorId":$("input[name=colorId]").val(),
	    	"formId":$("input[name=formId]").val(),
	    	"token":$("input[name=token]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	       initTable(result);
	    },
	    error: function (errorMsg) {
	    }
	});
}
//---------------------修改为highchart
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