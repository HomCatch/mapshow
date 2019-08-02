<template>
  <div id="register">
    <el-form :model="registerForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
      <div class="title">
        <p :class="{'active':clicked=='phone'}" @click="clickPhone">手机</p>
        <p :class="{'active':clicked=='email'}" @click="clickEmail">邮箱</p>
      </div>
      <el-form-item prop="username" class="user">
        <p style="background:#fff">账号</p>
        <el-input
          v-model="registerForm.username"
          autocomplete="off"
          :placeholder="clicked==='phone'?'请输入手机号码':'请输入邮箱地址'"
          size="large"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <p style="background:#fff">密码</p>
        <el-input
          v-model="registerForm.password"
          show-password
          placeholder="请输入密码"
          type="password"
          size="large"
          autocomplete="off"
          name="clear"
        ></el-input>
      </el-form-item>
      <el-form-item prop="verifiCode" class="verifiCode" v-if="clicked==='phone'">
        <p style="background:#fff">验证码</p>
        <el-input
          v-model="registerForm.verifiCode"
          autocomplete="off"
          placeholder="请输入验证码"
          size="large"
        ></el-input>
        <p class="senSms" @click="senSms" v-if="btnText=='获取验证码'||btnText=='重发'">{{btnText}}</p>
        <p class="senSms try" v-if="btnText!=='获取验证码'&&btnText!=='重发'">{{btnText}}</p>
      </el-form-item>
      <!-- <el-form-item prop="checkPass">
        <el-input v-model="registerForm.checkPass" placeholder="请确认密码" type="password" size="large" show-password>
          <i slot="prefix" class="el-input__icon icon-login iconfont"></i>
        </el-input>
      </el-form-item>-->
      <el-form-item prop="captcha" class="captcha" v-if="clicked==='email'">
        <p style="background:#fff">验证码</p>
        <el-input v-model="registerForm.captcha" placeholder="请输入验证码" size="large">
          <!-- <i slot="prefix" class="el-input__icon icon-tuxingyanzhengma iconfont"></i> -->
        </el-input>
        <img :src="imgValid" alt />
        <i @click="refreshImg" class="el-icon-refresh"></i>
      </el-form-item>
      <el-form-item>
        <el-button
          style="width: 100%;"
          type="primary"
          @click="onSubmit('ruleForm')"
          size="default"
          :loading="submitting"
        >注册</el-button>
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
import { register, registerEmail, sendSms, sendEmail } from "./api";
export default {
  data() {
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.registerForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      clicked: "phone",
      registerForm: {},
      btnText: "获取验证码",
      imgValid: "",
      key: null,
      pwdType: "text",
      phone: null,
      phoneCode: "",
      submitting: false,
      rules: {
        username: [
          { required: true, trigger: "blur", message: "账号不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        captcha: [
          { required: true, trigger: "blur", message: "验证码不能为空" }
        ],
        verifiCode: [
          { required: true, trigger: "blur", message: "验证码不能为空" }
        ]
      }
    };
  },
  created() {
    this.refreshImg();
    console.log(this.$route.query);
  },
  methods: {
    login() {
      this.$router.push({ path: "/login" });
    },
    clickPhone() {
      this.clicked = "phone";
      this.registerForm = {};
    },
    clickEmail() {
      this.clicked = "email";
      this.registerForm = {};
    },
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.submitting = true;
          if (this.clicked === "phone") {
            this.registerForm.key = this.key;
            register({
              ...this.registerForm
            }).then(
              res => {
                if (res.ret === 0) {
                  this.$message({
                    message: "注册成功，稍后进入登录页面",
                    type: "success",
                    duration: 3000,
                    onClose: () => {
                      this.$router.push({
                        path: "/login"
                      });
                    }
                  });
                } else {
                  this.$message({
                    message: res.msg,
                    type: "error"
                  });
                  this.submitting = false;
                  this.registerForm.captcha = null;
                  this.refreshImg();
                }
              },
              () => {
                this.submitting = false;
              }
            );
          } else {
            this.registerForm.key = this.key;
            registerEmail({
              ...this.registerForm,
              captcha: this.registerForm.captcha.toLocaleLowerCase()
            }).then(
              res => {
                // console.log(res);
                if (res.ret === 0) {
                  sendEmail({ username: this.registerForm.username });
                  this.$message({
                    message: "需要邮箱验证进行账号激活",
                    type: "warning",
                    duration: 1000,
                    onClose: () => {
                      this.$router.push({
                        path: "/regUser",
                        query: { username: this.registerForm.username }
                      });
                    }
                  });
                } else {
                  this.$message({
                    message: res.msg,
                    type: "error"
                  });
                  this.submitting = false;
                  this.registerForm.captcha = null;
                  this.refreshImg();
                }
              },
              () => {
                this.submitting = false;
              }
            );
          }
        } else {
          return false;
        }
      });
    },
    refreshImg() {
      this.key = Math.random();
      this.imgValid = `/api/access/captcha?key=${this.key}`;
    },
    senSms() {
      this.startCountDown();
      sendSms({ phone: this.registerForm.username }).then(res => {
        if (res.ret !== 0) {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
      });
    },
    startCountDown() {
      this.btnText = "60s 后重发";
      let s = 60;
      let set1 = setInterval(() => {
        if (s > 0) {
          s--;
          this.btnText = s + "s 后重发";
        } else {
          clearInterval(set1);
          this.btnText = "重发";
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
#register {
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
