<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/web/common.jsp"%>
<%@ include file="selectDevice.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script src="${pageContext.request.contextPath}/map/js/myCharts.js"></script>
<link href="${pageContext.request.contextPath}/map/css/bmap.css"
	rel="stylesheet" />
<script src="${pageContext.request.contextPath}/js/highcharts-more.js"></script>
<script
	src="${pageContext.request.contextPath}/js/dashboard.js?time=0707151601"></script>
<script src="js/charts/Chart.js"></script>
<script type="text/javascript"
	src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
<script type="text/javascript"
	src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
<script type="text/javascript"
	src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
<script type="text/javascript"
	src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
<script type="text/javascript"
	src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>

<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/index.css?update=14">

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

.gauge {
	padding: 0;
	margin: 15px 0 0 40px;
}

ul {
	padding: 0;
	margin: 0;
}

ul li {
	list-style: none;
}

#btnShowDevice {
	position: fixed;
	z-index: 999;
	top: 5px;
	right: 35px;
	border-radius: 50%;
	background-color: #447bea;
	width: 30px;
	height: 30px;
	text-align: center;
	line-height: 28px;
	font-size: 16px;
	color: #fff;
	cursor: pointer;
	text-decoration: none;
}

#btnShowDevice:hover {
	background-color: #2557ea;
}

#amountStatic li {
	text-align: left;
	margin-bottom: 1px;
}

#amountStatic li span {
	font-size: 16px;
	margin: 0 3px;
}
</style>

</head>
<body>

	<div class='container'>
		<section>
			<div class="guageContainer score2"
				style="background: #568FBF; margin-bottom: 0">
				<div class="title">综合评分</div>
				<div class="deviceData"></div>
				<div class="quality">优</div>
				<div id="score2" class="gauge">90</div>
			</div>


			<div class="guageContainer tdsIn" style="background: #8774A7">
				<div class="title">入水TDS</div>
				<div class="quality"></div>
				<div class="deviceData"></div>
				<div id="tdsIn" class="gauge"></div>
			</div>
			<div class="guageContainer sdIn" style="background: #E35A5A">
				<div class="title">入水色度</div>
				<div class="deviceData"></div>
				<div class="quality"></div>
				<div id="sdIn" class="gauge"></div>
			</div>
			<div class="guageContainer zdIn" style="background: #44B7AF">
				<div class="title">入水浊度</div>
				<div class="deviceData"></div>
				<div class="quality"></div>
				<div id="zdIn" class="gauge"></div>
			</div>

			<div id="amountStatic" class="guageContainer tdsIn"
				style="background: #568FBF; margin-bottom: 0; color: #fff;">
				<ul style="font-size: 12px; padding-top: 30%; padding-left: 15px;">
					<li style="margin-bottom: 10px;">设备编码：<span id="deviceId2"
						style="font-size: 15px; margin: 0;">${startup.devceNo}</span></li>
					<!-- <li>一级滤芯：<span>89%</span>可用</li>
                <li>二级滤芯：<span>91%</span>可用</li>
                <li>三级滤芯：<span>95%</span>可用</li>
                <li>四级滤芯：<span>96%</span>可用</li> -->
				</ul>

			</div>


			<div class="guageContainer tdsOut"
				style="background: #8774A7; margin-bottom: 0">
				<div class="title">出水TDS</div>
				<div class="deviceData"></div>
				<div class="quality"></div>
				<div id="tdsOut" class="gauge"></div>
			</div>
			<div class="guageContainer sdOut"
				style="background: #E35A5A; margin-bottom: 0">
				<div class="title">出水色度</div>
				<div class="deviceData"></div>
				<div class="quality"></div>
				<div id="sdOut" class="gauge"></div>
			</div>
			<div class="guageContainer zdOut"
				style="background: #44B7AF; margin-bottom: 0">
				<div class="title">出水浊度</div>
				<div class="deviceData"></div>
				<div class="quality"></div>
				<div id="zdOut" class="gauge"></div>
			</div>
		</section>
		<div class='right'>
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-area-chart fa-lg" style="padding-right: 5px;"></i>近一周TDS统计
				</div>
				<div class="panel-body">
					<canvas id="Canvas2" style="height: 85%; width: 100%;"></canvas>
				</div>
			</div>

		</div>
	</div>
	</div>


	<div class="panel-heading" style='clear: left'>
		<i class="fa fa-area-chart fa-lg" style="padding: 10px 5px 0 0;"></i>TDS实时图
	</div>


	<%--
