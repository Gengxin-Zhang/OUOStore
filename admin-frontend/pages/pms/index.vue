<template>
  <div class="productPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>商品</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="属性列表" icon="bulb">
      <div slot="body">
        <nuxt-link to="/pms/attr">
          <a-button>商品属性</a-button>
        </nuxt-link>
        <nuxt-link to="/pms/categories">
          <a-button>商品分类</a-button>
        </nuxt-link>
        <nuxt-link to="/pms/attr">
          <a-button>商品类型</a-button>
        </nuxt-link>
      </div>
    </card>

    <card title="商品列表" icon="unordered-list">
      <div class slot="body">
        <div class="actions">
          <nuxt-link to="/pms/add">
            <a-button type="primary">添加商品</a-button>
          </nuxt-link>
        </div>
        <div class>
          <a-table
            :columns="columns"
            :row-key="record => record.id"
            :data-source="data"
            :pagination="pagination"
            :loading="loading"
            @change="handleTableChange"
          >
            <template slot="pic" slot-scope="pic">
              <img :src="pic" style="width: 50px; height: 50px; border-radius: 100%;" alt />
            </template>
            <template slot="description" slot-scope="product">
              {{product.name}} - {{product.description}}
              <br />
              [{{product.keywords}}]
            </template>
            <template slot="price" slot-scope="product">
              价格：{{product.price}}
              <br />
              货号：{{product.productSn}}
            </template>
            <template slot="status" slot-scope="product">
              <a-switch :checked="product.publishStatus !== 0"></a-switch>
            </template>
            <template slot="actions" slot-scope="product">
              <a-button size="small" @click="handleShowButton(product)">查看</a-button>
              <nuxt-link :to="`/pms/updateProduct?id=${product.id}`">
                <a-button size="small">修改</a-button>
              </nuxt-link>
              <a-button type="danger" @click="handleDelete(product.id)" size="small">删除</a-button>
            </template>
          </a-table>
        </div>
      </div>
    </card>
    <a-modal
      v-model="showDetail"
      :title="`[${currentShowProduct.name}]商品详情`"
      v-if="showDetail&&currentShowProduct"
    >
      <a-row :gutter="16" class="ProductDetail">
        <a-col :span="10" class="key">商品名:</a-col>
        <a-col :span="14">{{currentShowProduct.name}}</a-col>
        <a-col :span="10" class="key">商品描述:</a-col>
        <a-col :span="14">{{currentShowProduct.description}}</a-col>
        <a-col :span="10" class="key">关键词:</a-col>
        <a-col :span="14">{{currentShowProduct.keywords}}</a-col>
        <a-col :span="10" class="key">价格:</a-col>
        <a-col :span="14">{{currentShowProduct.price}}</a-col>
        <a-col :span="10" class="key">分类:</a-col>
        <a-col :span="14">[{{currentShowProduct.productCategoryId}}]{{currentShowProduct.productCategoryName}}</a-col>
        <a-col :span="10" class="key">上线状态:</a-col>
        <a-col :span="14">{{currentShowProduct.publishStatus===0?'未上线':'已上线'}}</a-col>
      </a-row>
    </a-modal>
  </div>
</template>

<script>
import card from "~/components/card";
import { mapState, mapGetters, mapActions, mapMutations } from "vuex";

const columns = [
  { title: "编号", dataIndex: "id", width: "10%" },
  {
    title: "商品图片",
    key: "pic",
    scopedSlots: { customRender: "pic" },
    width: "10%"
  },
  {
    title: "商品名称",
    key: "description",
    scopedSlots: { customRender: "description" },
    width: "30%"
  },
  {
    title: "价格/货号",
    key: "price",
    scopedSlots: { customRender: "price" }
  },
  {
    title: "状态",
    key: "status",
    scopedSlots: { customRender: "status" }
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
  head() {
    return {
      title: "商品 - OUOStore"
    };
  },
  data() {
    return {
      columns,
      data: [],
      loading: false,
      pagination: {},
      showDetail: false,
      currentShowProduct: {}
    };
  },
  mounted() {
    this.fetch();
  },
  methods: {
    ...mapActions({ getProducts: "Product/getProducts" }),
    handleTableChange() {
      this.fetch();
    },
    handleShowButton(product) {
      console.log(product);
      this.currentShowProduct = product;
      this.showDetail = true;
    },
    fetch() {
      this.loading = true;
      this.getProducts().then(res => {
        const pagination = { ...this.pagination };
        pagination.current = res.current;
        pagination.total = res.total;
        pagination.pageSize = res.size;
        this.data = res.records;
        this.loading = false;
        this.pagination = pagination;
      });
    }
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
.ProductDetail {
  font-size: 1rem;
  line-height: 1.5rem;
  .key {
    text-align: right;
    font-size: .8rem;
    color: #888;
  }
}
</style>