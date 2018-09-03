var namey = [];
var numo = [];
var classname = "";
$(document).ready(function(){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceInTimeDetail.htm',
	    data:{
	    	"type":$("input[name=type]").val(),
	    	"priceId":$("input[name=priceId]").val()
	    },
	    dataType: "json",
	    success: function (result) {
	    	//页数
	    	$("input[name=pagecount]").val(Math.ceil(result.length/20));
	    	var isshow = 0;
	    	$.each(result, function (index, item) {
	            namey.push(item.dealDate);     
	            numo.push(item.currentPrice);
	            classname = item.goodClassName;
	        });
	       	//heighchart
	       	echartInit(namey, classname, numo);   
	       	showdataByPage(type,$("input[name=priceId]").val(),"WEEK",1);
	    },
	    error: function (errorMsg) {
	        //alert("图表数据请求失败!");
	    }

	});	
})

//初始化填充表格
function initTable(plist){
	$("tbody").empty();
	var html = "";	
	//plist.length
	for(var i=0;i<plist.length;i++){
		html = html+"<tr><td><span>"+plist[i].goodClassName+"</span></td>"+
			"<td><span>"+plist[i].goodArea+"</span></td>"+
			"<td><span class='colrRed'>￥"+plist[i].currentPrice+"</span></td>";
		
		if(plist[i].currentPrice > plist[i].prePrice){			
			html=html+"<td><span class='colrRed'>"+plist[i].sandByOne+"</span></td>";
		}else if(plist[i].currentPrice < plist[i].prePrice){
			html=html+"<td><span class='colGreen'>"+plist[i].sandByOne+"</span></td>";			
		}else {
			html=html+"<td><span class='colrRed'>"+plist[i].sandByOne+"</span></td> ";
		} 
		html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
	}
	$("tbody").append(html);
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
		showdatas(type,priceId,$(this).attr("name"));
		//加载表格数据列表，默认加载首页
		showdataByPage(type,priceId,$(this).attr("name"),1);
		mui('#pullrefresh').pullRefresh().enablePullupToRefresh(true);
	});
})
function showdatas(type,priceId,dateBetweenType){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceInTimeDetail.htm',
	    data:{
	    	"type":type,
	    	"priceId":priceId,
	    	"dateBetweenType":dateBetweenType
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
	            numo.push(item.currentPrice);
	            classname = item.goodClassName;
	        });
	       echartInit(namey, classname, numo);  
	        //初始化填充数据列表数据列表
	        //initTable(result);
	    },
	    error: function (errorMsg) {
	        //alert("图表数据请求失败!");
	        myChart.hideLoading();
	    }
	});
}
//实时报价详情列表-分页展示 
function showdataByPage(type,priceId,dateBetweenType,currentPage){
	$.ajax({
	    type: 'post',
	    url: 'front/app/hangq/priceInTimeDetailPage.htm',
	    data:{
	    	"type":type,
	    	"priceId":priceId,
	    	"dateBetweenType":dateBetweenType,
	    	"currentPage":currentPage
	    },
	    dataType: "json",
	    success: function (result) {
	       //初始化填充数据列表数据列表
	       initTable(result);
	    },
	    error: function (errorMsg) {
	        //alert("图表数据请求失败!");
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
	        	//enabled: true
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
	            },
	            tooltip: {
	            	formatter: function () {
	            	var s = "haha";
	            	return s;
	            	}
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