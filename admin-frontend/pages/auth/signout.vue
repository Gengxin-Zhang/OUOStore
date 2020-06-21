<template></template>

<script>
import { mapActions } from "Vuex";
export default {
  layout: "auth",
  data() {
    return {};
  },
  mounted() {
    this.signout()
      .then(res => {
        this.$router.push("/auth/signin");
      })
      .catch(err => {
        this.$message.error(err);
        this.$router.back();
      });
  },
  methods: {
    ...mapActions({ signout: "User/signout" })
  }
};
</script>


<style lang="scss" scoped>
</style>