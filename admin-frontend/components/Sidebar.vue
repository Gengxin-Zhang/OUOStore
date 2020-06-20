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
      <nuxt-link to="/pms" class="SidebarList__Item">
        <a-tooltip placement="right">
          <template slot="title">
            <span>商品</span>
          </template>
          <a-icon class="icon" type="shopping" />
        </a-tooltip>
      </nuxt-link>
      <nuxt-link to="/oms" class="SidebarList__Item">
        <a-tooltip placement="right">
          <template slot="title">
            <span>订单</span>
          </template>
          <a-icon class="icon" type="container" />
        </a-tooltip>
      </nuxt-link>
      <nuxt-link to="/" class="SidebarList__Item">
        <a-tooltip placement="right">
          <template slot="title">
            <span>活动</span>
          </template>
          <a-icon class="icon" type="account-book" />
        </a-tooltip>
      </nuxt-link>
      <nuxt-link to="/ums" class="SidebarList__Item">
        <a-tooltip placement="right">
          <template slot="title">
            <span>权限</span>
          </template>
          <a-icon class="icon" type="audit" />
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
export default {};
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