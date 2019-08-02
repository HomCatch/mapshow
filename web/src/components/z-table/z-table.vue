<template>
  <div class="z-table">
    <div class="funcs">
      <el-button v-for="item in funcs" :key="item" @click="func(item)" :type="item === '删除' ? 'danger': 'default'">{{item}}</el-button>
      <!-- <el-button @click="func('导出')" v-if="funcs.indexOf('导出') !== -1">导出</el-button> -->
    </div>
    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column v-for="column in tableColumns" :key="column.value" :label="column.label" :width="column.width" show-overflow-tooltip>
        <!-- 单行表头， 可接收html格式文本 -->
        <template slot-scope="scope" v-if="!column.mul">
          <div v-html="column.formatter(scope.row)" v-if="column.formatter"></div>
          <div v-else>
            {{scope.row[column.value]}}
          </div>
        </template>
        <!-- 多行表头， 可接收html格式文本 -->
        <el-table-column v-if="column.mul" v-for="subColumn in column.subColumns" :label="subColumn.label" :key="subColumn.value" :width="subColumn.width" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-html="subColumn.formatter(scope.row)" v-if="subColumn.formatter"></div>
            <div v-else>
              {{scope.row[subColumn.value]}}
            </div>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100" v-if="btns.length > 0">
        <template slot-scope="scope">
          <el-button v-for="text in btns" :key="text" @click="btnClick(scope.row, text)" type="text" size="small">{{text}}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @current-change="handleCurrentChange" :current-page="page.currentPage" background :page-size="10" layout="prev, pager, next" :total="page.total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  props: {
    tableData: {
      type: Array,
      default: function() {
        return [{ name: "zzq" }];
      }
    },
    tableColumns: {
      type: Array,
      default: function() {
        return [{ label: "姓名", value: "name" }];
      }
    },
    page: {
      type: Object,
      default: function() {
        return {
          currentPage: 1,
          total: 0
        };
      }
    },
    funcs: {
      type: Array,
      default: function() {
        return ["新增"];
      }
    },
    btns: {
      type: Array,
      default: function() {
        return [];
      }
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleCurrentChange(val) {
      this.$emit("handleCurrentChange", val);
    },
    // 向父组件触发功能函数
    func(opera) {
      if (opera === "新增" || opera === "打印表格" || opera === '导出' || opera === '导入') {
        // 新增不需要检测是否选中一项
        this.$emit("func", { opera, row: this.multipleSelection });
      } else if (opera === "删除") {
        if (!this.multipleSelection || this.multipleSelection.length === 0) {
          this.$message({
            message: "请至少选择一项进行操作",
            type: "warning"
          });
          return;
        }
        this.$emit("func", { opera, row: this.multipleSelection });
      } else {
        // 非新增需要检测是否选中一项
        if (
          !this.multipleSelection ||
          this.multipleSelection.length === 0 ||
          this.multipleSelection.length > 1
        ) {
          this.$message({
            message: "请选择一项进行操作",
            type: "warning"
          });
          return;
        }
        this.$emit("func", { opera, row: this.multipleSelection[0] });
      }
    },
    btnClick(row, opera) {
      this.$emit("btnClick", { opera, row });
    }
  }
};
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
