<template>
  <div id="devDesc">
    <div class="top">
      <div class="activeDev">
        <div class="left">
          <h3>激活设备(台)</h3>
          <p>{{overview.activeDeviceCount}}</p>
          <div class="lifting">
            <span>{{devThan}}</span>
            <span>{{devDistan}}</span>
          </div>
        </div>
        <div class="right">
          <img src="@/common/img/kanban_icon_Activatedevice.png" alt>
        </div>
      </div>
      <div class="isLine">
        <div class="left">
          <h3>在线</h3>
          <p>{{overview.onlineCount}}</p>
          <span>{{online}}%</span>
        </div>
        <div class="center">
          <v-echart :options="isLineOption" style="width:100%;hight:100%"></v-echart>
        </div>
        <div class="right">
          <h3>离线</h3>
          <p>{{overview.offlineCount}}</p>
          <span>{{offline}}%</span>
        </div>
      </div>
    </div>
    <div class="bottom">
      <div class="activeFilter">
        <div class="left">
          <h3>激活滤芯(个)</h3>
          <p>{{overview.activeFilterCount}}</p>
          <div class="lifting">
            <span>{{filterThan}}</span>
            <span>{{filterDistan}}</span>
          </div>
        </div>
        <div class="right">
          <img src="@/common/img/kanban_icon_Filterelement.png" alt>
        </div>
      </div>
      <div class="expireFilter">
        <div class="left">
          <h3>滤芯到期(个)</h3>
          <p>{{overview.maturityFilterCount}}</p>
          <div class="lifting">
            <span>{{maturityThan}}</span>
            <span>{{maturityDistan}}</span>
          </div>
        </div>
        <div class="right">
          <img src="@/common/img/kanban_icon_FilterDaoqi.png" alt>
        </div>
      </div>
      <div class="brokeDev">
        <div class="left">
          <h3>故障设备(台)</h3>
          <p>{{overview.faultDeviceCount}}</p>
          <div class="lifting">
            <span>{{defaultThan}}</span>
            <span>{{defaultDistan}}</span>
          </div>
        </div>
        <div class="right">
          <img src="@/common/img/kanban_icon_Faultyequipment.png" alt>
        </div>
      </div>
    </div>
    <div class="trendMap">
      <div class="title">
        <h3>趋势图</h3>
        <div class="date">
          <ul>
            <li>
              <a href="javascript:void(0)">今日</a>
            </li>
            <li>
              <a href="javascript:void(0)">昨天</a>
            </li>
            <li>
              <a class="active" href="javascript:void(0)">7天</a>
            </li>
            <li>
              <a href="javascript:void(0)">14天</a>
            </li>
            <li>
              <a href="javascript:void(0)">30天</a>
            </li>
          </ul>
          <el-date-picker
            v-model="value2"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            :start-placeholder="start"
            :end-placeholder="end"
          ></el-date-picker>
        </div>
      </div>
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="activeDev">激活设备</el-menu-item>
        <el-menu-item index="onlineDev">在线设备</el-menu-item>
        <el-menu-item index="defaultDev">故障设备</el-menu-item>
        <el-menu-item index="activeFilter">激活滤芯</el-menu-item>
        <el-menu-item index="maturityFilter">滤芯到期</el-menu-item>
      </el-menu>
      <v-echart :options="filterOption" style="width:100%;height:240px"></v-echart>
    </div>
    <div class="detailData">
      <div class="title">
        <h3>详细数据</h3>
        <a href="#">导出CSV</a>
      </div>
      <template>
        <el-table
          :data="allList"
          style="width: 100%"
          :default-sort="{prop: 'date', order: 'descending'}"
        >
          <el-table-column prop="date" label="日期" sortable></el-table-column>
          <el-table-column prop="addDeviceCount" label="新增激活设备(台)" sortable></el-table-column>
          <el-table-column prop="activeDeviceCount" label="累计激活设备(台)" sortable></el-table-column>
        </el-table>
      </template>
    </div>
  </div>
</template>

