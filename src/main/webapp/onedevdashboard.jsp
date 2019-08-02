<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ include file="../common/web/common.jsp" %>
<%@ include file="selectDevice.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <script src="${pageContext.request.contextPath}/map/js/myCharts.js"></script>
    <link href="${pageContext.request.contextPath}/map/css/bmap.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/js/highcharts-more.js"></script>
    <script src="${pageContext.request.contextPath}/js/dashboard.js?time=201707071555"></script>
    <script src="js/charts/Chart.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>

    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/index.css?update=1753a">

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
    .gauge{padding:0;margin:15px 0 0 40px;}
        ul{padding:0;margin:0;}
        ul li{list-style: none;}
    #amountStatic ul{margin-left:15px;}
    #btnShowDevice{ position:fixed;z-index:999;top:5px;right:35px;border-radius:50%;background-color:#447bea;
    width:30px;height:30px;text-align:center;line-height:28px;font-size:16px;color:#fff;cursor:pointer;text-decoration:none;}
    #btnShowDevice:hover{background-color:#2557ea;}
    #amountStatic ul{margin-left:15px;}
    #amountStatic ul li{margin-bottom:3px;}
    </style>

</head>
<body>

<div class='container'>
    <section>
        <div class="guageContainer" style="background: #568FBF;">
 			<div class="title">综合评分</div>
            <div class="deviceData">90</div>
            <div class="quality">优</div>
            <div id="score2" class="gauge"></div>
        </div>
        <div class="guageContainer tdsIn" style="background: #8774A7;">
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

 
        
        <div id="amountStatic" class="guageContainer tdsIn" style="background: #568FBF;margin-bottom:0;color:#fff;text-align:left;">
            <p style="font-size:18px;margin-left:15px;margin-top:10px;margin-bottom:10px;">流量统计</p>
         	<ul style="margin-left:15px;">
         		<li>设备编码：<span id="deviceId2">${startup.devceNo}</span></li>
         		<li>当天流量：<span id="dayFlux">1</span>L</li>
         		<li>本周流量：<span id="weekFlux">7</span>L</li>
         		<li>本月流量：<span id="monthFlux">14</span>L</li>
         	</ul>
          
        </div>
        
        <div class="guageContainer tdsOut" style="background: #8774A7;margin-bottom:0">
            <div class="title">出水TDS</div>
            <div class="deviceData"></div>
            <div class="quality"></div>
            <div id="tdsOut" class="gauge"></div>
        </div>
        <div class="guageContainer sdOut" style="background: #E35A5A;margin-bottom:0">
            <div class="title">出水色度</div>
            <div class="deviceData"></div>
            <div class="quality"></div>
            <div id="sdOut" class="gauge"></div>
        </div>
        <div class="guageContainer zdOut" style="background: #44B7AF;margin-bottom:0">
            <div class="title">出水浊度</div>
            <div class="deviceData"></div>
            <div class="quality"></div>
            <div id="zdOut" class="gauge"></div>
        </div>
    </section>
    <div class='right'>
        <div class="panel panel-default">
            <div class="panel-heading"><i class="fa fa-area-chart fa-lg" style="padding-right: 5px;"></i>近一周TDS统计</div>
            <div class="panel-body">
                <canvas id="Canvas2" style="height: 85%; width: 100%;"></canvas>
            </div>
        </div>

    </div>
</div>
</div>


<div class="panel-heading" style='color:#777;position: relative;top: 20px;z-index: 99;'><i class="fa fa-area-chart fa-lg" style="padding:10px 5px 0
        0;"></i>TDS实时图
</div>
    <%--
    <div>
        <c:if test="${fn:length(devices)> 1 }">
            <select id="seldev">

                    <c:forEach items="${devices}" var="item">

                      <option value=${item.deviceId}>${item.deviceId}</option>


                    </c:forEach>


            </select>
        </c:if>
    </div>
    <%--

    <%--
    <div id="main_tds" style="width: 100%; height: 40%;"></div>
    --%>
<div id="main_tds" style="height: 50%"></div>
    <%-- 选择设备--%>
    <a id="btnShowDevice">•••</a>
<div style="display: none;">
    <span id="list_url">${pageContext.request.contextPath}/devmng</span>
</div>
</body>

