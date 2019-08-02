<template>
  <div class="main" :style="`background: ${style.main.backgroundColor};height:100%`">
    <router-view></router-view>
  </div>
</template>

<script>
import { style } from "@/config/config";
export default {
  data() {
    return {
      style
    };
  }
};
</script>

<style>
</style>
