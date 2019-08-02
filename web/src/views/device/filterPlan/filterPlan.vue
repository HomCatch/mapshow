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
      <el-form :model="dialogData" ref="ruleForm" label-width="130px" class="demo-ruleForm">
        <!--不生成创建时间字段-->
        <el-form-item label="滤芯编号:" prop="deviceId">
          <el-input v-model="dialogData.deviceId" :disabled="dialogTitle==='编辑'"></el-input>
        </el-form-item>
        <el-form-item label="上次滤芯更换时间:" prop="previousTime">
          <!-- <el-input v-model="dialogData.previousTime"></el-input> -->
          <el-date-picker v-model="dialogData.previousTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="上次滤芯更换备注:" prop="previousRemark">
          <el-input v-model="dialogData.previousRemark"></el-input>
        </el-form-item>
        <el-form-item label="下次滤芯更换时间:" prop="nextTime">
          <el-date-picker v-model="dialogData.nextTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="下次滤芯更换备注:" prop="nextRemark">
          <el-input v-model="dialogData.nextRemark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')" :loading="btnLoading">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 导入导出 -->
    <el-dialog title="滤芯导入" :visible.sync="importDialog" width="600px">
      <el-row style="margin-top: -20px;">
        <el-col :span="18">
          <h3 style="font-size: 16px;font-weight: 700">导入说明：</h3>
          <p>
            1.导入数据前，先下载数据模板，
            <a href="javascript:void(0)" @click="downloadTemp">点击下载模板</a>
          </p>
          <p>2.填写数据并选择上传文件</p>
          <p>3.每次导入不超过5000条数据</p>
          <p>4.请注意导入数据格式及必填项</p>
        </el-col>
        <el-col :span="6">
          <my-upload
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
            @_import="importconfirm"
            :fileName="'files'"
          ></my-upload>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import { getList, del, add, edit } from "./api.js";
import { tableColumns, searchItems } from "./data";
import * as util from "@/common/js/util";
import { downloadFile } from "@/common/js/blobdownload";
import MyUpload from "@/components/upload/upload";
import { upload, download_ter_template, _export } from "@/request/upload";

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
      allDev: [],
      importDialog: false
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
        case "导入":
          this._import(row);
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
    _export(rows) {
      _export({ ids: rows.map(item => item.id) }).then(res => {
        downloadFile(res, "操作日志");
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
    },
    _import() {
      this.importDialog = true;
    },
    importconfirm(formData) {
      upload(formData).then(res => {
        this.$message({
          message: res.ret === 0 ? `导入成功` : res.msg,
          type: res.ret === 0 ? "success" : "error"
        });
        this.getList();
        this.importDialog = false;
      });
    },
    downloadTemp() {
      download_ter_template().then(res => {
        downloadFile(res, "滤芯模板");
      });
    }
  },
  computed: {
    funcs() {
      console.log(util.funcsParse(this.$route, this.$store.state.auth.menus));
      return util.funcsParse(this.$route, this.$store.state.auth.menus) || [];
    }
  },
  components: { ZTable, ZSearch, MyUpload }
};
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
