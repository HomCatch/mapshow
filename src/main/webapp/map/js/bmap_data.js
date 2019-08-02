var dataObj = {

	center_point : {
		x : 116.331398,
		y : 39.897445
	},

	marker : {
		x : [ 114.051858, 114.14097, 113.994941, 114.061057, 114.067710000000000000000000000000 ],
		y : [ 22.617199, 22.697243, 22.707378, 22.704711, 22.547543000000000000000000000000 ],
		content : [ "深圳北站", "平湖街道", "<h4 style='margin:0 0 5px 0;padding:0.2em 0'>庆丰农场</h4><img style='float:right;margin:4px' id='imgDemo' src='http://app.baidu.com/map/images/tiananmen.jpg' width='139' height='104' title='天安门'/><p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>天安门坐落在中国北京市中心,故宫的南侧,与天安门广场隔长安街相望,是清朝皇城的大门...</p><Iframe src='lineGraph.html' width='500px' height='300px' scrolling='NO' frameborder='0' name='main'></iframe>",
				"<Iframe src='lineGraph.html' width='100%' height='100%' scrolling='NO' frameborder='0' name='main'></iframe>", "市民中心地铁站设备" ],
		type : [ 0, 0, 2, 1, 0 ],
		isopen : [ 0, 0, 0, 0, 0 ]
	},

	getContent : function(data) {
		var deviceInfo = data.deviceName + "（设备ID：" + data.deviceId + "）";
		var content2 = "<div style='text-align:center;'><h4 class='tiph4'>" + deviceInfo + "</h4>";
		content2 += "<Iframe id='f_content' deviceId=" + data.deviceId + " src='/mapShow/map/hightChatTds.jsp' width='100%' height='350px' scrolling='NO' frameborder='0' name='main'></iframe>";
//		content2 += "<div style='width:100%;text-align:center;padding-top: 10px;padding-left:9px;'>";
//		content2 += "<table class='popupContent' cellspacing='0' cellpading='0'>" 
//		+ "<tr style='background-color: #DB9C86;font-weight: bold;'><td>名称</td><td>测量值</td></tr>" 
//		+ "<tr><td>水量</td><td><span id='pop_purifyAmount'>" + data.purifyAmount + "</span>&nbsp;（L）</td></tr>" 
//		+ "<tr><td>TDS</td><td><span id='pop_purifyTds'>" + data.purifyTds + "</span>&nbsp;（mg/L）</td></tr>" 
//		+ "<tr><td>色度</td><td><span id='pop_purifyColor'>" + data.purifyColor + "</span>&nbsp;</td></tr>" 
//		+ "<tr><td>水浊度</td><td><span id='pop_purifyTbdt'>" + data.purifyTbdt + "</span>&nbsp;（NTU）</td></tr>" 
//		+ "<tr><td>温度</td><td><span id='pop_purifyTrt'>" + data.purifyTrt+ "</span>&nbsp;（°C）</td></tr>" 
//		+ "</table>";
//		content2 += "</div>"
		content2 += "</div>";
		return content2;
	},

	getLeftContent : function(map, data) {
		$("#content2").empty();
		if (data != '' && data.length > 0) {
			var content = "";
			for (var i = 0; i < data.length; i++) {
				content += "<li name='d_device' id='" + data[i].deviceId + "' style='margin: 2px 0px; padding: 0px 5px 5px 0px; cursor: pointer; overflow: hidden; line-height: 17px;'>" 
						+"<span style='width: 30px; height: 35px; cursor: pointer; float: left; *zoom: 1; overflow: hidden; margin: 2px 3px 0 5px; *margin-right: 0px; display: inline;'>"
						+"<img src='"+common.logoImg+"'>"
						+"</span>"
						+ "<div style='zoom: 1; overflow: hidden; padding: 0px 5px; background-color: rgb(240, 240, 240);'>" + "<div style='line-height: 20px; font-size: 12px;'>" + "<span style='color: #00c;'><b>设备：" + data[i].deviceId + "</b></span> <a href='#' style='margin-left: 5px; font-size: 12px; color: #3d6dcc; font-weight: normal; text-decoration: none;'>MORE»</a>" + "</div>" + "<div style='padding: 2px 0; line-height: 18px; *zoom: 1; overflow: hidden;'>"
						+ "<b style='float: left; font-weight: bold; *zoom: 1; overflow: hidden; padding-right: 5px; *margin-right: -3px;'>TDS值:</b> <span style='color: #666; display: block; zoom: 1; overflow: hidden;'><b>" + data[i].purifyTds + "</b></span>" + "</div>" + "<div style='padding: 2px 0; line-height: 18px; *zoom: 1; overflow: hidden;'>"
						+ "<b style='float: left; font-weight: bold; *zoom: 1; overflow: hidden; padding-right: 5px; *margin-right: -3px;'>用水量:</b> <span style='color: #666;'>" + data[i].purifyAmount + "</span>" + "</div>" + "</div>" + "</li>";

				var d_x = data[i].deviceX;
				var d_y = data[i].deviceY;

				var marker = bmap.addMarkerPoint(map, d_x, d_y);
				var isopen = 0;
				if (i == 0) {
					isopen = 1;
					$("#d_deviceId").val(data[i].deviceId);
					$("#f_content4").attr("deviceId", data[i].deviceId);
				}
				var m_opions = {
					x : d_x,
					y : d_y,
					content : dataObj.getContent(data[i]),
					type : 1,
					deviceId : data[i].deviceId,
					isopen : isopen
				};
				// 描点监听事件
				bmap.addMarkerClick(map, marker, m_opions);
			}
			$("#content2").html(content);
		}

	},

	/**
	 * 右上角汇总信息
	 * 
	 * @param data
	 */
	getRightSum : function(data) {
		var tds_p = ((data.tds - data.purifyTds) / data.tds * 100).toFixed(2);
		var color_p = ((data.color - data.purifyColor) / data.color * 100).toFixed(2);
		var tbdt_p = ((data.tbdt - data.purifyTbdt) / data.tbdt * 100).toFixed(2);
		$("#s_amount").text(data.amount);
		$("#s_purifyAmount").text(data.purifyAmount);
		$("#s_tds").text(data.tds);
		$("#s_purifyTds").text(data.purifyTds);
		$("#p_s_tds").text(tds_p);
		$("#s_color").text(data.color);
		$("#s_purifyColor").text(data.purifyColor);
		$("#p_s_color").text(color_p);
		$("#s_tbdt").text(data.tbdt);
		$("#s_purifyTbdt").text(data.purifyTbdt);
		$("#p_s_tbdt").text(tbdt_p);
		$("#s_trt").text(data.trt);
		$("#s_purifyTrt").text(data.purifyTrt);
	},

	style1 : [ {
		"featureType" : "land",
		"elementType" : "geometry",
		"stylers" : {
			"color" : "#e7f7fc"
		}
	}, {
		"featureType" : "water",
		"elementType" : "all",
		"stylers" : {
			"color" : "#96b5d6"
		}
	}, {
		"featureType" : "green",
		"elementType" : "all",
		"stylers" : {
			"color" : "#b0d3dd"
		}
	}, {
		"featureType" : "highway",
		"elementType" : "geometry.fill",
		"stylers" : {
			"color" : "#a6cfcf"
		}
	}, {
		"featureType" : "highway",
		"elementType" : "geometry.stroke",
		"stylers" : {
			"color" : "#7dabb3"
		}
	}, {
		"featureType" : "arterial",
		"elementType" : "geometry.fill",
		"stylers" : {
			"color" : "#e7f7fc"
		}
	}, {
		"featureType" : "arterial",
		"elementType" : "geometry.stroke",
		"stylers" : {
			"color" : "#b0d5d4"
		}
	}, {
		"featureType" : "local",
		"elementType" : "labels.text.fill",
		"stylers" : {
			"color" : "#7a959a"
		}
	}, {
		"featureType" : "local",
		"elementType" : "labels.text.stroke",
		"stylers" : {
			"color" : "#d6e4e5"
		}
	}, {
		"featureType" : "arterial",
		"elementType" : "labels.text.fill",
		"stylers" : {
			"color" : "#374a46"
		}
	}, {
		"featureType" : "highway",
		"elementType" : "labels.text.fill",
		"stylers" : {
			"color" : "#374a46"
		}
	}, {
		"featureType" : "highway",
		"elementType" : "labels.text.stroke",
		"stylers" : {
			"color" : "#e9eeed"
		}
	} ],

	style2 : [ {
		"featureType" : "all",
		"elementType" : "all",
		"stylers" : {
			"lightness" : 10,
			"saturation" : -100
		}
	} ]
}
