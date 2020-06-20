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
          <a-button type="primary">添加用户</a-button>
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
            <a-button size="small">查看</a-button>
            <a-button size="small">修改</a-button>
            <a-button size="small" type="danger" @click="handleDeleteUser(user)">删除</a-button>
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
      loading: false
    };
  },
  mounted() {
    this.fetch();
  },
  methods: {
    ...mapActions({ getUsers: "User/getUsers", deleteUser: "User/deleteUser" }),
    handleTableChange() {},
    fetch() {
      this.getUsers().then(res => {
        console.log(res.records);
        this.data = res.records;
      });
    },
    handleShowRole(role) {
      console.log(role);
      const h = this.$createElement;
      this.$info({
        title: `用户角色「${role.name}」`,
        content: h("div", {}, [
          h("p", `权限：${role.description}`),
          h("p", `创建时间: ${role.createTime}`)
        ]),
        onOk() {}
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