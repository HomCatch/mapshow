 


    // desgine by pengkuan

    //$('tspan.highcharts-text-outline').siblings().css('font-size','24px')

    var guageTimer;
    function addGuage(id, range, bgColor, currentVal, quality, unit, interval) {
        $('#' + id).css('background', bgColor);
        $('.' + id + ' .quality').text(quality);
        $('.' + id + ' .deviceData').text(currentVal.toFixed(1));
        currentVal=parseFloat(currentVal.toFixed(2));
        var rangeStart = range[0],
                rangeEnd = range[1],
                color1 = (rangeEnd - rangeStart) / 3 + rangeStart,
                color2 = (rangeEnd - rangeStart) / 3 * 2 + rangeStart
        color3 = rangeEnd;
        Highcharts.chart(id, {
                    chart: {
                        type: 'gauge',
                        backgroundColor: bgColor,
                        // plotBackgroundColor: bgColor,
                        plotBackgroundImage: null,
                        plotBorderWidth: 0,
                        plotShadow: false
                    },
                    title: {
                        text: ''
                    },
                    pane: {
                        startAngle: -150, //刻度圆范围（度数）
                        endAngle: 150,
                        background: [{
                            backgroundColor: {
                                // linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                // linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '107%'
                        }, {
                            // default background
                            // backgroundColor: bgColor,
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                    },
                    // the value axis
                    yAxis: {
                        min: rangeStart,
                        max: rangeEnd,
                        minorTickInterval: 'auto',
                        // minorTickWidth: 1,
                        minorTickLength: 8,//刻度长度
                        minorTickPosition: 'inside',
                        minorTickColor: '#666',
                        tickPixelInterval: 30,
                        tickWidth: 2,
                        tickPosition: 'inside',
                        tickLength: 10,
                        tickColor: '#666',
                        labels: {
                            step: 2,
                            rotation: 'auto'
                        },
                        title: {
                            text: '',
                            y: 14,
                            style: {
                                color: 'green',
                                fontWeight: 600
                            },
                        },
                        plotBands: [{
                            from: rangeStart,
                            to: color1,
                            color: '#55BF3B' // green
                        }, {
                            from: color1,
                            to: color2,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: color2,
                            to: rangeEnd,
                            color: '#DF5353' // red
                        }]
                    },
                    series: [{
                        name: '当前值',
                        data: [currentVal],
                        dataLabels: {
                            color: '#018101',
                            fontSize: '22px',
                            border: 0
                        },
                        lineWidth: 0,
                        tooltip: {
                            valueSuffix: unit
                        }
                    }]

                },
                function (chart) {
                    if (!chart.renderer.forExport) {
                        guageTimer=setInterval(function () {
                        	var url;
                        	if (targetDeviceID == "" || targetDeviceID == undefined || targetDeviceID == null){
                        		url =  dataUrl;
                        	}else{
                        		url =  dataUrl+"?deviceId=" + targetDeviceID;
                        	}
                            jQuery.getJSON(url, null, function (data) {
                                if(chart.series) {
                                    var point = chart.series[0].points[0], newVal;
                                }

                                $('.deviceNoNumber').text(data.device_size) //设备数

                                $('.tdsIn .quality').text(data.in_tds_quality) //入水TDS质量
                                $('.tdsOut .quality').text(data.out_tds_quality) //出水TDS质量
                                $('.sdIn .quality').text(data.in_sd_quality) //入水色度质量
                                $('.sdOut .quality').text(data.out_sd_quality) //出水色度质量
                                $('.zdIn .quality').text(data.in_zd_quality) //入水浊度质量
                                $('.zdOut .quality').text(data.out_zd_quality) //出水浊度质量


                                $('.tdsIn .deviceData').text(data.latest_in_tds.toFixed(1))
                                $('.tdsOut .deviceData').text(data.latest_out_tds.toFixed(1))
                                $('.sdIn .deviceData').text(data.latest_in_sd.toFixed(1))
                                $('.sdOut .deviceData').text(data.latest_out_sd.toFixed(1))
                                $('.zdIn .deviceData').text(data.latest_in_zd.toFixed(1))
                                $('.zdOut .deviceData').text(data.latest_out_zd.toFixed(1))

                                $('#deviceId2').html(data.deviceId);
                                $('#dayFlux').html(data.today_amount.toFixed(1));
                                $('#weekFlux').html(data.week_amount.toFixed(1));
                                $('#monthFlux').html(data.month_amount.toFixed(1));

                                if (id == 'tdsIn') {
                                    newVal = data.latest_in_tds
                                }
                                if (id == 'sdIn') {
                                    newVal = data.latest_in_sd
                                }
                                if (id == 'zdIn') {
                                    newVal = data.latest_in_zd
                                }
                                if (id == 'tdsOut') {
                                    newVal = data.latest_out_tds
                                }
                                if (id == 'sdOut') {
                                    newVal = data.latest_out_sd
                                }
                                if (id == 'zdOut') {
                                    newVal = data.latest_out_zd
                                }
                                if (newVal < 0) {
                                    newVal = 0;
                                }
                                if (newVal > rangeEnd) {
                                    newVal = rangeEnd;
                                }
                                if(chart.series) {
                                    point.update(newVal);
                                }


                            });

                        }, interval);
                    }
                }
        );
    }

 
    
    function addGuage2(id, range, bgColor, currentVal, quality, unit, interval) {
 
        $('#' + id).css('background', bgColor);
        $('.' + id + ' .quality').text(quality);
        $('.' + id + ' .deviceData').text(currentVal.toFixed(2));
        currentVal=parseFloat(currentVal.toFixed(2));
        var rangeStart = range[0],
                rangeEnd = range[1],
                color1 = (rangeEnd - rangeStart) / 3 + rangeStart,
                color2 = (rangeEnd - rangeStart) / 3 * 2 + rangeStart
        color3 = rangeEnd;
        Highcharts.chart(id, {
                    chart: {
                        type: 'gauge',
                        backgroundColor: bgColor,
                        // plotBackgroundColor: bgColor,
                        plotBackgroundImage: null,
                        plotBorderWidth: 0,
                        plotShadow: false
                    },
                    title: {
                        text: ''
                    },
                    pane: {
                        startAngle: -140, //刻度圆范围（度数）
                        endAngle: 130,
                        background: [{
                            backgroundColor: {
                                // linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                // linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '107%'
                        }, {
                            // default background
                            // backgroundColor: bgColor,
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                    },
                    // the value axis
                    yAxis: {
                        min: rangeStart,
                        max: rangeEnd,
                        minorTickInterval: 'auto',
                        // minorTickWidth: 1,
                        minorTickLength: 8,//刻度长度
                        minorTickPosition: 'inside',
                        minorTickColor: '#666',
                        tickPixelInterval: 20,
                        tickWidth: 2,
                        tickPosition: 'inside',
                        tickLength: 10,
                        tickColor: '#666',
                        labels: {
                            step: 2,
                            rotation: 'auto'
                        },
                        title: {
                            text: '',
                            y: 14,
                            style: {
                                color: 'green',
                                fontWeight: 600
                            },
                        },
                        plotBands: [{
                            from: rangeStart,
                            to: color1,
                            color: '#DF5353'  // red
                        }, {
                            from: color1,
                            to: color2,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: color2,
                            to: rangeEnd,
                            color:  '#55BF3B'// green
                        }]
                    },
                    series: [{
                        name: '当前值',
                        data: [currentVal],
                        dataLabels: {
                            color: '#018101',
                            fontSize: '22px',
                            border: 0
                        },
                        lineWidth: 0,
                        tooltip: {
                            valueSuffix: unit
                        }
                    }]

                }
          
        );
    }

    function addGuage3(id, range, bgColor, currentVal, quality, unit, interval) {
        $('#' + id).css('background', bgColor);
        $('.' + id + ' .quality').text(quality);
        $('.' + id + ' .deviceData').text(currentVal.toFixed(1));
        var rangeStart = range[0],
            rangeEnd = range[1],
            color1 = (rangeEnd - rangeStart) / 3 + rangeStart,
            color2 = (rangeEnd - rangeStart) / 3 * 2 + rangeStart
        color3 = rangeEnd;
        Highcharts.chart(id, {
                chart: {
                    type: 'gauge',
                    backgroundColor: bgColor,
                    // plotBackgroundColor: bgColor,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false
                },
                title: {
                    text: ''
                },
                pane: {
                    startAngle: -150, //刻度圆范围（度数）
                    endAngle: 150,
                    background: [{
                        backgroundColor: {
                            // linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                            stops: [
                                [0, '#FFF'],
                                [1, '#333']
                            ]
                        },
                        borderWidth: 0,
                        outerRadius: '109%'
                    }, {
                        backgroundColor: {
                            // linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                            stops: [
                                [0, '#333'],
                                [1, '#FFF']
                            ]
                        },
                        borderWidth: 0,
                        outerRadius: '107%'
                    }, {
                        // default background
                        // backgroundColor: bgColor,
                    }, {
                        backgroundColor: '#DDD',
                        borderWidth: 0,
                        outerRadius: '105%',
                        innerRadius: '103%'
                    }]
                },
                // the value axis
                yAxis: {
                    min: rangeStart,
                    max: rangeEnd,
                    minorTickInterval: 'auto',
                    // minorTickWidth: 1,
                    minorTickLength: 8,//刻度长度
                    minorTickPosition: 'inside',
                    minorTickColor: '#666',
                    tickPixelInterval: 30,
                    tickWidth: 2,
                    tickPosition: 'inside',
                    tickLength: 10,
                    tickColor: '#666',
                    labels: {
                        step: 2,
                        rotation: 'auto'
                    },
                    title: {
                        text: '',
                        y: 14,
                        style: {
                            color: 'green',
                            fontWeight: 600
                        },
                    },
                    plotBands: [{
                        from: rangeStart,
                        to: color1,
                        color: '#55BF3B' // green
                    }, {
                        from: color1,
                        to: color2,
                        color: '#DDDF0D' // yellow
                    }, {
                        from: color2,
                        to: rangeEnd,
                        color: '#DF5353' // red
                    }]
                },
                series: [{
                    name: '当前值',
                    data: [currentVal],
                    dataLabels: {
                        color: '#018101',
                        fontSize: '22px',
                        border: 0
                    },
                    lineWidth: 0,
                    tooltip: {
                        valueSuffix: unit
                    }
                }]

            },
            function (chart) {
                if (!chart.renderer.forExport) {
                    if(id){
                        guageTimer=setInterval(function () {
                            var url;
                            if (targetDeviceID == "" || targetDeviceID == undefined || targetDeviceID == null){
                                url =  dataUrl;
                            }else{
                                url =  dataUrl+"?deviceId=" + targetDeviceID;
                            }
                            jQuery.getJSON(url, null, function (data) {

                                if(chart.series) {
                                    var point = chart.series[0].points[0], newVal;
                                }

                                $('.deviceNoNumber').text(data.device_size) //设备数

                                $('.tdsIn .quality').text(data.in_tds_quality) //入水TDS质量
                                $('.tdsOut .quality').text(data.out_tds_quality) //出水TDS质量
                                $('.sdIn .quality').text(data.in_sd_quality) //入水色度质量
                                $('.sdOut .quality').text(data.out_sd_quality) //出水色度质量
                                $('.zdIn .quality').text(data.in_zd_quality) //入水浊度质量
                                $('.zdOut .quality').text(data.out_zd_quality) //出水浊度质量



                                $('.tdsIn .deviceData').text(data.latest_in_tds.toFixed(1))
                                $('.tdsOut .deviceData').text(data.latest_out_tds.toFixed(1))
                                $('.sdIn .deviceData').text(data.latest_in_sd.toFixed(1))
                                $('.sdOut .deviceData').text(data.latest_out_sd.toFixed(1))
                                $('.zdIn .deviceData').text(data.latest_in_zd.toFixed(1))
                                $('.zdOut .deviceData').text(data.latest_out_zd.toFixed(1))

                                $('#deviceId2').html(data.deviceId);
                                $('#dayFlux').html(data.today_amount.toFixed(1));
                                $('#weekFlux').html(data.week_amount.toFixed(1));
                                $('#monthFlux').html(data.month_amount.toFixed(1));
                                var tdsArr=[data.latest_in_tds,data.latest_in_sd,data.latest_in_zd,data.latest_out_tds,data.latest_out_sd,data.latest_out_zd];
                                for(var i=0;i<6;i++) {
                                    if(chart.series) {
                                        newVal=tdsArr[i];
                                        point.update(newVal);
                                    }
                                }

                            });

                        }, interval);
                    }

                }
            }
        );
    }