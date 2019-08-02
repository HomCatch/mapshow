<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<link href="${pageContext.request.contextPath}/css/amazeui.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/xiaohesys.css?time=201706141048" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/iconfont.js"></script>
	<style type="text/css">
		#list{margin:50px auto 0;padding:0;overflow:hidden;width:970px;}
		#list li{float:left;list-style:none;text-align:center;height:200px;border-radius:6px;font-size:18px;border:1px solid #ddd;width:300px;}
		#list li a{color:rgba(255,255,255,0.7);display: block;width: 100%;height: 100%;padding-top: 40px;font-family:"microsoft yahei";
					letter-spacing:3px;}
		#list li:hover a{color:#fff}
		.number{font-size: 50px;font-family: Arial;margin-top: 20px;}
		.icon {width: 1em; height: 1em;vertical-align: -0.15em;fill: currentColor;overflow: hidden;margin-right:10px;}

	</style>
</head>
<body style="width: 1350px;">
	<ul id="list">
		<li style="background-color:#568FBF">
			<a href="devtagmng?tagType=0">
				<div class="title"><svg class="icon" aria-hidden="true"><use xlink:href="#icon-icon1"></use></svg>设备数量</div>
				<div id="devTotal" class="number"></div>
			</a>
		</li>
		<li style="background-color:#8774A7;margin:0 35px;">
			<!-- <a href="devtagmng?tagType=1"> -->
			<a href="filter_plan"> <!-- 跳转到滤芯更换计划 -->
				<div class="title"><svg class="icon" aria-hidden="true"><use xlink:href="#icon-jingshuiqi"></use></svg>需换芯提醒</div>
				<div id="replaceCoreDev" class="number"></div>
			</a>
		</li>
		<li style="background-color:#E35A5A;">
			<a href="devtagmng?tagType=2">
				<div class="title"><svg class="icon" aria-hidden="true"><use xlink:href="#icon-guzhang"></use></svg>故障提醒</div>
				<div id="breakdownDev" class="number">0</div>
			</a>
		</li>
	</ul>
<script>

	$(function(){
		$.getJSON('/mapShow/getDeviceSummary', null, function (data) {
			if(data.code===200){
				console.log(data);
				var _data=data.obj;
				$('#devTotal').html(_data.devTotal);
				$('#replaceCoreDev').html(_data.replaceCoreDev);
				$('#breakdownDev').html(_data.breakdownDev);
			}
		})
	})

</script>
</body>
</html>
