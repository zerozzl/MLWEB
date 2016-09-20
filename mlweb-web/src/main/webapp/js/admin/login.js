function login() {
	$.ajax({
		traditional : true,
		type : "post",
		dataType : "json",
		cache : false,
		timeout : 10000,
		url : "adLogin.action",
		data : $("#login-form").serialize(),
		beforeSend : function() {
			$('#inemail').addClass('disabled');
			$('#inemail').prop('disabled', true);
			$('#inpassword').addClass('disabled');
			$('#inpassword').prop('disabled', true);
			$('#submit-but').addClass('disabled');
			$('#submit-but').prop('disabled', true);
			$('#submit-but').html('正在登录......');
		},
		success : function(data, textStatus) {
			if (data.ajaxObj.flag == 1) {
				location.href = "admin/adIndex.action";
			} else {
				$("#signin-msg").html(data.ajaxObj.msg);
				$("#signin-response").show();
				$("#inpassword").val("");

				$('#inemail').removeClass('disabled');
				$('#inemail').prop('disabled', false);
				$('#inpassword').removeClass('disabled');
				$('#inpassword').prop('disabled', false);
				$('#submit-but').removeClass('disabled');
				$('#submit-but').prop('disabled', false);
				$('#submit-but').html('登录');
			}
		},
		error : function(jqXHR, status, errorThrown) {
			if (status == "timeout") {
				$("#signin-msg").html("登录超时");
			} else {
				$("#signin-msg").html("登录过程出现异常");
			}
			$("#signin-response").show();

			$('#inemail').removeClass('disabled');
			$('#inemail').prop('disabled', false);
			$('#inpassword').removeClass('disabled');
			$('#inpassword').prop('disabled', false);
			$('#submit-but').removeClass('disabled');
			$('#submit-but').prop('disabled', false);
			$('#submit-but').html('登录');
		}
	});
}

function close_msg() {
	$("#signin-response").hide();
}