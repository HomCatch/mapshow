/*
 * @file bdmap.js
 * @description 设备在线地图的操作
 * @author zzq
 * @update (zzq 2018/05/28)
 */
import BMap from "BMap";
import BMapLib from "BMapLib";

import eventBus from "@/request/eventbus";
let map = null; // 定义文件级全局变量，在各方法中操作修改

/*
 * 地图初始化
 * config = {domId, city, minzoom, maxzoom, zoom}
 * @returns 返回map实例
 */
export function _initBdMap({
  domId,
  maxzoom = 18,
  minzoom = 5,
  city = "深圳",
  zoom = 5,
  pt = []
}) {
  map = null;
  map = new BMap.Map(domId, { enableMapClick: false });
  map.enableScrollWheelZoom(); // 开启滚轮缩放
  // const point = new BMap.Point(center[0], center[1]); // 设置地图中心点
  if (pt[0] == "" || !pt[0]) {
    map.centerAndZoom(city, zoom); // 设置初始缩放级别
  } else {
    let point = new BMap.Point(pt[0], pt[1]);
    map.centerAndZoom(point, zoom);
  }
  return map; // 返回map实例供在不同场景使用时可修改地图的属性
}

/*
 * 根据省份数据添加覆盖物
 * @params data:各省份名称和设备数量
 *
 */
export function _addOverLay(data, fn) {
  data.map((item, index) => {
    let provinceName = item[1];
    let starttime = new Date().getTime();
    // TODO 耗时巨长
    _getGeo(provinceName).then(pt => {
      let time = new Date().getTime() - starttime;
      console.log(index, time);
      let point = new BMap.Point(pt.lng, pt.lat);
      let rm = new BMapLib.TextIconOverlay(point, item[0]);
      rm.data = {
        name: item[1]
      };
      map.addOverlay(rm);
      rm.addEventListener("click", function(e) {
        // 覆盖物添加点击事件，跳转至该省份
        map.clearOverlays();
        var name = this.data.name;
        // that.getBoundary(map, name);
        _getBoundary(name, fn);
        return;
      });
    });
  });
}

// 根据城市名称返回该城市坐标
function _getGeo(geoname) {
  var myGeo = new BMap.Geocoder();
  return new Promise(function(resolve) {
    myGeo.getPoint(geoname, function(params) {
      resolve(params);
    });
  });
}

// 添加覆盖物边界
function _getBoundary(geoname, fn) {
  map.clearOverlays();
  const bList = []; // 边界点数组
  const bdary = new BMap.Boundary();
  bdary.get(geoname, function(rs) {
    const count = rs.boundaries.length; // city边界点数量
    if (count === 0) {
      alert("未能获取当前行政区域");
      eventBus.$emit("refresh");
      return;
    }
    for (var i = 0; i < count; i++) {
      bList.push({ points: rs.boundaries[i], name: geoname });
    }
    _drawBoundary(bList, fn);
  });
}

