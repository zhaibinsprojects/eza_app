/**
 * 
 */mui.init({
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
			/**
			 * 下拉刷新具体业务实现
			 */
			function pulldownRefresh() {
				setTimeout(function() {
					//下拉从新加载，不要重定向
					//window.location.href=baseurl+"/front/app/home/analyseAndReport.htm?type="+type+"&currentPage=1";
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
					mui('#pullrefresh').pullRefresh().enablePullupToRefresh(true);
					}, 1500);
			}
			var count = 0;
			/**
			 * 上拉加载具体业务实现
			 */
			function pullupRefresh() {
				setTimeout(function() {
					mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > pagecount)); //参数为true代表没有更多数据了。
					var table = document.body.querySelector('.mui-table-view');
					var li = document.createElement('div');
					li.className = 'child_cont';
					var html=pagertemp(count);
					li.innerHTML = html;
					table.appendChild(li);
						
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
			
			function pagertemp(pageno){
				var html="";
				$.ajax({
					type : "post",
					url : baseurl+"/front/app/home/analyseAndReportPage.htm",
					data : {
						 "currentPage":pageno+1,
						  "type":type,
						  "ecId":$('.ezsm-normal-tab-sel').attr("id")
					},
					dataType : "html",
					async : false,
					success : function(data) {
						if(pageno==0){
							$(".secNeiron").empty();
						}
						html=data;
					},
					error:function(e){
						html=e;
						}
				});
				return html;
			}
				