<script>
import "echarts";
import Echarts from "vue-echarts";
// import "echarts/lib/chart/line";
// import "echarts/lib/chart/pie";
import { isLineData, filterData } from "./data";
import { getList } from "./api";
export default {
  data() {
    return {
      isLineOption: {},
      value2: null,
      activeIndex: "activeDev",
      filterOption: {},
      sendData: {
        page: 1
      },
      allList: [],
      overview: {},
      overviewYes: {},
      devDistan: "",
      filterDistan: "",
      maturityDistan: "",
      defaultDistan: "",
      devThan: "",
      filterThan: "",
      maturityThan: "",
      defaultThan: "",
      online: "",
      offline: "",
      dateList: [],
      dataList: [],
      name: "激活设备",
      start:"",
      end:""
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
          this.allList = res.datas.list;
          res.datas.list.forEach(v => {
            this.dateList.unshift(v.date);
            this.dataList.unshift(v.activeDeviceCount);
          });
          this.start = this.dateList[0];
          this.end = this.dateList[this.dateList.length - 1];
          this.overview = res.datas.list[0];
          this.overviewYes = res.datas.list[1];
          this.devDistan = this.distance(
            this.overviewYes,
            this.overview,
            "activeDeviceCount"
          );
          this.filterDistan = this.distance(
            this.overviewYes,
            this.overview,
            "activeFilterCount"
          );
          this.maturityDistan = this.distance(
            this.overviewYes,
            this.overview,
            "maturityFilterCount"
          );
          this.defaultDistan = this.distance(
            this.overviewYes,
            this.overview,
            "faultDeviceCount"
          );
          this.devThan = this.toProportion(
            this.overviewYes,
            this.overview,
            "activeDeviceCount"
          );
          this.filterThan = this.toProportion(
            this.overviewYes,
            this.overview,
            "activeFilterCount"
          );
          this.maturityThan = this.toProportion(
            this.overviewYes,
            this.overview,
            "maturityFilterCount"
          );
          this.defaultThan = this.toProportion(
            this.overviewYes,
            this.overview,
            "faultDeviceCount"
          );
          let onlineCount = this.overview.onlineCount;
          let offlineCount = this.overview.offlineCount;
          this.isLineOption = isLineData(onlineCount, offlineCount);
          let on = onlineCount / (onlineCount + offlineCount);
          let off = offlineCount / (onlineCount + offlineCount);
          this.online = (on * 100).toFixed();
          this.offline = (off * 100).toFixed();
          // 默认渲染激活设备
          this.filterOption = filterData(
            this.dateList,
            this.dataList,
            this.name
          );
        } else {
          this.$message({
            type: "error",
            message: "获取列表失败"
          });
        }
      });
    },
    // 两天之间的差值
    distance(yesterday, today, keyWords) {
      let distan = 0;
      for (const key in yesterday) {
        if (key === keyWords) {
          distan = today[key] - yesterday[key];
          if (distan >= 0) {
            distan = `+${distan}`;
          } else {
            distan = `-${distan}`;
          }
        }
      }
      return distan;
    },
    // 转换为百分比
    toProportion(yesterday, today, keyWords) {
      let proportion = "";
      for (const key in yesterday) {
        if (key === keyWords) {
          let add = today[key] - yesterday[key];
          if (add >= 0) {
            proportion = "+" + (add / today[key]).toFixed(2) * 100;
          } else {
            proportion =
              "-" + (Math.abs(add) / yesterday[key]).toFixed(2) * 100;
          }
        }
      }
      return `${proportion}%`;
    },
    handleSelect(index) {
      // console.log(index);
      switch (index) {
        case "activeDev":
          this.allList.forEach(v => {
            this.dataList.unshift(v.activeDeviceCount);
          });
          this.name = "激活设备";
          break;
        case "onlineDev":
          this.allList.forEach(v => {
            this.dataList.unshift(v.onlineCount);
          });
          this.name = "在线设备";
          break;
        case "defaultDev":
          this.allList.forEach(v => {
            this.dataList.unshift(v.faultDeviceCount);
          });
          this.name = "故障设备";
          break;
        case "activeFilter":
          this.allList.forEach(v => {
            this.dataList.unshift(v.activeFilterCount);
          });
          this.name = "激活滤芯";
          break;
        case "maturityFilter":
          this.allList.forEach(v => {
            this.dataList.unshift(v.maturityFilterCount);
          });
          this.name = "滤芯到期";
          break;

        default:
          break;
      }
      this.filterOption = filterData(this.dateList, this.dataList, this.name);
    }
  },
  components: {
    "v-echart": Echarts
  }
};
</script>

<style lang="scss" scoped>
@import "./index.scss";
</style>
