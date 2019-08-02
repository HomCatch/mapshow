<template>
  <div class="left" :style="`background: ${style.top.backgroundColor}`">
    <div class="logo" :style="`height: ${style.top.height}`">
      小荷净水大数据云管理平台
    </div>
    <div class="nav">导航</div>
    <el-menu
      :default-active="defaultActive"
      class="el-menu-vertical-demo"
      router
      :background-color="style.left.backgroundColor"
      :text-color="style.left.textColor"
      :active-text-color="style.left.activeTextColor"
    >
      <template v-for="router in menus">
        <el-submenu
          :index="`/content/${router.url}`"
          :key="router.url"
          v-if="router.childType === 1"
        >
          <template @click="addColor" slot="title">
            <i :style="`color:${iconColor};`" :class="'iconfont ' + router.icon"></i>
            <span class="title">{{router.name}}</span>
          </template>
          <el-menu-item
            v-for="subRouter in router.list"
            :index="`/content/${router.url}/${subRouter.url}`"
            :key="subRouter.url"
          >
            <i :class="'iconfont ' + subRouter.icon"></i>
            <span class="title" slot="title">{{subRouter.name}}</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item :index="'/content/'+router.url" :key="router.url" v-else>
          <i :class="'iconfont ' + router.icon"></i>
          <span class="title" slot="title">{{router.name}}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
import { style } from "@/config/config";
import logo from "@/assets/logo_da.png";
export default {
  data() {
    return {
      style,
      logo,
      iconColor: "#767c90"
    };
  },
  methods: {
    addColor() {
      this.iconColor = "#4a81d4";
      console.log(this.iconColor);
      this.style.left.backgroundColor = "#f7f8f9";
    }
  },
  created() {
    // 获取nav成功后生成menu并根据route生成funcs
    this.$store.dispatch("getNav");
  },
  computed: {
    defaultActive() {
      return "";
    },
    menus() {
      console.log(this.$store.state.auth.menus);
      const menus = [...this.$store.state.auth.menus];
      return menus;
    }
  }
};
</script>

<style scoped lang="scss">
.left {
  display: flex;
  flex-direction: column;
  flex: 1;
  .logo {
    display: flex;
    flex: 1 0 auto;
    justify-content: center;
    align-items: center;
    color: #fff;
    cursor: pointer;
  }
  .nav {
    background-color: #fff;
    height: 40px;
    line-height: 60px;
    padding-left: 10px;
    font-size: 14px;
    color: #767c90;
  }
  .el-menu-vertical-demo {
    overflow: auto;
    height: 100%;
    .iconfont {
      // color: #767c90;
      margin-right: 10px;
    }
  }
}
</style>
