<template>
  <div class="warning-card">
    <Card>
      <p slot="title">
        <Icon type="alert-circled" size="15"></Icon>
        实时攻击预警
      </p>
      <div id="warning" class="warning-wrapper"></div>
      <div style="height: 50px; width: 100%; text-align: center">
        <h2 style="color: #c03a38; margin: 0px auto">攻击进程完成，主机受到入侵</h2>
      </div>
    </Card>
  </div>
</template>

<script>
let echarts = require('echarts/lib/echarts')
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')
require('echarts/lib/chart/graph')
require('echarts/lib/chart/gauge')
require('echarts/lib/chart/radar')

export default {
  name: 'warning',
  data () {
    return {}
  },
  methods: {
    getWarning () {
      let warningChart = echarts.init(document.getElementById('warning'))
      let option = {
        tooltip: {
          formatter: '{a} <br/>{b} : {c}%'
        },
        toolbox: {
          feature: {
            restore: {},
            saveAsImage: {}
          }
        },
        series: [
          {
            name: '攻击进程',
            type: 'gauge',
            detail: {formatter: '{value}%'},
            data: [{value: 100, name: '攻击完成率'}]
          }
        ]
      }
      warningChart.setOption(option)
      window.addEventListener('resize', function () {
        warningChart.resize()
      })
    }
  },
  mounted: function () {
    this.getWarning()
  }
}
</script>

<style scoped>
  .warning-card {
    float: right;
    width: 30%;
    margin-bottom: 15px;
  }

  .warning-wrapper {
    width: 100%;
    height: 300px;
  }
</style>
