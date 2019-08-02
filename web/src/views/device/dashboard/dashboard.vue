<template>
  <div class="box">
     <el-button class="add" type="success" icon="el-icon-plus" @click="add" circle></el-button>
    <grid-layout
      :layout.sync="layout"
      :col-num="12"
      :row-height="30"
      :is-draggable="true"
      :is-resizable="true"
      :is-mirrored="false"
      :vertical-compact="true"
      :margin="[10, 10]"
      :use-css-transforms="true"
      @layout-updated="layoutUpdatedEvent"
    >
      <grid-item
        class="grid-item"
        v-for="(item, index) in layout"
        :x="item.x"
        :y="item.y"
        :w="item.w"
        :h="item.h"
        :i="item.i"
        :key="item.i"
      >
        <div class="header-bar">
          <span class="name">{{options[index].name}}</span>
          <div class="set">
            <!-- <el-button type="text" icon="el-icon-edit"></el-button> -->
            <el-button type="text" icon="el-icon-delete" @click="delPanel(index)"></el-button>
          </div>
        </div>
        <!-- 根据type进行不同的渲染 -->
        <div class="content">
          <panel class="panel" :panelInfo="options[index]"></panel>
        </div>
      </grid-item>
    </grid-layout>

    <!-- 添加信息 -->
    <el-dialog title="新增面板" :visible.sync="dialogVisible">
      <el-form :model="addForm" label-width="100px">
        <el-form-item label="面板名称：">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="宽：">
          <el-input-number
            v-model="addForm.w"
            autocomplete="off"
            :min="2"
            :max="12"
            :precision="0"
            :step="1"
            controls-position="right"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="高：">
          <el-input-number
            v-model="addForm.h"
            autocomplete="off"
            :min="2"
            :max="12"
            :precision="0"
            :step="1"
            controls-position="right"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="面板类型：">
          <el-select v-model="addForm.typeId" placeholder="请选择面板类型">
            <el-option v-for="type in typeList" :label="type.type" :value="type.id" :key="type.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addPanel">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import VueGridLayout from "vue-grid-layout";
import { layout } from "./data";
import panel from "./panel/index";
import {
  dashboardLayout,
  commitDashboardLayout,
  getTypeList,
  addPanel,
  delPanel
} from "./api";
export default {
  data() {
    return {
      layout,
      options: null,
      dialogVisible: false,
      typeList: [],
      addForm: { w: null, h: null, typeId: null }
    };
  },
  created() {
    this.dashboardLayout();
    // this.getTypeList();
  },
  methods: {
    layoutUpdatedEvent(newLayout) {
      this.commitDashboardLayout();
    },
    dashboardLayout() {
      // 获取配置信息
      dashboardLayout().then(res => {
        console.log('res',res)
        if (res.ret === 0) {
          this.options = res.datas.list;
          this.layout = res.datas.list.map(item => {
            return { i: item.id, ...JSON.parse(item.position) };
          });
        }
      });
    },
    commitDashboardLayout() {
      // 首先将layout配置合并到options中
      this.options.forEach(item => {
        this.layout.forEach(layoutItem => {
          if (item.id == layoutItem.i) {
            item.position = JSON.stringify(layoutItem);
          }
        });
      });
      // 提交配置信息
      commitDashboardLayout(this.options).then(res => {});
    },
    async add() {
      if (this.typeList.length === 0) {
        await this.getTypeList();
      }
      this.addForm = {};
      this.dialogVisible = true;
      // this.options.push({})
    },
    addPanel() {
      this.addForm = { ...this.addForm, x: 0, y: 10 };
      addPanel(this.addForm).then(res => {
        if (res.ret === 0) {
          this.$message({
            message: "添加成功",
            type: "success"
          });
          this.dialogVisible = false;
          this.dashboardLayout();
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
      });
    },
    delPanel(index) {
      this.$confirm("确认删除该面板?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delPanel({ id: this.options[index].id }).then(res => {
            if (res.ret === 0) {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.dashboardLayout();
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
    async getTypeList() {
      await getTypeList().then(res => {
        this.typeList = res.datas;
      });
    }
  },
  components: {
    GridLayout: VueGridLayout.GridLayout,
    GridItem: VueGridLayout.GridItem,
    panel
  }
};
</script>

<style scoped lang="scss">
.box {
  position: relative;
  .add {
    position: fixed;
    bottom: 10px;
    right: 10px;
    z-index: 10;
    padding: 18px;
  }
}
.grid-item {
  border: 1px solid #dcdfe6;
  display: flex;
  flex-direction: column;
  border-radius: 4px;
  border: 1px solid #ebeef5;
  background-color: #fff;
  overflow: hidden;
  color: #303133;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  .header-bar {
    height: 20px;
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #e4e7ed;
    padding: 10px;
    .set {
      margin-right: 10px;
    }
  }
  .content {
    flex: 1 1 auto;
    .panel {
      height: 100%;
    }
  }
}
</style>
