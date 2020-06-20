<template>
  <div class="signinPage">
    <div class="signinCard">
      <div class="logo">
        <span class="name">OUOStore</span>
      </div>
      <div class="form">
        <div class="inputblock">
          <input type="text" placeholder="账号" v-model="username" />
        </div>
        <div class="inputblock">
          <input type="password" placeholder="密码" v-model="password" />
        </div>
        <div class="inputblock">
          <div class="button" @click="handleSigninButton">登入</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions, mapMutations } from "vuex";
export default {
  layout: "auth",
  components: {},
  data() {
    return {
      username: "",
      password: ""
    };
  },
  methods: {
    ...mapActions({signin: "User/signin"}),
    handleSigninButton() {
        this.signin({username: this.username, password: this.password}).then(
          res=>{
            this.$router.push("/")
          }
        ).catch(
          error=>{
            this.$message.error(error)
          }
        )
    }
  }
};
</script>

<style lang="scss" scoped>
@import url(https://fonts.googleapis.com/css?family=Pacifico);
.signinPage {
  width: 350px;
  max-width: 100%;
  margin: 0 auto;
  padding-top: 10%;
  .signinCard {
    padding: 2rem 1rem;
    background-color: #fff;
    border-radius: 0.3rem;
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    position: relative;
    &:before {
      //   content: "";
      height: 0.5rem;
      width: 100%;
      position: absolute;
      top: 0;
      left: 0;
      background-color: #cce7f8;
      //   box-shadow: 0 0 6px rgba(182, 211, 207, 1),
      //     0 0 30px rgba(182, 211, 207, 0.4), 0 0 12px rgba(15, 115, 223, 0.6),
      //     0 0 22px rgba(15, 115, 223, 0.8), 0 0 38px rgba(15, 115, 223, 0.9),
      //     0 0 60px rgba(15, 115, 223, 1);
    }
    .logo {
      width: 100%;
      text-align: center;
      .name {
        // color: #cce7f8;
        font-weight: bolder;
        font-size: 2.5rem;
        font-family: "Pacifico", cursive;
        // text-transform: uppercase;
        color: #ebcb8b;
        // text-shadow: 0 0 2px rgba(182, 211, 207, 0.9),
        //   0 0 10px rgba(211, 188, 182, 0.3), 0 0 4px rgba(233, 215, 115, 0.5),
        //   0 0 5px rgba(223, 74, 15, 0.9), 0 0 8px rgba(223, 116, 15, 0.8),
        //   0 0 11px rgba(220, 223, 15, 0.9);
        // animation: shining 3s alternate infinite;
      }
    }
    .form {
      padding-top: 0.7rem;
      .inputblock {
        margin-top: 0.5rem;
        width: 100%;
        position: relative;
        input {
          width: 80%;
          height: 2.3rem;
          margin: 0 auto;
          display: block;
          padding: 0.5rem 1rem;
          outline: none;
          border: 1px rgba(0, 0, 0, 0.1) solid;
          border-radius: 0.3rem;
          font-size: 1rem;
          line-height: 1.3rem;
          font-weight: 400;
          &::placeholder {
            color: #aaa;
          }
        }
        .button {
          width: 80%;
          margin: 0 auto;
          height: 2.3rem;
          background-color: #5e81ac;
          border-radius: 0.3rem;
          border: 1px rgba(0, 0, 0, 0.1) solid;
          font-size: 1rem;
          text-align: center;
          line-height: 2.3rem;
          color: #fff;
          font-weight: 500;
          cursor: pointer;
        }
      }
    }
  }
}
@media screen and (max-width: 600px) {
  .signinPage {
    padding: 0;
    margin: 0;
  }
}
</style>