<div id="main_tds" style="width: 100%; height: 40%;"></div>
--%>
	<div id="main_tds" style="height: 50%"></div>
	<%-- 选择设备--%>
	<a id="btnShowDevice">•••</a>
</body>

<script type="text/javascript">

	var targetDeviceID;
    var dataUrl='/mapShow/getLatestWaterQuality';

	function drawRecent7daysTds() {
	    var randomScalingFactor = function () {
	        return Math.round(Math.random() * 100)
	    };
	    var day7stime = ${day7stime};
	    var day7samout = ${day7samout};
	    var day7intds = ${day7intds};
	    var lineChartData = {
	        labels: day7stime,
	        datasets: [
	                   
	                   {
	   	                fillColor: "rgba(210,38,21, 0.2)",
	   	                strokeColor: "rgba(210,38,21,1)",
	   	                pointColor: "rgba(210,38,21,1)",
	   	                pointStrokeColor: "#fff",
	   	                pointHighlightFill: "#fff",
	   	                pointHighlightStroke: "rgba(220,120,230,1)",
	   	                data: day7intds
	   	            },                               
	                   
	            {
	                fillColor: "rgba(50,220,220,0.2)",
	                strokeColor: "rgba(1,129,1,1)",
	                pointColor: "rgba(1,145,1,1)",
	                pointStrokeColor: "#fff",
	                pointHighlightFill: "#fff",
	                pointHighlightStroke: "rgba(220,220,220,1)",
	                data: day7samout
	            }
	
	        ]
	    }
	    var ctx = document.getElementById("Canvas2").getContext("2d");
	    window.myLine = new Chart(ctx).Line(lineChartData, {
	        bezierCurve: false,
	    });
	}

    function drawDashBoard(data) {

        var count = ${startup.deviceCount};
        var score = ${startup.evaluate};

        $('.deviceNoNumber').text(count) //设备数
    
        
        var tdsRange = new Array(1, 500);
        var sdRange = new Array(0.1, 3.0);
        var zdRange = new Array(0.1, 3.0);
        var scoreRnage = new Array(0, 100);

        // id 范围区间 背景色 当前值 质量 值单位
        addGuage('tdsIn', tdsRange, '#8774A7', data.latest_in_tds, data.in_tds_quality, 'TDS单位', ${startup.interval});
        addGuage('sdIn', sdRange, '#E35A5A', data.latest_in_sd, data.in_sd_quality, '色度单位', ${startup.interval});
        addGuage('zdIn', zdRange, '#44B7AF', data.latest_in_zd, data.in_zd_quality, '浊度单位', ${startup.interval});

        addGuage('tdsOut', tdsRange, '#8774A7', data.latest_out_tds, data.out_tds_quality, 'TDS单位', ${startup.interval});
        addGuage('sdOut', sdRange, '#E35A5A', data.latest_out_sd, data.out_sd_quality, '色度单位', ${startup.interval});
        addGuage('zdOut', zdRange, '#44B7AF', data.latest_out_zd, data.out_zd_quality, '浊度单位', ${startup.interval});
        
        
        addGuage2('score2', scoreRnage, '#568FBF', score,  '优', '浊度单位', ${startup.interval});
        
        $('.highcharts-label-box.highcharts-data-label-box').css('display', 'none')

    }
    
    //初始化仪表盘
    var initDashBoard=function(){
        var url = "/mapShow/getLatestWaterQuality";
        $.ajax({
            url : url,
            async : false,
            success : function(data) {
                drawDashBoard(data);
            }
        });
    }

    var dom = document.getElementById("main_tds");
    var myChart = echarts.init(dom);
    var xAxis = [];
    var inTdsData = [];
    var outTdsData = [];
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['入水TDS', '出水TDS']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: { readOnly: false },
                magicType: { type: ['line', 'bar'] },
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: [1]
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [
            {
                name: '入水TDS',
                type: 'line',
                data: [2]
            },
            {
                name: '出水TDS',
                type: 'line',
                data: [3]
            }
        ]
    };
    myChart.setOption(option)
    function getTds(result) {
    	xAxis = [];
        inTdsData = [];
        outTdsData = [];
        
        $.each(result, function (index, item) {
            xAxis.push(item.strTime)  
            inTdsData.push(item.tds)
            outTdsData.push(item.purifyTds)
        })

    }

    

    function initData(device_id) {

        var url = "/mapShow/getInitialDynamicTds";
        var result;
        $.ajax({
            url: url,
            data: {
            	device_id: device_id || ''
            },
            success: function (mydata) {
                var _xAxis = [];
		        var _inTdsData = [];
		        var _outTdsData = [];
		        $.each(mydata, function (index, item) {
		            _xAxis.push(item.strTime);
		            _inTdsData.push(item.tds);
		            _outTdsData.push(item.purifyTds);
		        })
            	var newOption = {
            	        tooltip: {
            	            trigger: 'axis'
            	        },
            	        legend: {
            	            data: ['入水TDS', '出水TDS']
            	        },
            	        toolbox: {
            	            show: true,
            	            feature: {
            	                dataZoom: {
            	                    yAxisIndex: 'none'
            	                },
            	                dataView: { readOnly: false },
            	                magicType: { type: ['line', 'bar'] },
            	                restore: {},
            	                saveAsImage: {}
            	            }
            	        },
            	        xAxis: {
            	            type: 'category',
            	            boundaryGap: false,
            	            data: _xAxis
            	        },
            	        yAxis: {
            	            type: 'value',
            	            axisLabel: {
            	                formatter: '{value}'
            	            }
            	        },
            	        series: [
            	            {
            	                name: '入水TDS',
            	                type: 'line',
            	                data: _inTdsData
            	            },
            	            {
            	                name: '出水TDS',
            	                type: 'line',
            	                data: _outTdsData
            	            }
            	        ]
            	    };
                //getTds(mydata);
                myChart.setOption(newOption);
            }
        });

    }

    function getLatestTdsData() {
    	
    	var url;
    	
    	if (targetDeviceID == "" || targetDeviceID == undefined || targetDeviceID == null){
            url =  dataUrl;
    	}else{
            url =  dataUrl+"?deviceId=" + targetDeviceID;
    	}
    	
      	
        $.ajax({
            url: url,
            async: false,
            success: function (mydata) {
                // now = new Date(+now + oneDay);
                console.log(mydata)
                var tempTimeArray = [];
                // 判断如果xaxis的最后一个时间不等于mydata的时间时，更新xaxis、inTdsData、inTdsData
                if (xAxis[xAxis.length - 1] != mydata.last_time) {
                    // 删除第一条记录，在最后面推入新纪录
                    inTdsData.shift()
                    inTdsData.push(mydata.latest_in_tds)
                    outTdsData.shift()
                    outTdsData.push(mydata.latest_out_tds)
                    xAxis.shift()
                    xAxis.push(mydata.hours + ':' + mydata.minutes + ':' + mydata.seconds)
                }

            }
        });

    }

    initData();

    // myChart.setOption(option, true);

   /* setInterval(function () {

    	getLatestTdsData();

        myChart.setOption({
            series: [{
                data: inTdsData
            },

                {
                    data: outTdsData
                }
            ]
        });
    }, ${startup.interval});*/
    


    // if (option && typeof option === "object") {
    //     myChart.setOption(option, true);
    // }
    var s = setInterval(function () {
        initData()
    }, 5000)
    
    
    $('#seldev').change(function(){ 
    	//alert($(this).children('option:selected').val()); 
    	
    	targetDeviceID = $(this).children('option:selected').val();
    	
    	//alert(targetDeviceID);
    
   }) 

    ///////
    $(document).ready(function () {

        drawRecent7daysTds();
        initDashBoard();

    });
    $('#btnShowDevice').on('click',function(){
        $('#deviceListWrap').slideDown();
    });
</script>
</html>