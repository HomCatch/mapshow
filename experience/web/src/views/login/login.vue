<template>
  <div id="login" @keyup.enter="onSubmit('ruleForm')">
    <el-form :model="loginForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
      <p style="text-align: center;font-size: 22px;font-weight: 400;line-height: 80px;" size="large">用户登录</p>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" placeholder="用户名" size="large">
          <i slot="prefix" class="el-input__icon icon-denglu iconfont"></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" placeholder="密码" type="password" size="large">
          <i slot="prefix" class="el-input__icon icon-login iconfont"></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="captcha" class="captcha">
        <el-input v-model="loginForm.captcha" placeholder="验证码">
          <i slot="prefix" class="el-input__icon icon-tuxingyanzhengma iconfont"></i>
        </el-input>
        <img :src="imgValid" alt="" @click="refreshImg" style="cursor: pointer;">
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%;" type="primary" @click="onSubmit('ruleForm')" size="default" :loading="submitting">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { login } from "./api";
export default {
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
        captcha: null
      },
      imgValid: "",
      key: null,
      submitting: false,
      rules: {
        username: [
          { required: true, trigger: "blur", message: "请输入用户名" }
        ],
        password: [{ required: true, trigger: "blur", message: "请输入密码" }],
        captcha: [{ required: true, trigger: "blur", message: "请输入验证码" }]
      }
    };
  },
  created() {
    this.refreshImg();
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.submitting = true;
          this.loginForm.key = this.key;
          login({
            ...this.loginForm,
            captcha: this.loginForm.captcha.toLocaleLowerCase()
          }).then(res => {
            if (res.ret === 0) {
              localStorage.setItem("token", res.datas.token);
              localStorage.setItem("username", this.loginForm.username);
              localStorage.setItem("userId", res.datas.userId);
              this.$router.push({ path: "/content" });
            } else {
              this.$message({
                message: res.msg,
                type: "error"
              });
              this.submitting = false;
              this.loginForm.captcha = null;
              this.refreshImg();
            }
          });
        } else {
          return false;
        }
      });
    },
    refreshImg() {
      this.key = Math.random();
      this.imgValid = `/api/access/captcha?key=${this.key}`;
    }
  }
};
</script>
<style lang="scss">
$bg: #283443;
$light_gray: #eee;
$cursor: #fff;
#login {
  .el-form {
    background: rgba(255, 255, 255, 0.1);
    padding: 10px 20px;
    border-radius: 10px;
  }
  .el-input {
    display: inline-block;
    input {
      background: transparent;
      border: 0px;
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
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style scoped lang="scss">
@import "./index.scss";
</style>
