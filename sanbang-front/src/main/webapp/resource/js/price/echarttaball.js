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
$.ajax({
    type: 'get',
    url: 'front/resource/json/city2.json',
    dataType: "json",
    success: function (result) {
        $.each(result.list, function (index, item) {
            namey.push(item.name);     
            numo.push(item.value);
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
            	name:'浏览量(PV)',
                type:'line',
                smooth: true,
                data: numo
            }
            ]
        });
    },
    error: function (errorMsg) {
        alert("图表数据请求失败!");
        myChart.hideLoading();
    }
});
