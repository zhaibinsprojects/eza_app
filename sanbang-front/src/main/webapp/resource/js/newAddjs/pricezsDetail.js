	
/*----------*/
(function($, doc) {
				$.init();
				$.ready(function() {
					//品类
					var userPicker = new $.PopPicker({
						layer: 3
					});
					userPicker.setData(catedate);
					var showUserPickerButton = doc.getElementById('showUserPicker');
					var userResult = doc.getElementById('userResult');
					showUserPickerButton.addEventListener('tap', function(event) {
						userPicker.show(function(items) {
							if(undefined==items[2].value||-1==items[2].value){
								if(undefined==items[1].value||-1==items[1].value){
									userResult.innerText =items[0].text;
									document.getElementsByName("kindId")[0].value=items[0].value;
									document.getElementsByName("kindval")[0].value=items[0].text;
								}else{
									userResult.innerText =items[1].text;
									document.getElementsByName("kindId")[0].value=items[1].value;
									document.getElementsByName("kindval")[0].value=items[1].text;
								}
							}else{
								userResult.innerText =items[2].text;
								document.getElementsByName("kindId")[0].value=items[2].value;
								document.getElementsByName("kindval")[0].value=items[2].text;
							}
							//返回 false 可以阻止选择框的关闭
							//return false;
							csubmit();
						});
					}, false);
					//-----------------------------------------
					//地区
					var cityPicker = new $.PopPicker({
						layer: 3
					});
					cityPicker.setData(cityData);
					var showCityPickerButton = doc.getElementById('showCityPicker');
					var cityResult = doc.getElementById('cityResult');
					showCityPickerButton.addEventListener('tap', function(event) {
						cityPicker.show(function(items) {
							if(undefined==items[2].value||-1==items[2].value){
								if(undefined==items[1].value||-1==items[1].value){
									cityResult.innerText =items[0].text;
									document.getElementsByName("areaId")[0].value=items[0].value;
									document.getElementsByName("areaval")[0].value=items[0].text;
								}else{
									cityResult.innerText =items[1].text;
									document.getElementsByName("areaId")[0].value=items[1].value;
									document.getElementsByName("areaval")[0].value=items[1].text;
								}
							}else{
								cityResult.innerText =items[2].text;
								document.getElementsByName("areaId")[0].value=items[2].value;
								document.getElementsByName("areaval")[0].value=items[2].text;
							}
							csubmit();
							
							//返回 false 可以阻止选择框的关闭
							//return false;
						});
					}, false);
					//-----------------------------------------
					//颜色
					var colorPicker = new $.PopPicker();
					colorPicker.setData(colordate);
					var showColorPickerButton = doc.getElementById('showColorPicker');
					var colorResult = doc.getElementById('colorResult');
					showColorPickerButton.addEventListener('tap', function(event) {
						colorPicker.show(function(items) {
							if(items[0].text!="全部"){
								document.getElementsByName("colorId")[0].value=items[0].value;
								document.getElementsByName("colorval")[0].value=items[0].text;
								colorResult.innerText = items[0].text;
							}else{
								document.getElementsByName("colorId")[0].value="";
								document.getElementsByName("colorval")[0].value="";
								
								colorResult.innerText = "全部";
							}
							csubmit();
						});
					}, false);
					
					//形态
					var formPicker = new $.PopPicker();
					formPicker.setData(formdate);
					var showFormPickerButton = doc.getElementById('showFormPicker');
					var formResult = doc.getElementById('formResult');
					showFormPickerButton.addEventListener('tap', function(event) {
						formPicker.show(function(items) {
							if(items[0].text!="全部"){
								document.getElementsByName("formId")[0].value=items[0].value;
								document.getElementsByName("formval")[0].value=items[0].text;
								formResult.innerText = items[0].text;
							}else{
								document.getElementsByName("formId")[0].value="";
								document.getElementsByName("formval")[0].value="";
								
								formResult.innerText = "全部";
							}
							csubmit();
						
							
							
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
//通过ajax提交
function csubmit(){
	var xdata="";
	var name="";
	var data="";
	var plist="";
	var baojia_goodclass = $("input[name='kindId']").val();
	var formId = $("input[name='formId']").val();
	var colorId = $("input[name='colorId']").val();
	var areaId = $("input[name='areaId']").val();
	var dateBetweenType = $("input[name='dateBetweenType']").val();
	$.ajax({
		  type: 'POST',
		  url: baseurl+"/front/app/home/getPriceTrendcyShow.htm",
		  data: {
			  kindId:baojia_goodclass,
			  formId:formId,
			  colorId:colorId,
			  areaId:areaId,
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
		   	initTable(plist);
		   	echartInit(xdata, name, data);
		  },
		  error : function(errorMsg) {
			  //无数据,清空列表
			  $("tbody").empty();
			  //无数据，清空折线图
			  $("#container").empty();
	      },
		  dataType : "json"
		});
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
function initTable(plist){
	$("tbody").empty();
	var html = "";	
	for(var i=0;i<plist.length;i++){
		html = html+"<tr><td><span>ABS</span></td><td><span>北京</span></td><td><span>"+plist[i].currentAVGPrice+"</span></td>";
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
	