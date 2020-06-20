<template>
  <div class="orderPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>订单管理</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="管理" icon="setting">
        <div slot="body">
            <nuxt-link to="/oms/order_setting"><a-button>订单设置</a-button></nuxt-link> 
            <nuxt-link to="/oms/return_setting"><a-button>退换货设置</a-button></nuxt-link>
        </div>
    </card>
    <card title="订单列表" icon="ordered-list">
      
    </card>
  </div>
</template>
<script>
import card from '~/components/card'
export default {
    components:{
        card
    },
    head(){
      return{
        title: "订单 - OUOStore"
      }
    }
};
</script>


<style lang="scss" scoped>
.orderPage {
    width: 100%;
    padding: 1rem 2rem;
}
</style>