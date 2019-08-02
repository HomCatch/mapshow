<template>
  <div id="findPwd">
    <el-form status-icon class="demo-ruleForm">
      <p
        style="text-align: center;font-size: 22px;font-weight: 600;line-height: 50px;width:190px;padding:30px;padding-left:100px"
        size="large"
      >找回密码</p>
      <p style="text-align: center;font-size: 14px;font-weight: 500;margin-bottom:20px">
        验证邮件已发送至
        <span style="color:#409eff">{{username}}</span>请按提示，进行操作。
      </p>
      <p
        style="text-align: center;font-size: 14px;font-weight: 500;margin-top:40px;margin-bottom:60px"
      >
        若您没有收到邮件您可以：检查您的垃圾邮件中，是否包含验证邮件；或者
        <span
          v-if="sendText === '重发验证邮件' || sendText === '重发'"
          style="color:#409eff;cursor:pointer"
          @click="emailAgain"
        >{{sendText}}</span>
        <span v-if="sendText !== '重发验证邮件' && sendText !== '重发'" style="color:#409eff;">{{sendText}}</span>
      </p>
      <el-form-item>
        <el-button style="width: 100%;" type="primary" size="default" @click="email">查看邮件</el-button>
      </el-form-item>
      <el-form-item>
        <p style="text-align:center;line-height:30px;background:#fff">
          <span @click="login" style="color: #409eff;cursor: pointer;font-size: 14px;">登录</span>
        </p>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { sendEmail } from "./api";
export default {
  data() {
    return {
      username: "",
      sendText: "重发验证邮件"
    };
  },
  created() {
    console.log(this.$route.query.username);
    if (this.$route.query.username) {
      let user = "";
      user = this.$route.query.username.split("@")[0];
      let str = "";
      if (user.length > 6) {
        str += user.substring(0, 3);
        str += "***";
        str += user.substring(user.length - 3, user.length);
      }
      this.username = str + "@" + this.$route.query.username.split("@")[1];
    }
  },
  methods: {
    login() {
      this.$router.push({ path: "/login" });
    },
    email() {
      let key = this.$route.query.username.split("@")[1];
      switch (key) {
        case "qq.com":
          window.location.href = "https://mail.qq.com";
          break;
        case "163.com":
          window.location.href = "https://mail.163.com";
          break;
        case "126.com":
          window.location.href = "https://mail.126.com";
          break;
        default:
          window.location.href = `http://www.${key}`;
          break;
      }
    },
    emailAgain() {
      this.startCountDown();
      sendEmail({ username: this.$route.query.username }).then(res => {
        console.log(res);
        if (res.ret === 0) {
          this.$message({
            type: "success",
            message: "重发成功，请点击查看邮件",
            duration: 1500
          });
        }else{
          this.$message({
            type: "error",
            message: res.msg,
            duration: 1500
          });
        }
      });
    },
    startCountDown() {
      this.sendText = "60s 后重试";
      let s = 60;
      let set1 = setInterval(() => {
        if (s > 0) {
          s--;
          this.sendText = s + "s 后重试";
        } else {
          clearInterval(set1);
          this.sendText = "重发";
        }
      }, 1000);
    }
  }
};
</script>
<style lang="scss">
$bg: #fff;
$light_gray: #454545;
$cursor: #333;
#findPwd {
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
