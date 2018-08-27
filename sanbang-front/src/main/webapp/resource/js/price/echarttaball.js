var myChart = echarts.init(document.getElementById('mainAll'));
myChart.setOption({
	title : {
        show:false
    },
	 tooltip: {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'none'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '0%',
        right: '0%',
        bottom: '4%',
        top:'6%',
        containLabel: true
    },
    calculable : true,
     xAxis : [
        {
            type : 'category',
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#000'
                }
            },
  			data:[],
  			 axisLabel:{
            inside:true,
        },
    	}
	],
	yAxis : [
    	{
        	type : 'value',
        	 axisLabel:{
            inside:true,
        },
    	}
	],
    series : [
    	{
        	name:'',
        	type:'bar',
        	smooth: true,
        	data:[],
        }
    ]
});
myChart.showLoading();
var namey = [];
var numo = [];
var classname = "";
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
       $.each(result, function (index, item) {
            namey.push(item.dealDate);     
            numo.push(item.currentPrice);
            classname = item.goodClassName;
        });
        myChart.hideLoading();
        myChart.setOption({
            xAxis: {
                data: namey
            },
            yAxis: {
                data: numo
            },
            series: [{
            	name:classname,
                type:'line',
                smooth: true,
                data: numo
            }
            ]
        });
        //初始化填充数据列表数据列表
        //initTable(result);
        showdataByPage(type,$("input[name=priceId]").val(),"WEEK",1);
    },
    error: function (errorMsg) {
        alert("图表数据请求失败!");
        myChart.hideLoading();
    }
});

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
	        myChart.hideLoading();
	        myChart.setOption({
	            xAxis: {
	                data: namey
	            },
	            yAxis: {
	                data: numo
	            },
	            series: [{
	            	name:classname,
	                type:'line',
	                smooth: true,
	                data: numo
	            }
	            ]
	        });
	        //初始化填充数据列表数据列表
	        //initTable(result);
	    },
	    error: function (errorMsg) {
	        alert("图表数据请求失败!");
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
	        alert("图表数据请求失败!");
	        myChart.hideLoading();
	    }
	});
}