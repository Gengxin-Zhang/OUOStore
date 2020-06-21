<template>
  <div class="UMSPage">
    <a-breadcrumb style="margin-bottom: .7rem">
      <a-breadcrumb-item>
        <nuxt-link to="/">首页</nuxt-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>权限</a-breadcrumb-item>
    </a-breadcrumb>
    <card title="管理" icon="setting">
      <div slot="body">
        <nuxt-link to="/ums/roles">
          <a-button>角色管理</a-button>
        </nuxt-link>
        <nuxt-link to="/ums/menus">
          <a-button>菜单管理</a-button>
        </nuxt-link>
      </div>
    </card>
    <card title="后台用户列表" icon="unordered-list">
      <div slot="body">
        <div class="actions">
          <a-button type="primary" @click="handleNewUser">添加用户</a-button>
        </div>
        <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
        >
          <template slot="roles" slot-scope="user">
            <a-tag
              color="#2db7f5"
              v-for="role,index in user.roles"
              @key="index"
              @click="handleShowRole(role)"
            >{{role.name}}</a-tag>
          </template>
          <template slot="actions" slot-scope="user">
            <!-- <a-button size="small">查看</a-button> -->
            <a-button size="small" type="primary" @click="handleUpdateUser(user)">修改</a-button>
            <a-button size="small" type="danger" @click="handleDeleteUser(user)">删除</a-button>
          </template>
        </a-table>
      </div>
    </card>
    <a-modal v-model="showEditUserModal" :title="editingUser?'修改用户':'添加用户'" @ok="handleUserSubmit">
      <a-form
        :form="userForm"
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 12 }"
        @submit="handleUserSubmit"
      >
        <a-form-item label="邮箱">
          <a-input
            type="email"
            v-decorator="['email', { rules: [{ required: true, message: '请输入邮箱' }], initialValue: targetUser.email }]"
          />
        </a-form-item>
        <a-form-item label="用户名">
          <a-input
            v-decorator="['username', { rules: [{ required: true, message: '请输入角色名' }], initialValue: targetUser.username }]"
          />
        </a-form-item>
        <a-form-item label="昵称">
          <a-input
            v-decorator="['nickName', { rules: [{ required: true, message: '请输入昵称' }], initialValue: targetUser.nickName }]"
          />
        </a-form-item>
        <a-form-item label="密码" v-if="addingUser">
          <a-input
            type="password"
            v-decorator="['password', { rules: [{ required: addingUser, message: '请输入密码' }] }]"
          />
        </a-form-item>
        <a-transfer
          v-if="editingUser"
          :dataSource="roles"
          :titles="['未拥有', '已拥有']"
          :targetKeys="targetKeys"
          :checkedKeys="checkedMenuKeys"
          :render="item => item.title"
          @change="handleRoleChange"
          style="width: 100%"
        />
      </a-form>
    </a-modal>
  </div>
</template>
<script>
import card from "~/components/card";
import { mapActions } from "Vuex";
const columns = [
  { title: "编号", dataIndex: "id", width: "10%" },
  { title: "用户名", dataIndex: "username" },
  { title: "邮箱", dataIndex: "email" },
  { title: "注册时间", dataIndex: "createTime" },
  {
    title: "角色",
    key: "roles",
    scopedSlots: { customRender: "roles" },
    width: "20%"
  },
  { title: "操作", key: "actions", scopedSlots: { customRender: "actions" } }
];
export default {
  components: {
    card
  },
  head() {
    return {
      title: "权限 - OUOStore"
    };
  },
  data() {
    return {
      columns,
      pagination: {},
      data: [],
      loading: false,
      editingUser: false,
      addingUser: false,
      showEditUserModal: false,
      targetUser: {},
      userForm: this.$form.createForm(this, { name: "coordinated" }),
      roles: [],
      targetKeys: [],
      checkedMenuKeys: []
    };
  },
  mounted() {
    this.fetch();
  },
  watch: {
    editingUser(value) {
      this.showEditUserModal = value;
    },
    addingUser(value) {
      this.showEditUserModal = value;
    },
    showEditUserModal(value) {
      if (value === false) {
        this.editingUser = false;
        this.addingUser = false;
      }
    }
  },
  methods: {
    ...mapActions({
      getUsers: "User/getUsers",
      deleteUser: "User/deleteUser",
      getMenusByRole: "User/getMenusByRole",
      updateUser: "User/updateUser",
      addUser: "User/addUser",
      giveRole: "User/giveRole",
      removeRole: "User/removeRole",
      getRoles: "User/getRoles"
    }),
    handleTableChange() {},
    fetch() {
      this.getUsers().then(res => {
        console.log(res.records);
        this.data = res.records;
      });
    },
    handleShowRole(role) {
      const h = this.$createElement;
      this.getMenusByRole(role.id)
        .then(res => {
          const menus = res.map(item => h("p", `${item.title}: ${item.name}`));
          console.log(menus);
          this.$info({
            title: `用户角色「${role.name}」`,
            content: h("div", {}, [
              h("p", `权限：${role.description}`),
              h("p", `创建时间: ${role.createTime}`),
              h("p", `权限目录：`),
              ...menus
            ]),
            onOk() {}
          });
        })
        .catch(err => {
          this.$message.error(err);
        });
    },
    handleDeleteUser(user) {
      let that = this;
      this.$confirm({
        title: `确定删除用户「${user.nickName}」？`,
        content: h => <div style="color:red;">数据删除后不可恢复</div>,
        onOk() {
          that
            .deleteUser(user.id)
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
    handleUpdateUser(user) {
      this.getRoles()
        .then(res => {
          this.roles = res.records.map(item => {
            return {
              key: item.id.toString(),
              title: item.name
            };
          });
          this.targetKeys = user.roles.map(item => item.id.toString());
        })
        .catch(err => {
          this.$message.error(err);
        });
      this.targetUser = user;
      this.editingUser = true;
    },
    handleNewUser() {
      this.addingUser = true;
    },
    handleRoleChange(nextTargetKeys, direction, moveKeys) {
      moveKeys.forEach(item => {
        if (direction === "right") {
          this.giveRole({ userId: this.targetUser.id, roleId: item })
            .then(res => {
              this.targetKeys.push(item);
            })
            .catch(err => {
              this.$message.error(err);
            });
        } else if (direction === "left") {
          this.removeRole({ userId: this.targetUser.id, roleId: item })
            .then(res => {
              let index = this.targetKeys.indexOf(item);
              if (index > -1) {
                this.targetKeys.splice(index, 1);
              }
            })
            .catch(err => {
              this.$message.error(err);
            });
        } else {
        }
      });
    },
    handleUserSubmit() {
      this.userForm.validateFields((err, values) => {
        if (!err) {
          values.status = 1;
          values.id = this.targetUser.id;
          if (this.editingUser) {
            this.updateUser(values)
              .then(res => {
                this.$message.success("更新成功");
                this.editingUser = false;
                this.fetch();
              })
              .catch(err => {
                this.$message.error(err);
              });
          } else if (this.addingUser) {
            this.addUser(values)
              .then(res => {
                this.$message.success("添加成功");
                this.addingUser = false;
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
.UMSPage {
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