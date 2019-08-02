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
        <el-form-item label="订单编号:" prop="orderNumber">
          <el-input v-model="dialogData.orderNumber"></el-input>
        </el-form-item>
        <el-form-item label="设备编号:" prop="deviceNumber">
          <el-input v-model="dialogData.deviceNumber"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称:" prop="userName">
          <el-input v-model="dialogData.userName"></el-input>
        </el-form-item>
        <el-form-item label="注册号码:" prop="phoneNumber">
          <el-input v-model="dialogData.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名:" prop="realName">
          <el-input v-model="dialogData.realName"></el-input>
        </el-form-item>
        <el-form-item label="套餐类型:" prop="type">
           <el-select v-model="dialogData.type">
            <el-option v-for="item in options1" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单价:" prop="price">
          <el-input v-model="dialogData.price"></el-input>
        </el-form-item>
        <el-form-item label="共计水量:" prop="totalAmount">
          <el-input v-model="dialogData.totalAmount"></el-input>
        </el-form-item>
        <el-form-item label="扣减水量:" prop="minusAmount">
          <el-input v-model="dialogData.minusAmount"></el-input>
        </el-form-item>
        <el-form-item label="账单金额:" prop="billPrice">
          <el-input v-model="dialogData.billPrice"></el-input>
        </el-form-item>
        <el-form-item label="支付方式:" prop="payType">
                <el-select v-model="dialogData.payType">
            <el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付金额:" prop="payPrice">
          <el-input v-model="dialogData.payPrice"></el-input>
        </el-form-item>
        <el-form-item label="第三方流水号:" prop="thirdNumber">
          <el-input v-model="dialogData.thirdNumber"></el-input>
        </el-form-item>
        <el-form-item label="账单时间:" prop="billDate">
          <el-input v-model="dialogData.billDate"></el-input>
        </el-form-item>
        <el-form-item label="支付时间:" prop="payDate">
          <el-input v-model="dialogData.payDate"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="dialogData.startTime"
            type="datetime"
            placeholder="选择时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            v-model="dialogData.endTime"
            type="datetime"
            placeholder="选择时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
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
import { tableColumns, searchItems,options1,options2 } from "./data";
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
      options1,
      options2,
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
        downloadFile(res, "用户账单");
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
