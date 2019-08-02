<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>管理登录</title>
<link href="${pageContext.request.contextPath}/css/login.css" type="text/css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
</head>
<body>
	<div class="login">
		<div class="message">智水小荷-管理登录</div>
		<div id="darkbannerwrap"></div>
		<input name="action" value="login" type="hidden"> <input id="username" name="username" placeholder="用户名" required="" type="text">
		<hr class="hr15">
		<input id="password" name="password" placeholder="密码" required="" type="password">
		<hr class="hr15">
		<input value="登录" id="lo-btn" style="width: 100%;" type="button" />
		<hr class="hr20">
		<!-- 帮助 <a onClick="alert('请联系管理员')">忘记密码</a> -->
	</div>

	<div class="copyright">
		©2016 He-live.com</a>
	</div>
	<script type="text/javascript">
		$(function() {
			document.onkeydown = function(event) {
				var e = event || window.event || arguments.callee.caller.arguments[0];
				if (e && e.keyCode == 13) { // enter 键
					$("#lo-btn").click();
				}
			};

			$("#lo-btn").click(function() {
				var username = $("#username").val();
				var password = $("#password").val();
				if (username == '' || password == '') {
					layer.alert('用户名密码不能为空', {
						icon : 2
					});
					return;
				}
				var url = "${pageContext.request.contextPath}/login";
				var params = {
					username : username,
					password : password
				};
				$.post(url, params, function(result) {
					if (result.code == 200) {
						top.window.location.href = "${pageContext.request.contextPath}/loginSucc";
					} else {
						layer.alert(result.msg,{
							icon : 2
						});
					}
				}, "json");
			});
		});
	</script>
</body>
</html>