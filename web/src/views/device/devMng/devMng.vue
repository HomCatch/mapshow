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
        <el-form-item label="设备ID：" prop="deviceId">
          <el-input v-model="dialogData.deviceId"></el-input>
        </el-form-item>
        <el-form-item label="设备别名：" prop="deviceName">
          <el-input v-model="dialogData.deviceName"></el-input>
        </el-form-item>
        <el-form-item label="所属经销商：" prop="deptId">
          <p
            style="width: 200px;height: 30px;line-height: 30px;border: 1px solid #eee;background: #eee;cursor: pointer;"
            @click="deptVisible = true"
          >{{dialogData.deptName}}</p>
        </el-form-item>
        <el-form-item label="地理位置:">
          <my-map :pos="[dialogData.deviceY, dialogData.deviceX]" @setPos="setPos"></my-map>
          <!-- <el-input v-model="dialogData.remark">此处要调用百度地图</el-input> -->
        </el-form-item>
        <el-form-item label="备注:" prop="remark">
          <el-input v-model="dialogData.remark"></el-input>
        </el-form-item>
      </el-form>
      <el-dialog title="选择经销商" :visible.sync="deptVisible" z-index="600">
        <el-tree :data="allDept" :props="defaultProps" accordion @node-click="handleNodeClick"></el-tree>
        <div slot="footer" class="dialog-footer">
          <el-button @click="deptVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmDeptId">确 定</el-button>
        </div>
      </el-dialog>
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
import { getList, del, add, edit, _export, getAllDept } from "./api.js";
import { tableColumns, searchItems } from "./data";
import * as util from "@/common/js/util";
import { downloadFile } from "@/common/js/blobdownload";
import map from "./map/map";

export default {
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      depts: [],
      tableColumns,
      searchItems,
      tableLoading: false,
      btnLoading: false,
      searchForm: {},
      dialogTitle: "新增",
      deptVisible: false,
      dialogVisible: false,
      labelWidth: "100px",
      dialogData: {
        schemalist: [],
        deptName: null
      },
      allDev: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      allDept: []
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
        console.log("res", res);
        if (res.ret === 0) {
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
      this.getAllDept();
    },
    async edit(row) {
      this.dialogTitle = "编辑";
      this.dialogData = { ...row, deptName: null };
      this.dialogVisible = true;
      await this.getAllDept();
      // this.dialogData.deptName = row.deptName;
      this.dialogData.deptId = row.deptId;
      this.dialogData.deptName = this.depts.filter(
        item => item.deptId == row.deptId
      )[0].name;
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
        downloadFile(res, "设备管理");
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
            edit({ id: this.dialogData.id, data: { ...this.dialogData } }).then(
              res => {
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
              }
            );
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
    async getAllDept() {
      this.treeLoading = true;
      await getAllDept().then(res => {
        this.treeLoading = false;
        if (res.code == 0) {
          console.log(res);
          this.allDept = util.formatterData(res.deptList, "deptId");
          this.depts = res.deptList;
          console.log(this.allDept);
        }
      });
    },
    setPos(params) {
      console.log(params);
      this.dialogData.deviceY = params[0];
      this.dialogData.deviceX = params[1];
    },
    handleNodeClick(val) {
      console.log(val);
      this.selectedDeptId = val.deptId;
      this.selectedDeptName = val.name;
    },
    confirmDeptId() {
      this.deptVisible = false;
      this.dialogData.deptId = this.selectedDeptId;
      this.dialogData.deptName = this.selectedDeptName;
    }
  },

  computed: {
    funcs() {
      // return ["修改"];
      return (
        util.funcsParse(this.$route, this.$store.state.auth.menus) || ["修改"]
      );
    }
  },
  components: { ZTable, ZSearch, "my-map": map }
};
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
