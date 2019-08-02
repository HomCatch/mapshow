<template>
  <div class="role box">
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
      <el-form
        :model="dialogData"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
        :disabled="dialogTitle === '详情'"
      >
        <el-form-item label="角色名称：" prop="roleName">
          <el-input v-model="dialogData.roleName"></el-input>
        </el-form-item>
        <el-form-item label="所属经销商：" prop="deptId">
          <p
            style="width: 200px;height: 30px;line-height: 30px;border: 1px solid #eee;background: #eee;cursor: pointer;"
            @click="deptVisible = true"
          >{{dialogData.deptName}}</p>
        </el-form-item>
        <el-form-item label="功能权限：" prop="menuIdList">
          <el-tree
            v-loading="treeLoading"
            :data="allMenu"
            :default-checked-keys="dialogData.menuIdList || []"
            show-checkbox
            node-key="menuId"
            default-expand-all
            :props="defaultProps"
            check-strictly
            ref="roleTree"
          ></el-tree>
        </el-form-item>
        <el-form-item label="数据权限：" prop="deptIdList">
          <el-tree
            v-loading="treeLoading"
            :data="allDept"
            :default-checked-keys="dialogData.deptIdList || []"
            show-checkbox
            node-key="deptId"
            default-expand-all
            :props="defaultProps"
            check-strictly
            ref="deptTree"
          ></el-tree>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input type="textarea" v-model="dialogData.remark"></el-input>
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
import { tableColumns, searchItems } from "./data";
import {
  roleList,
  role,
  addRole,
  editRole,
  delRole,
  getAllMenu,
  getAllDept
} from "./api";
import * as util from "@/common/js/util";

export default {
  data() {
    return {
      selectedDeptId: null,
      selectedDeptName: null,
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      tableColumns,
      searchItems,
      tableLoading: false,
      dialogFormVisible: false,
      deptVisible: false,
      searchForm: {},
      dialogTitle: "新增",
      dialogVisible: false,
      labelWidth: "100px",
      dialogData: {
        schemalist: []
      },
      allMenu: [],
      allDept: [],
      treeLoading: false,
      defaultProps: {
        children: "children",
        label: "name"
      },
      btnLoading: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.tableLoading = true;
      const params = {
        limit: 10,
        page: this.page.currentPage,
        ...this.searchForm
      };
      roleList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.page.list;
        this.page.total = res.page.totalCount;
      });
    },
    getAllMenu() {
      this.treeLoading = true;
      getAllMenu().then(res => {
        this.treeLoading = false;
        if (res.code == 0) {
          this.allMenu = util.formatterData(res.menulist, "menuId");
        }
      });
    },
    getAllDept() {
      this.treeLoading = true;
      getAllDept().then(res => {
        this.treeLoading = false;
        if (res.code == 0) {
          console.log(res);
          this.allDept = util.formatterData(res.deptList, "deptId");
          console.log(this.allDept);
        }
      });
    },
    search(searchForm) {
      this.page.currentPage = 1;
      this.searchForm = searchForm;
      this.getList();
    },
    func({ opera, row }) {
      switch (opera) {
        case "查看":
          this.view(row);
          break;
        case "修改":
          this.edit(row);
          break;
        case "新增":
          this.add();
          break;
        case "删除":
          this.del(row);
          break;
      }
    },
    add() {
      this.dialogTitle = "新增";
      this.dialogData = {};
      this.dialogVisible = true;
      this.getAllMenu();
      this.getAllDept();
    },
    view(row) {
      this.dialogTitle = "详情";
      // this.dialogData = {...row};
      this.dialogVisible = true;
      this.getAllMenu();
      this.getAllDept();
      role({ roleId: row.roleId }).then(res => {
        if (res.code === 0) {
          this.dialogData = res.role;
        }
      });
    },
    edit(row) {
      this.dialogTitle = "编辑";
      this.dialogVisible = true;
      this.getAllMenu();
      this.getAllDept();
      role({ roleId: row.roleId }).then(res => {
        if (res.code === 0) {
          this.dialogData = res.role;
          this.dialogData.deptName = row.deptName;
        }
      });
    },
    del(row) {
      this.$confirm("此操作将永久删除该条数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delRole([row[0].roleId]).then(res => {
            this.$message({
              message: res.code === 0 ? "删除成功" : res.msg,
              type: res.code === 0 ? "success" : "error"
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
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.btnLoading = true;
          if (this.dialogTitle === "编辑") {
            editRole({
              ...this.dialogData,
              menuIdList: this.$refs.roleTree.getCheckedKeys(),
              deptIdList: this.$refs.deptTree.getCheckedKeys()
            }).then(res => {
              this.btnLoading = false;
              if (res.code == 0) {
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
            addRole({
              ...this.dialogData,
              menuIdList: this.$refs.roleTree.getCheckedKeys(),
              deptIdList: this.$refs.deptTree.getCheckedKeys()
            }).then(res => {
              if (res.code == 0) {
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
          } else if (this.dialogTitle === "详情") {
            this.dialogVisible = false;
          }
        } else {
          return false;
        }
      });
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
      return util.funcsParse(this.$route, this.$store.state.auth.menus);
    }
  },
  components: { ZTable, ZSearch }
};
</script>

<style scoped lang="scss">
</style>
