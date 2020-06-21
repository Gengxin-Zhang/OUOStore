<template>
  <div class="rolesPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>
        <nuxt-link to="/ums">权限</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>角色管理</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="角色管理">
      <div slot="body">
        <div class="actions">
          <a-button type="primary" @click="handleAddRole">添加角色</a-button>
        </div>
        <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
        >
          <template slot="actions" slot-scope="role">
            <a-button size="small" @click="handleEditMenu(role)">管理菜单</a-button>
            <a-button size="small">分配资源</a-button>
            <br />
            <a-button size="small" type="primary" @click="handleUpdateRole(role)">修改</a-button>
            <a-button size="small" type="danger" @click="handleDeleteRole(role)">删除</a-button>
          </template>
        </a-table>
      </div>
    </card>
    <a-modal
      v-model="showEditRoleModal"
      :title="editingRole?`修改角色「${targetRole?targetRole.name:''}」`:'新建角色'"
      @ok="handleRoleSubmit"
    >
      <a-form
        :form="roleForm"
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 12 }"
        @submit="handleRoleSubmit"
      >
        <a-form-item label="角色名">
          <a-input
            v-decorator="['name', { rules: [{ required: true, message: '请输入角色名' }], initialValue: targetRole.name }]"
          />
        </a-form-item>
        <a-form-item label="描述">
          <a-input
            v-decorator="['description', { rules: [{ required: true, message: '请输入角色描述' }], initialValue: targetRole.description }]"
          />
        </a-form-item>
      </a-form>
    </a-modal>
    <a-modal v-model="showEditMenuModel" title="管理菜单" @ok="handleEditMenuOk">
      <a-transfer
        :dataSource="menus"
        :titles="['未拥有', '已拥有']"
        :targetKeys="targetKeys"
        :checkedKeys="checkedMenuKeys"
        :render="item => item.title"
        @scroll="handleMenuScroll"
        @change="handleMenuChange"
        style="width: 100%"
      />
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
    dataIndex: "name",
    width: "20%"
  },
  {
    title: "描述",
    dataIndex: "description",
    width: "20%"
  },
  {
    title: "用户数",
    dataIndex: "adminCount"
  },
  {
    title: "创建时间",
    dataIndex: "createTime"
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
      editingRole: false,
      addingRole: false,
      showEditRoleModal: false,
      showEditMenuModel: false,
      targetRole: {},
      targetKeys: [],
      checkedMenuKeys: [],
      roleForm: this.$form.createForm(this, { name: "coordinated" }),
      menus: []
    };
  },
  watch: {
    editingRole(value) {
      this.showEditRoleModal = value;
    },
    addingRole(value) {
      this.showEditRoleModal = value;
    },
    showEditRoleModal(value) {
      if (value === false) {
        this.addingRole = false;
        this.editingRole = false;
      }
    }
  },
  mounted() {
    this.fetch();
  },
  methods: {
    ...mapActions({
      getRoles: "User/getRoles",
      updateRole: "User/updateRole",
      deleteRole: "User/deleteRole",
      addRole: "User/addRole",
      getMenus: "User/getMenus",
      removeMenu: "User/removeMenu",
      getMenusByRole: "User/getMenusByRole",
      giveMenu: "User/giveMenu"
    }),
    handleTableChange() {
      this.fetch();
    },
    fetch() {
      this.getRoles({})
        .then(res => {
          console.log(res);
          this.data = res.records;
        })
        .catch(err => {
          this.$message.error(err);
        });
    },
    handleUpdateRole(role) {
      this.targetRole = role;
      this.editingRole = true;
    },
    handleDeleteRole(role) {
      let that = this;
      this.$confirm({
        title: `确定删除角色「${role.name}」？`,
        content: h => <div style="color:red;">数据删除后不可恢复</div>,
        onOk() {
          that
            .deleteRole(role.id)
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
    handleAddRole() {
      this.targetRole = {
        name: "",
        description: ""
      };
      this.addingRole = true;
    },
    handleEditMenu(role) {
      this.targetRole = role;

      this.getMenus()
        .then(res => {
          this.menus = res.map(item => {
            return {
              key: item.id.toString(),
              title: `${item.title}[${item.name}]`,
              description: item.name,
              disabled: false
            };
          });
        })
        .catch(err => {
          this.$message.error(err);
        });
      console.log(role);
      this.getMenusByRole(role.id)
        .then(res => {
          this.targetKeys = res.map(item => item.id.toString());
          console.log(this.targetKeys);
        })
        .catch(err => {
          this.$message.error(err);
        });
      this.showEditMenuModel = true;
    },
    handleEditMenuOk() {
      this.targetRole = {};
      this.showEditMenuModel = false;
    },
    handleMenuChange(nextTargetKeys, direction, moveKeys) {
      moveKeys.forEach(item => {
        if (direction === "right") {
          this.giveMenu({ roleId: this.targetRole.id, menuId: item })
            .then(res => {
              this.targetKeys.push(item);
            })
            .catch(err => {
              this.$message.error(err);
            });
        } else if (direction === "left") {
          this.removeMenu({ roleId: this.targetRole.id, menuId: item })
            .then(res => {
              let index = this.targetKeys.indexOf(item);
              if (index > -1) {
                this.targetKeys.splice(index, 1);
              }
            })
            .catch(err => {
              this.$message.error(err);
            });
        }else{

        }
      });
    },
    handleMenuScroll(e) {
      console.log(e);
    },
    handleRoleSubmit(e) {
      this.roleForm.validateFields((err, values) => {
        if (!err) {
          values.sort = 0;
          values.status = 1;
          values.id = this.targetRole.id;
          if (this.targetRole.id) {
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
            this.addRole(values)
              .then(res => {
                this.$message.success("添加成功");
                this.addingRole = false;
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
.rolesPage {
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