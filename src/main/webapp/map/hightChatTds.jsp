<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../common/web/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script src="${pageContext.request.contextPath}/map/js/myCharts.js"></script>
<link href="${pageContext.request.contextPath}/map/css/bmap.css" rel="stylesheet" />

<style type="text/css">
.morediv {
	position: absolute;
	z-index: 1;
	float: right;
	top: 15px;
	right: 36px;
	background-color: white;
	overflow: auto;
}
</style>

</head>
<body>
	<div id="main_tds" style="width: 452px; height: 205px;"></div>


	<div style='width: 100%; text-align: center; padding-top: 3px; padding-left: 8.25px;'>
		<table class='popupContent' cellspacing='0' cellpading='0'>
			<tr style='background-color: #DB9C86; font-weight: bold;'>
				<td>名称</td>
				<td>测量值</td>
			</tr>

			<tr>
				<td>TDS</td>
				<td><span id='pop_purifyTds'></span>&nbsp;（mg/L）</td>
			</tr>
			<tr>
				<td>色度</td>
				<td><span id='pop_purifyColor'></span>&nbsp;</td>
			</tr>
			<tr>
				<td>水浊度</td>
				<td><span id='pop_purifyTbdt'></span>&nbsp;（NTU）</td>
			</tr>
			<tr>
				<td>温度</td>
				<td><span id='pop_purifyTrt'></span>&nbsp;（°C）</td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	var deviceId = $(window.parent.document).find("#f_content").attr("deviceId");
	
	function setContent(data) {
		var p_amount = 0;
		var purifyAmount = data[0].purifyAmount;
		if(purifyAmount!=null){
			p_amount = parseFloat(purifyAmount);
		} 
		$("#pop_purifyAmount").text(p_amount.toFixed(4));
		$("#pop_purifyTds").text(parseFloat(data[0].purifyTds).toFixed(4));
		$("#pop_purifyColor").text(parseFloat(data[0].purifyColor).toFixed(4));
		$("#pop_purifyTbdt").text(parseFloat(data[0].purifyTbdt).toFixed(4));
		$("#pop_purifyTrt").text(parseFloat(data[0].purifyTrt).toFixed(4));
	}
	
	function showHightCharts(result){
		setContent(result);

		var tds = getTds(result);
		var purifyTds = getPurifyTds(result);
		
		Highcharts.setOptions({
			global : {
				useUTC : false
			}
		});

		var chart;

		$('#main_tds').highcharts({
			colors : [ 'red', 'green' ],
			chart : {
				backgroundColor : {
					linearGradient : {
						x1 : 0,
						y1 : 0,
						x2 : 1,
						y2 : 1
					},
					stops : [ [ 0, '#FFFFFF' ], [ 1, '#FFFFFF' ] ]
				},
				borderWidth : 0,
				plotBackgroundColor : 'rgba(255, 255, 255, .9)',
				plotShadow : true,
				plotBorderWidth : 1,
				animation : Highcharts.svg,
				type : 'spline',
				events : {
					load : function() {
						var series_tds = this.series[0];
						var series_purifyTds = this.series[1];
						setInterval(function() {
							jQuery.getJSON('/mapShow/getlineGraphData?deviceId=' + deviceId, null, function(data) {
								var x = (new Date()).getTime();

								var tds = data[0].tds;
								var purifyTds = data[0].purifyTds;
								series_tds.addPoint([ x, tds ], true, true);
								series_purifyTds.addPoint([ x, purifyTds ], true, true);

								setContent(data);
							});
						}, 4000);
					}
				}
			},
			title : {
				text : 'tds',
				align : 'left',
				floating : false,
				x : 26,
				style : {
					fontWeight : 'bold'
				}
			},
			xAxis : {
				gridLineWidth : 1,
				lineColor : '#000',
				tickColor : '#000',
				labels : {
					style : {
						color : '#000',
						font : '11px Trebuchet MS, Verdana, sans-serif'
					}
				},
				title : {
					style : {
						color : '#333',
						fontWeight : 'bold',
						fontSize : '12px',
						fontFamily : 'Trebuchet MS, Verdana, sans-serif'

					}
				},
				type : 'datetime'
			},
			yAxis : {
				endOnTick : true,
				startOnTick : true,
				minorTickInterval : 'auto',
				lineColor : '#000',
				lineWidth : 1,
				tickWidth : 1,
				tickColor : '#000',
				labels : {
					style : {
						color : '#000',
						font : '11px Trebuchet MS, Verdana, sans-serif'
					}
				},
				title : {
					style : {
						color : '#333',
						fontWeight : 'bold',
						fontSize : '12px',
						fontFamily : 'Trebuchet MS, Verdana, sans-serif'
					},
					text : '单位(mg/L)'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			tooltip : {
				borderColor : '#000000',
				crosshairs : true,
				shared : true
			},
			plotOptions : {
				spline : {
					marker : {
						radius : 4,
						lineColor : '#666666',
						lineWidth : 1
					}
				}
			},
			legend : {
				itemStyle : {
					font : '9pt Trebuchet MS, Verdana, sans-serif',
					color : 'black'

				},
				itemHoverStyle : {
					color : '#039'
				},
				itemHiddenStyle : {
					color : 'gray'
				},
				layout : 'horizontal',
				align : 'center',
				verticalAlign : 'center	',
				y : -5,
				borderWidth : 0
			},
			labels : {
				style : {
					color : '#99b'
				}
			},
			navigation : {
				buttonOptions : {
					theme : {
						stroke : '#CCCCCC'
					}
				}
			},
			credits : {
				enabled : false
			},
			exporting : {
				enabled : false
			},
			series : [ {
				name : '净化前',
				marker : {
					symbol : 'diamond'
				},
				data : tds

			}, {
				name : '净化后',
				marker : {
					symbol : 'diamond'
				},
				data : purifyTds
			} ]
		});
	}


	function getTds(result) {
		var data = [], time = (new Date()).getTime();
		for (i = -5; i <= 0; i += 1) {
			data.push({
				x : time + i * 1000,
				y : result[0].tds
			});
		}
		return data;
	}

	function getPurifyTds(result) {
		var data = [], time = (new Date()).getTime();
		for (i = -5; i <= 0; i += 1) {
			data.push({
				x : time + i * 1000,
				y : result[0].purifyTds
			});
		}
		return data;
	}


	$(document).ready(function() {
		var url = "/mapShow/getlineGraphData?deviceId=" + deviceId;
		var result;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
				showHightCharts(data);
			}
		});
	});
</script>
</html>