$(document).ready(function(){
	/*------------*/
		$(".tabul_rirlr li").click(function(){
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
			var indexuio = $(this).index();
			$(".tabFater_tsi").hide();
			$(".tabFater_tsi").eq(indexuio).show();
		});
		$(".lockyuip").click(function(){
			$(".graybaksuio,.tishxuyo").show();
		})
		$(".ortyui").click(function(){
			$(".graybaksuio,.tishxuyo").hide();
		});
	
		$(".section_roptab ul li").click(function(){
			$(this).addClass("active");
			$(this).siblings("li").removeClass("active")
		});
		/*-------地区选择-------*/
		$(".cliksuo").click(function(){
			$(".content_area").show();
			$(".content_area").animate({ left:'0' }, 500 );
		});
		$(".aicon_yzsarea").click(function(){
			$(".content_area").animate({ left:'100%' }, 500 ).hide();
		});
		$(".nexticker,.hot_area a").click(function(){
			$(".content_area").animate({ left:'100%' }, 500 ).hide();
			var indexTxt=$(this).text();
			$(".cliksuo a").text(indexTxt)
		});
		/*--------品类筛选-------*/
		$(".clickProtuy").click(function(){
			$(".protuyiod").show();
			$(".protuyiod").animate({ left:'0' }, 500 );
		});
		$(".aicon_yzsproduy").click(function(){
			$(".protuyiod").animate({ left:'100%' }, 500 ).hide();
		});
		$(".spanprodut").click(function(){
			$(this).toggleClass("spanclikcor")
		});
		$(".registr").click(function(){
			$(".spanprodut").removeClass("spanclikcor")
		});
		/*------*/
		$(".agment").click(function(){
			$(".protuyiod").animate({ left:'100%' }, 500 ).hide();
		});
		/*---sclicksxsd-----*/
		$(".sclicksxsd").click(function(){
			$(".graytuiops,.shaxyuiop").show();
			$(".graytuiops").animate({ left:'0' }, 500 );
			$(".shaxyuiop").animate({ left:'8%' }, 500 );
		});
		$(".registrsx").click(function(){
			$(".spanprodut").removeClass("spanclikcor")
		});
		$(".agmentsx,.graytuiops").click(function(){
			$(".graytuiops,.shaxyuiop").animate({ left:'100%' }, 500 ).hide();
		});
	});