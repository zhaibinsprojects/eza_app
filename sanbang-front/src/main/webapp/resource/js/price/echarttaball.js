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
    	"type":"oldclass",
    	"priceId":'13530'
    },
    dataType: "json",
    success: function (result) {
    	//alert(result);
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
        initTable(result);
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