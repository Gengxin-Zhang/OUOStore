<template>
  <div class="orderSettingPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/oms">订单管理</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>订单设置</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="订单设置"></card>
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
      title: "订单设置 - OUOStore"
    };
  }
};
</script>

<style lang="scss" scoped>
.orderSettingPage {
  width: 100%;
  padding: 1rem 2rem;
}
</style>