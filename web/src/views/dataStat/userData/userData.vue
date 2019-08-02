<template>
  <div id="userDesc">
    <div class="top">
      <div class="regUser">
        <div class="left">
          <h3>注册用户(个)</h3>
          <p>{{overview.totalCount}}</p>
          <div class="lifting">
            <span>{{registerThan}}</span>
            <span>{{registerDistan}}</span>
          </div>
        </div>
        <div class="right">
          <img src="@/common/img/kanban_icon_user.png" alt>
        </div>
      </div>
      <div class="activeUser">
        <div class="left">
          <div class="today">
            <h3>今日活跃用户</h3>
            <p>{{overview.activeToday}}</p>
            <div class="lifting">
              <span>{{todayThan}}</span>
              <span>{{todayDistan}}</span>
            </div>
          </div>
          <div class="sevenDays">
            <h3>7天活跃用户</h3>
            <p>{{overview.activeWeek}}</p>
            <div class="lifting">
              <span>{{weekThan}}</span>
              <span>{{weekDistan}}</span>
            </div>
          </div>
          <div class="thirtyDays">
            <h3>30天活跃用户</h3>
            <p>{{overview.activeMonth}}</p>
            <div class="lifting">
              <span>{{monthThan}}</span>
              <span>{{monthDistan}}</span>
            </div>
          </div>
        </div>
        <div class="right">
          <img src="@/common/img/kanban_icon_activeuser.png" alt>
        </div>
      </div>
    </div>
    <div class="trendMap">
      <div class="title">
        <h3>趋势图</h3>
        <div class="date">
          <ul>
            <li>
              <a href="#">今日</a>
            </li>
            <li>
              <a href="#">昨天</a>
            </li>
            <li>
              <a class="active" href="#">7天</a>
            </li>
            <li>
              <a href="#">14天</a>
            </li>
            <li>
              <a href="#">30天</a>
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
        <el-menu-item index="register">注册用户</el-menu-item>
        <el-menu-item index="active">活跃用户</el-menu-item>
      </el-menu>
      <v-echart :options="userOption" style="width:100%;height:240px"></v-echart>
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
          <el-table-column prop="addCount" label="新增用户(个)" sortable></el-table-column>
          <el-table-column prop="totalCount" label="累计用户(个)" sortable></el-table-column>
        </el-table>
      </template>
    </div>
  </div>
</template>

<script>
import "echarts";
import Echarts from "vue-echarts";
// import  'echarts/lib/chart/line';
import { userData } from "./data";
import { getList } from "./api";
export default {
  data() {
    return {
      userOption: {},
      value2: null,
      activeIndex: "register",
      sendData: {
        page: 1
      },
      allList: [],
      overview: {},
      overviewYes: {},
      registerThan: "",
      todayThan: "",
      weekThan: "",
      monthThan: "",
      registerDistan: "",
      todayDistan: "",
      weekDistan: "",
      monthDistan: "",
      dataList: [],
      dateList: [],
      name: "注册用户",
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
        // console.log("res", res);
        if (res.ret === 0) {
          this.allList = res.datas.list;
          res.datas.list.forEach(v => {
            this.dateList.unshift(v.date);
            this.dataList.unshift(v.registCount);
          });
          this.start = this.dateList[0];
          this.end = this.dateList[this.dateList.length - 1];
          this.overview = res.datas.list[0];
          this.overviewYes = res.datas.list[1];
          this.registerThan = this.toProportion(
            this.overviewYes,
            this.overview,
            "totalCount"
          );
          this.todayThan = this.toProportion(
            this.overviewYes,
            this.overview,
            "activeToday"
          );
          this.weekThan = this.toProportion(
            this.overviewYes,
            this.overview,
            "activeWeek"
          );
          this.monthThan = this.toProportion(
            this.overviewYes,
            this.overview,
            "activeMonth"
          );
          this.registerDistan = this.distance(
            this.overviewYes,
            this.overview,
            "totalCount"
          );
          this.todayDistan = this.distance(
            this.overviewYes,
            this.overview,
            "activeToday"
          );
          this.weekDistan = this.distance(
            this.overviewYes,
            this.overview,
            "activeWeek"
          );
          this.monthDistan = this.distance(
            this.overviewYes,
            this.overview,
            "activeMonth"
          );
          // 默认渲染激活设备

          this.userOption = userData(this.dateList, this.dataList, this.name);
        } else {
          this.$message({
            type: "error",
            message: "获取列表失败"
          });
        }
      });
    },
    // 两天直接的差值
    distance(yesterday, today, keyWords) {
      let distan = 0;
      for (const key in yesterday) {
        if (key === keyWords) {
          distan = today[key] - yesterday[key];
          if (distan >= 0) {
            distan = `+${distan}`;
          } else {
            distan = `${distan}`;
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
      switch (index) {
        case "register":
          this.allList.forEach(v => {
            this.dataList.unshift(v.registCount);
          });
          this.name = "注册用户";
          break;
        case "active":
          this.allList.forEach(v => {
            this.dataList.unshift(v.activeToday);
          });
          this.name = "活跃用户";
          break;

        default:
          break;
      }
      this.userOption = userData(this.dateList, this.dataList, this.name);
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
