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
        <el-form-item label="电话号码:" prop="phoneNumber">
          <el-input v-model="dialogData.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="密码:" prop="password">
          <el-input v-model="dialogData.password"></el-input>
        </el-form-item>
        <el-form-item label="令牌:" prop="token">
          <el-input v-model="dialogData.token"></el-input>
        </el-form-item>
        <el-form-item label="注册时间:" prop="registeredTime">
          <el-input v-model="dialogData.registeredTime"></el-input>
        </el-form-item>
        <el-form-item label="修改时间:" prop="modifiedTime">
          <el-input v-model="dialogData.modifiedTime"></el-input>
        </el-form-item>
        <el-form-item label="状态:" prop="status">
          <el-input v-model="dialogData.status"></el-input>
        </el-form-item>
        <el-form-item label="上次登录时间:" prop="lastLoginTime">
          <el-input v-model="dialogData.lastLoginTime"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="lastLoginOs">
          <el-input v-model="dialogData.lastLoginOs"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="lastLoginSdkVersion">
          <el-input v-model="dialogData.lastLoginSdkVersion"></el-input>
        </el-form-item>
        <el-form-item label="个人头像:" prop="imgUrl">
          <el-input v-model="dialogData.imgUrl"></el-input>
        </el-form-item>
        <el-form-item label="个人昵称:" prop="nickName">
          <el-input v-model="dialogData.nickName"></el-input>
        </el-form-item>
        <el-form-item label="小区ID:" prop="communityId">
          <el-input v-model="dialogData.communityId"></el-input>
        </el-form-item>
        <el-form-item label="用户积分数:" prop="bonusPoint">
          <el-input v-model="dialogData.bonusPoint"></el-input>
        </el-form-item>
        <el-form-item label="是否认证:" prop="isAuthorized">
          <el-input v-model="dialogData.isAuthorized"></el-input>
        </el-form-item>
        <el-form-item label="折扣数量:" prop="discount">
          <el-input v-model="dialogData.discount"></el-input>
        </el-form-item>
        <el-form-item label="用户拥有水数量:" prop="waterBottles">
          <el-input v-model="dialogData.waterBottles"></el-input>
        </el-form-item>
        <el-form-item label="月费抵扣券:" prop="couponCount">
          <el-input v-model="dialogData.couponCount"></el-input>
        </el-form-item>
        <el-form-item label="累计使用抵扣券:" prop="totalCoupon">
          <el-input v-model="dialogData.totalCoupon"></el-input>
        </el-form-item>
        <el-form-item label=":" prop="agentNo">
          <el-input v-model="dialogData.agentNo"></el-input>
        </el-form-item>
        <el-form-item label="注册时经纬度:" prop="amapId">
          <el-input v-model="dialogData.amapId"></el-input>
        </el-form-item>
        <el-form-item label="引导状态,0:未引导，1已引导:" prop="leadStatus">
          <el-input v-model="dialogData.leadStatus"></el-input>
        </el-form-item>
        <el-form-item label="介绍人:" prop="sponsor">
          <el-input v-model="dialogData.sponsor"></el-input>
        </el-form-item>
        <el-form-item label="第三方昵称:" prop="thirdNickname">
          <el-input v-model="dialogData.thirdNickname"></el-input>
        </el-form-item>
        <el-form-item label="第三方头像:" prop="thirdPortrait">
          <el-input v-model="dialogData.thirdPortrait"></el-input>
        </el-form-item>
        <el-form-item label="第三方来源（1：qq,2:weixin,3:weibo）:" prop="thirdSource">
          <el-input v-model="dialogData.thirdSource"></el-input>
        </el-form-item>
        <el-form-item label="第三方用户id:" prop="thirdUserId">
          <el-input v-model="dialogData.thirdUserId"></el-input>
        </el-form-item>
        <el-form-item label="性别:" prop="gender">
          <el-input v-model="dialogData.gender"></el-input>
        </el-form-item>
        <el-form-item label="出生地:" prop="birthland">
          <el-input v-model="dialogData.birthland"></el-input>
        </el-form-item>
        <el-form-item label="居住地:" prop="livePlace">
          <el-input v-model="dialogData.livePlace"></el-input>
        </el-form-item>
        <el-form-item label="博主简介（用户简介）:" prop="profile">
          <el-input v-model="dialogData.profile"></el-input>
        </el-form-item>
        <el-form-item label="是否是博主用户:" prop="isBlogger">
          <el-input v-model="dialogData.isBlogger"></el-input>
        </el-form-item>
        <el-form-item label="第三方qq昵称:" prop="thirdQqNickname">
          <el-input v-model="dialogData.thirdQqNickname"></el-input>
        </el-form-item>
        <el-form-item label="第三方qq头像:" prop="thirdQqPortrait">
          <el-input v-model="dialogData.thirdQqPortrait"></el-input>
        </el-form-item>
        <el-form-item label="第三方qq用户id:" prop="thirdQqUserId">
          <el-input v-model="dialogData.thirdQqUserId"></el-input>
        </el-form-item>
        <el-form-item label="第三方weixin昵称:" prop="thirdWeixinNickname">
          <el-input v-model="dialogData.thirdWeixinNickname"></el-input>
        </el-form-item>
        <el-form-item label="第三方weixin头像:" prop="thirdWeixinPortrait">
          <el-input v-model="dialogData.thirdWeixinPortrait"></el-input>
        </el-form-item>
        <el-form-item label="第三方weixin用户id:" prop="thirdWeixinUserId">
          <el-input v-model="dialogData.thirdWeixinUserId"></el-input>
        </el-form-item>
        <el-form-item label="第三方weibo昵称:" prop="thirdWeiboNickname">
          <el-input v-model="dialogData.thirdWeiboNickname"></el-input>
        </el-form-item>
        <el-form-item label="第三方weibo头像:" prop="thirdWeiboPortrait">
          <el-input v-model="dialogData.thirdWeiboPortrait"></el-input>
        </el-form-item>
        <el-form-item label="第三方weibo用户id:" prop="thirdWeiboUserId">
          <el-input v-model="dialogData.thirdWeiboUserId"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')" :loading="btnLoading">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 设备列表 -->
    <el-dialog title="设备列表" :visible.sync="dialog">
      <devlist :userinfo="userinfo" ref="devlist"></devlist>
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
import devlist from "./devlist/devlist";

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
      dialog: false,
      userinfo: {}
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
        // console.log("res", res);
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
        case "查看设备":
          this.view(row);
          break;
      }
    },
    async view(row) {
      console.log(row);
      this.dialog = true;
      this.userinfo = { ...row };
      await this.$nextTick();
      console.log(this.$refs);
      this.$refs.devlist.getList();
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
        downloadFile(res, "用户管理");
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
  components: { ZTable, ZSearch, devlist }
};
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
