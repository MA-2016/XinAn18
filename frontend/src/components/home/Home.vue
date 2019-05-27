<style scoped>
  @import "home.css";
</style>
<template>
  <div class="layout">
    <Layout style="height: 100%">
      <Sider ref="side1" hide-trigger collapsible :collapsed-width="78" v-model="isCollapsed" style="height: 100%;">
        <Menu active-name="realtime" theme="dark" width="auto" :class="menuitemClasses">
          <router-link to="/home/attack-detector">
            <MenuItem name="realtime">
              <Icon type="ios-navigate"></Icon>
              <span>安全态势</span>
            </MenuItem>
          </router-link>
          <router-link to="/home/attack-realtime">
            <MenuItem name="warning">
              <Icon type="pinpoint"></Icon>
              <span>实时监测</span>
            </MenuItem>
          </router-link>
          <router-link to="/home/attack-protect">
            <MenuItem name="protect">
              <Icon type="network"></Icon>
              <span>安全防护</span>
            </MenuItem>
          </router-link>
          <router-link to="/home/attack-overview">
            <MenuItem name="overview">
              <Icon type="bug"></Icon>
              <span>攻击概览</span>
            </MenuItem>
          </router-link>
        </Menu>
      </Sider>
      <Layout>
        <Header :style="{padding: 0}" class="layout-header-bar">
          <Icon @click.native="collapsedSider" :class="rotateIcon" :style="{margin: '20px 20px 0'}" type="navicon-round" size="24"></Icon>
          <h1 class="title">网络安全情景感知系统</h1>
        </Header>
        <Content :style="{padding: '0 16px 16px'}">
          <Breadcrumb :style="{margin: '16px 0'}">
            <BreadcrumbItem>Home</BreadcrumbItem>
            <BreadcrumbItem>Components</BreadcrumbItem>
            <BreadcrumbItem>Layout</BreadcrumbItem>
          </Breadcrumb>
          <div class="content">
            <router-view/>
          </div>
        </Content>
      </Layout>
    </Layout>
  </div>
</template>
<script>
export default {
  data () {
    return {
      isCollapsed: false
    }
  },
  computed: {
    rotateIcon () {
      return [
        'menu-icon',
        this.isCollapsed ? 'rotate-icon' : ''
      ]
    },
    menuitemClasses () {
      return [
        'menu-item',
        this.isCollapsed ? 'collapsed-menu' : ''
      ]
    }
  },
  methods: {
    collapsedSider () {
      this.$refs.side1.toggleCollapse()
    }
  }
}
</script>
