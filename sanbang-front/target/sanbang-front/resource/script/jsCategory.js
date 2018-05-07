
﻿var categoryList = "";
$.ajaxSettings.async = false; 

$.getJSON("/file/jsonpcjson/16/jsCategory.json", function(jsonObject){
		categoryList = jsonObject;
        
	});
$.ajaxSettings.async = true; 

var categoryInit = function(_fcategory, _scategory, _tcategory, defaultProvince, defaultCity, defaultArea)
{

	var fcategory = document.getElementById(_fcategory);
	var scategory = document.getElementById(_scategory);
	var tcategory = document.getElementById(_tcategory);
	
	function cmbSelect(cmb, str)
	{
		for(var i=0; i<cmb.options.length; i++)
		{
			if(cmb.options[i].value == str)
			{
				cmb.selectedIndex = i;
				return;
			}
		}
	}
	function cmbAddOption(cmb, str, obj)
	{
		var option = document.createElement("OPTION");
		cmb.options.add(option);
		option.innerHTML = str;
		option.value = str;
		option.obj = obj;
	}
	
	function changescategory()
	{
		tcategory.options.length = 0;
		if(scategory.selectedIndex == -1)return;
		var item = scategory.options[scategory.selectedIndex].obj;
		for(var i=0; i<item.catnameList.length; i++)
		{
			cmbAddOption(tcategory, item.catnameList[i].name, null);
		}
		cmbSelect(tcategory, defaultArea);
	}
	function changefcategory()
	{
		scategory.options.length = 0;
		scategory.onchange = null;
		if(fcategory.selectedIndex == -1)return;
		var item = fcategory.options[fcategory.selectedIndex].obj;
		for(var i=0; i<item.catnameList.length; i++)
		{
			cmbAddOption(scategory, item.catnameList[i].name, item.catnameList[i]);
		}
		cmbSelect(scategory, defaultCity);
		changescategory();
		scategory.onchange = changescategory;
	}
	
	for(var i=0; i<categoryList.length; i++)
	{
		cmbAddOption(fcategory, categoryList[i].name, categoryList[i]);
	}
	cmbSelect(fcategory, defaultProvince);
	changefcategory();
	fcategory.onchange = changefcategory;
}

