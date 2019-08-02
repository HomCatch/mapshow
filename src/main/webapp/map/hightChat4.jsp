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
			
			var data = mychart.getDeviceInfo(deviceId);
			$("#e_color").empty();
			$("#e_trt").empty();
			$("#e_tbdt").empty();
			$("#e_amount").empty();
			if (data) {
				mychart.hightCharts("#e_color", "色度", "", data.time, data.color, data.purifyColor);
				mychart.hightCharts("#e_trt", "温度", "单位(°C)", data.time, data.trt, data.purifyTrt);
				mychart.hightCharts("#e_tbdt", "水浊度", "单位(NTU)", data.time, data.tbdt, data.purifyTbdt);
				mychart.hightCharts("#e_amount", "水量", "单位(L)", data.time, data.amount, data.purifyAmount);
			}
		}
	}
	
	initChart4();
	
	$(document).ready(function() {
		setInterval("initChart4()", 15000);
	});
	
</script>
</html>