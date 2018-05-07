
$(function(){
	$(".yzs-head-menu li").hover(function(){
		var len = $(this).children(".yzs-head-menu-liul").find("li").length;
		var marginleft = (len-1)/2*125;
		$(this).children(".yzs-head-menu-liul").css({"display":"block","margin-left":-marginleft+"px"});
	},function(){
		$(this).children(".yzs-head-menu-liul").css("display","none");
	});
});

function _init_EZS_Type_Shop(){
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
}
