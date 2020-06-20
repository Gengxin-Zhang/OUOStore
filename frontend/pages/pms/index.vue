<template>
  <div class="productPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>商品</a-breadcrumb-item>
    </a-breadcrumb>
    <!-- <card title="查询">
      <div slot="body">
        <a-row :gutter="16">
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6"></a-col>
        </a-row>
      </div>
    </card>-->

    <card title="商品列表" icon="unordered-list">
      <div class slot="body">
        <div class="actions">
          <nuxt-link to="/pms/add">
            <a-button type="primary">添加商品</a-button>
          </nuxt-link>
        </div>
        <div class></div>
      </div>
    </card>
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
      title: "商品 - OUOStore"
    };
  },
  data() {
    return {};
  }
};
</script>


<style lang="scss" scoped>
.productPage {
  width: 100%;
  height: 100%;
  padding: 1rem 2rem;
  .actions {
    position: absolute;
    width: 100%;
    text-align: right;
    top: 0.5rem;
    right: 0.5rem;
  }
}
</style>