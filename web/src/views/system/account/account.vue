<template>
    <div class="box">
        <!-- 搜索 -->
        <z-search :searchItems="searchItems" @search="search"></z-search>
        <!-- 表格 -->
        <z-table
                :tableData="tableData"
                :tableColumns="tableColumns"
                :page="page"
                :funcs="funcs"
                @func="func"
                @handleCurrentChange="handleCurrentChange"
                v-loading="tableLoading"
        ></z-table>

        <!-- 编辑/新增 -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
            <el-form
                    :model="dialogData"
                    :rules="rules"
                    ref="ruleForm"
                    label-width="150px"
                    class="demo-ruleForm"
                    :disabled="dialogTitle==='详情'"
            >
                <el-form-item label="账户名：" prop="username">
                    <el-input v-model="dialogData.username"></el-input>
                </el-form-item>
                <el-form-item label="所属经销商：">
                    <p
                            style="min-width: 200px;padding-left: 10px;height: 30px;line-height: 30px;border: 1px solid #eee;background: #eee;cursor: pointer;"
                            @click="deptVisible = true"
                    >{{dialogData.deptName}}</p>
                </el-form-item>
                <el-form-item label="角色：" prop="roleIdList">
                    <!-- 角色列表 -->
                    <!-- <el-input v-model="dialogData.roleIdList"></el-input> -->
                    <el-select
                            v-model="dialogData.roleIdList"
                            multiple
                            placeholder="请选择"
                            style="width: 100%;"
                    >
                        <el-option
                                v-for="item in allRole"
                                :key="item.roleId"
                                :label="item.roleName"
                                :value="item.roleId"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="密码：" prop="password">
                    <el-input v-model="dialogData.password" type="password"></el-input>
                </el-form-item>
                <el-form-item label="手机号：" prop="mobile">
                    <el-input v-model="dialogData.mobile"></el-input>
                </el-form-item>
                <el-form-item label="备注：" prop="remark">
                    <el-input v-model="dialogData.remark" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <el-dialog title="选择经销商" :visible.sync="deptVisible" :append-to-body="true">
                <el-tree :data="allDept" :props="defaultProps" accordion @node-click="handleNodeClick"></el-tree>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="deptVisible = false">取 消</el-button>
                    <el-button type="primary" @click="confirmDeptId">确 定</el-button>
                </div>
            </el-dialog>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="ok" :loading="oking">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import ZTable from "@/components/z-table/z-table";
    import ZSearch from "@/components/z-search/z-search";
    import {users, getUser, addUser, editUser, delUser, getAllDept} from "./api";
    import {tableColumns, searchItems} from "./data";
    import {roleList} from "../role/api";
    import Moment from "moment";
    import * as util from "@/common/js/util";

    export default {
        data() {
            return {
                selectedDeptId: null,
                selectedDeptName: null,
                headers: {
                    token: null
                },
                searchData: {},
                tableData: [],
                tableColumns,
                page: {
                    total: 0,
                    currentPage: 1
                },
                searchItems,
                tableLoading: false,
                searchForm: {},
                dialogTitle: "编辑",
                dialogFormVisible: false,
                deptVisible: false,

                dialogData: {
                    wxConfig: {}
                },
                rules: {},
                defaultProps: {
                    children: "children",
                    label: "name"
                },
                allUnbindDev: [],
                allRole: [],
                allDept: [],
                oking: false,
                total: 0,
                currentPage: 1,
                user_id: null,
                selectedIds: []
            };
        },
        created() {
            this.getList();
        },
        mounted() {
            this.headers.token = localStorage.getItem("token");
        },
        methods: {
            formatterTime(row, column, cellValue) {
                return Moment(cellValue).format("YYYY-MM-DD hh:mm:ss");
            },
            getList() {
                this.tableLoading = true;
                const params = {limit: 10, page: this.page.currentPage, ...this.searchForm};
                users(params).then(res => {
                    this.tableLoading = false;
                    if (res.code === 0) {
                        this.tableData = res.page.list;
                        this.page.total = res.page.totalCount;
                    }
                });
            },
            getRoles() {
                roleList({limit: 100}).then(res => {
                    this.allRole = res.page.list;
                });
            },
            getAllDept() {
                this.treeLoading = true;
                getAllDept().then(res => {
                    this.treeLoading = false;
                    if (res.code == 0) {
                        this.allDept = util.formatterData(res.deptList, "deptId");
                    }
                });
            },
            func({opera, row}) {
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
            currentChange(val) {
                this.currentPage = val;
            },
            handleCurrentChange(val) {
                this.page.currentPage = val;
                this.getList();
            },
            // 保存编辑或新增
            ok() {
                this.oking = true;
                if (this.dialogTitle === "编辑") {
                    editUser({
                        ...this.dialogData
                    })
                        .then(res => {
                            this.oking = false;
                            if (res.code === 0) {
                                this.getList();
                                this.$message({
                                    message: "修改成功",
                                    type: "success"
                                });
                                this.dialogFormVisible = false;
                            } else {
                                this.$message({
                                    message: res.msg,
                                    type: "error"
                                });
                            }
                        })
                        .catch(() => {
                            this.oking = false;
                        });
                } else if (this.dialogTitle === "新增") {
                    addUser({
                        ...this.dialogData
                    })
                        .then(res => {
                            this.oking = false;
                            if (res.code === 0) {
                                this.getList();
                                this.$message({
                                    message: "新增成功",
                                    type: "success"
                                });
                                this.dialogFormVisible = false;
                            } else {
                                this.$message({
                                    message: res.msg,
                                    type: "error"
                                });
                            }
                        })
                        .catch(() => {
                            this.oking = false;
                        });
                }
            },
            view(row) {
                this.dialogTitle = "详情";
                this.currentPage = 1;
                this.user_id = row.userId;
                this.getRoles();
                this.getAllDept();
                getUser({user_id: row.userId}).then(res => {
                    if (res.code === 0) {
                        this.dialogData = {...res.user};
                        this.dialogData.wxConfig = this.dialogData.wxConfig || {};
                        this.selectedIds = this.dialogData.deviceIdList;
                    }
                });
                this.dialogFormVisible = true;
            },
            edit(row) {
                this.dialogTitle = "编辑";
                this.currentPage = 1;
                this.user_id = row.userId;
                this.getRoles();
                this.getAllDept();
                getUser({user_id: row.userId}).then(res => {
                    if (res.code === 0) {
                        this.dialogData = {...res.user};
                        this.dialogData.wxConfig = this.dialogData.wxConfig || {};
                        this.selectedIds = this.dialogData.deviceIdList;
                    }
                });
                this.dialogFormVisible = true;
            },
            add() {
                this.dialogTitle = "新增";
                this.currentPage = 1;
                this.user_id = null;
                this.selectedIds = [];
                this.getRoles();
                this.getAllDept();
                this.dialogData = {wxConfig: {}, roleIdList: []};
                this.dialogFormVisible = true;
            },
            search(searchForm) {
                this.page.currentPage = 1;
                this.searchForm = searchForm;
                this.getList();
            },
            del(row) {
                console.log('row', row);
                let ids = row.map(item => item.userId)
                this.$confirm("此操作将永久删除该条数据, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(() => {
                        delUser([...ids]).then(res => {
                            if (res.code === 0) {
                                this.getList();
                                this.$message({
                                    message: "删除成功",
                                    type: "success"
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
                            type: "info",
                            message: "已取消删除"
                        });
                    });
            },
            confirmDel(row) {
                this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(() => {
                        this.del(row);
                    })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消删除"
                        });
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
        components: {ZTable, ZSearch}
    };
</script>

<style lang="scss" scoped>
    @import "./index.scss";
</style>
