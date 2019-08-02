var mychart = {

	showDiv : function(show_div, bg_div) {
		document.getElementById(show_div).style.display = 'block';
		document.getElementById(bg_div).style.display = 'block';
		var bgdiv = document.getElementById(bg_div);
		bgdiv.style.width = document.body.scrollWidth;
		$("#" + bg_div).height($(document).height());
	},

	closeDiv : function(show_div, bg_div) {
		document.getElementById(show_div).style.display = 'none';
		document.getElementById(bg_div).style.display = 'none';
	},

	getDeviceInfo : function(deviceId) {
		var result = {};
		var data = common.getlineGraphData(deviceId);
		var d_time = [];
		var d_tds = [];
		var d_purifyTds = [];
		var d_color = [];
		var d_purifyColor = [];
		var d_trt = [];
		var d_purifyTrt = [];
		var d_tbdt = [];
		var d_purifyTbdt = [];
		var d_amount = [];
		var d_purifyAmount = [];
		if (data && data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				if (i > 7) {
					break;
				}
				var recordTime = data[i].recordTime;
				var seconds = recordTime.seconds;
				if (seconds == 0) {
					seconds = "00";
				} else {
					if (seconds.toString().length == 1) {
						seconds = "0" + seconds;
					}
				}
				var minutes = recordTime.minutes;
				if (minutes == 0) {
					minutes = "00";
				} else {
					if (minutes.toString().length == 1) {
						minutes = "0" + minutes;
					}
				}
				var hours = recordTime.hours;
				if (hours == 0) {
					hours = "00";
				}
				recordTime = hours + ":" + minutes + ":" + seconds;

				d_time.push(recordTime);
				d_tds.push(data[i].tds);
				d_purifyTds.push(data[i].purifyTds);
				d_color.push(data[i].color);
				d_purifyColor.push(data[i].purifyColor);
				d_trt.push(data[i].trt);
				d_purifyTrt.push(data[i].purifyTrt);
				d_tbdt.push(data[i].tbdt);
				d_purifyTbdt.push(data[i].purifyTbdt);
				d_amount.push(data[i].amount);
				d_purifyAmount.push(data[i].purifyAmount);
			}
			result.time = d_time;
			result.tds = d_tds;
			result.purifyTds = d_purifyTds;
			result.color = d_color;
			result.purifyColor = d_purifyColor;
			result.trt = d_trt;
			result.purifyTrt = d_purifyTrt;
			result.tbdt = d_tbdt;
			result.purifyTbdt = d_purifyTbdt;
			result.amount = d_amount;
			result.purifyAmount = d_purifyAmount;
		}
		return result;
	},

	h_singleLine : function(divId, title, subtitle, data1, data2) {
		var v_max = Math.max.apply(null, data2);// 最大值

		var myChart = echarts.init(document.getElementById(divId));
		var option = {
			title : {
				text : title
			},
			legend : {
				data : [ subtitle ]
			},
			calculable : true,
			tooltip : {
				trigger : 'axis'
			},
			xAxis : [ {
				type : 'category',
				axisLine : {
					onZero : false
				},
				axisLabel : {
					formatter : '{value}'
				},
				boundaryGap : false,
				data : data1
			} ],
			yAxis : [ {
				name : '单位(L)',
				type : 'value',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '用水量(L)',
				type : 'line',
				smooth : true,
				itemStyle : {
					normal : {
						lineStyle : {
							shadowColor : 'rgba(0,0,0,0.4)'
						}
					}
				},
				data : data2
			} ]
		};
		myChart.setOption(option);
	},

	h_doubleLine_tds : function(divId, title, data1, data2, data3) {
		var v_max = Math.max.apply(null, data2);// 最大值
		var v_min = Math.min.apply(null, data2);// 最小值
		var myChart = echarts.init(document.getElementById(divId));
		var option = {
			title : {
				text : title,
				x : "26px",
				textStyle : {
					fontStyle : "normal"
				}
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '净化前', '净化后' ]
			},
			calculable : true,
			xAxis : [ {
				scale : true,
				type : 'category',
				boundaryGap : false,
				data : data1,
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			yAxis : [ {
				name : '单位(mg/L)',
				type : 'value',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '净化前',
				type : 'line',
				data : data2,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			}, {
				name : '净化后',
				type : 'line',
				data : data3,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ],
			animation : false
		};
		myChart.setOption(option);
	},

	h_doubleLine_color : function(divId, title, data1, data2, data3) {
		var v_max = Math.max.apply(null, data2);// 最大值
		var myChart = echarts.init(document.getElementById(divId));
		var option = {
			title : {
				text : title
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '净化前', '净化后' ]
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : data1,
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '净化前',
				type : 'line',
				data : data2,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			}, {
				name : '净化后',
				type : 'line',
				data : data3,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		};
		myChart.setOption(option);
	},

	h_doubleLine_trt : function(divId, title, data1, data2, data3) {
		var v_max = Math.max.apply(null, data2) + 3;// 最大值
		var v_min = Math.min.apply(null, data2);// 最小值
		var myChart = echarts.init(document.getElementById(divId));
		var option = {
			title : {
				text : title
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '净化前', '净化后' ]
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : data1,
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			yAxis : [ {
				name : '单位:(°C)',
				type : 'value',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '净化前',
				type : 'line',
				data : data2,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			}, {
				name : '净化后',
				type : 'line',
				data : data3,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		};
		myChart.setOption(option);
	},

	h_doubleLine_tbdt : function(divId, title, data1, data2, data3) {
		var v_max = Math.max.apply(null, data2);// 最大值
		var myChart = echarts.init(document.getElementById(divId));
		var option = {
			title : {
				text : title
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '净化前', '净化后' ]
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : data1,
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			yAxis : [ {
				name : '单位:(NTU)',
				type : 'value',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '净化前',
				type : 'line',
				data : data2,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			}, {
				name : '净化后',
				type : 'line',
				data : data3,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		};
		myChart.setOption(option);
	},

	h_doubleLine_amount : function(divId, title, data1, data2, data3) {
		var v_max = Math.max.apply(null, data2);// 最大值
		var v_min = Math.min.apply(null, data2);// 最小值
		var myChart = echarts.init(document.getElementById(divId));
		var option = {
			title : {
				text : title,
				x : "26px",
				textStyle : {
					fontStyle : "normal",
					color : "rgba(79, 76, 76, 0.61)"
				}
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '净化前', '净化后' ]
			},
			calculable : true,
			xAxis : [ {
				scale : true,
				type : 'category',
				boundaryGap : false,
				data : data1,
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			yAxis : [ {
				name : '单位(mg/L)',
				type : 'value',
				axisLabel : {
					formatter : '{value}'
				}
			} ],
			series : [ {
				name : '净化前',
				type : 'line',
				data : data2,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			}, {
				name : '净化后',
				type : 'line',
				data : data3,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		};
		myChart.setOption(option);
	},
	
	hightCharts:function(div,title,unit,xdata,ydata1,ydata2){
		Highcharts.setOptions({
			global : {
				useUTC : false
			}
		});
		$(div).highcharts({
			colors : [ 'red', 'green' ],
			chart : {
				backgroundColor: {
					linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
					stops: [
						[0, '#FFFFFF'],
						[1, '#FFFFFF']
					]
				},
				borderWidth: 0,
				plotBackgroundColor: 'rgba(255, 255, 255, .9)',
				plotShadow: true,
				plotBorderWidth: 1,
				animation : Highcharts.svg,
				type : 'spline'
			},
			title : {
				text : title,
				align : 'left',
				floating : false,
				x : 26,
				style : {
					fontWeight : 'bold'
				}
			},
			xAxis : {
				 gridLineWidth: 1,
		    		lineColor: '#000',
		    		tickColor: '#000',
		    		labels: {
		    			style: {
		    				color: '#000',
		    				font: '11px Trebuchet MS, Verdana, sans-serif'
		    			}
		    		},
		    		title: {
		    			style: {
		    				color: '#333',
		    				fontWeight: 'bold',
		    				fontSize: '12px',
		    				fontFamily: 'Trebuchet MS, Verdana, sans-serif'

		    			}
		    		},
		    	categories : xdata
			},
			yAxis : {
				endOnTick: true,
	            startOnTick: true,
	            minorTickInterval: 'auto',
	    		lineColor: '#000',
	    		lineWidth: 1,
	    		tickWidth: 1,
	    		tickColor: '#000',
	    		labels: {
	    			style: {
	    				color: '#000',
	    				font: '11px Trebuchet MS, Verdana, sans-serif'
	    			}
	    		},
				title : {
					style: {
						color: '#333',
						fontWeight: 'bold',
						fontSize: '12px',
						fontFamily: 'Trebuchet MS, Verdana, sans-serif'
					},
					text : unit
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			tooltip : {
				borderColor: '#000000',
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
				itemStyle: {
					font: '9pt Trebuchet MS, Verdana, sans-serif',
					color: 'black'

				},
				itemHoverStyle: {
					color: '#039'
				},
				itemHiddenStyle: {
					color: 'gray'
				},
				layout : 'horizontal',
				align : 'center',
				verticalAlign : 'center	',
				y : -5,
				borderWidth : 0
			},
			labels: {
				style: {
					color: '#99b'
				}
			},
			navigation: {
				buttonOptions: {
					theme: {
						stroke: '#CCCCCC'
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
					symbol : 'square'
				},
				data : ydata1

			}, {
				name : '净化后',
				marker : {
					symbol : 'diamond'
				},
				data : ydata2
			} ]
		});
	}
}