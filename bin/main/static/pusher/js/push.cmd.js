// JavaScript Document
$(document).ready(function() {
'use strict';
	//header fixed
	$(window).scroll(function(){
		$('.header').css('left', -$(this).scrollLeft() + "px");
	});

	//Upload image
	var isOriginImg = false;
	function readURL(input) {
		var imgPre = $('.imagePreview');
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e){
				imgPre.css('background-image','url('+e.target.result +')');
				imgPre.hide();
				imgPre.fadeIn(350);
				$('label.hide:after').hide();
			};
			reader.readAsDataURL(input.files[0]);
			isOriginImg = true;
		}
		if(isOriginImg === true){
			$('.avatar_upload .avatar_edit').addClass('avata_true');
		}
	}
	$("#imageUpload").change(function() {
		readURL(this);
	});

	//Click on
	var platform = $('.platform_option a');
	platform.click(function() {
//		$(this).siblings().removeClass('on');
		$(this).toggleClass('on');
	});

	//declare tab function
	$.fn.tabFunc = function() {

		var tab_inner = this.find('.tab_inner');
		var tab_btn = tab_inner.find('.tab_btn');
		var tabCnt_wrap = this.find('.tabCnt_wrap');
		var tabCnB = tabCnt_wrap.find('> div');
		tabCnB.not(":first").hide();

		// click event on tab handle
		tab_inner.find('.tab_btn').each(function(){
			$(this).click(function(){
				tab_btn.removeClass('on');
				$(this).addClass('on');
				var ind = $(this).index();
				tabCnB.hide();
				tabCnB.eq(ind).show();
			});
		});

	};
	// call tab function
	$(".pop_container").tabFunc();

	//view password
	$(".toggle-password").click(function() {

		$(this).toggleClass("off");
		var input = $($(this).attr("toggle"));
		if (input.attr("type") === "password") {
			input.attr("type", "text");
		} else {
			input.attr("type", "password");
		}
	});

	// header-control
	$('.lang_opt a').click(function(){
		var lang_ls = $(this);
		var drp_show = lang_ls.next().toggle();
		$('.pro_info a').next().hide();
		lang_ls.next().children().on("click",function(){
			$(this).addClass('on').siblings().removeClass('on');
			var txt = $(this).text();
			var langClass = $(this).attr('class');
			lang_ls.html(txt);
			lang_ls.removeClass();
			lang_ls.addClass(langClass).addClass('select_control').removeClass('on');
			drp_show.hide();
		});
	});

	$('.pro_info a').click(function(){
		var pro_ls = $(this);
		var drp_show = pro_ls.next().toggle();
		$('.lang_opt a').next().hide();
		pro_ls.next().children().on("click",function(){
			drp_show.hide();
		});
	});

	$(document).click(function(e){
		var select_con = $('.select_control');
		if($(e.target).is(select_con) || $(e.target).closest(select_con).length){
			return true;
		}
		else{
			$('.layer').hide();
		}
	});

	//change postion when pop height higher than screen
	var docHeight = $(document).height();
	var pop_h =$('.pop_container').outerHeight();

	if(docHeight - 23 < pop_h){

		$('.pop_inner').addClass('js-pop-scroll');
		$('.pop_wrap').css('overflow','auto');
	}
});