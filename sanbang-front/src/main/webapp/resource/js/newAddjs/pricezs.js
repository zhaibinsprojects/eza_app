	
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
									$(".kindId").val(items[0].value);
									$(".kindval").val(items[0].text);
								}else{
									userResult.innerText =items[1].text;
									$(".kindId").val(items[1].value);
									$(".kindval").val(items[1].text);
								}
							}else{
								userResult.innerText =items[2].text;
								$(".kindId").val(items[2].value);
								$(".kindval").val(items[2].text);
							}
							$(".csubmit").submit();
							//返回 false 可以阻止选择框的关闭
							//return false;
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
									$(".areaId").val(items[0].value);
									$(".areaval").val(items[0].text);
								}else{
									cityResult.innerText =items[1].text;
									$(".areaId").val(items[0].value);
									$(".areaval").val(items[0].text);
								}
							}else{
								cityResult.innerText =items[2].text;
								$(".areaId").val(items[0].value);
								$(".areaval").val(items[0].text);
							}
							$(".csubmit").submit();
							
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
								$(".colorId").val(items[0].value);
								$(".colorval").val(items[0].text);
								colorResult.innerText = items[0].text;
							}else{
								$(".colorId").val("");
								$(".colorval").val("");
								colorResult.innerText = "全部";
							}
							$(".csubmit").submit();
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
								$(".formId").val(items[0].value);
								$(".formval").val(items[0].text);
								formResult.innerText = items[0].text;
							}else{
								$(".formId").val("");
								$(".formval").val("");
								formResult.innerText = "全部";
							}
							$(".csubmit").submit();
							
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