$(document).ready(function(){
	
	$.ajax({
		type : "post",
		url : baseurl + "front/app/hangq/pushData/"+pushcode+".htm",
		data : {
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.success) {
				var zszs=data.obj.zszs;//再生走势
				var zsbj=data.obj.zsbj;//再生报价 vdutydiop
				var yjbg=data.obj.yzbg;//研究报告
				var xlbj=data.obj.xlbj;
				var weekday=data.obj.weekday;
				var pushdate=data.obj.pushdate;
				
				var  title1htm='';
				if(''!=weekday){
					title1htm+='<p>'+pushdate+' <span>'+weekday+'</span></p>'
				}
				$("#grad").empty();
				$("#grad").append(title1htm);
				//再生报价
				var zsbjhtml='';
				for (var i = 0; i < zsbj.length; i++) {
					
					if(zsbj[i].list.length==0){
						continue;
					}
					
					 zsbjhtml+='<div class="vdutydiop">';
					
					zsbjhtml+='<h5 class="dingzhi_show">'+zsbj[i].name+'</h5>';
					zsbjhtml+='<table border="0" cellspacing="0" cellpadding="0" width="100%">';
					zsbjhtml+='<tr>';
					zsbjhtml+='<th width="20%"><span>区域</span></th>';
					zsbjhtml+='<th width="40%"><span>规格</span></th>';
					zsbjhtml+='<th width="20%"><span>涨跌幅</span></th>';
					zsbjhtml+='<th width="20%"><span>价格/吨</span></th>';
					zsbjhtml+='</tr>';
					
					for (var j = 0; j < zsbj[i].list.length; j++) {
						zsbjhtml+='<tr>';
						zsbjhtml+='<td><span>'+zsbj[i].list[j].goodArea+'</span></td>';
						zsbjhtml+='<td><span>'+zsbj[i].list[j].goodClassName+'</span></td>';
						zsbjhtml+='<td><span class="greencolor">'+zsbj[i].list[j].sandByOne+'</span></td>';
						zsbjhtml+='<td><span class="redcolor">'+zsbj[i].list[j].prePrice+'</span></td>';
						zsbjhtml+='</tr>';
					}
					
					zsbjhtml+='</table>';
					zsbjhtml+='</div>';
		  			
				}	
		  			
				if(zsbj.length==0){
					zsbjhtml+='<div class="vdutydiop">';
					zsbjhtml+='<h5 class="dingzhi_show">暂无数据</h5>';
					zsbjhtml+='</div>';
		  		}
				
		  		$(".zsbj").empty();
		  		$(".zsbj").append(zsbjhtml);
		  		
		  		
		  	   //新料报价
		  		var xlbjhtml='';
		  		for (var i = 0; i < xlbj.length; i++) {
					 xlbjhtml+='<div class="vdutydiop">';
					
					xlbjhtml+='<h5 class="dingzhi_show">'+xlbj[i].name+'</h5>';
					xlbjhtml+='<table border="0" cellspacing="0" cellpadding="0" width="100%">';
					xlbjhtml+='<tr>';
					xlbjhtml+='<th width="20%"><span>区域</span></th>';
					xlbjhtml+='<th width="40%"><span>规格</span></th>';
					xlbjhtml+='<th width="20%"><span>涨跌幅</span></th>';
					xlbjhtml+='<th width="20%"><span>价格/吨</span></th>';
					xlbjhtml+='</tr>';
					
					for (var j = 0; j < xlbj[i].list.length; j++) {
						xlbjhtml+='<tr>';
						xlbjhtml+='<td><span>'+xlbj[i].list[j].goodArea+'</span></td>';
						xlbjhtml+='<td><span>'+xlbj[i].list[j].goodClassName+'</span></td>';
						xlbjhtml+='<td><span class="greencolor">'+xlbj[i].list[j].sandByOne+'</span></td>';
						xlbjhtml+='<td><span class="redcolor">'+xlbj[i].list[j].prePrice+'</span></td>';
						xlbjhtml+='</tr>';
					}
					
					xlbjhtml+='</table>';
					xlbjhtml+='</div>';
		  			
				}
		  		if(xlbj.length==0){
		  			 xlbjhtml+='<div class="vdutydiop">';
						
						xlbjhtml+='<h5 class="dingzhi_show">暂无数据</h5>';
						xlbjhtml+='</div>';
		  		}
		  		
		  					
		  		$(".xlbj").empty();
		  		$(".xlbj").append(xlbjhtml);			
		  					
		  				
		  	//再生走势
		  		
		  		 
		  		var zszshtml='<div class="gtuceifop">';
		  		var echartdata=new Array();
		  		for (var t = 0; t < zszs.length; t++) {
			  		var onex=Array();
			  		var oney=Array();
			  		var cha={};
			  		var goodArea="";
			  		if(zszs[t].list.length>0){
			  		for (var j = 0; j < zszs[t].list.length; j++) {
			  			onex.push(zszs[t].list[j].dealDate);
			  			oney.push(zszs[t].list[j].preAVGPrice);
			  			goodArea=zszs[t].list[j].goodArea;
					}
			  		zszshtml+='<h4>'+goodArea+zszs[t].name+'</h4>';
			  		zszshtml+='<div class="vdutydiop" style="width: 100%; height: 150px;" id="container'+t+'">';
		  			zszshtml+='<div id="'+t+'" style="width: 100%; height: 150px;"></div>';
			  		zszshtml+='</div>';
			  		var cha={};
			  		cha["onex"]=onex;
			  		cha["oney"]=oney;
			  		cha["name"]=goodArea+zszs[t].name;
			  		echartdata.push(cha);
			  		}
				}
		  		zszshtml+='</div>';
		  		
		  		$(".zszs").empty();
		  		$(".zszs").append(zszshtml);
		  		
		  		for (var i = 0; i < echartdata.length; i++) {
		  			echartInit(echartdata[i].onex,echartdata[i].name,echartdata[i].oney,"container"+i);
				}
				
		  		
		  		var yjbghtml='';
		  		for (var i = 0; i < yjbg.length; i++) {
					yjbghtml+='<li><a href="javascript:;">'+yjbg[i].name+'</a></li>';
				}
		  		$(".ukyuioghs").empty();
		  		$(".ukyuioghs").append(yjbghtml);
			}else{
				layer.open({
					content : "查看失败",
					skin : 'msg',
					time : 2
				});
			} 
		},
		error : function(e) {
		}
	});
	
	
});


function echartInit(xdata,name,mydata,classname){
	
	Highcharts.chart(classname, {
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
	        	 tickInterval: 1,
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