<template>
  <div id="devDesc">
    <div class="top">
      <div class="activeDev">
        <div class="left">
          <h3>支付金额</h3>
          <p>16600</p>
          <div class="lifting">
            <span>+3.3%</span>
            <span>+3300</span>
          </div>
        </div>
        <div class="right">
          <img src="../../common/img/kanban_icon_Activatedevice.png" alt="">
        </div>
      </div>
      <div class="isLine">
        <div class="left">
          <h3>今日订单数</h3>
          <p>12</p>
          <span>80%</span>
        </div>
        <div class="center">
          <v-echart :options="isLineOption" style="width:100%;hight:100%"></v-echart>
        </div>
        <div class="right">
          <h3>总订单数</h3>
          <p>9184</p>
          <span>20%</span>
        </div>
      </div>
    </div>
    <div class="bottom">
      <div class="activeFilter">
        <div class="left">
          <h3>未完成订单</h3>
          <p>34</p>
          <div class="lifting">
            <span>+2.3%</span>
            <span>+6</span>
          </div>
        </div>
        <div class="right">
          <img src="../../common/img/kanban_icon_Filterelement.png" alt="">
        </div>
      </div>
      <div class="expireFilter">
        <div class="left">
          <h3>终止订单</h3>
          <p>389</p>
          <div class="lifting">
            <span>+6.3%</span>
            <span>+60</span>
          </div>
        </div>
        <div class="right">
          <img src="../../common/img/kanban_icon_FilterDaoqi.png" alt="">
        </div>
      </div>
      <div class="brokeDev">
        <div class="left">
          <h3>完成订单</h3>
          <p>1324</p>
          <div class="lifting">
            <span>-7.5%</span>
            <span>-6</span>
          </div>
        </div>
        <div class="right">
          <img src="../../common/img/kanban_icon_Faultyequipment.png" alt="">
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
              <a href="#">7天</a>
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
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </div>
      </div>
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="1">下单数</el-menu-item>
        <el-menu-item index="2">支付金额</el-menu-item>
        
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
          :data="tableData"
          style="width: 100%"
          :default-sort="{prop: 'date', order: 'descending'}"
        >
          <el-table-column prop="date" label="日期" sortable></el-table-column>
          <el-table-column prop="addActive" label="新增订单" sortable></el-table-column>
          <el-table-column prop="totalActive" label="累计订单" sortable></el-table-column>
        </el-table>
      </template>
    </div>
  </div>
</template>

<script>
import Echarts from "vue-echarts";
import 'echarts/lib/chart/line';
import 'echarts/lib/chart/pie';
import { isLineData, filterData } from "./data";
export default {
  data() {
    return {
      isLineOption: {},
      value2: null,
      activeIndex: "1",
      filterOption: {},
      tableData: [
        {
          date: "2019-06-01",
          addActive: 1600,
          totalActive: 2300
        },
        {
          date: "2019-06-02",
          addActive: 900,
          totalActive: 3200
        },
        {
          date: "2019-06-03",
          addActive: 1800,
          totalActive: 5000
        },
        {
          date: "2019-06-04",
          addActive: 2000,
          totalActive: 7000
        },
      ]
    };
  },
  created() {
    this.isLineOption = isLineData();
    this.filterOption = filterData();
  },
  methods: {
    handleSelect(){}
  },
  components: {
    "v-echart": Echarts
  }
};
</script>

<style lang="scss" scoped>
@import "./index.scss";
</style>
