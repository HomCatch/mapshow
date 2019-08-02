<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../common/web/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta name="path" value="${pageContext.request.contextPath}" />

</head>


<body>



<div id="container" style="width: 485px; height: 175px; margin: 0  auto"></div>

<script language="JavaScript">

	var deviceId = $(window.parent.document).find("#f_content").attr("deviceId");
	var request = "deviceId=" + deviceId;

Highcharts.theme = {
		colors: ['#50B432', '#ED561B', '#058DC7', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
	
		credits: {
	          enabled:false
	},

		title: {
			style: {
				color: '#000',
				font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
			}
		},
		subtitle: {
			style: {
				color: '#666666',
				font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
			}
		},
		xAxis: {
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
			}
		},
		yAxis: {
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
			title: {
				style: {
					color: '#333',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: 'Trebuchet MS, Verdana, sans-serif'
				}
			}
		},
		legend: {
			itemStyle: {
				font: '9pt Trebuchet MS, Verdana, sans-serif',
				color: 'black'

			},
			itemHoverStyle: {
				color: '#039'
			},
			itemHiddenStyle: {
				color: 'gray'
			}
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
		}
	};

function showTable(before_tds, after_tds){
	   var chart = {
			      type: 'spline',
				  animation: Highcharts.svg, // don't animate in IE < IE 10.
			      marginRight: 50,
				  events: {
			         load: function () {
			            // set up the updating of the chart each second
			            var series = this.series[0];
			            var second = this.series[1];
			            
			            
			            setInterval(function () {
			               var x = (new Date()).getTime(), // current time
			               y = 10;
			              

			           	var deviceId = $(window.parent.document).find("#f_content").attr("deviceId");
			           	var request = "deviceId=" + deviceId;
			               
			           	$.ajax({  
			       	     type:'get',  
			       	     url:'/mapShow/getLatestTDS', 
			       	     data: request,
			       	     success:function(data){  
			       	    	 
			       	      				
			       	     var strs= new Array();
			       	     strs=data.split(",");
			       	    	
			       	      var value = eval(strs[1]);
			       	    	 series.addPoint([x, value], true, true);	
			       	    	 
			       	    	var value = eval(strs[0]);
			       	    	second.addPoint([x, value], true, true);	
			       	      },  
			       	      error:function(){
			       	    	  
			       	      }}) 
			           	}, 10000);
			            
			 
			            

			          
			         }
			      }
			   };
			   var title = {
			      text: ''   
			   };   
			   var xAxis = {
			      type: 'datetime',
			      tickPixelInterval: 75
			   };
			   var yAxis = {
			      title: {
			         text: ''
			      },
			      plotLines: [{
			         value: 0,
			         width: 1,
			         color: '#FF0000'
			      }]
			   };
		
			   
			   var tooltip = {
			      formatter: function () {
			      return '<b>' + this.series.name + '</b><br/>' +
			         Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
			         Highcharts.numberFormat(this.y, 2);
			      }
			   };
			   var plotOptions = {
			      area: {
			         pointStart: 1940,
			         marker: {
			            enabled: false,
			            symbol: 'circle',
			            radius: 2,
			            states: {
			               hover: {
			                 enabled: true
			               }
			            }
			         }
			      }
			   };
			
			   var legend = {
					      enabled: false
					   };
			   
			   var exporting = {
			      enabled: false
			   };
			   
			   var series= [
			   {
			      name: '净化后TDS',
			      data: (function () {
			         // generate an array of random data
			         var data = [],time = (new Date()).getTime(),i;
			         for (i = -5; i <= 0; i += 1) {
			            data.push({
			               x: time + i * 1000,
			               y: after_tds
			            });
			         }
			         return data;
			      }())    
			   },
			   
			   {
				      name: '净化前TDS',
				      data: (function () {
				         // generate an array of random data
				         var data = [],time = (new Date()).getTime(),i;
				         
				         for (i = -5; i <= 0; i += 1) {
				            data.push({
				               x: time + i * 1000,
				               y: before_tds
				            });
				         }
				         return data;
				      }())    
				   }  
			   ];     
			      
			   var json = {};   
			   json.chart = chart; 
			   json.title = title;     
			   json.tooltip = tooltip;
			   json.xAxis = xAxis;
			   json.yAxis = yAxis; 
			   json.legend = legend;  
			   json.exporting = exporting;   
			   json.series = series;
			   json.plotOptions = plotOptions;
			   
			   
			   Highcharts.setOptions({
			      global: {
			         useUTC: false
			      }
			   });
			   
			   Highcharts.setOptions(Highcharts.theme);
			   
			   $('#container').highcharts(json);	
}


$(document).ready(function() {  

	var deviceId = $(window.parent.document).find("#f_content").attr("deviceId");
	var request = "deviceId=" + deviceId;
	
	$.ajax({  
 	     type:'get',  
 	     url:'/mapShow/getLatestTDS', 
 	     data:request,
 	     success:function(data){  
 	 
 	    	 
       	     var strs= new Array();
       	     strs=data.split(",");
       	    	
       	      var after = eval(strs[1]);
       	      var before = eval(strs[0]);
 
 	    	  showTable(before, after);	      
 	    	
 	      },  
 	      error:function(){
 	    	alert("error");
 	      }}) 
     	
});
</script>

	<div style=" width:430px;height:50px;padding-bottom:9px;overflow:hidden;text-align:right">
		<input type="button" value="更多>>" onclick="parent.showDiv('showDiv','fade')" style="cursor: pointer;" />
	</div>

</body>



</html>