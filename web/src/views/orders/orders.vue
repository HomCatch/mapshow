<template>
  <div class="water_status top-border">
    <!-- 搜索 -->
    <z-search :searchItems="searchItems" @search="search"></z-search>
    <!-- 功能+table -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
    <!-- 新增/编辑dialog -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="dialogData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--不生成创建时间字段-->
        <el-form-item label="订单号:" prop="orderNo">
          <el-input v-model="dialogData.orderNo"></el-input>
        </el-form-item>
        <el-form-item label="第三方系统交易号:" prop="tradeNo">
          <el-input v-model="dialogData.tradeNo"></el-input>
        </el-form-item>
        <el-form-item label="设备mac地址:" prop="devMac">
          <el-input v-model="dialogData.devMac"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="devId">
          <el-input v-model="dialogData.devId"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker v-model="dialogData.startTime" type="datetime" placeholder="选择时间" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker v-model="dialogData.endTime" type="datetime" placeholder="选择时间" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="水通道号 1：开水，2：热水，3,：常温水，4：冷水):" prop="waterTemp">
          <el-input v-model="dialogData.waterTemp"></el-input>
        </el-form-item>
        <el-form-item label="水量:" prop="waterAmount">
          <el-input v-model="dialogData.waterAmount"></el-input>
        </el-form-item>
        <el-form-item label="微信openId:" prop="openId">
          <el-input v-model="dialogData.openId"></el-input>
        </el-form-item>
        <el-form-item label="微信支付回调时间:" prop="payNotifyTime">
          <el-input v-model="dialogData.payNotifyTime"></el-input>
        </el-form-item>
        <el-form-item label="硬件出水完成，上报时间:" prop="hardwareNotifyTime">
          <el-input v-model="dialogData.hardwareNotifyTime"></el-input>
        </el-form-item>
        <el-form-item label="支付状态 1. 待支付 2.已付款 3.已退款:" prop="payStatus">
          <el-input v-model="dialogData.payStatus"></el-input>
        </el-form-item>
        <el-form-item label="0.订单已取消 2.已下发出水指令，等待应答 (待出水) 1.已应答，售卖成功 （已出水）3.售水失败:" prop="sellStatus">
          <el-input v-model="dialogData.sellStatus"></el-input>
        </el-form-item>
        <el-form-item label="商户退款单号:" prop="refundNo">
          <el-input v-model="dialogData.refundNo"></el-input>
        </el-form-item>
        <el-form-item label="优惠金额:" prop="couponPrice">
          <el-input v-model="dialogData.couponPrice"></el-input>
        </el-form-item>
        <el-form-item label="微信支付金额:" prop="moneyAmount">
          <el-input v-model="dialogData.moneyAmount"></el-input>
        </el-form-item>
        <el-form-item label="微信退款单号:" prop="refundId">
          <el-input v-model="dialogData.refundId"></el-input>
        </el-form-item>
        <el-form-item label="售水类型:" prop="sellType">
          <el-input v-model="dialogData.sellType"></el-input>
        </el-form-item>
        <el-form-item label="脉冲通道:" prop="passageNo">
          <el-input v-model="dialogData.passageNo"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="couponUsageId">
          <el-input v-model="dialogData.couponUsageId"></el-input>
        </el-form-item>
        <el-form-item label="脉冲数:" prop="pulse">
          <el-input v-model="dialogData.pulse"></el-input>
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
import { getList, del, add, edit } from "./api.js";
import { tableColumns, searchItems } from "./data";
import * as util from "@/common/js/util";

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
