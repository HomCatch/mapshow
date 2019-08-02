<template>
  <div class="main-panel">
    <div style="margin-bottom: 10px;">
      <el-button v-for="btn in funcs" :key="btn" @click="func(btn)">{{btn}}</el-button>
    </div>
    <tree-table
      :data="tableData"
      :columns="columns"
      @selectChange="rowSelect"
      border
      v-loading="loading"
    />

    <!--编辑、新增的弹窗-->
    <el-dialog :title="dialogTitle" :visible.sync="outerVisible" width="500px">
      <el-form
        :model="formData"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="经销商名称:" prop="name">
          <el-input v-model="formData.name"></el-input>
        </el-form-item>
        <!-- 一级经销商永远只有一个 -->
        <el-form-item label="上级经销商:" prop="parentName" @click="innerVisible = true">
          <p
            @click="innerVisible=true"
            style="background: #efefef;padding: 0 10px;border-radius: 3px; cursor: pointer;height: 32px;line-height:32px"
          >{{formData.parentName}}</p>
        </el-form-item>
      </el-form>
      <el-dialog width="30%" title="选择经销商" :visible.sync="innerVisible" append-to-body>
        <el-tree
          check-strictly
          :data="nodeList"
          :props="defaultProps"
          @node-click="handleNodeClick"
          default-expand-all
        ></el-tree>
        <div slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="ok">确定</el-button>
        </div>
      </el-dialog>
      <div slot="footer" class="dialog-footer">
        <el-button @click="outerVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import treeTable from "@/components/TreeTable";
import { columns } from "./data.js";
import { formatterData } from "@/common/js/formatterData.js";
import * as util from "@/common/js/util";
import { getDeptList, getSelectList, addDept, delDept, editDept } from "./api";
export default {
  name: "TreeTableDemo",
  components: { treeTable },
  data() {
    return {
      columns,
      tableData: [],
      outerVisible: false,
      innerVisible: false,
      dialogTitle: "新增",
      formData: {
        // parentName: "一级经销商",
        // parentId: 0
      },
      rules: {
        name: [{ required: true, message: "请输入经销商名称", trigger: "blur" }]
      },
      nodeList: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      selectedNode: [],
      selectedRow: [],
      loading: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      getDeptList().then(res => {
        this.loading = false;
        console.log(res.deptList);
        this.tableData = formatterData(res.deptList);
        // this.tableData.map(item => {
        //   // if (!item.parentName) {
        //   //   item.parentName = "一级经销商";
        //   // }
        // });
        console.log(this.tableData);
      });
    },
    getSelectList() {
      getSelectList().then(res => {
        this.nodeList = formatterData(res.deptList);
      });
    },
    rowSelect(val) {
      this.selectedRow = val;
    },
    func(btn) {
      switch (btn) {
        case "修改":
          this.edit();
          break;
        case "新增":
          this.add();
          break;
        case "删除":
          this.del();
          break;
      }
    },
    add() {
      this.getSelectList();
      this.dialogTitle = "新增";
      this.outerVisible = true;
    },
    edit() {
      this.getSelectList();
      if (this.selectedRow.length !== 1) {
        this.$message({
          message: "请选择一条记录",
          type: "warning"
        });
        return;
      }
      this.dialogTitle = "修改";
      this.outerVisible = true;
      this.formData = { ...this.selectedRow[0] };
      this.formData.parentName = this.formData.parentName || "一级经销商";
      console.log(this.formData);
    },
    del() {
      if (this.selectedRow.length !== 1) {
        this.$message({
          message: "请选择一条记录",
          type: "warning"
        });
        return;
      }
      this.$confirm("确认删除该经销商？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delDept({ deptId: this.selectedRow[0].deptId }).then(res => {
            if (res.code === 0) {
              this.$message({
                message: "删除成功",
                type: "success"
              });
              this.getList();
            } else if (res.code === 500) {
              this.$message({
                message: "请先删除子经销商",
                type: "warning"
              });
            } else {
              this.$message({
                message: res.msg,
                type: "error"
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            message: "已取消"
          });
        });
    },
    ok() {
      this.innerVisible = false;
      this.formData.parentId = this.selectedNode.deptId;
      this.formData.parentName = this.selectedNode.name;
    },
    handleNodeClick(data) {
      this.selectedNode = data;
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.dialogTitle === "新增") {
            addDept(this.formData).then(res => {
              console.log(res);
              if (res.code == 0) {
                this.$message({
                  message: `${this.dialogTitle}成功`,
                  type: "success"
                });
                this.outerVisible = false;
                this.getList();
              } else {
                this.$message({
                  message: res.msg,
                  type: "error"
                });
              }
            });
          } else if (this.dialogTitle === "修改") {
            delete this.formData.children;
            delete this.formData.parent;
            editDept(this.formData).then(res => {
              if (res.code == 0) {
                this.$message({
                  message: `${this.dialogTitle}成功`,
                  type: "success"
                });
                this.outerVisible = false;
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
      return util.funcsParse(this.$route, this.$store.state.auth.menus).filter(item => item !== '查看');
    }
  }
};
</script>