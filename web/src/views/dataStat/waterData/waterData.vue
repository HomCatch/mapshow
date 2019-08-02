<template>
  <div id="waterDesc">
    <div class="selector">
      <el-select v-model="value" size="mini">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
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
    </div>
    <div class="water">
      <div class="all">
        <h3>总用水量</h3>
        <p>{{waterAmount}}L</p>
        <img src="@/common/img/Kanban_icon_Waterdroplets.png" alt>
      </div>
      <div class="num">
        <h3>用水频次</h3>
        <p>{{useCount}}次</p>
        <img src="@/common/img/Kanban_icon_Frequencyofwateruse.png" alt>
      </div>
      <div class="time">
        <h3>单次用水时长</h3>
        <p>{{useTime}}min</p>
        <img src="@/common/img/kanban_icon_Lengthofwaterused.png" alt>
      </div>
    </div>
    <div class="list">
      <div class="totalList">
        <div class="title">用水总量</div>
        <v-echart :options="totalOption" style="width:100%;hight:100%"></v-echart>
      </div>
      <div class="numberList">
        <div class="title">用水频次</div>
        <v-echart :options="numberOption" style="width:100%;hight:100%"></v-echart>
      </div>
      <div class="analyseList">
        <div class="title">水质分析</div>
        <v-echart :options="analyseOption" style="width:100%;hight:100%"></v-echart>
      </div>
    </div>
  </div>
</template>

<script>
import "echarts";
import Echarts from "vue-echarts";
// import "echarts/lib/chart/line";
// import "echarts/lib/component/legend";
import { waterData, analyseData } from "./data";
import { getList } from "./api";
export default {
  data() {
    return {
      totalOption: {},
      numberOption: {},
      analyseOption: {},
      options: [
        {
          value: "选项1",
          label: "北京"
        },
        {
          value: "选项2",
          label: "上海"
        },
        {
          value: "选项3",
          label: "广州"
        },
        {
          value: "选项4",
          label: "深圳"
        },
        {
          value: "选项5",
          label: "武汉"
        }
      ],
      value: "全国",
      sendData: {
        page: 1
      },
      waterAmount: null,
      useCount: null,
      useTime: null,
      x: [],
      totalY: [],
      numY: [],
      yObj: {
        inTds: [],
        outTds: [],
        inColor: [],
        outColor: [],
        intTbdt: [],
        outTbdt: [],
        intTemp: [],
        outTemp: []
      }
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
          this.waterAmount = res.datas.list[0].waterAmount;
          this.useCount = res.datas.list[0].useCount;
          this.useTime = res.datas.list[0].useTime;
          res.datas.list.forEach(v => {
            this.x.unshift(v.date);
            this.totalY.unshift(v.waterAmount);
            this.numY.unshift(v.useCount);
            this.yObj.inTds.unshift(v.inTds);
            this.yObj.outTds.unshift(v.outTds);
            this.yObj.inColor.unshift(v.inColor);
            this.yObj.outColor.unshift(v.outColor);
            this.yObj.intTbdt.unshift(v.intTbdt);
            this.yObj.outTbdt.unshift(v.outTbdt);
            this.yObj.intTemp.unshift(v.intTemp);
            this.yObj.outTemp.unshift(v.outTemp);
          });
          // console.log(this.yObj);

          this.totalOption = waterData(this.x, this.totalY,"用水总量");
          this.numberOption = waterData(this.x, this.numY,"用水频次");
          this.analyseOption = analyseData(this.x, this.yObj);
        } else {
          this.$message({
            type: "error",
            message: "获取列表失败"
          });
        }
      });
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
