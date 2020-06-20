<template>
  <div class="returnSettingPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/oms">订单管理</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>退换货设置</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="退换货设置"></card>
  </div>
</template>

<script>
import card from "~/components/card";
export default {
  components: {
    card
  },
  head() {
    return {
      title: "退换货设置 - OUOStore"
    };
  }
};
</script>

<style lang="scss" scoped>
.returnSettingPage {
  width: 100%;
  padding: 1rem 2rem;
}
</style>