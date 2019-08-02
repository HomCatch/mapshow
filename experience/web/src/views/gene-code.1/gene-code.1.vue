<template>
  <div class="box">
    <!-- 搜索 -->
    <z-search :searchItems="searchItems" @search="search"></z-search>
    <!-- 功能+table -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
    <!-- 弹窗设置生成格式 -->
    <el-dialog :title="dialog1.title" :visible.sync="dialog1.visible" width="80%">
      <el-form :model="dialog1Data">
        <el-form-item label="table：" prop="sort">
          {{dialog1Data.table}}
        </el-form-item>
        <el-form-item label="搜索条">

        </el-form-item>
        <el-form-item label="排序方式：" prop="sort">
          <el-select v-model="dialog1Data.sort">
            <el-option label="a" value="a"></el-option>
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="24" v-for="data in dialog1Data.data" :key="data.label">
            <el-col :span="2">
              <el-form-item :label="`${data.label}：`"></el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="查询条件：">
                <el-select v-model="data.operators">
                  <el-option label="like" value="like"></el-option>
                  <el-option label="=" value="="></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="是否排序：">
                <el-switch v-model="data.sortable">
                </el-switch>
              </el-form-item>
            </el-col>
          </el-col>
        </el-row>
        <!-- <el-row>
          <el-col :span="24" v-for="item in tableField" :key="item">
            <el-form-item :label="item+'：'">
            </el-form-item>
            <el-form-item label="查询条件" >
              <el-select v-model="operators"></el-select>
            </el-form-item>
            <el-form-item label="排序方式">
              <el-select></el-select>
            </el-form-item>
          </el-col>
        </el-row> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog1.visible = false">取 消</el-button>
        <el-button type="primary" @click="submitDialog1">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import { getList, geneCode, getTableField, geneMybatisCode } from "./api.js";
import { tableColumns, searchItems, funcs, PostParams } from "./data";
export default {
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      funcs,
      tableColumns,
      searchItems,
      tableLoading: false,
      searchForm: {},
      dialog1: {
        title: "配置代码生成",
        visible: false
      },
      dialog1Data: {},
      tableField: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.tableLoading = true;
      const params = {
        page: this.page.currentPage,
        pageSize: 10,
        ...this.searchForm
      };
      const newParams = {
        page: this.page.currentPage,
        pageSize: 10,
        params: { ...this.searchForm },
        operators: {} // 查询方式
      };
      getList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.datas.list;
        this.page.total = res.datas.itemCounts;
      });
    },
    search(searchForm) {
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
        case "生成JPA模板":
          this.geneCode(row, 0);
          break;
        case "生成Mybatis模板":
          this.geneMybatisCode(row, 1);
      }
    },
    geneCode(row, model) {
      console.log(model);
      geneCode({ tableName: row.table, version: "1.5.9", model: model }).then(
        res => {
          if (res.data.ret === 0) {
            this.$message({
              message: "生成成功",
              type: "success"
            });
          }
        }
      );
    },
    // 获取表
    async geneMybatisCode(row, model) {
      console.log(row);
      await getTableField({...row}).then(res => {
        this.tableField = res.datas;
        this.dialog1Data = new PostParams(this.tableField, row.table);
        console.log(this.dialog1Data);
        this.dialog1.visible = true;
      })
      // 在ajax回调函数里设置
    },
    // 提交搜索配置
    submitDialog1() {
      geneMybatisCode(this.dialog1Data).then(res => {
        this.$message({
          message: "生成成功",
          type: "success"
        });
        this.dialog1.visible = false;
      });
    },
    edit(row) {
      console.log(row);
    },
    add(row) {
      console.log(row);
    },
    del(row) {
      console.log(row);
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    }
  },
  components: { ZTable, ZSearch }
};
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
