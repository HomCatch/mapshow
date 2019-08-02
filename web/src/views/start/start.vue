<template>
  <div id="start">
    <img class="bg" src="@/common/img/bg.jpg" alt>
    <div class="container">
      <h3>小荷物联网大数据云平台</h3>
      <ul>
        <li @click="login">
          <img src="@/common/img/im-1.png" alt>
          <p>净水产品智能化解决方案</p>
        </li>
        <li>
          <img src="@/common/img/im-2.png" alt>
          <p>空净产品智能化解决方案</p>
        </li>
        <li>
          <img src="@/common/img/im-3.png" alt>
          <p>智能尿检马桶解决方案</p>
        </li>
      </ul>
    </div>
    <div class="copyright">
      <ul>
        <li><a href="javescript:void(0)">关于小荷</a></li>
        <li>|</li>
        <li><a href="javescript:void(0)">加入小荷</a></li>
        <li>|</li>
        <li><a href="javescript:void(0)">联系我们</a></li>
        <li>|</li>
        <li><a href="javescript:void(0)">意见反馈</a></li>
      </ul>
      <p>Copyright © 2019 智水小荷 All rights reserved 粤ICP备 160818号</p>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    login(){
      this.$router.push({path:"/content/waterMap"})
    }
  },
};
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
