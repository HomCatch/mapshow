<template>
  <div id="map"></div>
</template>

<script>
import {
  _initBdMap,
  _addOverLay,
  _MarkerClusterer
} from "@/common/js/bdmap.js";
import BMap from "BMap";
import BMapLib from "BMapLib";

export default {
  props: {
    pos: {
      type: Array,
      default: function() {
        return [];
      }
    }
  },
  data(){
    return {
      x: null,
      y: null
    }
  },
  mounted() {
    this.showMap();
  },
  methods: {
    showMap(device_x = this.pos[1], device_y = this.pos[0]) {
      console.log(22);
      const map = _initBdMap({
        domId: "map",
        pt: [device_x, device_y],
        maxzoom: 18,
        minzoom: 5,
        zoom: 15
      });
      if (device_x != "" && device_y != "") {
        console.log(device_x, device_y);
        let point = new BMap.Point(device_x, device_y);
        let marker = new BMap.Marker(point);
        // map.centerAndZoom(point, 15); // 重置中心点
        map.addOverlay(marker);
      }
      map.addEventListener("click", e => {
        console.log(e)
        map.clearOverlays();
        let marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));
        // this.ruleForm.device_x = e.point.lng;
        // this.ruleForm.device_y = e.point.lat;
        map.addOverlay(marker);
        this.$emit('setPos', [e.point.lat, e.point.lng])
      });
    }
  }
};
</script>

<style scoped lang="scss">
#map{
  height: 400px;
}
</style>
