<template>
  <div class="OUOStore" v-if="valid">
    <Navbar></Navbar>
    <div class="Container">
      <div class="Sidebar"><Sidebar/></div>
      <div class="Main">
        <nuxt />
      </div>
    </div>
  </div>
</template>
<script>
import Navbar from "~/components/Navbar";
import Sidebar from "~/components/Sidebar";
import { mapState, mapGetters, mapActions, mapMutations } from "vuex";

export default {
  components: {
    Navbar,
    Sidebar
  },
  data(){
    return{
      valid: true
    }
  },
  created(){
    let authToken = localStorage.getItem("token");
    if (!authToken) {
      this.$router.push("/auth/signin");
      return;
    }
    this.validToken(authToken)
      .then(res => {
        this.valid = true
      })
      .catch(err => {
        this.$message.error("身份验证失败，请重新登入:" + err);
        this.$router.push("/auth/signin");
      })
      .finally();
  },
  methods:{
    ...mapActions({ validToken: "User/validToken" }),
  }
};
</script>
<style lang="scss">
html,
body {
  font-family: "Source Sans Pro", -apple-system, BlinkMacSystemFont, "Segoe UI",
    Roboto, "Helvetica Neue", Arial, sans-serif;
  font-size: 16px;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
  width: 100vw;
  min-height: 100vh;
  background-color: #f7f8fb;
}

*,
*:before,
*:after {
  box-sizing: border-box;
  margin: 0;
}

.OUOStore {
  width: 100vw;
  min-height: 100vh;
  .Container{
    .Main{
      padding: 3rem 0 0 3rem;
      min-height: 100vh;
      position: relative;
    }
  }
}
</style>
