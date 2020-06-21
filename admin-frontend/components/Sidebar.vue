<template>
  <div class="Sidebar">
    <div class="SidebarList">
      <nuxt-link to="/" class="SidebarList__Item">
        <a-tooltip placement="right">
          <template slot="title">
            <span>仪表盘</span>
          </template>
          <a-icon class="icon" type="dashboard" />
        </a-tooltip>
      </nuxt-link>
      <nuxt-link
        :to="menu.name"
        class="SidebarList__Item"
        v-for="menu, index in showMenu"
        @key="index"
      >
        <a-tooltip placement="right">
          <template slot="title">
            <span>仪表盘</span>
          </template>
          <a-icon class="icon" :type="menu.icon" />
        </a-tooltip>
      </nuxt-link>
      <nuxt-link to="/system" class="SidebarList__Item">
        <a-tooltip placement="right">
          <template slot="title">
            <span>系统设置</span>
          </template>
          <a-icon class="icon" type="control" />
        </a-tooltip>
      </nuxt-link>
    </div>
  </div>
</template>

<script>
import { mapActions } from "Vuex";
let menus = [
  { name: "/pms", title: "商品", icon: "shopping" },
  { name: "/oms", title: "订单", icon: "container" },
  { name: "/ums", title: "权限", icon: "audit" }
];
export default {
  data() {
    return {};
  },
  computed: {
    userMenu() {
      return this.$store.state.User.menus;
    },
    showMenu() {
      let temp = menus.filter(
        item => this.userMenu.indexOf(item.name) > -1
      );
      return temp;
    }
  },
  methods: {
    ...mapActions({})
  }
};
</script>

<style lang="scss" scoped>
$sidebar-width: 3rem;
.Sidebar {
  width: $sidebar-width;
  height: 100vh;
  position: fixed;
  background-color: #fff; //#f0ece3;
  box-shadow: 3px 0px 5px -2px rgba(0, 0, 0, 0.1);
  z-index: 98;
  padding-top: 3rem;
  .SidebarList {
    .SidebarList__Item {
      position: relative;
      display: inline-block;
      width: $sidebar-width;
      height: $sidebar-width;
      border-bottom: 1px #f2f4f6 solid;
      text-align: center;
      cursor: pointer;
      &:hover {
        background: #f3f4f8;
      }
      .icon {
        color: #35495e;
        font-size: 1.3rem;
        line-height: $sidebar-width;
      }
    }
  }
}
</style>