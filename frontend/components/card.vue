<template>
  <div class="card">
    <div class="head" v-if="title">
      <span class="title"><a-icon class="icon" :type="icon" v-if="icon"/>{{title}}</span>
    </div>
    <div class="body">
      <slot name="body"></slot>
    </div>
    <div class="foot">
      <slot class="foot"></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: null
    },
    icon:{
      type: String,
      default: null
    }
  },
  methods:{

  }
};
</script>

<style lang="scss" scoped>
.card {
  background-color: #fff;
  // background-color: #11223344;
  padding: 0;
  margin: 0.3rem 0;
  border-radius: 0.3rem;
  box-shadow: 0 0 8px -1px #efefef;
  position: relative;
  .head {
    padding: 0.5rem 1.5rem;
    .title {
      font-size: 1rem;
      font-weight: 500;
      .icon{
        margin-right: .3rem;
      }
    }
  }
  .body {
    padding: .5rem 1.5rem 1rem 1.5rem;
  }
}
</style>