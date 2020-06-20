<template>
  <div class="newProductPage">
    <a-breadcrumb>
      <a-breadcrumb-item>首页</a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/pms">商品</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>添加商品</a-breadcrumb-item>
    </a-breadcrumb>
    <div class="addPreview">
      <card>
        <div slot="body">
          <a-steps :current="currentStep" size="small">
            <a-step title="添加商品信息" />
            <a-step title="添加商品促销" />
            <a-step title="添加商品属性" />
            <a-step title="添加商品关联" />
          </a-steps>
          <div class="form" v-if="currentStep===0"></div>
          <div class="form" v-if="currentStep===1"></div>
          <div class="form" v-if="currentStep===2"></div>
          <div class="form" v-if="currentStep===3"></div>
          <div class="action">
            <a-button-group>
              <a-button :disabled="currentStep===0" @click="hendlePreButtonClick">
                <a-icon type="left" />上一页
              </a-button>
              <a-button type="primary" @click="handleNextButtonClick">
                {{currentStep === 3? '添加商品':'下一页'}}
                <a-icon type="right" v-if="currentStep !== 3" />
              </a-button>
            </a-button-group>
          </div>
        </div>
      </card>
    </div>
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
      title: "添加新商品 - OUOStore"
    };
  },
  data() {
    return {
      currentStep: 0
    };
  },
  methods: {
    hendlePreButtonClick() {
      if (this.currentStep === 0) {
        return;
      } else {
        this.currentStep -= 1;
      }
    },
    handleNextButtonClick() {
      if (this.currentStep === 3) {
        addProduct();
      } else {
        this.currentStep += 1;
      }
    },
    addProduct() {}
  }
};
</script>

<style lang="scss" scoped>
.newProductPage {
  width: 100%;
  padding: 1rem 2rem;
  .addPreview {
    width: 1000px;
    max-width: 100%;
    margin: 1.5rem auto;
    .form {
      padding: 1rem 2rem;
    }
    .action {
    }
  }
}
</style>