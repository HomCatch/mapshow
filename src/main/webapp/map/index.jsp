<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../common/web/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" scope="request" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta name="path" value="${path}" />


<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=mqUIE6D6lxbYTfVWuqwfveBsxVLa06eG"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>

<link href="${path}/common/css/popup.css" rel="stylesheet" />
<script src="${path}/map/js/bmap_data.js"></script>
<script src="${path}/map/js/bmap.js?time=1139"></script>
<script src="${path}/map/js/myCharts.js"></script>
<link href="${path}/map/css/bmap.css" rel="stylesheet" />



<title>地图</title>

<script type="text/javascript">
	$(function() {
		var map = new BMap.Map("allmap");
		var point = bmap.getPoint(105.000, 38.000);
		map.centerAndZoom(point, 10);

		var keyword = "${keyword }";
		var keyword_txt = "${keyword_txt }";
		var regionId = "${regionId }";
		var isGlobal = "${isGlobal}";

		//初始化地图数据
		initMapData(map, isGlobal, regionId, keyword, keyword_txt);

		// 地图控件
		//map.addControl(bmap.getMapTypeControl());
		map.addControl(bmap.getNavigationControl());
		// 支持鼠标滚动
		map.enableScrollWheelZoom(true);
		/* map.setMapStyle({
			styleJson : common.mapStyle
		}); */

		//初始化查询参数
		initSearchParams(regionId, keyword);
		//左侧设备列表单击定位事件
		clickDevice(map);

		//bmap.mapClick(map);
		
		//右上角汇总
		SumTotal(map, isGlobal, regionId, keyword, keyword_txt);
		
		//设置左侧图标
		$("img[name=logoImg]").attr("src",common.logoImg);
		
		//IE下最小高度显示
		setMinHeight();
	});

	function setMinHeight() {
		var c_height = $("#content").height();
		if (c_height > 600) {
			$("#content").height(600);
		}
		var h = $("#showDiv").height() - 40;
		$("#charts4").height(h);
	}

	function SumTotal(map, isGlobal, regionId, keyword, keyword_txt){
		if (isGlobal == 1){
			bmap.getSum(keyword, keyword_txt, 0, 0, 0, 0, 0);
			$(".tiph4").text("全国汇总");
		}else if (isGlobal == 0 || isGlobal == -2){
			bmap.getSum(keyword, keyword_txt, regionId, 0, 0, 0, 0);
			$(".tiph4").text(keyword_txt + "汇总");
		}else if (isGlobal == -1){
			var xmin = $("#xmin").val();
			var xmax = $("#xmax").val();
			var ymin = $("#ymin").val();
			var ymax = $("#ymax").val();
			if(xmin!=''){
				bmap.getSum("", "", 0, xmin, xmax, ymin, ymax);
			}
			$(".tiph4").text(keyword_txt + "汇总");
		}
	}
	
	//初始化地图数据
	function initMapData(map, isGlobal, regionId, keyword, keyword_txt) {
		
	//	alert(keyword + ", " + keyword_txt);
		if (isGlobal == 1) {//定位全国
			//全国地图
			var point = bmap.getPoint(105.000, 38.000);
			map.centerAndZoom(point, 5);
			//查询设备以及描点(聚合)
			bmap.getDeviceAndAddMarker(map, regionId, true, '');
			
		} else if (isGlobal == 0 || isGlobal == -2) {//根据区域/设备号
			//定位到具体区域
			var r_x = "${devices[0].deviceX }";
			var r_y = "${devices[0].deviceY }";

			var deviceId = '';
			if (isGlobal == -2) {
				deviceId = keyword;
			}
			bmap.setCenterByPoint(map, r_x, r_y);
			//查询设备以及描点(不聚合)
			bmap.getDeviceAndAddMarker(map, regionId, false, deviceId);
			// 区域圆
			bmap.addCircle(map, r_x, r_y, 50000);
		} else if (isGlobal == -1) {//根据关键字定位
			if (keyword_txt != '' && keyword_txt != '全国') {
				if (keyword == '') {
					keyword = keyword_txt;
				}
				var localSearch = new BMap.LocalSearch(map);
				localSearch.enableAutoViewport(); //允许自动调节窗体大小

				localSearch.setSearchCompleteCallback(function(searchResult) {
					var point = searchResult.getPoi(0).point;
					var c_x = point.lng;
					var c_y = point.lat;

					var circle;
					if (keyword.indexOf('桃源村') > 0 || keyword.indexOf('桐梓县') > 0) {
						map.centerAndZoom(point, 18);
						circle = bmap.addCircle(map, c_x, c_y, 300);
					} else if (keyword.indexOf('贵阳') > 0) {
						map.centerAndZoom(point, 11);
						circle = bmap.addCircle(map, c_x, c_y, 30000);
					}else {
						map.centerAndZoom(point, 12);
						circle = bmap.addCircle(map, c_x, c_y, 20000);
					}

					//bmap.addGlobalRegion(map, keyword);

					var cir_bounds = circle.getBounds();
					var b_Ie = cir_bounds.Ie;
					var b_De = cir_bounds.De;
					var b_Je = cir_bounds.Je;
					var b_Ee = cir_bounds.Ee;

					var b_url = "${path}/getDeviceByPoint";
					var b_data = {
						xmin : b_Je,
						xmax : b_Ee,
						ymin : b_Ie,
						ymax : b_De
					};
					
					$("#xmin").val(b_Je);
					$("#xmax").val(b_Ee);
					$("#ymin").val(b_Ie);
					$("#ymax").val(b_De);
					
					$.post(b_url, {
						json : JSON.stringify(b_data)
					}, function(data) {
						//左边设备列表
						dataObj.getLeftContent(map, data.list);

						//汇总
						dataObj.getRightSum(data.sum);

						//设备click事件
						clickDevice(map);
						//设置最小高度
						setMinHeight();

						//bmap.updateShenzhTds(data.list);

						//刷新
						document.getElementById('f_content4').contentWindow.location.reload(true);
					}, "json");
				});
				if (!isNaN(keyword)) {
					localSearch.search(keyword_txt);
				} else {
					localSearch.search(keyword);
				}
			}
		}

	}

	//初始化查询参数
	function initSearchParams(regionId, keyword) {
		var keyword_txt = "${keyword_txt}";
		if (keyword_txt != '') {
			$("#txtSearch").val(keyword_txt);
		}
		//下拉框回显值
		if (keyword != '') {
			$("#keyword").val(keyword);
		}
		if (regionId != '' || keyword_txt != '') {
			$("#slSearch").val(regionId);
			$("#keyword").val(regionId);
		}
	}

	//左侧设备列表单击定位事件
	function clickDevice(map) {
		$("li[name=d_device]").bind("click", function() {
			var deviceId = $(this).attr("id");
			$.post("${path}/retriveDeviceInfo", {
				deviceId : deviceId
			}, function(data) {
				if (data && data.length > 0) {
					var d_x = data[0].deviceX;
					var d_y = data[0].deviceY;
					bmap.setCenterByPoint(map, d_x, d_y);

					var deviceId = data[0].deviceId;
					$("#d_deviceId").val(deviceId);
					$("#f_content4").attr("deviceId", deviceId);
					//打开信息框
					var m_opions = {
						x : d_x,
						y : d_y,
						content : dataObj.getContent(data[0]),
						type : 1,
						isopen : 0
					};
					bmap.openInfoWindow(map, null, m_opions);
					document.getElementById('f_content4').contentWindow.location.reload(true);
				}
			}, "json");
		});
	}
