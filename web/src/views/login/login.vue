<template>
  <div id="login" @keyup.enter="onSubmit('ruleForm')">
    <el-form :model="loginForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
      <p
        style="text-align: center;font-size: 22px;font-weight: 600;line-height: 36px;width:190px;padding:20px;padding-left:130px"
        size="large"
      >小荷净水大数据云管理平台</p>
      <el-form-item prop="username">
        <p style="background:#fff">账号</p>
        <el-input v-model="loginForm.username" placeholder="请输入账号" size="large">
          <!-- <i slot="prefix" class="el-input__icon icon-denglu iconfont"></i> -->
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <p style="background:#fff">密码</p>
        <el-input v-model="loginForm.password" placeholder="请输入密码" type="password" size="large">
          <!-- <i slot="prefix" class="el-input__icon icon-login iconfont"></i> -->
        </el-input>
      </el-form-item>

      <el-form-item prop="captcha" class="captcha">
        <p style="background:#fff">验证码</p>
        <el-input v-model="loginForm.captcha" placeholder="请输入验证码" size="large">
          <!-- <i slot="prefix" class="el-input__icon icon-tuxingyanzhengma iconfont"></i> -->
        </el-input>
        <img :src="imgValid" alt />
        <i @click="refreshImg" class="el-icon-refresh"></i>
      </el-form-item>
      <div class="forgetPwd">
        <el-checkbox v-model="checked">7天免登录</el-checkbox>
        <p style="text-align:right;line-height:30px;">
          <span @click="forget" style="color: #409eff;cursor: pointer;font-size: 14px;">忘记密码</span>
        </p>
      </div>
      <el-form-item>
        <el-button
          style="width: 100%;"
          type="primary"
          @click="onSubmit('ruleForm')"
          size="default"
          :loading="submitting"
        >登录</el-button>
      </el-form-item>
      <el-form-item>
        <p style="text-align:center;line-height:30px;background:#fff">
          <span @click="register" style="color: #409eff;cursor: pointer;font-size: 14px;">注册新账号</span>
        </p>
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
          { required: true, trigger: "blur", message: "账号不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        captcha: [
          { required: true, trigger: "blur", message: "验证码不能为空" }
        ]
      },
      checked: false
    };
  },
  created() {
    this.refreshImg();
  },
  methods: {
    register() {
      this.$router.push({ path: "/register" });
    },
    forget() {
      this.$router.push({ path: "/forgetPwd" });
    },
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.submitting = true;
          this.loginForm.key = this.key;
          localStorage.setItem("username", this.loginForm.username);
          login({
            ...this.loginForm,
            captcha: this.loginForm.captcha.toLocaleLowerCase(),
            rememberMe:this.checked
          }).then(res => {
            if (res.ret === 0) {
              localStorage.setItem("token", res.datas.token);
              localStorage.setItem("userId", res.datas.userId);
              this.$router.push({ path: "/content" });
            } else if (res.ret === 2002) {
              this.$message({
                message: "用户名或密码有误",
                type: "error"
              });
              this.submitting = false;
              this.loginForm.captcha = null;
              this.refreshImg();
            }else if(res.ret === 1010){
              this.$message({
                message: "您的邮箱尚未激活，3s后将进入激活页面,请手动点击重发验证邮件以激活账号。",
                type: "error"
              });
              setTimeout(()=> {
                this.$router.push({ path: "/regUser",query: {username:this.loginForm.username}});
              },3000);
              this.submitting = false;
              this.loginForm.captcha = null;
              this.refreshImg();
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
$bg: #fff;
$light_gray: #454545;
$cursor: #333;
#login {
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
    // border: 1px solid #e4e8eb;
    // background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style scoped lang="scss">
@import "./index.scss";
</style>
