<template>
  <div class="box">
    <!-- 搜索 -->
    <z-search :searchItems="searchItems" @search="search"></z-search>
    <!-- 功能+table -->
    <z-table
      :tableData="tableData"
      :tableColumns="tableColumns"
      :page="page"
      :funcs="funcs"
      @func="func"
      @handleCurrentChange="handleCurrentChange"
      v-loading="tableLoading"
    ></z-table>
    <!-- 新增/编辑dialog -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="dialogData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--不生成创建时间字段-->
        <el-form-item label="设备ID:" prop="deviceId">
          <el-input v-model="dialogData.deviceId"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="deviceName">
          <el-input v-model="dialogData.deviceName"></el-input>
        </el-form-item>
        <el-form-item label="历史记录时间:" prop="recordTime">
          <el-input v-model="dialogData.recordTime"></el-input>
        </el-form-item>
        <el-form-item label="净化前tds 值:" prop="tds">
          <el-input v-model="dialogData.tds"></el-input>
        </el-form-item>
        <el-form-item label="净化前ph 值:" prop="ph">
          <el-input v-model="dialogData.ph"></el-input>
        </el-form-item>
        <el-form-item label="净化前色度:" prop="color">
          <el-input v-model="dialogData.color"></el-input>
        </el-form-item>
        <el-form-item label="净化前温度:" prop="trt">
          <el-input v-model="dialogData.trt"></el-input>
        </el-form-item>
        <el-form-item label="净化前水浊度:" prop="tbdt">
          <el-input v-model="dialogData.tbdt"></el-input>
        </el-form-item>
        <el-form-item label="净化后的tds:" prop="purifyTds">
          <el-input v-model="dialogData.purifyTds"></el-input>
        </el-form-item>
        <el-form-item label="净化后的ph 值:" prop="purifyPh">
          <el-input v-model="dialogData.purifyPh"></el-input>
        </el-form-item>
        <el-form-item label="净化后的色度:" prop="purifyColor">
          <el-input v-model="dialogData.purifyColor"></el-input>
        </el-form-item>
        <el-form-item label="净化后的温度:" prop="purifyTrt">
          <el-input v-model="dialogData.purifyTrt"></el-input>
        </el-form-item>
        <el-form-item label="净化后的水浊度:" prop="purifyTbdt">
          <el-input v-model="dialogData.purifyTbdt"></el-input>
        </el-form-item>
        <el-form-item label="水量:" prop="amount">
          <el-input v-model="dialogData.amount"></el-input>
        </el-form-item>
        <el-form-item label="净化后水量:" prop="purifyAmount">
          <el-input v-model="dialogData.purifyAmount"></el-input>
        </el-form-item>
        <el-form-item label="净化后水量:" prop="purifyAmountBase">
          <el-input v-model="dialogData.purifyAmountBase"></el-input>
        </el-form-item>
        <el-form-item label="净化后水量:" prop="purifyAmountTotal">
          <el-input v-model="dialogData.purifyAmountTotal"></el-input>
        </el-form-item>
        <el-form-item label="当日用水量:" prop="todayAmount">
          <el-input v-model="dialogData.todayAmount"></el-input>
        </el-form-item>
        <el-form-item label="周用水量:" prop="weekAmount">
          <el-input v-model="dialogData.weekAmount"></el-input>
        </el-form-item>
        <el-form-item label="月用水量:" prop="monthAmount">
          <el-input v-model="dialogData.monthAmount"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="deviceX">
          <el-input v-model="dialogData.deviceX"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="deviceY">
          <el-input v-model="dialogData.deviceY"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="deviceDesc">
          <el-input v-model="dialogData.deviceDesc"></el-input>
        </el-form-item>
        <el-form-item label="区域:" prop="regionId">
          <el-input v-model="dialogData.regionId"></el-input>
        </el-form-item>
        <el-form-item label="绑定的经销商ID:" prop="bindedUserId">
          <el-input v-model="dialogData.bindedUserId"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="lastTime">
          <el-input v-model="dialogData.lastTime"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')" :loading="btnLoading">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import { getList, del, add, edit, _export } from "./api.js";
import { tableColumns, searchItems } from "./data";
import * as util from "@/common/js/util";
import { downloadFile } from "@/common/js/blobdownload";

export default {
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      tableColumns,
      searchItems,
      tableLoading: false,
      btnLoading: false,
      searchForm: {},
      dialogTitle: "新增",
      dialogVisible: false,
      labelWidth: "100px",
      dialogData: {
        schemalist: []
      },
      allDev: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.tableLoading = true;
      const params = { page: this.page.currentPage, ...this.searchForm };
      getList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.datas.list;
        this.page.total = res.datas.itemCounts;
        if (
          res.datas.list &&
          res.datas.list.length == 0 &&
          this.page.currentPage != 1
        ) {
          this.page.currentPage--;
          this.getList();
        }
      });
    },
    search(searchForm) {
      console.log(searchForm);
      this.page.currentPage = 1;
      this.searchForm = searchForm;
      this.getList();
    },
    func({ opera, row }) {
      switch (opera) {
        case "修改":
          this.edit(row);
          break;
        case "新增":
          this.add();
          break;
        case "删除":
          this.del(row);
          break;
        case "导出":
          this._export(row);
          break;
      }
    },
    add() {
      this.dialogTitle = "新增";
      this.dialogData = {};
      this.dialogVisible = true;
    },
    edit(row) {
      this.dialogTitle = "编辑";
      this.dialogData = { ...row };
      this.dialogVisible = true;
    },
    del(row) {
      row = row.map(item => item.id);
      this.$confirm("此操作将永久删除所选数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          del({ id: row }).then(res => {
            console.log("shanchu ");
            this.$message({
              message: res.ret === 0 ? "删除成功" : res.msg,
              type: res.ret === 0 ? "success" : "error"
            });
            this.getList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    _export(row) {
      _export().then(res => {
        downloadFile(res, "水质详情");
      });
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    },
    submitForm(formName) {
      //不解析下面代码

      this.$refs[formName].validate(valid => {
        if (valid) {
          const params = this.dialogData;
          if (this.dialogTitle === "编辑") {
            edit({
              id: this.dialogData.id,
              data: { ...this.dialogData }
            }).then(res => {
              if (res.ret == 0) {
                this.$message({
                  message: "修改成功",
                  type: "success"
                });
                this.dialogVisible = false;
                this.getList();
              } else {
                this.$message({
                  message: res.msg,
                  type: "error"
                });
              }
            });
          } else if (this.dialogTitle === "新增") {
            add(params).then(res => {
              if (res.ret == 0) {
                this.$message({
                  message: "新增成功",
                  type: "success"
                });
                this.dialogVisible = false;
                this.getList();
              } else {
                this.$message({
                  message: res.msg,
                  type: "error"
                });
              }
            });
          }
        } else {
          return false;
        }
      });
    }
  },
  computed: {
    funcs() {
      return util.funcsParse(this.$route, this.$store.state.auth.menus);
    }
  },
  components: { ZTable, ZSearch }
};
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