</script>

<style type="text/css">
.optiont {
	background-color: black;
	color: white;
}
</style>

</head>
<body>
	<input type="hidden" id="xmin" />
	<input type="hidden" id="xmax" />
	<input type="hidden" id="ymin" />
	<input type="hidden" id="ymax" />
	<input type="hidden" id="d_deviceId" />
	<div id="allmap"></div>
	<div id="search">
		<div class="insidediv">
			<table>
				<tr>
					<td width="85%">
						<table style="width: 100%; margin: 0px; padding: 0px;">
							<tr>
								<td style="width: 100%;">
									<div style="position: relative;">
										<select id="slSearch" name="slSearch" style="width: 100%;" class="se1" onchange="slChange()">
										
											<c:forEach items="${regions }" var="item">
												<option value="${item.regionId }">${item.regionName }</option>
											</c:forEach>
										</select> <input id="txtSearch" class="input1" name="txtSearch" style="position: absolute; width: 90%; height: 28px; left: 1px; top: 2px; border-bottom: 0px; border-right: 0px; border-left: 0px; border-top: 0px;" /> <input id="keyword" name="keyword" type="hidden" />
									</div>
								</td>
							</tr>
						</table>
					</td>
					<td style="vertical-align: bottom;"><input type="button" class="inbtn1" value="搜索" id="btnSearch" onclick="bmap.loadMap()"></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="content" style="display: none;">
		<div style="font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: arial, sans-serif; border: 1px solid rgb(153, 153, 153);">
			<div style="background: rgb(255, 255, 255);">
				<ol style="list-style: none; padding: 0px; margin: 0px;">
					<span id="content2"> <c:if test="${fn:length(requestScope.devices)==0 && isGlobal==0}">
							<li style="margin: 2px 0px; padding: 0px 5px 0px 3px; overflow: hidden; line-height: 17px;">抱歉，未找到相关设备。</li>
						</c:if> <c:if test="${fn:length(requestScope.devices) > 0 }">
							<c:forEach items="${devices }" var="item">
								<li name="d_device" id="${item.deviceId }" style="margin: 2px 0px; padding: 0px 5px 5px 0px; cursor: pointer; overflow: hidden; line-height: 17px;">
								<span style="width: 30px; height: 35px; cursor: pointer; float: left; *zoom: 1; overflow: hidden; margin: 2px 3px 0 5px; *margin-right: 0px; display: inline;">
									<img name="logoImg" src="">
								</span>
									<div style="zoom: 1; overflow: hidden; padding: 0px 5px; background-color: rgb(240, 240, 240);">
										<div style="line-height: 20px; font-size: 12px;">
											<span style="color: #00c;"><b>设备：${item.deviceId }(${item.deviceId})</b></span> 
											<a href="#" style="margin-left: 5px; font-size: 12px; color: #3d6dcc; font-weight: normal; text-decoration: none;">MORE»</a>
										</div>
										<div style="padding: 2px 0; line-height: 18px; *zoom: 1; overflow: hidden;">
											<b style="float: left; font-weight: bold; *zoom: 1; overflow: hidden; padding-right: 5px; *margin-right: -3px;">TDS值:</b> 
											<span style="color: #666; display: block; zoom: 1; overflow: hidden;"><b>${item.purifyTds }</b></span>
										</div>
										<div style="padding: 2px 0; line-height: 18px; *zoom: 1; overflow: hidden;">
											<b style="float: left; font-weight: bold; *zoom: 1; overflow: hidden; padding-right: 5px; *margin-right: -3px;">用水量:</b> 
											<span style="color: #666;">${item.purifyAmount }</span>
										</div>
									</div></li>
							</c:forEach>
						</c:if>
					</span>
				</ol>
			</div>
		</div>

	</div>
	<div class="flip">
		<img style="cursor: pointer;" src="${path }/common/img/jiantou.jpg" />
	</div>

	<div id="sum" class="sum1">
		<div class="insidediv" id="insidediv_sum">
			<h4 class='tiph4'></h4>

			<table class="sumtable sumtable1" cellspacing="0" cellpading="0">
				<tr>
					<td>&nbsp;</td>
					<td>净化前</td>
					<td>净化后</td>
					<td>百分比</td>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td>TDS</td>
					<td><span id="s_tds"></span>&nbsp;</td>
					<td><span id="s_purifyTds"></span>&nbsp;</td>
					<td>&nbsp; <span id="p_s_tds"></span>%</td>
					<td><img alt="" src="./common/img/xiajiang2.png" /></td>
				</tr>
				<tr>
					<td>色度</td>
					<td><span id="s_color"></span>&nbsp;</td>
					<td><span id="s_purifyColor"></span>&nbsp;</td>
					<td>&nbsp; <span id="p_s_color"></span> %</td>
					<td><img alt="" src="./common/img/xiajiang2.png" /></td>
				</tr>
				<tr>
					<td>水浊度</td>
					<td><span id="s_tbdt"></span>&nbsp;</td>
					<td><span id="s_purifyTbdt"></span>&nbsp;</td>
					<td>&nbsp; <span id="p_s_tbdt"></span> %</td>
					<td><img alt="" src="./common/img/xiajiang2.png" /></td>
				</tr>
				<tr>
					<td>温度</td>
					<td><span id="s_trt"></span>&nbsp;</td>
					<td><span id="s_purifyTrt"></span>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>

			</span>
		</div>
	</div>

	<div id="mapversion">版本号：${lastVersion }</div>
	<div id="fade" class="black_overlay"></div>
	<div id="showDiv" class="white_content">
		<div style="text-align: right; cursor: default; height: 40px;">
			<img alt="关闭" style="cursor: pointer;" src="${path }/common/img/close.png" onclick="mychart.closeDiv('showDiv','fade')" />
			<table style="width: 100%;">
				<tr>
					<td id="charts4"><Iframe id='f_content4' deviceId="${devices[0].deviceId}" src='/mapShow/map/lineGraph4.jsp' width='100%' height='100%' scrolling='NO' frameborder='0' name='main'></iframe></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	//弹出隐藏层
	function showDiv(show_div, bg_div) {
		mychart.showDiv(show_div, bg_div);
	};

	function slChange() {
		var val = $("#slSearch").val();
		var text = $("#slSearch").find("option:selected").text();
		$("#txtSearch").val(text);
		$("#keyword").val(val);
		bmap.loadMap();
	}

	$(document).ready(function() {
		$(".flip").click(function() {
			$("#content").slideToggle("slow");
		});
	});
</script>
