<template>
  <div class="attrPage" style="margin-bottom: .7rem">
    <a-breadcrumb>
      <a-breadcrumb-item>首页</a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/pms">商品</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>商品属性</a-breadcrumb-item>
    </a-breadcrumb>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions, mapMutations } from "vuex";

export default {
  data() {
    return {};
  },
  methods: {
    ...mapActions({ getAttrs: "Product/getAttrs" })
  }
};
</script>

<style lang="scss" scoped>
.attrPage {
  width: 100%;
  padding: 1rem 2rem;
}
</style>
