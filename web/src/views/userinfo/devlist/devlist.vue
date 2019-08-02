<template>
  <div class="box">
    <z-table
      :tableData="tableData"
      :tableColumns="tableColumns"
      :page="page"
      @handleCurrentChange="handleCurrentChange"
      v-loading="tableLoading"
      :funcs="[]"
      :needAdd="false"
    ></z-table>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import { tableColumns } from "./data";
import { getList } from "./api";
export default {
  props:{
    userinfo: {
      type: Object,
      default: function(){
        return {}
      }
    }
  },
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      tableColumns,
      tableLoading: false
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
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    },
  },
  computed: {
    searchForm(){
      return {
        userId: this.userinfo.id
      }
    }
  },
  components: {ZTable}
};
</script>

<style>
</style>
