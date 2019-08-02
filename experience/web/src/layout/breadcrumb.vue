<template>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item v-for="item in routes" :to="{path: item.path}" :key="item.path">{{item.name}}</el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
export default {
  computed: {
    routes() {
      return this.$route.matched;
    }
  }
};
</script>

<style lang="scss" scoped>
.el-breadcrumb {
  padding: 10px;
  background: #fff;
  margin-bottom: 10px;
}
</style>
