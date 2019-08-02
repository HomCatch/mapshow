<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" href="css/font-awesome.min.css">
<link href="${pageContext.request.contextPath}/css/amazeui.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/xiaohesys.css?time=201706141047" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.uploadify.min.js"></script>
<style type="text/css">
	ul{padding:0;margin:0;}
	ul li{list-style: none;}
	#deviceListWrap{text-align:center;width:450px;margin:0 auto;position:fixed;top:0;left:0;display:none;
		width:100%;height:100%;z-index:99999;background:rgba(0,0,0,0.85)}
	#deviceListDiv{width: 500px;background-color: #fff;border-radius: 5px;padding: 15px 30px;margin: 50px auto;position:relative;}
	#deviceList{width:420px;padding:0;}
	#deviceList table th{text-align:center;font-size:15px;background-color: #f0f0f0}
	#deviceList table td{font-size:14px;}
	.device{cursor:pointer;}
	.device:hover{background-color: #eae5e5!important;}
	.device:hover th,.device:hover td{background-color: #eae5e5!important;}
	.select-text{color: #333;font-size:18px;margin:20px 0;}
	#btnHideDevice{font-size:18px;color:#999;position:absolute;right:10px;top:10px;}
	#btnHideDevice:hover{color:rgb(68, 123, 234);}
	#selectError{font-size: 12px;color: red;display:none;}
</style>
</head>
<body style="width: 1350px;">
<%-- 选择设备--%>
	<div id="deviceListWrap">
		<div id="deviceListDiv">
			<h3 class="select-text">请选择设备</h3>
			<!--记录列表开始-->
			<div class="jilu-list" id="deviceList">
				<table class="am-table am-table-bordered am-table-striped">
					<thead>
						<tr>
							<th>设备ID</th>
							<th>设备别名</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(page.list)>0 }">
							<c:forEach items="${ page.list }" var="item">
								<tr class="device">

									<td class="deviceId">${item.deviceId}</td>
									<td>${item.deviceName}</td>

								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${ page.list ==null || fn:length(page.list)==0 }">
								<tr>
									<td colspan="100">没有记录</td>
								</tr>
						</c:if>
					</tbody>
				</table>
				<i class="fa fa-times" id="btnHideDevice"></i>
			</div>
			<!--记录列表结束-->
			<!--分页开始-->
			<div class="he-page">
				<%@ include file="public/page.jsp"%>
			</div>
			<!--分页结束-->
		</div>

	</div>

	<div style="display: none;">
		<span id="list_url">${pageContext.request.contextPath}/devmng</span>
		<span id="save_url">${pageContext.request.contextPath}//saveDevice</span>
		<span id="get_url">${pageContext.request.contextPath}/getDeviceInfo</span>
	</div>

	<script>

		$('#btnHideDevice').on('click',function(){
			$('#deviceListWrap').slideUp();
		});
		$('.device').on('click',function(){
			if(s){
				clearInterval(s)
			}
			$(this).addClass('selected');
			$('#deviceListWrap').slideUp();
			targetDeviceID=$(this).find('.deviceId').html();
			initData(targetDeviceID)
			s = setInterval(function () {
				initData(targetDeviceID)
			}, ${ startup.interval })
			var url = "/mapShow/getLatestWaterQuality2?deviceId="+targetDeviceID;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
					console.log(data);
					if(data){
						dataUrl='/mapShow/getLatestWaterQuality2';
						$('#deviceId2').html(data.deviceId);
						drawRecent7daysTds();
						deviceDashBoard(data);
						getLatestTdsData();
					}
				},
				error:function(msg){
					console.log(msg);
				}
			});

		myChart.setOption({
			series: [{
				data: inTdsData
			},

			{
				data: outTdsData
			}]
		});
		});

		function deviceDashBoard(data){
			$('#deviceId2').html(data.deviceId);
			$('#dayFlux').html(data.today_amount.toFixed(1));
			$('#weekFlux').html(data.week_amount.toFixed(1));
			$('#monthFlux').html(data.month_amount.toFixed(1));

			var count = ${startup.deviceCount};
			var score = ${startup.evaluate};
			var tdsRange = new Array(1, 500);
			var sdRange = new Array(0.1, 3.0);
			var zdRange = new Array(0.1, 3.0);
			var scoreRnage = new Array(0, 100);

			// id 范围区间 背景色 当前值 质量 值单位
			addGuage2('tdsIn', tdsRange, '#8774A7', data.latest_in_tds, data.in_tds_quality, 'TDS单位', ${startup.interval});
			addGuage2('sdIn', sdRange, '#E35A5A', data.latest_in_sd, data.in_sd_quality, '色度单位', ${startup.interval});
			addGuage2('zdIn', zdRange, '#44B7AF', data.latest_in_zd, data.in_zd_quality, '浊度单位', ${startup.interval});

			addGuage2('tdsOut', tdsRange, '#8774A7', data.latest_out_tds, data.out_tds_quality, 'TDS单位', ${startup.interval});
			addGuage2('sdOut', sdRange, '#E35A5A', data.latest_out_sd, data.out_sd_quality, '色度单位', ${startup.interval});
			addGuage2('zdOut', zdRange, '#44B7AF', data.latest_out_zd, data.out_zd_quality, '浊度单位', ${startup.interval});


			addGuage2('score2', scoreRnage, '#568FBF', score,  '优', '浊度单位', ${startup.interval});

			$('.highcharts-label-box.highcharts-data-label-box').css('display', 'none')
	}
		</script>
	<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>

