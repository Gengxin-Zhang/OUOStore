<template>
  <div class="index">
    <div class="indexHead">
      <a-breadcrumb :routes="routes">
        <template slot="itemRender" slot-scope="{ route, params, routes, paths }">
          <span v-if="routes.indexOf(route) === routes.length - 1">{{ route.breadcrumbName }}</span>
          <router-link v-else :to="`${basePath}/${paths.join('/')}`">{{ route.breadcrumbName }}</router-link>
        </template>
      </a-breadcrumb>
    </div>
    <div class="indexBody">
      <div class="side-column">
        <div class="sideList">
          <nuxt-link
            :to="url.path"
            :class="['sideListItem', $route.hash==url.path?'active':'']"
            v-for="url in urls"
          >
            <a-icon class="icon" :type="url.icon" />
            <span>{{url.name}}</span>
          </nuxt-link>
        </div>
      </div>
      <div class="content">
        <a-row :gutter="16" v-if="$route.hash==''">
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
            <div class="card">
              <a-statistic title="今日订单数" :value="1128" style="margin-right: 50px" />
            </div>
          </a-col>
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
            <div class="card">
              <a-statistic title="今日销售额" :value="11283.35" style="margin-right: 50px" />
            </div>
          </a-col>
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
            <div class="card">
              <a-statistic title="昨日销售额" :value="11232.01" style="margin-right: 50px" />
            </div>
          </a-col>
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
            <div class="card">
              <a-statistic title="用户总数" :value="3057" style="margin-right: 50px" />
            </div>
          </a-col>
          
        </a-row>
      </div>
    </div>
  </div>
</template>

<script>
import Logo from "~/components/Logo.vue";

export default {
  data() {
    const { lang } = this.$route.params;
    return {
      urls: [
        {
          path: "",
          name: "总览",
          icon: "global"
        },
        {
          path: "#todo",
          name: "待处理",
          icon: "alert"
        },
        {
          path: "#order",
          name: "订单统计",
          icon: "area-chart"
        },
        {
          path: "#seo",
          name: "SEO数据",
          icon: "experiment"
        },
        {
          path: "#system",
          name: "系统运行",
          icon: "database"
        }
      ],
      basePath: "",
      routes: [
        {
          path: "/",
          breadcrumbName: "仪表盘"
        },
        // {
        //   path: "first",
        //   breadcrumbName: "first"
        // }
      ]
    };
  },
  created() {},
  mounted() {}
};
</script>

<style lang='scss'>
.index {
  width: 100%;
  height: 100%;
  padding: 1rem 2rem;
  .indexHead {
    height: 2rem;
  }
  .indexBody {
    .side-column {
      position: absolute;
      width: 20%;
      // padding: 1rem 1rem;
      max-height: 100vh;
      overflow: auto;
      z-index: 1;
      .sideList {
        width: 100%;
        position: relative;
        .sideListItem {
          height: 2.5rem;
          width: 100%;
          line-height: 2.5rem;
          padding: 0 1rem;
          display: block;
          // background-color: #3f3f3f;
          margin: 0.3rem 0;
          border-radius: 0.3rem;
          color: #2e313f;
          font-weight: 500;
          font-size: 0.9rem;
          overflow: hidden;
          cursor: pointer;
          &.active {
            background-color: #0f4c81;
            color: #fff;
          }
          .icon {
            font-size: 1rem;
            margin-right: 1rem;
          }
        }
      }
    }
    .content {
      width: 100%;
      max-height: 100vh;
      overflow: auto;
      position: relative;
      padding: 0 1rem 1rem calc(20vw + 1rem);
      .card{
        background-color: #fff;
        // background-color: #11223344;
        padding: 1rem 1.5rem;
        margin: .3rem 0;
        border-radius: .3rem;
        box-shadow: 0 0 8px -1px #efefef;
      }
    }
  }
}
@media screen and(max-width: 600px) {
  .index {
    padding: 1rem;
    .indexBody {
      .side-column {
        width: 100%;
        position: relative;
      }
      .content {
        padding: 1rem 0;
      }
    }
  }
}
</style>
