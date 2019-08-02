<template>
  <div id="imgDesc">
    <div class="top">
      <div class="left">
        <div class="userGender">
          <div class="man">
            <div class="icon">
              <b>男性</b>
              <img src="../../../common/img/kanban_icon_male.png" alt>
            </div>
            <p>{{overview.male}}</p>
            <span>{{man}}%</span>
          </div>
          <div class="circle">
            <v-echart :options="userOption" style="width:100%;hight:100%"></v-echart>
          </div>
          <div class="woman">
            <div class="icon">
              <img src="../../../common/img/kanban_icon_female.png" alt>
              <b>女性</b>
            </div>
            <p>{{overview.female}}</p>
            <span>{{woman}}%</span>
          </div>
        </div>
        <div class="userAge">
          <h3>年龄分布</h3>
          <v-echart :options="ageOption"></v-echart>
        </div>
      </div>
      <div class="right">
        <div class="userEnd">
          <h3>用户终端分布</h3>
          <v-echart :options="endOption" style="width:100%;height:100%;"></v-echart>
        </div>
        <el-table
          :data="endTableData"
          style="width: 100%"
          :default-sort="{prop: 'userNum', order: 'descending'}"
        >
          <el-table-column prop="brand" label="品牌" sortable></el-table-column>
          <el-table-column prop="userNum" label="用户数量" sortable></el-table-column>
        </el-table>
      </div>
    </div>
    <div class="area">
      <h3>地域分布</h3>
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="1">省份</el-menu-item>
        <el-menu-item index="2">区域</el-menu-item>
        <el-menu-item index="3">城市</el-menu-item>
      </el-menu>
      <div class="areaData">
        <div class="map">
          <v-echart :options="areaOption" style="width:100%;height:100%"></v-echart>
        </div>
        <div class="list">
          <el-table
            :data="areaTableData"
            style="width: 100%"
            :default-sort="{prop: 'userNum', order: 'descending'}"
          >
            <el-table-column prop="province" label="省份" sortable></el-table-column>
            <el-table-column prop="userNum" label="用户数量" sortable></el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "echarts";
import Echarts from "vue-echarts";
import "echarts/map/js/china";
// import 'echarts/lib/chart/bar';
// import 'echarts/lib/chart/pie';
import { userData, ageData, endData, areaDatas , endTable , areaTable } from "./data";
import { getList } from "./api";
export default {
  data() {
    return {
      userOption: {},
      ageOption: {},
      endOption: {},
      activeIndex: "1",
      endTableData:[],
      areaTableData: [],
      areaOption: {},
      sendData: {
        page: 1
      },
      overview: {},
      man: "",
      woman: "",
      ageList: []
    };
  },
  created() {
    this.list();
  },
  methods: {
    list() {
      getList(this.sendData).then(res => {
        console.log("res", res);
        if (res.ret === 0) {
          this.overview = res.datas.list[0];

          //性别分布
          let male = this.overview.male;
          let female = this.overview.female;
          this.userOption = userData(male, female);
          this.man = (male / (male + female)).toFixed(2) * 100;
          this.woman = (female / (male + female)).toFixed(2) * 100;

          //年龄分布
          this.ageList.push(
            this.overview.childCount,
            this.overview.youngCount,
            this.overview.middleCount,
            this.overview.teenCount,
            this.overview.oldCount
          );
          this.ageOption = ageData(this.ageList);

          //终端分布
          this.endOption = endData(this.overview);
          this.endTableData = endTable(this.overview);

          //区域分布
          this.areaOption = areaDatas(this.overview);
          this.areaTableData = areaTable(this.overview);
        } else {
          this.$message({
            type: "error",
            message: "获取列表失败"
          });
        }
      });
    },
    handleSelect() {}
  },
  components: {
    "v-echart": Echarts
  }
};
</script>

<style lang="scss" scoped>
@import "./index.scss";
</style>