<script type="text/javascript">

	var targetDeviceID;
    var dataUrl='/mapShow/getLatestWaterQuality';

    //近一周TDS统计
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
        $('#dayFlux').html(data.today_amount.toFixed(1));
        $('#weekFlux').html(data.week_amount.toFixed(1));
        $('#monthFlux').html(data.month_amount.toFixed(1));
        
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
    var app = {};
    option = null;
    var inTdsData = [];
    var outTdsData = [];

    function getTds(result) {

        for (i = -(result.length-1); i <= 0; i += 1) {
            var strTime = result[-i].strTime;


            var time = new Date(strTime);

            time = new Date();

            inTdsData.push({
                name: result[-i].unixTime,
                value: [
                    result[-i].unixTime,
                    result[-i].tds
                ]
            });

            outTdsData.push({
                name: result[-i].unixTime,
                value: [
                    result[-i].unixTime,
                    result[-i].purifyTds
                ]
            });


        }

    }


    function initData() {

        var url = "/mapShow/getInitialDynamicTds";
        var result;
        $.ajax({
            url: url,
            async: false,
            success: function (mydata) {

                getTds(mydata);
            }
        });

    }

    //TDS实时图数据
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

                if (mydata.latest_tds_tm.time > inTdsData[0].value[0]) {
                    inTdsData.unshift({
                        name: mydata.latest_tds_tm.time,
                        value: [
                            mydata.latest_tds_tm.time,
                            mydata.latest_in_tds
                        ]
                    });

                    inTdsData.pop(); //删除最后一个

                    outTdsData.unshift({
                        name: mydata.latest_tds_tm.time,
                        value: [
                            mydata.latest_tds_tm.time,
                            mydata.latest_out_tds
                        ]
                    });

                    outTdsData.pop();

                }

            }
        });

    }

    initData();

    option = {
        backgroundColor: '#fff',
        legend: {
            data: ['净化前', '净化后']
        },


        title: {
            text: ''
        },
        grid: {
            left: '2%',
            right: '1%'
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                var params0 = params[0];
                var params1 = params[1];
               
                var time = params0.name;
                var date = new Date(time);

                var hour = date.getHours();

                if (hour < 10) {
                    hour = "0" + hour;
                }

                var minutes = date.getMinutes();

                if (minutes < 10) {
                    minutes = "0" + minutes;
                }


                var seconds = date.getSeconds();

                if (seconds < 10) {
                    seconds = "0" + seconds;
                }


                var value = hour + ":" + minutes + ":" + seconds + ",净化前" + params0.value[1] + ",净化后" + params1.value[1]

                // return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
                //return params.name;

                return value;
            },
            axisPointer: {
                animation: true
            }
        },
        xAxis: {
            type: 'time',

            splitLine: {
                show: true
            },

            boundaryGap: ['50%', '20%'],
            splitNumber: 20,
            axisLabel: {
                interval: 10,
                formatter: function (value, index) {
                    // 格式化成月/日，只在第一个刻度显示年份
                    var date = new Date(value);
                    // alert(date);


                    var hour = date.getHours();
                    var minutes = date.getMinutes();
                    var seconds = date.getSeconds();


                    if (hour < 10) {
                        hour = "0" + hour;
                    }

                    var minutes = date.getMinutes();

                    if (minutes < 10) {
                        minutes = "0" + minutes;
                    }


                    var seconds = date.getSeconds();

                    if (seconds < 10) {
                        seconds = "0" + seconds;
                    }


                    var value = hour + ":" + minutes + ":" + seconds;

                    return value;
                },

                margin: 2,
                textStyle: {
                    color: "#222"
                }
            }
        },
        yAxis: {
            type: 'value',
            max: 'dataMax',
            boundaryGap: [0, '100%'],
            splitNumber: 15,
            splitLine: {
                show: true
            }
        },

        axisTick: {length: 150},


        series: [{
            name: '净化前',
            type: 'line',
            showSymbol: 'true',
            symbol: 'circle',
            symbolSize: 6,
            hoverAnimation: false,
            itemStyle: {
                normal: {
                    color: '#d22615',
                },
            },
            lineStyle: {
                normal: {
                    color: '#D22615'
                }
            },
            data: inTdsData,
            markPoint: {
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: [
                    {type: 'max', name: '净化前最大'},
                    {type: 'min', name: '净化前最小'}
                ]
            }
        },

            {
                name: '净化后',
                type: 'line',
                showSymbol: 'true',
                symbol: 'circle',
                symbolSize: 6,

                markPoint: {
                    data: [
                        {type: 'max', name: '净化后最大'},
                        {type: 'min', name: '净化后最小'}
                    ]
                },


                hoverAnimation: false,
                itemStyle: {
                    normal: {
                        color: '#277d26',
                    },
                },
                lineStyle: {
                    normal: {
                        color: '#277d26'
                    }
                },
                data: outTdsData
            }


        ]


    };

    var tdsTimer=setInterval(function () {
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
    }, ${startup.interval});
    


    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

    $(document).ready(function () {
    	
    	drawRecent7daysTds();
        initDashBoard();

    });
    $('#btnShowDevice').on('click',function(){
        $('#deviceListWrap').slideDown();
    });
</script>
</html>
