<template>
  <div class="panel" style="height: 100%;">
    <div class="left">
      <overview :devicesStatus="datas" />
    </div>
    <div class="right">
      <v-chart :options="options" :autoresize="true"/>
    </div>
  </div>
</template>

<script>
import Echarts from "vue-echarts";
import overview from "@/components/overview/overview";
import { geneOptions } from "./data";
import "echarts";
export default {
  props: {
    api: {
      type: Function
    }
  },
  created() {
    this.api().then(res => {
      if (res.ret === 0) {
        console.log(res);
        this.datas = res.datas;
        this.analyzeData(res.datas);
      }
    });
  },
  methods: {
    analyzeData(data) {
      let _data = [];
      _data.push({ name: "活跃设备数", value: data.online });
      _data.push({ name: "离线设备数", value: data.offline });
      this.options = geneOptions(_data);
    }
  },
  data() {
    return {
      x: [1],
      y: [3],
      options: {},
      datas: {}
    };
  },
  components: {
    "v-chart": Echarts,
    overview
  }
};
</script>

<style scoped lang="scss">
.panel {
  padding: 10px;
  overflow: hidden;
  // display: flex;
  // position: relative;
  // justify-content: space-between;
  box-sizing: border-box;
  .left {
    width: 50%;
    height: 100%;
    float: left;
    // position: absolute;
    .total,
    .online,
    .offline {
      .value {
        font-weight: 900;
      }
      font-size: 18px;
    }
  }
  .right {
    float: left;
    width: 50%;
    height: 100%;
    // flex: 1;
  }
}
</style>
