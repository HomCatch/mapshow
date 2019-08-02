<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../common/web/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta name="path" value="${pageContext.request.contextPath}" />
<script src="${pageContext.request.contextPath}/map/js/myCharts.js"></script>
<script src="${pageContext.request.contextPath}/map/js/bmap.js"></script>
</head>
<body>
	<table style="width: 100%;">
		<tr>
			<td>
				<div id="e_color" style="width: 450px; height: 295px;"></div>
			</td>
			<td>
				<div id="e_trt" style="width: 450px; height: 295px;"></div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="e_tbdt" style="width: 450px; height: 295px;"></div>
			</td>
			<td>
				<div id="e_amount" style="width: 450px; height: 295px;"></div>
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">

	var deviceId = $(window.parent.document).find("#f_content4").attr("deviceid");
	
	function initChart4() {
		if(deviceId!=''){
			bmap.popupEcharts(deviceId);
		}
	}
	
	initChart4();
	
	$(document).ready(function() {
		setInterval("initChart4()", 15000);
	});
	
</script>
</html>