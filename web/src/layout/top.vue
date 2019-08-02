<template>
  <div class="top" :style="`color: ${style.top.color};background: ${style.top.backgroundColor};`">
    <div class="left">
      <i class="el-icon-menu"></i>
    </div>
    <div class="right">
      <el-dropdown @command="command" style="color: #fff;" trigger="click">
        <span class="el-dropdown-link">
          <i class="iconfont icon-denglu"></i>
          {{username}}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="quit">安全退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
// import { logout } from "./api";
import { style } from "@/config/config";
import logo from "@/assets/logo.png";
export default {
  data() {
    return {
      logo,
      style
    };
  },
  created() {
    // console.log(111,document.cookie);
  },
  methods: {
    command(type) {
      switch (type) {
        case "quit":
          this.$router.push({ path: "/login" });
          localStorage.removeItem("token");
          var cval = document.cookie;
          if (cval != null) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            document.cookie =
              "rememberMe=" + cval + ";expires=" + exp.toGMTString();
          }
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
  display: flex;
  justify-content: space-between;
  .logo {
    float: left;
    height: 100%;
    display: flex;
    vertical-align: center;
    justify-content: center;
  }
  .left {
    i {
      height: 60px;
      line-height: 60px;
      width: 60px;
      cursor: pointer;
      margin-left: 20px;
      font-size: 24px;
    }
  }
  .right {
    .el-dropdown {
      line-height: 60px;
      cursor: pointer;
      margin-right: 20px;
    }
  }
}
</style>
