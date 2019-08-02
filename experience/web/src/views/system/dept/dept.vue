<template>
  <div class="main-panel">
    <div style="margin-bottom: 10px;">
      <el-button v-if="func.includes('add')" @click="add">新增</el-button>
      <el-button v-if="func.includes('edit')" @click="edit">修改</el-button>
      <el-button v-if="func.includes('del')" @click="del" type="danger">删除</el-button>
    </div>
    <tree-table :data="tableData" :columns="columns" @selectChange="rowSelect" border v-loading="loading" />

    <!--编辑、新增的弹窗-->
    <el-dialog :title="dialogTitle" :visible.sync="outerVisible" width="500px">
      <el-form :model="formData" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="部门名称:" prop="name">
          <el-input v-model="formData.name"></el-input>
        </el-form-item>
        <el-form-item label="上级部门:" prop="parentName" @click="innerVisible = true">
          <p @click="innerVisible=true" style="background: #efefef;padding: 0 10px;border-radius: 3px; cursor: pointer;height: 32px;line-height:32px">{{formData.parentName}}</p>
        </el-form-item>
      </el-form>
      <el-dialog width="30%" title="选择部门" :visible.sync="innerVisible" append-to-body>
        <el-tree check-strictly :data="nodeList" :props="defaultProps" @node-click="handleNodeClick" default-expand-all></el-tree>
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
import { columns, func } from "./data.js";
import { formatterData } from "@/common/js/formatterData.js";
import {
  getDeptList,
  getSelectList,
  addDept,
  delDept,
  editDept
} from "./api";
export default {
  name: "TreeTableDemo",
  components: { treeTable },
  data() {
    return {
      columns,
      func,
      tableData: [],
      outerVisible: false,
      innerVisible: false,
      dialogTitle: "新增",
      formData: {
        parentName: "一级部门",
        parentId: 0
      },
      rules: {
        name: [
          { required: true, message: '请输入部门名称', trigger: 'blur' }
        ]
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
        console.log(res.deptList)
        this.tableData = formatterData(res.deptList);
        this.tableData.map(item => {
          if (!item.parentName) {
            item.parentName = "一级部门";
          }
        });
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
      this.formData = {...this.selectedRow[0]};
      this.formData.parentName = this.formData.parentName || "一级部门";
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
      this.$confirm("确认删除该部门？", "提示", {
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
                message: "请先删除子部门",
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
              console.log(res)
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
  }
};
</script>