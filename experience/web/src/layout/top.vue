<template>
  <div class="top" :style="`color: ${style.top.color};background: ${style.top.backgroundColor};`">
    <el-dropdown @command="command" style="color: #fff;">
      <span class="el-dropdown-link">
        <i class="iconfont icon-denglu"></i> {{username}}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="quit">安全退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
// import { logout } from "./api";
import {style} from '@/config/config'
import logo from "@/assets/logo.png";
export default {
  data() {
    return {
      logo,
      style
    };
  },
  methods: {
    command(type) {
      switch (type) {
        case "quit":
          this.$router.push({ path: "/login" });
          localStorage.removeItem("token");
      }
    }
  },
  computed: {
    username() {
      return localStorage.getItem("username");
    }
  }
};
</script>

<style scoped lang="scss">
.top {
  overflow: hidden;
  .logo{
      float: left;
      height: 100%;
      display: flex;
      vertical-align: center;
      justify-content: center;
  }
}
.el-dropdown {
  float: right;
  line-height: 60px;
  cursor: pointer;
  margin-right: 20px;
}
</style>
