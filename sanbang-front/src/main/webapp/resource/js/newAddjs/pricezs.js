/*------折线图----*/
$(function () {
Highcharts.chart('container2', {
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
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    },
		credits: {
          enabled:false
},
       exporting: {
            enabled:false
},
        yAxis: {
			tickPositions: [0, 10, 20, 30,40,50,60],
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
                    radius: 3,
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
        name: 'Tokyo',
        data: [25.0, 16.9, 39.5, 34.5, 8.4, 21.5, 25.2, 26.5, 3.3, 1.3, 53.9, 9.6]
    }]
    });
	});
	
/*----------*/
(function($, doc) {
				$.init();
				$.ready(function() {
					//品类
					var userPicker = new $.PopPicker();
					userPicker.setData([{
						value: 'ywj',
						text: '废电视'
					}, {
						value: 'aaa',
						text: '废洗衣机'
					}, {
						value: 'lj',
						text: '废冰箱'
					}, {
						value: 'ymt',
						text: '废空调'
					}, {
						value: 'shq',
						text: '废纸'
					}, {
						value: 'zhbh',
						text: '废油'
					}, {
						value: 'zhy',
						text: '废轮胎'
					}, {
						value: 'gyf',
						text: '废有色'
					}, {
						value: 'zhz',
						text: '废钢'
					}, {
						value: 'gezh', 
						text: '废塑料'
					}]);
					var showUserPickerButton = doc.getElementById('showUserPicker');
					var userResult = doc.getElementById('userResult');
					showUserPickerButton.addEventListener('tap', function(event) {
						userPicker.show(function(items) {
							userResult.innerText = items[0].text;
						});
					}, false);
					//-----------------------------------------
					//地区
					var cityPicker = new $.PopPicker({
						layer: 1
					});
					cityPicker.setData(cityData);
					var showCityPickerButton = doc.getElementById('showCityPicker');
					var cityResult = doc.getElementById('cityResult');
					showCityPickerButton.addEventListener('tap', function(event) {
						cityPicker.show(function(items) {
							cityResult.innerText = items[0].text;
							//返回 false 可以阻止选择框的关闭
							//return false;
						});
					}, false);
					//-----------------------------------------
					//颜色
					var colorPicker = new $.PopPicker();
					colorPicker.setData([{
						value: 'red',
						text: '红色'
					}, {
						value: 'blue',
						text: '蓝色'
					}, {
						value: 'green',
						text: '绿色'
					}]);
					var showColorPickerButton = doc.getElementById('showColorPicker');
					var colorResult = doc.getElementById('colorResult');
					showColorPickerButton.addEventListener('tap', function(event) {
						colorPicker.show(function(items) {
							colorResult.innerText = items[0].text;
						});
					}, false);
					
					//形态
					var formPicker = new $.PopPicker();
					formPicker.setData([{
						value: 'water',
						text: '液态'
					}, {
						value: 'qi',
						text: '气态'
					}, {
						value: 'gu',
						text: '固态'
					}]);
					var showFormPickerButton = doc.getElementById('showFormPicker');
					var formResult = doc.getElementById('formResult');
					showFormPickerButton.addEventListener('tap', function(event) {
						formPicker.show(function(items) {
							formResult.innerText = items[0].text;
						});
					}, false);
				});
			})(mui, document);
/*-----------*/
$(".nav_ulsty_yzs li").click(function(){
		$(this).siblings().find("a").removeClass("active_yzs");
		$(this).children("a").addClass("active_yzs");
		});
	$(".dy_yzsbtn").click(function(){
		$(".graybaks,.grayChren").show();
		});
	$(".qx_yzsbtn").click(function(){
		$(".graybaks,.grayChren").hide();
		});	