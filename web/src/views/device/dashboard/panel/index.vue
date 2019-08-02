<template>
  <div style="height: 100%;">
    <!-- 根据不同的type渲染不同的图形 -->
    <component v-bind:is="currentView" :api="api" :type="id"></component>
  </div>
</template>

<script>
import { type2Api } from "./data";
import trend from "./trend/index";
import total from "./total/index";
import history from "./history/index";
console.log(type2Api);
export default {
  props: {
    panelInfo: {
      type: Object
    }
  },
  data() {
    return {};
  },
  computed: {
    currentView() {
      // 根据type设置组件
      return type2Api[this.panelInfo.dashboardPropertyId - 1].component;
    },
    api() {
      return type2Api[this.panelInfo.dashboardPropertyId - 1].fetchData;
    },
    id() {
      return type2Api[this.panelInfo.dashboardPropertyId - 1].id
    }
  },
  components: { trend, history, total }
};
</script>

<style>
</style>
