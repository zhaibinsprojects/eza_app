mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					down: {
						callback: pulldownRefresh
					},
					up: {
						contentrefresh: '正在加载...',
						callback: pullupRefresh
					}
				}
			});

			var vcount = 1;
			/**
			 * 下拉刷新具体业务实现
			 */
			function pulldownRefresh() {
				setTimeout(function() {
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
					mui('#pullrefresh').pullRefresh().enablePullupToRefresh(true);
					vcount = 1;
					shuaxindata();
				}, 1500);
			}
			/**
			 * 上拉加载具体业务实现
			 */
			function pullupRefresh() {
				vcount = ++vcount;
				setTimeout(function() {
					var channelchanged = $("input[name=channelchanged]").val();
					if(channelchanged=="1")
						{
						vcount = 2;
						}
					$("input[name=channelchanged]").val("0");
					var pagecount = $("input[name=pagecount]").val();
					mui('#pullrefresh').pullRefresh().endPullupToRefresh((vcount > pagecount)); //参数为true代表没有更多数据了。
					var table = document.body.querySelector('tbody');
						if(!(vcount > pagecount)){
							pagertemp(vcount);
						}
						
				}, 1500);
			}
			if (mui.os.plus) {
				mui.plusReady(function() {
					setTimeout(function() {
					}, 1000);
				});
			} else {
				mui.ready(function() {
				});
			}
			//更多加载
			function pagertemp(currentPage){
				var html="";
				$.ajax({
					type : "post",
					url : "front/app/hangq/priceTrendDetailPage.htm",
					data : {
						"currentPage":currentPage,
						"goodClassId":$("input[name=goodClassId]").val(),
					    "dateBetweenType":$("input[name=dateBetweenType]").val(),
					    "areaId":$("input[name=areaId]").val(),
					    "formId":$("input[name=formId]").val(),
					    "colorId":$("input[name=colorId]").val(),
					    "token":$("input[name=token]").val()
					},
					dataType : "json",
					async : false,
					success : function(data) {
						addTable(data);
						doclickTwo();
					},
					error:function(e){
						html=e;
						}
				});
				return html;
			}
			//刷新加载
			function shuaxindata(){
				var html="";
				$.ajax({
					type : "post",
					url : "front/app/hangq/priceTrendDetailPage.htm",
					data : {
						"currentPage":"1",
						"goodClassId":$("input[name=goodClassId]").val(),
					    "dateBetweenType":$("input[name=dateBetweenType]").val(),
					    "areaId":$("input[name=areaId]").val(),
					    "formId":$("input[name=formId]").val(),
					    "colorId":$("input[name=colorId]").val(),
					    "token":$("input[name=token]").val()
					},
					dataType : "json",
					async : false,
					success : function(data) {
						initTable(data);
						doclickTwo();
					},
					error:function(e){
						html=e;
						}
				});
				return html;
			}
			
			//更多表格列表
			function addTable(plist){
				var html = "";	
				for(var i=0;i<plist.length;i++){
					html = html+"<tr><td><span>"+plist[i].goodClassName+"</span></td>"+
						"<td><span>"+plist[i].goodArea+"</span></td>";
					
					if(plist[i].isshow=='1'){
						html = html+"<td><span class='colrRed'>￥"+plist[i].currentAVGPrice+"</span></td>";
						
						if((plist[i].preAVGPrice!=null)&&(plist[i].currentAVGPrice > plist[i].preAVGPrice)){			
							html=html+"<td><span class='colrRed'>"+plist[i].sandByOne+"</span></td>";
						}else if((plist[i].preAVGPrice!=null)&&(plist[i].currentAVGPrice < plist[i].preAVGPrice)){
							html=html+"<td><span class='colGreen'>"+plist[i].sandByOne+"</span></td>";			
						}else {
							html=html+"<td><span>"+plist[i].sandByOne+"</span></td> ";
						} 
						//$("#container").css('display','block');//显示
						//$("#containerLock").css('display','none');//隐藏   
					}else{
						html = html+ "<td><span><i class='lockyuip'></i></span></td>"+
						"<td><span><i class='lockyuip'></i></span></td>";
					}
					html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
				}
				$("tbody").append(html);
			}
			
			//初始化表格列表
			function initTable(plist){
				$("tbody").empty();
				var html = "";	
				for(var i=0;i<plist.length;i++){
					html = html+"<tr><td><span>"+plist[i].goodClassName+"</span></td>"+
						"<td><span>"+plist[i].goodArea+"</span></td>";
					
					if(plist[i].isshow=='1'){
						html = html+"<td><span class='colrRed'>￥"+plist[i].currentAVGPrice+"</span></td>";
						
						if((plist[i].preAVGPrice!=null)&&(plist[i].currentAVGPrice > plist[i].preAVGPrice)){			
							html=html+"<td><span class='colrRed'>"+plist[i].sandByOne+"</span></td>";
						}else if((plist[i].preAVGPrice!=null)&&(plist[i].currentAVGPrice < plist[i].preAVGPrice)){
							html=html+"<td><span class='colGreen'>"+plist[i].sandByOne+"</span></td>";			
						}else {
							html=html+"<td><span>"+plist[i].sandByOne+"</span></td> ";
						} 
						//$("#container").css('display','block');//显示
						//$("#containerLock").css('display','none');//隐藏   
					}else{
						html = html+ "<td><span><i class='lockyuip'></i></span></td>"+
						"<td><span><i class='lockyuip'></i></span></td>";
					}
					html=html+"<td><span>"+plist[i].dealDate+"</span></td></tr>";
				}
				$("tbody").append(html);
			}
			
				