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
      this.y1 = [];
      this.y2 = [];
      data.forEach(item => {
        this.x.push(item.recordDate);
        if (this.type === 2) {
          this.y1.push(item.purifyTds);
          this.y2.push(item.tds);
        } else if (this.type === 4) {
          this.y1.push(item.purifyColor);
          this.y2.push(item.color);
        } else if (this.type === 5) {
          this.y1.push(item.purifyTbdt);
          this.y2.push(item.tbdt);
        }
      });
      this.options = geneOptions(this.x, this.y1, this.y2);
    }
  },
  data() {
    return {
      x: [1],
      y1: [3],
      y2: [2],
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
