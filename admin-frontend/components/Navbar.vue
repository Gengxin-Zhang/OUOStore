<template>
  <div class="Navbar">
    <div class="NavbarList left">
      <div class="NavbarList__Item logo">
        <img src="@/assets/logo.svg" />
      </div>
      <div class="NavbarList__Item title">
        <span>OUOStore系统</span>
      </div>
    </div>
    <div class="NavbarList right">
      <div class="NavbarList__Item">
        <a-icon class="icon" type="pushpin" />
      </div>
      <div class="NavbarList__Item">
        <a-icon class="icon" type="notification" />
      </div>
      <div class="NavbarList__Item">
        <div class="avatar">
          <img src="https://cdn.uminsi.com/avatars/10f68df8-ecfa-43df-a646-6d8216c5b106.png" alt />
          <div class="avatarList">
            <nuxt-link to="/auth/signout" class="avatarListItem" >退出</nuxt-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {};
</script>

<style lang="scss" scoped>
$navbar-height: 3rem;
.Navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: $navbar-height;
  box-shadow: 0 3px 5px -2px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  z-index: 99;
  .NavbarList {
    position: absolute;
    top: 0;
    height: $navbar-height;
    .NavbarList__Item {
      position: relative;
      display: inline-block;
      text-align: center;
      height: $navbar-height;
      vertical-align: top;
      margin: 0;
      padding: 0;
      &.logo {
        width: $navbar-height;
        padding: 0.3rem;
        background-color: #0f4c81;
        img {
          width: 100%;
          height: 100%;
        }
      }
      &.title {
        font-family: "Quicksand", "Source Sans Pro", -apple-system,
          BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial,
          sans-serif;
        color: #35495e;
        padding: 0 0.5rem;
        font-size: 1.1rem;
        font-weight: 450;
        line-height: $navbar-height;
        letter-spacing: 1px;
      }
      .icon {
        line-height: $navbar-height;
        width: $navbar-height;
        font-size: 1.1rem;
        &:hover {
          background: #f3f4f8;
        }
      }
      .avatar {
        height: $navbar-height;
        padding: 0.5rem;
        position: relative;
        img {
          border-radius: $navbar-height;
          width: $navbar-height - 1rem;
          height: $navbar-height - 1rem;
          margin: 0 0.5rem;
        }
        &:hover {
          background: #f3f4f8;
          .avatarList {
            display: block;
            opacity: 1;
          }
        }
        .avatarList {
          display: none;
          opacity: 0;
          width: auto;
          position: absolute;
          top: $navbar-height;
          right: .3rem;
          padding-top: .3rem;
          min-width: 100%;
          transition: all .3s;
          box-shadow: 0 2px 5px -1px rgba(0,0,0,0.1);
          border-radius: .3rem;
          overflow: hidden;
          .avatarListItem {
            cursor: pointer;
            height: 2rem;
            width: 100%;
            font-size: 1rem;
            line-height: 2rem;
            background: #fff;
            color: #666;
            display: block;
            &:hover {
              background-color: #efefef;
            }
          }
        }
      }
    }
    &.left {
      left: 0;
    }
    &.right {
      right: 0;
    }
  }
}
</style>