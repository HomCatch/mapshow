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
        <el-form-item label="设备号:" prop="deviceId">
          <el-input v-model="dialogData.deviceId"></el-input>
        </el-form-item>
        <el-form-item label="客户:" prop="customer">
          <el-input v-model="dialogData.customer"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="phoneNumber">
          <el-input v-model="dialogData.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="0：更换--1：不更换:" prop="replaceFirstFilter">
          <el-input v-model="dialogData.replaceFirstFilter"></el-input>
        </el-form-item>
        <el-form-item label="0：更换--1：不更换:" prop="replaceSecondFilter">
          <el-input v-model="dialogData.replaceSecondFilter"></el-input>
        </el-form-item>
        <el-form-item label="0：更换--1：不更换:" prop="replaceThirdFilter">
          <el-input v-model="dialogData.replaceThirdFilter"></el-input>
        </el-form-item>
        <el-form-item label="0：更换--1：不更换:" prop="replaceFouthFilter">
          <el-input v-model="dialogData.replaceFouthFilter"></el-input>
        </el-form-item>
        <el-form-item label="需要更换时间:" prop="planReplaceTime">
          <el-input v-model="dialogData.planReplaceTime"></el-input>
        </el-form-item>
        <el-form-item label="更换时间:" prop="realReplaceTime">
          <el-input v-model="dialogData.realReplaceTime"></el-input>
        </el-form-item>
        <el-form-item label="地址:" prop="address">
          <el-input v-model="dialogData.address"></el-input>
        </el-form-item>
        <el-form-item label="是否已更换(0：已换，1：未换）:" prop="replaceFinshed">
          <el-input v-model="dialogData.replaceFinshed"></el-input>
        </el-form-item>
        <el-form-item label="维护人:" prop="repairer">
          <el-input v-model="dialogData.repairer"></el-input>
        </el-form-item>
        <el-form-item label="维护人电话:" prop="repairerPhoneNumber">
          <el-input v-model="dialogData.repairerPhoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="备注:" prop="remark">
          <el-input v-model="dialogData.remark"></el-input>
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
import { getList, del, add, edit, _export, gene } from "./api.js";
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
        case "生成":
          this.gene(row);
          break;
      }
    },
    gene() {
      gene().then(
        res => {
          if (res.ret === 0) {
            this.getList();
          }
          this.$message({
            message: res.ret === 0 ? "生成完成" : res.msg,
            type: res.ret === 0 ? "success" : "error"
          });
        },
        () => {
          this.$message({
            message: "生成出错",
            type: "error"
          });
        }
      );
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
        downloadFile(res, "滤芯管理");
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
