<template>
  <div class="menusPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/ums">权限</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>角色管理</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="菜单管理">
      <div slot="body">
        <div class="actions">
          <a-button type="primary" @click="handleAddMenu">添加菜单</a-button>
        </div>
        <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
        >
          <template slot="actions" slot-scope="menu">
            <a-button size="small">查看</a-button>
            <a-button size="small" type="primary">修改</a-button>
            <a-button size="small" type="danger" @click="handleDeleteMenu(menu)">删除</a-button>
          </template>
        </a-table>
      </div>
    </card>
    <a-modal
      v-model="showEditMenuModal"
      :title="editingMenu?`修改角色「${targetRole.name}」`:'新建角色'"
      @ok="handleMenuSubmit"
    >
      <a-form
        :form="menuForm"
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 12 }"
        @submit="handleMenuSubmit"
      >
        <a-form-item label="名称">
          <a-input
            v-decorator="['title', { rules: [{ required: true, message: '请输入名称' }], initialValue: targetMenu.name }]"
          />
        </a-form-item>
        <a-form-item label="地址">
          <a-input
            v-decorator="['name', { rules: [{ required: true, message: '请输入地址' }], initialValue: targetMenu.description }]"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import card from "~/components/card";
import { mapActions } from "Vuex";
const columns = [
  {
    title: "编号",
    dataIndex: "id",
    width: "10%"
  },
  {
    title: "名称",
    dataIndex: "title"
  },
  {
    title: "路径",
    dataIndex: "name"
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
      columns,
      pagination: {},
      loading: false,
      data: [],
      showEditMenuModal: false,
      editingMenu: false,
      addingMenu: false,
      targetMenu: {},
      menuForm: this.$form.createForm(this, { name: "coordinated" })
    };
  },
  watch: {
    editingMenu(value) {
      this.showEditMenuModal = value;
    },
    addingMenu(value) {
      this.showEditMenuModal = value;
    },
    showEditMenuModal(value) {
      if (value === false) {
        this.addingMenu = false;
        this.editingMenu = false;
      }
    }
  },
  mounted() {
    this.fetch();
  },
  methods: {
    ...mapActions({
      getMenus: "User/getMenus",
      deleteMenu: "User/deleteMenuById",
      updateMenu: "User/updateMenuById",
      addMenu: "User/addMenu"
    }),
    handleTableChange() {},
    handleAddMenu() {
      this.addingMenu = true;
    },
    fetch() {
      this.getMenus({})
        .then(res => {
          console.log(res);
          this.data = res;
        })
        .catch(err => {
          this.$message.error(err);
        });
    },
    handleDeleteMenu(menu) {
      let that = this;
      this.$confirm({
        title: `确定删除菜单「${menu.title}」？`,
        content: h => <div style="color:red;">数据删除后不可恢复</div>,
        onOk() {
          that
            .deleteMenu(menu.id)
            .then(res => {
              that.$message.success(res);
              that.fetch();
            })
            .catch(err => {
              that.$message.error(err);
            });
        },
        onCancel() {}
      });
    },
    handleMenuSubmit(e) {
      this.menuForm.validateFields((err, values) => {
        if (!err) {
          values.sort = 0;
          values.status = 1;
          values.hidden = 0;
          values.parentId = 0;
          values.icon = "";
          values.id = this.targetMenu.id;
          if (this.targetMenu.id) {
            this.updateRole(values)
              .then(res => {
                this.$message.success("更新成功");
                this.editingRole = false;
                this.fetch();
              })
              .catch(err => {
                this.$message.error(err);
              });
          } else {
            this.addMenu(values)
              .then(res => {
                this.$message.success("添加成功");
                this.addingMenu = false;
                this.fetch();
              })
              .catch(err => {
                this.$message.error(err);
              });
          }
        } else {
        }
      });
    }
  }
};
</script>


<style lang="scss" scoped>
.menusPage {
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