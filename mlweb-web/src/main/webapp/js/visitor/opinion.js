function init_opinion() {
	check_visitor();
	draw_image("images/visitor_opinion/upload-image.png");
	$("#dialog-msg-container").load("common/dialog_msg.html");
}

function check_visitor() {
	$.ajax({
		traditional : true,
		type : "POST",
		dataType : "json",
		cache : false,
		timeout : 10000,
		url : "checkVisitor.action",
	});
}

function return_index() {
	window.location.href = "index.html";
}

function init_form() {
	$("#opinion-form")[0].reset();
	$("#input-image").val("");
	on_off_forminput(1);
	draw_image("images/visitor_opinion/upload-image.png");
}

function on_off_forminput(status) {
	if(status == 1) {
		$("#input-title").attr("readonly", false);
		$("#input-opinion").attr("readonly", false);
		$("#submit-but").attr("disabled", false);
		$("#submit-but").html("提交意见");
	} else {
		$("#input-title").attr("readonly", true);
		$("#input-opinion").attr("readonly", true);
		$("#submit-but").attr("disabled", true);
		$("#submit-but").html("正在处理......");
	}
}

function submit() {
	var opinion = $("#input-opinion").val();
	if (!(new RegExp("\\S+")).test(opinion)) {
		$("#input-opinion").addClass("warning");
		$("#alert-opinion").show();
	} else {
		$("#input-opinion").removeClass("warning");
		$("#alert-opinion").hide();
		on_off_forminput(0);
		submit_opinion();
	}
}

function submit_opinion() {
	var url = "submitOpinion.action"
	$.ajax({
		traditional : true,
		type : "POST",
		dataType : "json",
		cache : false,
		timeout : 10000,
		url : url,
		data : $("#opinion-form").serialize(),
		success : function(data, textStatus) {
			if (data.ajaxObj.flag == 1) {
				init_dialog_msg(2, "谢谢反馈", "<i class='icon icon-blue icon-info'></i>系统已收到了您宝贵的意见，谢谢反馈",
						["继续反馈", "返回体验"], ["return_index()"]);
				$("#dialog-msg").modal("show");
				init_form();
			} else {
				init_dialog_msg(1, "系统提示", "<i class='icon icon-red icon-cross'></i><span style='color:#a94442;'>很抱歉，提交失败</span>", ["返回"]);
				$("#dialog-msg").modal("show");
				on_off_forminput(1);
			}
		},
		error : function(jqXHR, status, errorThrown) {
			init_dialog_msg(1, "系统提示", "<i class='icon icon-red icon-cross'></i><span style='color:#a94442;'>很抱歉，提交失败</span>", ["返回"]);
			$("#dialog-msg").modal("show");
			on_off_forminput(1);
		}
	});
}

function draw_image(url) {
	load_image(url, function() {
		var canvas = canvas = document.getElementById("image-canvas");
		var context = canvas.getContext("2d");
		canvas.width = canvas.clientWidth;
		canvas.height = canvas.clientHeight;
		var left = 0, top = 0;
		if (this.width > canvas.clientWidth
				|| this.height > canvas.clientHeight) {
			if (this.width > canvas.clientWidth) {
				this.height *= canvas.clientWidth / this.width;
				this.width = canvas.clientWidth;
			}
			if (this.height > canvas.clientHeight) {
				this.width *= canvas.clientHeight / this.height;
				this.height = canvas.clientHeight;
			}
		}
		left = (canvas.width - this.width) / 2;
		top = (canvas.height - this.height) / 2;
		context.drawImage(this, left, top, this.width, this.height);
	});
}

function load_image(url, callback) {
	var img = new Image();
	img.src = url;
	if (img.complete) {
		callback.call(img);
		return;
	}
	img.onload = function() {
		callback.call(img);
	};
}

function upload() {
	var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
	if (ie) {
		document.getElementById("upload-image").click();
	} else {
		var eve = document.createEvent("MouseEvents");
		eve.initEvent("click", true, true);
		document.getElementById("upload-image").dispatchEvent(eve);
	}
}

document.getElementById("upload-image").onchange = function() {
	var input = document.getElementById("upload-image");
	var acceptedTypes = {
		'image/png' : true,
		'image/jpeg' : true,
		'image/gif' : true,
		'image/bmp' : true
	}, maxSize = 5;

	if (!input.value) {
		return;
	}
	if (acceptedTypes[input.files[0].type] != true) {
		show_msg("<i class='icon icon-orange icon-alert'></i>请上传jpg、jpeg、bmp、png、gif格式的图片");
		return;
	}
	if (maxSize && input.files[0].size > maxSize * 1024 * 1024) {
		show_msg("<i class='icon icon-orange icon-alert'></i>请上传小于" + maxSize + "M的图片");
		return;
	}

	wait_for_uploading(1);
	upload_image();
}

function upload_image() {
	var upload_url = "uploadImageOfOpinion.action", get_image_url = "getImageOfOpinion.action?image=";
	var xhr = new XMLHttpRequest();
	var form = document.getElementById("image-form");
	xhr.open('post', upload_url);
	xhr.onreadystatechange = function() {
		if (xhr.status == 200) {
			if (xhr.readyState == 4) {
				var data = JSON.parse(xhr.responseText).ajaxObj;
				if (data.flag == 0) {
					wait_for_uploading(0,
							"<i class='icon icon-orange icon-alert'></i>" + data.msg);
				} else {
					wait_for_uploading(0);
					$("#input-image").val(data.file);
					draw_image(get_image_url + data.file);
				}
			}
		} else {
			wait_for_uploading(0, "<i class='icon icon-red icon-cross'></i>上传失败，请重新尝试");
		}
	}
	xhr.send(new FormData(form));
}

function show_msg(msg) {
	$("#help-msg").html("<span style='color:#ff0000;'>" + msg + "</span>");
}

function wait_for_uploading(status, msg) {
	if (status == 1) {
		$("#submit-but").attr("disabled", true);
		$("#image-canvas").hide();
		$("#image-loading").show();
		$("#help-msg").html("正在上传......");
	} else {
		$("#submit-but").attr("disabled", false);
		$("#image-loading").hide();
		$("#image-canvas").show();
		draw_image("images/visitor_opinion/upload-image.png");
		if (typeof (msg) == "undefined") {
			$("#help-msg").html("请把检测失败的图片上传，吐槽作者");
		} else {
			$("#help-msg").html(msg);
		}
	}
}