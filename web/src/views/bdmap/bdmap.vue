<template>
  <div class="bdmap">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="入水TDS" name="first"></el-tab-pane>
      <el-tab-pane label="出水TDS" name="second"></el-tab-pane>
    </el-tabs>
    <div class="btn">
      <img src="../../common/img/tool_btn_zoom.png" alt="">
      <img src="../../common/img/tool_btn_zoomout.png" alt="">
      <img src="../../common/img/tool_btn_Positioning.png" alt="">
    </div>
    <div class="TDS">
      <div class="title">TDS指数</div>
      <div class="color">
        <div class="left">低</div>
        <div class="center">
          <ul>
            <li class="blue"></li>
            <li class="green"></li>
            <li class="yellow"></li>
            <li class="orange"></li>
            <li class="red"></li>
          </ul>
        </div>
        <div class="right">高</div>
      </div>
    </div>
    <!-- <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="1">入水TDS</el-menu-item>
      <el-menu-item index="2">出水TDS</el-menu-item>
    </el-menu>-->
    <div class="query">
      地点：
      <el-cascader
        v-model="selectCity"
        :options="options"
        @active-item-change="handleItemChange"
        :props="props"
        :show-all-levels="true"
        @change="itemChange"
        :change-on-select="true"
        :clearable="true"
      ></el-cascader>
      <el-button type="primary" @click="queryList">查看</el-button>
    </div>
    <div
      id="bdMap"
      style="height: 100%;"
      v-loading="bdmaploading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
    ></div>
  </div>
</template>

<script>
import eventBus from "@/request/eventbus";
import {
  _initBdMap,
  _addOverLay,
  _MarkerClusterer
} from "@/common/js/bdmap.js";
import { getProvinces, getCities, getDevList } from "./api";
export default {
  name: "bdmap",
  data() {
    return {
      bdmaploading: true,
      selectCity: null,
      options: [
        {
          province: "江苏",
          provinceid: "",
          cities: []
        },
        {
          label: "浙江",
          cities: []
        }
      ],
      props: {
        value: "provinceid",
        label: "province",
        children: "cities"
      },
      map: null,
      activeName: "first"
    };
  },
  mounted() {
    this.init();
    this.getProvinces();
  },
  methods: {
    handleClick() {},
    queryList() {
      let params = {};
      if (this.selectCity.length > 0) {
        this.options.forEach(item => {
          if (item.provinceid === this.selectCity[0]) {
            params.province = item.province;
            item.cities.forEach(subItem => {
              if (subItem.provinceid == this.selectCity[1]) {
                params.city = subItem.province;
              }
            });
          }
        });
        this.getDevList(params);
      } else {
        this.getDevList();
      }
    },
    handleItemChange(val) {
      this.getCities({ provinceId: val[0] });
    },
    itemChange(val) {
      if (val.length == 1) {
        this.getCities({ provinceId: val[0] });
      }
    },
    init() {
      let config = { domId: "bdMap", city: "深圳", maxzoom: 18, minzoom: 5 };
      this.map = _initBdMap(config);
      this.getDevList();
    },
    getDevList(params = {}) {
      this.map.clearOverlays();
      this.bdmaploading = true;
      this.map.clearOverlays();
      getDevList(params).then(res => {
        // 请求某省设备列表
        console.log("res",res);
        const devList = res.datas;
        console.log("devList", devList);
        setTimeout(() => {
          _MarkerClusterer(devList); // 依据设备列表画标注
        }, 0);
        this.bdmaploading = false;
      });
    },
    getProvinces() {
      getProvinces().then(res => {
        this.options = res.datas.map(item => {
          item.cities = [];
          return item;
        });
      });
    },
    getCities(params) {
      getCities(params).then(res => {
        this.options.forEach(item => {
          if (item.provinceid === params.provinceId) {
            item.cities = res.datas.map(item => ({
              provinceid: item.cityid,
              province: item.city
            }));
          }
        });
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "./bdmap.scss";
</style>
