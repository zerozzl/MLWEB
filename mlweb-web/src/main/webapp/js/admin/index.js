var ERROR_MSG = "<div class='alert alert-danger'><i class='icon icon-red icon-cross'></i>系统异常</div>";

function init() {
	init_header();
	init_nav_left();
	$("#main").load("dashboard.html", function() {
		init_dashboard();
	});
}

function init_header() {
	$.ajax({
		traditional : true,
		type : "post",
		dataType : "json",
		cache : false,
		timeout : 10000,
		url : "../checkLogin.action",
		success : function(data, textStatus) {
			if (data.ajaxObj.hasLogin == 0 || data.ajaxObj.isAdmin == 0) {
				location.href = "../ad_login.html";
			} else {
				$("#user-email").html(data.ajaxObj.email);
			}
		},
		error : function(jqXHR, status, errorThrown) {
			location.href = "../ad_login.html";
		}
	});
}

function init_nav_left() {
	$.ajax({
		traditional : true,
		type : "post",
		dataType : "json",
		cache : false,
		timeout : 10000,
		url : "initNavLeft.action",
		success : function(data, textStatus) {
			var obj = data.ajaxObj;
			if(obj.opinions > 0) {
				$("#nav-left").find("li[module=visitor-opinion]").find("a").html("访客意见<span class='badge'>" + obj.opinions + "</span>");
			} else {
				$("#nav-left").find("li[module=visitor-opinion]").find("a").html("访客意见");
			}
		},
		error : function(jqXHR, status, errorThrown) {
			location.href = "../ad_login.html";
		}
	});
}

function switch_module(item) {
	item = $(item);
	$("#nav-left").find("li").removeClass("active");
	item.addClass("active");
	$("#main").html("");
	var module = item.attr("module");
	if(module == "dashboard") {
		$("#main").load("dashboard.html", function() {
			init_dashboard();
		});
	} else if(module == "visitor-opinion") {
		$("#main").load("visitor_opinion_list.html", function() {
			init_visitor_opinion_list();
		});
	}
	init_nav_left();
}