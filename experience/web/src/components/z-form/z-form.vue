<template>
  <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item v-for="item in formItems" :key="item.value" :label="item.label+'：'" :prop="item.name" :rules="item.rules">
      <el-input v-model="ruleForm.name"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  props: {
    ruleForm: {
      type: Object,
      default: {}
    },
    formItems: {
      type: Array,
      default: function() {
        return [
          {
            label: "设备别名",
            value: "deviceName",
            type: "input",
            rules: [
              { required: true, message: "订单号不能为空", trigger: "blur" }
            ]
          },
          {
            label: "商家",
            value: "merchantName",
            type: "input",
            rules: [
              { required: true, message: "订单号不能为空", trigger: "blur" }
            ]
          }
        ];
      }
    }
  }
};
</script>

<style>
</style>
