<template>
  <div class="systemPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>系统设置</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="系统设置"></card>
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
      title: "系统设置 - OUOStore"
    };
  }
};
</script>

<style lang="scss" scoped>
.systemPage {
  width: 100%;
  padding: 1rem 2rem;
}
</style>