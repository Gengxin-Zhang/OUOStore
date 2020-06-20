<template>
  <div class="newProductPage">
    <a-breadcrumb style="margin-bottom: .7rem">
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
          <a-form
            :form="form"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 12 }"
            @submit="handleSubmit"
          >
            <div :class="['form', currentStep === 0?'show': 'hidden']">
              <a-form-item label="商品名">
                <a-input
                  v-decorator="['name', { rules: [{ required: true, message: '请输入商品名'}]  }]"
                />
              </a-form-item>
              <a-form-item label="商品描述">
                <a-textarea
                  v-decorator="['description', { rules: [{ required: true, message: '请输入商品描述'}]  }]"
                />
              </a-form-item>
              <a-form-item label="关键词">
                <a-input
                  v-decorator="['keywords', { rules: [{ required: true, message: '请输入关键词'}]  }]"
                />
              </a-form-item>
              <a-form-item label="价格">
                <a-input
                  v-decorator="['price', { rules: [{ required: true, message: '请输入价格'}]  }]"
                />
              </a-form-item>
            </div>
            <div :class="['form', currentStep === 1?'show': 'hidden']">
              
            </div>
            <div :class="['form', currentStep === 2?'show': 'hidden']"></div>
            <div :class="['form', currentStep === 3?'show': 'hidden']"></div>
          </a-form>
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
import { mapState, mapGetters, mapActions, mapMutations } from "vuex";

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
      currentStep: 0,
      form: this.$form.createForm(this, { name: "", showStatus: 0 })
    };
  },
  methods: {
    ...mapActions({ addProduct: "Product/add" }),
    hendlePreButtonClick() {
      if (this.currentStep === 0) {
        return;
      } else {
        this.currentStep -= 1;
      }
    },
    handleNextButtonClick() {
      if (this.currentStep === 3) {
        this.addProduct();
      } else {
        this.currentStep += 1;
      }
    },
    handleSubmit() {}
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
      &.hidden {
        display: none;
      }
    }
    .action {
    }
  }
}
</style>