$(function() {
	document.onkeydown = function(e) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			var $query_btn = $("#btnSearch");
			if ($query_btn && $query_btn.length > 0) {
				$query_btn[0].click();
			}
		}
	};
});

var bmap = {

	opts : {
		width : "", // 信息窗口宽度
		title : "", // 信息窗口标题
		enableMessage : true
	},

	loadMap : function() {
		var keyword = document.getElementById("keyword").value;
		var keyword_txt = document.getElementById("txtSearch").value;
		if (keyword == '' && keyword_txt == '') {
			keyword = "贵州省桐梓县";
			keyword_txt = "贵州省桐梓县";
		}
		var url = "/mapShow/waterQualityMap?keyword=" + keyword + "&keyword_txt=" + keyword_txt;
		location.href = url;
	},

	/**
	 * 根据坐标获取点对象
	 */
	getPoint : function(x, y) {
		var point = new BMap.Point(x, y);
		return point;
	},

	/**
	 * 右上角显示卫星地图控件
	 */
	getMapTypeControl : function() {
		return new BMap.MapTypeControl({
			mapTypes : [ BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP, BMAP_HYBRID_MAP ]
		});
	},

	/**
	 * 左上角显示国、省、市、街控件
	 */
	getNavigationControl : function() {
		return new BMap.NavigationControl();
	},

	/**
	 * 根据IP地位城市,然后设置地图的中心点
	 */
	setCenterByIP : function(map) {
		/**
		 * 根据城市名称设置地图的中心点
		 */
		setCenterByCityName = function(result) {
			var cityName = result.name;
			map.setCenter(cityName);
		}
		var myCity = new BMap.LocalCity();
		myCity.get(setCenterByCityName);
	},

	setCenterByCity : function(map, cityName) {
		map.setCenter(cityName);
	},

	/**
	 * 根据横纵坐标设置地图中心点
	 * 
	 * @param map
	 * @param x
	 * @param y
	 */
	setCenterByPoint : function(map, x, y) {
		var new_point = bmap.getPoint(x, y);
		map.panTo(new_point);
	},

	/**
	 * 增加点
	 * 
	 * @param map
	 *            地图对象
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @returns {BMap.Marker}
	 */
	addMarkerPoint : function(map, x, y) {
		var point = bmap.getPoint(x, y);
		var myIcon = new BMap.Icon(common.logoImg, new BMap.Size(45, 41));
		var marker = new BMap.Marker(point, {
			icon : myIcon
		});
		map.addOverlay(marker);
		return marker;
	},

	/**
	 * 打开信息窗口
	 */
	openInfoWindow : function(map, e, m_opions) {
		var x, y;
		if (e) {
			var p = e.target;
			x = p.getPosition().lng;
			y = p.getPosition().lat;
		} else {
			x = m_opions.x;
			y = m_opions.y;
		}

		var content = m_opions.content;
		var type = m_opions.type;
		if (type == 0) {
			bmap.opts.width = content.length;
			bmap.opts.height = 50;
		} else if (type == 1) {
			bmap.opts.width = 480;
			bmap.opts.height = 390;
		} else if (type == 2) {
			bmap.opts.width = 500;
			bmap.opts.height = 510;
		}

		var point = new BMap.Point(x, y);
		var infoWindow = new BMap.InfoWindow(content, bmap.opts); // 创建信息窗口对象
		map.openInfoWindow(infoWindow, point); // 开启信息窗口
	},

	/**
	 * 给描点添加单击事件
	 */
	addMarkerClick : function(map, marker, m_opions) {
		var isopen = m_opions.isopen;
		if (isopen == 1) {
			bmap.openInfoWindow(map, null, m_opions);
		}
		marker.addEventListener("click", function(e) {
			bmap.openInfoWindow(map, e, m_opions);
		});
	},

	/**
	 * 增加圆
	 */
	addCircle : function(map, x, y, radius) {
		var circle = new BMap.Circle(new BMap.Point(x, y), radius, {
			fillColor : "#A3A5A6",
			strokeWeight : 1,
			fillOpacity : 0.5,
			strokeOpacity : 0.3
		});
		map.addOverlay(circle);
		circle.show();
		return circle;
	},

	/**
	 * 地图单击事件
	 * 
	 * @param map
	 */
	mapClick : function(map) {
		map.addEventListener("click", function(e) {
			var latlng = e.point;
			var regionId = $("#txtSearch").val();
			// var content = "update region set
			// device_x="+latlng.lng+",device_y="+latlng.lat+" where
			// region_id="+regionId;

			var content = latlng.lng + "," + latlng.lat;
			console.log(content);
			var info = new BMap.InfoWindow(content, {
				width : 220
			});
			map.openInfoWindow(info, latlng);
		});
	},

	/**
	 * 根据关键字在地图中搜索,显示到<div id="content">
	 */
	searchByKeyword : function(map) {
		var searchKey = [ "饭馆", "酒店" ];
		var local = new BMap.LocalSearch(map, {
			renderOptions : {
				map : map,
				panel : "content"
			},
			pageCapacity : 5
		});
		local.searchInBounds(searchKey, map.getBounds());
	},

	searchInfoWindow : function(map, x, y, title, content) {
		var searchInfoWindow3 = new BMapLib.SearchInfoWindow(map, content, {
			title : title, // 标题
			width : 290, // 宽度
			height : 40, // 高度
			panel : "panel", // 检索结果面板
			enableAutoPan : true, // 自动平移
			searchTypes : []
		});
		searchInfoWindow3.open(new BMap.Point(x, y));
	},

	/**
	 * 取34个省得坐标
	 * 
	 * @param map
	 */
	get34Point : function(map) {
		var result = [];
		var region = new Array("北京市", "上海市", "天津市", "重庆市", "黑龙江省", "辽宁省", "吉林省", "河北省", "河南省", "湖北省", "湖南省", "山东省", "山西省", "陕西省", "安徽省", "浙江省", "江苏省", "福建省", "广东省", "海南省", "四川省", "云南省", "贵州省", "青海省", "甘肃省", "江西省", "台湾省", "内蒙古自治区", "宁夏回族自治区", "新疆维吾尔自治区", "西藏自治区", "广西壮族自治区", "香港", "澳门");
		for (var i = 0; i < region.length; i++) {
			map.setCenter(region[i]);
			var c_point = map.getCenter();
			var x = c_point.lng;
			var y = c_point.lat;
			var obj = {
				region : region[i],
				x : x,
				y : y
			};
			result.push(obj);
		}
		return result;
	},

	/**
	 * 查询设备并描点
	 * 
	 * @param map
	 * @param regionId
	 */
	getDeviceAndAddMarker : function(map, regionId, isFlag, openDeviceId) {

		$.post("/mapShow/retriveDeviceInfo", {
			regionId : regionId
		}, function(data) {
			console.log(data);
			if (data && data.length > 0) {
				var markers_array = [];
				for (var i = 0; i < data.length; i++) {
					var d_x = data[i].deviceX;
					var d_y = data[i].deviceY;
					var d_region_id = data[i].regionId;
					// 描点
					var marker;
					if (isFlag) {
						var point = bmap.getPoint(d_x, d_y);
						var myIcon = new BMap.Icon(common.logoImg, new BMap.Size(45, 41));
						marker = new BMap.Marker(point, {
							icon : myIcon
						});
					} else {
						// 单个描点
						marker = bmap.addMarkerPoint(map, d_x, d_y);
					}
					markers_array.push(marker);
					var deviceId = data[i].deviceId;

					var isopen = 0;
					if (regionId != '' && i == 0) {
						isopen = 1;
						$("#d_deviceId").val(deviceId);
						$("#f_content4").attr("deviceId", deviceId);
					}

					if (openDeviceId == deviceId) {
						isopen = 1;
					}

					var m_opions = {
						x : d_x,
						y : d_y,
						content : dataObj.getContent(data[i]),
						type : 1,
						deviceId : deviceId,
						isopen : isopen
					};
					// 描点监听事件
					bmap.addMarkerClick(map, marker, m_opions);
				}
				if (isFlag) {
					// 聚合描点
					var markerClusterer = new BMapLib.MarkerClusterer(map, {
						markers : markers_array
					});
				}
			}
		}, "json").error(function() { alert("error"); });
	},

	/**
	 * 弹出层4个echart图展示
	 * 
	 * @param deviceId
	 */
	popupEcharts : function(deviceId) {
		var data = mychart.getDeviceInfo(deviceId);
		$("#e_color").empty();
		$("#e_trt").empty();
		$("#e_tbdt").empty();
		$("#e_amount").empty();
		if (data) {
			mychart.h_doubleLine_color("e_color", "色度", data.time, data.color, data.purifyColor);
			mychart.h_doubleLine_trt("e_trt", "温度", data.time, data.trt, data.purifyTrt);
			mychart.h_doubleLine_tbdt("e_tbdt", "水浊度", data.time, data.tbdt, data.purifyTbdt);
			mychart.h_doubleLine_amount("e_amount", "水量", data.time, data.amount, data.purifyAmount);
		}
	},

	addGlobalRegion : function(map, regionName) {
		var bdary = new BMap.Boundary();
		bdary.get(regionName, function(rs) {
			var ply = new BMap.Polygon(rs.boundaries[0], {
				strokeWeight : 2,
				strokeColor : "#FFFF00",
				fillColor : ""
			});
			map.addOverlay(ply);
			map.setViewport(ply.getPath()); // 调整视野
		});
	},

	/**
	 * 获取最新的版本号
	 */
	getLastVersion : function() {
		$.post("/mapShow/getLastVersion", {}, function(data) {
			$("#mapversion").text(data);
		}, "json");
	},

	/**
	 * 更新深圳地区的TDS值
	 * 
	 * @param data
	 */
	updateShenzhTds : function(data) {
		if (data != '' && data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				var deviceId = data[i].deviceId;
				var v_url = "/mapShow/test/updateTrt";
				$.post(v_url, {
					deviceId : deviceId
				}, function(data) {

				});
			}
		}
	},

	addMoreMarker : function(map, regionId, isFlag) {
		$.post("/mapShow/retriveDeviceInfo", {
			regionId : regionId
		}, function(data) {
			if (data && data.length > 0) {
				var markers_array = [];
				var points = [];
				var options = {
					size : BMAP_POINT_SIZE_SMALL,
					shape : BMAP_POINT_SHAPE_CIRCLE,
					color : '#d340c3'
				}
				for (var i = 0; i < data.length; i++) {
					var d_x = data[i].deviceX;
					var d_y = data[i].deviceY;
					var d_region_id = data[i].regionId;
					// 描点
					var point = new BMap.Point(d_x, d_y);
					var marker = new BMap.Marker(point);

					markers_array.push(marker);

					var deviceId = data[i].deviceId;
					points.push(point);
				}

				var pointCollection = new BMap.PointCollection(points, options); // 初始化PointCollection
				map.addOverlay(pointCollection); // 添加Overlay
				if (isFlag) {
					// 点集合
					var markerClusterer = new BMapLib.MarkerClusterer(map, {
						markers : markers_array
					});
				}
			}
		}, "json");
	},

	getSum : function(keyword, keyword_txt, regionId, b_Je, b_Ee, b_Ie, b_De) {
		b_url = "/mapShow/getSum";
		b_data = {
			keyword : keyword,
			keyword_txt : keyword_txt,
			regionId : regionId,
			xmin : b_Je,
			xmax : b_Ee,
			ymin : b_Ie,
			ymax : b_De
		};
		$.post(b_url, {
			json : JSON.stringify(b_data)
		}, function(data) {
			// 汇总
			dataObj.getRightSum(data);
		}, "json");

	}
}