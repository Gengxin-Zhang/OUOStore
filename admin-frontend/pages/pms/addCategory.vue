<template>
  <div class="addCategoryPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>首页</a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/pms">商品</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/pms/categories">商品分类</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>新建分类</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="新建分类">
      <div slot="body">
        <a-form
          :form="form"
          :label-col="{ span: 5 }"
          :wrapper-col="{ span: 12 }"
          @submit="handleSubmit"
        >
          <a-form-item label="分类名">
            <a-input v-decorator="['name', { rules: [{ required: true, message: '请输入分类名' }] }]" />
          </a-form-item>
          <a-form-item label="展示状态">
            <a-select
              v-decorator="[
          'showStatus',
          { rules: [{ required: true, message: '请选择展示状态' }] },
        ]"
              placeholder="展示状态"
            >
              <a-select-option value="1">展示</a-select-option>
              <a-select-option value="0">不展示</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
            <a-button type="primary" html-type="submit" @click>新建</a-button>
          </a-form-item>
        </a-form>
      </div>
    </card>
  </div>
</template>

<script>
import card from "~/components/card";
import { mapActions } from "Vuex";
export default {
  components: {
    card
  },
  data() {
    return {
      form: this.$form.createForm(this, { name: "coordinated" })
    };
  },
  methods: {
    ...mapActions({ addCategory: "Product/addCategory" }),
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          this.addCategory(values)
            .then(res => {
              this.$message.success(res)
              this.$router.push("/pms/categories")
            })
            .catch(err => {
              this.$message.error(err);
            });
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.addCategoryPage {
  width: 100%;
  padding: 1rem 2rem;
}
</style>