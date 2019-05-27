<template>
  <div class="result-card-container">
    <Card>
      <p slot="title">
        <Icon type="ios-analytics" size="15"></Icon>
        攻击结果分析
      </p>
      <div id="result" class="result-chart-container"></div>
    </Card>
  </div>
</template>

<script>
let echarts = require('echarts/lib/echarts')
require('echarts/lib/chart/bar')
require('echarts/lib/chart/pie')
require('echarts/lib/component/legend')
export default {
  name: 'result',
  data () {
    return {
      apiPrefix: '',
      failedNum: 0,
      successNum: 0
    }
  },
  methods: {
    async getResult () {
      let myChart = echarts.init(document.getElementById('result'))
      await this.axios.get(this.apiPrefix + '/event/getCensusAllAdmin')
        .then(result => {
          this.failedNum = result.data.faildNum
          this.successNum = result.data.successNum
        })
      let option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          // orient: 'vertical',
          x: 'left',
          data: ['攻击成功', '攻击失败']
        },
        series: [
          {
            name: '攻击结果',
            type: 'pie',
            selectedMode: 'single',
            label: {
              formatter: '{b}: {c}({d}%)'
            },
            data: [
              {name: '攻击成功', value: this.successNum},
              {name: '攻击失败', value: this.failedNum}
            ]
          }
        ]
      }
      myChart.setOption(option)
      myChart.hideLoading()
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    }
  },
  mounted: function () {
    this.apiPrefix = this.$store.state.apiPrefix
    this.getResult()
  }
}
</script>

<style scoped>
  .result-card-container {
    float: left;
    width: 39%;
    margin-right: 1%;
    margin-bottom: 15px;
  }

  .result-chart-container {
    height: 300px;
    width: 100%;
  }
</style>
