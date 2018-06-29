/*------折线图----*/
$(function () {
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
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    },
		credits: {
          enabled:false
},
       exporting: {
            enabled:false
},
        yAxis: {
			tickPositions: [0, 5, 10, 15,20,25,30],
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
        data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
    }]
    });
	});
/*------柱状图1----*/
$(function () {
    var chart = Highcharts.chart('containerbar', {
        chart: {
            type: 'column'
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
		colors: ['#4cd4c8'],
		credits: {
          enabled:false
},
       exporting: {
            enabled:false
},
legend: {
            enabled: false
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            tickPositions: [0, 200, 500, 800],
            title: {
                text: ''
            }
        },
        series: [{
            name: '指数',
            data: [434, 53, 345, 785, 565, 843, 726, 590, 665, 434, 312, 432]
        }],
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 50
                },
                
                chartOptions: {
                    xAxis: {
                        labels: {
                            formatter: function () {
                                return this.value.replace('', '')
                            }
                        }
                    },
                    yAxis: {
                        labels: {
                            align: 'left',
                            x: 0,
                            y: -2
                        },
                        title: {
                            text: ''
                        }
                    }
                }
            }]
        }
    });
});
