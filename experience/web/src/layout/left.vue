<template>
  <div class="left" :style="`background: ${style.top.backgroundColor}`">
    <div class="logo" :style="`height: ${style.top.height}`">
      <img :src="logo" height="50" alt="">
    </div>
    <el-menu :default-active="defaultActive" class="el-menu-vertical-demo" router :background-color="style.left.backgroundColor" :text-color="style.left.textColor" :active-text-color="style.left.activeTextColor">
      <template v-for="router in menus">
        <el-submenu :index="`/content/${router.url}`" :key="router.url" v-if="router.childType === 1">
          <template slot="title"><i :class="'iconfont ' + router.icon"></i><span class="title">{{router.name}}</span></template>
          <el-menu-item v-for="subRouter in router.list" :index="`/content/${router.url}/${subRouter.url}`" :key="subRouter.url">
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
import logo from "@/assets/logo.png";
export default {
  data() {
    return {
      style,
      logo
    };
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
    img {
      cursor: pointer;
    }
  }
  .el-menu-vertical-demo {
    overflow: auto;
    height: 100%;
  }
}
</style>
