<template>
  <div class="UMSPage">
    <a-breadcrumb>
      <a-breadcrumb-item><nuxt-link to="/">首页</nuxt-link></a-breadcrumb-item>
      <a-breadcrumb-item>权限</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="后台用户列表" icon="unordered-list"></card>
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
      title: "权限 - OUOStore"
    };
  },
  data() {
    return {};
  }
};
</script>


<style lang="scss" scoped>
.UMSPage {
  width: 100%;
  padding: 1rem 2rem;
}
</style>