<template>
  <el-container>
    <el-aside :width="style.left.width">
      <Left class="left" />
    </el-aside>
    <el-container>
      <el-header>
        <Top class="top" />
      </el-header>
      <el-main :style="`background: ${style.main.backgroundColor}`">
        <breadCrumb/>
        <Main class="main" />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { style } from "@/config/config";
import Left from "./left.vue";
import Main from "./main.vue";
import Top from "./top.vue";
import breadCrumb from "./breadcrumb";
export default {
  data() {
    return {
      style
    };
  },
  components: { Left, Main, Top,breadCrumb }
};
</script>

<style lang="scss" scoped>
.el-container {
  height: 100%;
  .el-header {
    padding: 0;
  }
  .el-aside {
    height: 100%;
    .left {
      height: 100%;
    }
  }
}
</style>
