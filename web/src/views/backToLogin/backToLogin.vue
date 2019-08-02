<template>
  <div id="setNewPwd">
    <!-- 激活弹框 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="500px"
      class="dialog"
      :show-close="false"
    >
      <div class="line"></div>
      <p>正在激活账号，请稍后...</p>
      <el-button class="login" size="large" @click="login" type="primary">返回登录</el-button>
    </el-dialog>
    <!-- 登录成功弹窗 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible2"
      width="500px"
      class="dialog"
      :show-close="false"
    >
      <div class="line"></div>
      <p>注册成功，点击进行登录</p>
      <el-button class="login" size="large" @click="login" type="primary">返回登录</el-button>
    </el-dialog>
  </div>
</template>

<script>
import { activate } from "./api";
export default {
  data() {
    return {
      dialogVisible: true,
      dialogVisible2: false,
      sendData:{}
    };
  },
  created() {
    // console.log("query", this.$route.query);
    this.sendData = this.$route.query;
    activate(this.sendData).then(res => {
      if (res.ret === 0) {
        this.dialogVisible2 = true;
        this.dialogVisible = false;
      } else {
        this.$message({
          message: res.msg,
          type: "error"
        });
      }
    });
  },
  methods: {
    login() {
      this.$router.push({ path: "/login" });
    }
  }
};
</script>
<style lang="scss">
$bg: #fff;
$light_gray: #454545;
$cursor: #333;
#setNewPwd {
  .el-form {
    background: rgb(255, 255, 255);
    padding: 10px 20px;
    border-radius: 4px;
  }
  .el-input {
    display: inline-block;
    input {
      // background: transparent;
      // border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      color: $light_gray;
      caret-color: $cursor;
      &:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }
  .el-form-item {
    // border: 1px solid rgba(255, 255, 255, 0.1);
    // background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style scoped lang="scss">
@import "./index.scss";
</style>
