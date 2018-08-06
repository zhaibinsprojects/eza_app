	
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
	
function csubmit(){
	$(".csubmit").submit();
}	
	