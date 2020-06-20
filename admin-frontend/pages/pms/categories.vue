<template>
  <div class="categoriesPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>首页</a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/pms">商品</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>商品分类</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="商品分类列表" icon="ordered-list">
      <div slot="body">
        <div class="actions">
          <nuxt-link to="/pms/addCategory">
            <a-button type="primary">添加分类</a-button>
          </nuxt-link>
        </div>
        <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
        >
          <template slot="icon" slot-scope="icon">
            <img :src="icon" style="width: 50px; height: 50px; border-radius: 100%;" alt />
          </template>
          <template slot="showStatus" slot-scope="category">
            <a-switch
              :checked="category.showStatus!==0"
              @change="handleShowStatusChange(category)"
            />
          </template>
          <template slot="actions" slot-scope="category">
            <nuxt-link :to="`/pms/updateCategory?id=${category.id}`">
              <a-button>修改</a-button>
            </nuxt-link>
            <a-button type="danger" @click="handleDelete(category.name, category.id)">删除</a-button>
          </template>
        </a-table>
      </div>
    </card>
  </div>
</template>

<script>
import card from "~/components/card";
import { mapActions } from "Vuex";
const columns = [
  {
    title: "Id",
    dataIndex: "id",
    width: "10%"
  },
  {
    title: "分类名",
    dataIndex: "name",
    width: "15%"
  },
  {
    title: "图标",
    dataIndex: "icon",
    width: "20%",
    scopedSlots: { customRender: "icon" }
  },
  {
    title: "产品数量",
    dataIndex: "productCount"
  },
  {
    title: "是否展示",
    key: "showStatus",
    scopedSlots: { customRender: "showStatus" }
  },
  {
    title: "操作",
    key: "actions",
    scopedSlots: { customRender: "actions" }
  }
];
export default {
  components: {
    card
  },
  data() {
    return {
      data: [],
      pagination: {},
      loading: false,
      columns
    };
  },
  methods: {
    ...mapActions({
      getCategories: "Product/getCategories",
      updateCategory: "Product/updateCategory",
      deleteCategory: "Product/deleteCategory"
    }),
    handleTableChange(pagination, filters, sorter) {
      console.log(pagination, filters, sorter);
      this.fetch({
        page: pagination.current,
        per_page: pagination.pageSize
      });
    },
    handleShowStatusChange(category) {
      console.log(category);
      if (category.showStatus === 0) {
        category.showStatus = 1;
      } else {
        category.showStatus = 0;
      }
      this.updateCategory(category)
        .then(res => {})
        .catch(err => {
          if (category.showStatus === 0) {
            category.showStatus = 1;
          } else {
            category.showStatus = 0;
          }
        });
    },
    handleDelete(name, id) {
      let that = this;
      this.$confirm({
        title: `确定删除分类「${name}」？`,
        content: h => <div style="color:red;">数据删除后不可恢复</div>,
        onOk() {
          that.deleteCategory(id)
            .then(res => {
              that.$message.success(res);
              that.fetch();
            })
            .catch(err => {
              that.$message.error(err);
            });
        },
        onCancel() {
        },
        class: "test"
      });
    },
    fetch() {
      this.loading = true;
      this.getCategories().then(res => {
        const pagination = { ...this.pagination };
        pagination.current = res.current;
        pagination.total = res.total;
        pagination.pageSize = res.size;
        this.data = res.records;
        this.loading = false;
        this.pagination = pagination;
      });
    }
  },
  mounted() {
    this.fetch();
  }
};
</script>

<style lang="scss" scoped>
.categoriesPage {
  width: 100%;
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