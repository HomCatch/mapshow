<template>
  <div id="setNewPwd">
    <el-form v-if="!dialogVisible && !dialogVisible2" :model="setNewPwdForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
      <p
        style="text-align: center;font-size: 22px;font-weight: 600;line-height: 50px;width:190px;padding:20px;padding-left:120px"
        size="large"
      >设置新密码</p>
      <el-form-item prop="password">
        <p style="background:#fff">新密码</p>
        <el-input
          v-model="setNewPwdForm.password"
          show-password
          placeholder="请输入密码"
          type="password"
          size="large"
          autocomplete="off"
          name="clear"
        ></el-input>
      </el-form-item>
      <el-form-item prop="repPassword">
        <p style="background:#fff">再次输入新密码</p>
        <el-input
          v-model="setNewPwdForm.repPassword"
          placeholder="请确认密码"
          type="password"
          size="large"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          style="width: 100%;"
          type="primary"
          @click="onSubmit('ruleForm')"
          size="default"
          :loading="submitting"
        >确认</el-button>
      </el-form-item>
      <el-form-item>
        <p style="text-align:center;line-height:30px;background:#fff">
          <span @click="login" style="color: #409eff;cursor: pointer;font-size: 14px;">登录</span>
        </p>
      </el-form-item>
    </el-form>
    <!-- 登录成功弹窗 -->
    <el-dialog title="提示" :visible.sync="dialogVisible" width="500px" class="dialog" :show-close="false">
      <div class="line"></div>
      <p>密码设置成功，点击进行登录</p>
      <el-button class="login" size="large" @click="login" type="primary">返回登录</el-button>
    </el-dialog>
    <!-- 链接失效弹窗 -->
    <el-dialog title="提示" :visible.sync="dialogVisible2" width="500px" class="dialog" :show-close="false">
      <div class="line"></div>
      <p>验证链接已失效，请重新发送邮件</p>
      <el-button class="login" size="large" @click="login" type="primary">返回登录</el-button>
    </el-dialog>
  </div>
</template>

<script>
import { setNewPwd } from "./api";
export default {
  data() {
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.setNewPwdForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      setNewPwdForm: {},
      key: null,
      submitting: false,
      rules: {
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        repPassword: [{ validator: validatePass2, trigger: "blur" }]
      },
      dialogVisible:false,
      dialogVisible2:false,
    };
  },
  created() {
    console.log("query",this.$route.query);
  },
  methods: {
    login() {
      this.$router.push({ path: "/login" });
    },
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.submitting = true;
          if (this.$route.query.verifiCode) {
            this.setNewPwdForm.verifiCode = this.$route.query.verifiCode;
            this.setNewPwdForm.username = this.$route.query.username;
            this.setNewPwdForm.accountType = 0;
          }else{
            this.setNewPwdForm.username = this.$route.query.email;
            this.setNewPwdForm.authcode = this.$route.query.authcode;
            this.setNewPwdForm.accountType = 1;
          }
          setNewPwd({
            ...this.setNewPwdForm
          }).then(
            res => {
              if (res.ret === 0) {
                this.$message({
                  message: "修改密码成功",
                  type: "success",
                  duration: 1500,
                  onClose: () => {
                    this.dialogVisible = true;
                  }
                });
                // this.$router.push({ path: "/login" });
              }else if(res.msg === "验证链接已失效，请重新发送邮件！"){
                this.dialogVisible2 = true;
              }else {
                this.$message({
                  message: res.msg,
                  type: "error"
                });
                this.submitting = false;
                this.setNewPwdForm.captcha = null;
                this.refreshImg();
              }
            },
            () => {
              this.submitting = false;
            }
          );
        } else {
          return false;
        }
      });
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
