/*----------*/
(function($, doc) {
	$.init();
	$
			.ready(function() {
				// 地区
				var cityPicker = new $.PopPicker({
					layer : 3
				});
				cityPicker.setData(cityData);
				var showCityPickerButton = doc.getElementById('areaval');
				var cityResult = doc.getElementById('areaval');
				showCityPickerButton
						.addEventListener(
								'tap',
								function(event) {
									cityPicker
											.show(function(items) {
												if (undefined == items[2].value
														|| -1 == items[2].value) {
													if (undefined == items[1].value
															|| -1 == items[1].value) {
														cityResult.innerText = items[0].text;
														document
																.getElementsByName("areaId")[0].value = items[0].value;
														document
																.getElementsByName("areaval")[0].value = items[0].text;
													} else {
														cityResult.innerText = items[0].value
																+ "-"
																+ items[1].text;
														document
																.getElementsByName("areaId")[0].value = items[1].value;
														document
																.getElementsByName("areaval")[0].value = items[0].text
																+ "-"
																+ items[1].text;
													}
												} else {
													cityResult.innerText = items[0].text
															+ "-"
															+ items[1].text
															+ items[2].text;
													document
															.getElementsByName("areaId")[0].value = items[2].value;
													document
															.getElementsByName("areaval")[0].value = items[0].text
															+ "-"
															+ items[1].text
															+ "-"
															+ items[2].text;
												}
												// 返回 false 可以阻止选择框的关闭
												// return false;
											});
								}, false);
			});
})(mui, document);
