<template>
  <div style="height: 100%;">
    <v-chart :options="options" :autoresize="true"/>
  </div>
</template>

<script>
import Echarts from "vue-echarts";
import { geneOptions } from "./data";
import "echarts";
export default {
  props: {
    type: {
      type: Number
    },
    api: {
      type: Function
    }
  },
  created() {
    this.api().then(res => {
      if (res.ret === 0) {
        this.analyzeData(res.datas);
      }
    });
  },
  methods: {
    analyzeData(data) {
      this.x = [];
      this.y = [];
      data.forEach(item => {
        this.x.push(item.recordDate);
        this.y.push(item.waterAmout);
      });
      this.options = geneOptions(this.x, this.y);
    }
  },
  data() {
    return {
      x: [1],
      y: [3],
      options: {}
    };
  },
  components: {
    "v-chart": Echarts
  }
};
</script>

<style>
.echarts {
  width: 100%;
  height: 100%;
}
</style>
