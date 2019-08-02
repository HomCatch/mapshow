<template>
  <div class="overview">
    <div v-for="item in overview" :key="item.label" class="item" :style="'background: ' + item.color">
      <!-- <div class="left">
        <i class="iconfont" :class="item.icon"></i>
      </div> -->
      <div class="right">
        <div class="item-name">
          {{item.label}}
        </div>
        <count-to class="count" :end="item.count">
        </count-to>
      </div>
    </div>
  </div>
</template>

<script>
import CountTo from "@/components/count-to/count-to.vue";
export default {
  props: {
    devicesStatus: {
      type: Object,
      default: function() {
        return {};
      }
    }
  },
  computed: {
    overview() {
      return [
        {
          label: "设备总数",
          count: this.devicesStatus.total || 0,
          color: "#2d8cf0",
          icon: "icon--ludeng"
        },
        {
          label: "活跃设备数",
          count: this.devicesStatus.online || 0,
          color: "#19be6b",
          icon: "icon--ludeng"
        },
        {
          label: "离线设备数",
          count: this.devicesStatus.offline || 0,
          color: "#F56C6C",
          icon: "icon--ludeng"
        }
      ];
    }
  },
  components: { CountTo }
};
</script>

<style lang="scss" scoped>
@import "./index.scss";
</style>
