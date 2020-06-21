<template>
  <div class="index">
    <div class="indexHead">
      <a-breadcrumb :routes="routes" style="margin-bottom: .7rem">
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
        <a-row :gutter="16" v-show="$route.hash===''">
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
            <card>
              <a-statistic slot="body" title="今日订单数" :value="1128" style="margin-right: 50px" />
            </card>
          </a-col>
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
            <card>
              <a-statistic slot="body" title="今日销售额" :value="11283.35" style="margin-right: 50px" />
            </card>
          </a-col>
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
            <card>
              <a-statistic slot="body" title="昨日销售额" :value="11232.01" style="margin-right: 50px" />
            </card>
          </a-col>
          <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
            <card>
              <a-statistic slot="body" title="用户总数" :value="3057" style="margin-right: 50px" />
            </card>
          </a-col>
        </a-row>
        <div v-show="$route.hash==='' || $route.hash==='#todo'">
          <card title="待处理">
            <div slot="body">暂无</div>
          </card>
        </div>
        <div v-show="$route.hash==='' || $route.hash==='#order'">
          <card title="订单统计">
            <div slot="body">暂无</div>
          </card>
        </div>
        <div v-show="$route.hash==='' || $route.hash==='#seo'">
          <card title="SEO">
            <div slot="body">暂无</div>
          </card>
        </div>
        <div v-show="$route.hash==='' || $route.hash==='#system'">
          <card title="系统运行">
            <div slot="body">
              <a-row :gutter="16">
                <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
                  <div class="infoCol">
                    <span class="key">系统版本</span>
                    <span>{{systemInfo.os}}</span>
                  </div>
                </a-col>
                <a-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
                  <div class="infoCol">
                    <span class="key">CPU核心数</span>
                    <span>{{systemInfo.cpuCoreCount}}</span>
                  </div>
                </a-col>
                <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <div class="infoCol">
                    <span class="key">启动时间</span>
                    <span>{{systemInfo.startTime}}</span>
                  </div>
                </a-col>
                <a-col :xs="24" :sm="24" :md="12" :lg="8" :xl="8">
                  <div id="jvmGraph"></div>
                </a-col>
                <a-col :xs="24" :sm="24" :md="12" :lg="8" :xl="8">
                  <div id="memoryGraph"></div>
                </a-col>
                <a-col :xs="24" :sm="24" :md="12" :lg="8" :xl="8">
                  <div id="storeGraph"></div>
                </a-col>
              </a-row>
            </div>
          </card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Logo from "~/components/Logo.vue";
import card from "~/components/card.vue";
import { mapActions } from "Vuex";
import { Donut, Ring } from "@antv/g2plot";
export default {
  components: {
    card
  },
  head() {
    return {
      title: "仪表盘 - OUOStore"
    };
  },
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
        }
        // {
        //   path: "first",
        //   breadcrumbName: "first"
        // }
      ],
      systemInfo: {},
      jvmGraph: null
    };
  },
  mounted() {
    this.fetchSystemInfo();
  },
  methods: {
    ...mapActions({ getSystemInfo: "Util/getSystemInfo" }),
    fetchSystemInfo() {
      this.getSystemInfo()
        .then(res => {
          console.log(res);
          this.systemInfo = res;
          const jvmData = [
            {
              type: "已使用",
              value: Number.parseInt(res.jvmLeftMemory)
            },
            {
              type: "未使用",
              value: Number.parseInt(res.jvmMaxMemory - res.jvmLeftMemory)
            }
          ];
          console.log(jvmData, document.getElementById("jvmGraph"));
          this.jvmGraph = new Donut(document.getElementById("jvmGraph"), {
            title: { visible: true, text: "JVM内存" },
            forceFit: true,
            radius: 1,
            data: jvmData,
            angleField: "value",
            colorField: "type"
          });
          this.jvmGraph.render();
          const menoryData = [
            {
              type: "已使用",
              value: Number.parseInt(res.systemLeftMemory)
            },
            {
              type: "未使用",
              value: Number.parseInt(res.systemMaxMemory - res.systemLeftMemory)
            }
          ];
          const memoryGraph = new Donut(
            document.getElementById("memoryGraph"),
            {
              title: { visible: true, text: "系统内存" },
              forceFit: true,
              radius: 1,
              data: menoryData,
              angleField: "value",
              colorField: "type"
            }
          );
          memoryGraph.render();
          const sotreData = [
            {
              type: "未使用",
              value: Number.parseInt(res.systemLeftStore)
            },
            {
              type: "已使用",
              value: Number.parseInt(res.systemMaxStore - res.systemLeftStore)
            }
          ];
          const storeGraph = new Donut(
            document.getElementById("storeGraph"),
            {
              title: { visible: true, text: "存储空间" },
              forceFit: true,
              radius: 1,
              data: sotreData,
              angleField: "value",
              colorField: "type"
            }
          );
          storeGraph.render();
        })
        .catch(err => {
          this.$message.error(err);
        });
    }
  }
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
      // max-height: 100vh;
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
      // max-height: 100vh;
      overflow: auto;
      position: relative;
      padding: 0 1rem 1rem calc(20vw + 1rem);
      .infoCol {
        margin: 0.2rem 0;
        .key {
          font-size: 0.8rem;
          color: #999;
          margin-right: 0.5rem;
          &::after {
            content: ":";
          }
        }
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