// 画线
function _drawBoundary(blist, fn) {
  //包含所有区域的点数组
  var pointArray = [];

  /*画遮蔽层的相关方法
   *思路: 首先在中国地图最外画一圈，圈住理论上所有的中国领土，然后再将每个闭合区域合并进来，并全部连到西北角
   *      这样就做出了一个经过多次西北角的闭合多边形*/
  //定义中国东南西北端点，作为第一层
  var pNW = { lat: 59.0, lng: 73.0 };
  var pNE = { lat: 59.0, lng: 136.0 };
  var pSE = { lat: 3.0, lng: 136.0 };
  var pSW = { lat: 3.0, lng: 73.0 };
  //向数组中添加一次闭合多边形，并将西北角再加一次作为之后画闭合区域的起点
  var pArray = [];
  pArray.push(pNW);
  pArray.push(pSW);
  pArray.push(pSE);
  pArray.push(pNE);
  pArray.push(pNW);
  //循环添加各闭合区域
  for (var i = 0; i < blist.length; i++) {
    //添加显示用标签层
    var label = new BMap.Label(blist[i].name, {
      offset: new BMap.Size(20, -10)
    });
    label.hide();
    map.addOverlay(label);

    //添加多边形层并显示
    var ply = new BMap.Polygon(blist[i].points, {
      strokeWeight: 1,
      strokeColor: "#FF0000",
      fillOpacity: 0.01,
      fillColor: " #FFFFFF"
    }); //建立多边形覆盖物
    ply.name = blist[i].name;
    ply.label = label;
    // ply.addEventListener("click", click);
    // ply.addEventListener("mouseover", mouseover);
    // ply.addEventListener("mouseout", mouseout);
    // ply.addEventListener("mousemove", mousemove);
    map.addOverlay(ply);

    //添加名称标签层
    var centerlabel = new BMap.Label(blist[i].name, {
      offset: new BMap.Size(0, 0)
    });
    centerlabel.setPosition(ply.getBounds().getCenter());
    map.addOverlay(centerlabel);

    //将点增加到视野范围内
    var path = ply.getPath();
    pointArray = pointArray.concat(path);
    //将闭合区域加到遮蔽层上，每次添加完后要再加一次西北角作为下次添加的起点和最后一次的终点
    pArray = pArray.concat(path);
    pArray.push(pArray[0]);
  }

  //限定显示区域，需要引用api库
  var boundply = new BMap.Polygon(pointArray);
  BMapLib.AreaRestriction.setBounds(map, boundply.getBounds());
  map.setViewport(pointArray); //调整视野

  //添加遮蔽层
  var plyall = new BMap.Polygon(pArray, {
    strokeOpacity: 0.0000001,
    strokeColor: "#000000",
    strokeWeight: 0.00001,
    fillColor: "#000000",
    fillOpacity: 0.3
  }); //建立多边形覆盖物
  map.addOverlay(plyall);

  // 遮罩层添加完成之后，这里添加回调函数来获取该省的所有设备并使用百度地图的点聚合功能展示
  fn(blist[0].name);
}

/*
 * 根据后台返回的某省的所有设备来画marker并给marker添加点击事件，并使用bmap的聚合点功能
 *
 */
import jsq from "@/common/img/jsq1.png";
var markerClusterer = null;
export function _MarkerClusterer(devList) {
  if (markerClusterer) {
    markerClusterer.clearMarkers();
  }
  const len = devList.length; // marker总数
  const markers = []; // 用来存储所有marker
  var myIcon = new BMap.Icon(jsq, new BMap.Size(30, 30), {
    anchor: new BMap.Size(0, 0),
    imageOffset: new BMap.Size(0, 0),
    imageSize: new BMap.Size(30, 30)
  });
  devList.map(item => {
    console.log(123);
    let marker = new BMap.Marker(new BMap.Point(item.deviceX, item.deviceY), {
      icon: myIcon
    });
    markers.push(marker);
    addClickHandler(item, marker);
  });
  markerClusterer = new BMapLib.MarkerClusterer(map, { markers: markers });
}

function addClickHandler(content, marker) {
  marker.addEventListener("click", function(e) {
    openInfo(content, e);
  });
}

function openInfo(content, e) {
  var p = e.target;
  var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
  var opts = {
    width: 0, // 信息窗口宽度
    height: 0, // 信息窗口高度
    title: `<div style="background:black;color:white">设备ID：${content.deviceId}</div>`, // 信息窗口标题
    enableMessage: true //设置允许信息窗发送短息
  };
  content = `<div style="background:black">
  <div>
    <span style="color:red;margin-right:20px">入水TDS：${content.tds}</span>
    <span style="color:green">出水TDS：${content.purifyTds}</span>
  </div>
  <div>
    <span style="color:red;margin-right:20px">入水色度：${content.color}</span>
    <span style="color:green">出水色度：${content.purifyColor}</span>
  </div>
  <div>
    <span style="color:red;margin-right:20px">入水浊度：${content.tbdt}</span>
    <span style="color:green">出水浊度：${content.purifyTbdt}</span>
  </div>
  <div>
    <span style="color:red;margin-right:20px">入水温度：${content.trt}</span>
    <span style="color:green">出水温度：${content.purifyTrt}</span>
  </div>
  </div>`;
  var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
  map.openInfoWindow(infoWindow, point); //开启信息窗口
}
