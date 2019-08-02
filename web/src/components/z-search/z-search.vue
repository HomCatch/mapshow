<template>
  <el-form :inline="true" :model="searchForm" class="demo-form-inline">
    <el-form-item :label="item.label + '：'" v-for="item in searchItems" :key="item.value">
      <!-- 普通input框 -->
      <el-input v-model="searchForm[item.value]" :placeholder="item.placeholder" v-if="item.type==='input'"></el-input>
      <!-- select框 -->
      <el-select v-model="searchForm[item.value]" :placeholder="item.placeholder" v-if="item.type === 'select'">
        <el-option v-for="option in item.options" :key="option.value" :label="option.label" :value="option.value"></el-option>
      </el-select>
      <!-- datetimerange -->
      <el-date-picker v-model="searchForm[item.value]" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" v-if="item.type==='datetimerange'" value-format="timestamp">
      </el-date-picker>
      <!-- datetime -->
      <el-date-picker v-model="searchForm[item.value]" type="datetime" v-if="item.type==='datetime'" value-format="yyyy-MM-dd HH:mm:ss">
      </el-date-picker>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  props: {
    searchItems: {
      type: Array,
      default: function() {
        return [{ label: "名称", value: "name", width: 100, type: "input" }];
      }
    }
  },
  data() {
    return {
      searchForm: {}
    };
  },
  methods: {
    search() {
      this.$emit("search", { ...this.searchForm });
    }
  }
};
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
