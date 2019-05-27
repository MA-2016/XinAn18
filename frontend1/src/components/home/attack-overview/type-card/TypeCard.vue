<template>
  <div class="type-card-container">
    <Card>
      <p slot="title">
        <Icon type="navicon-round" size="15"></Icon>
        攻击类型分析
      </p>
      <div id="type" class="type-chart-container"></div>
    </Card>
  </div>
</template>

<script>
let echarts = require('echarts/lib/echarts')
require('echarts/lib/chart/bar')
require('echarts/lib/chart/pie')
require('echarts/lib/component/legend')
export default {
  name: 'type',
  data () {
    return {}
  },
  methods: {
    getType () {
      let myChart = echarts.init(document.getElementById('type'))
      myChart.showLoading()
      let option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: ['Collect and Analyze Information', 'Inject Unexpected Items', 'Engage in Deceptive Interactions', 'Manipulate Timing and State', 'Abuse Existing Functionality', 'Employ Probabilistic Techniques', 'Subvert Access Control', 'Manipulate Data Structures', 'Manipulate System Resources']
        },
        series: [
          {
            name: '病毒类型',
            type: 'pie',
            radius: '55%',
            label: {
              normal: {
                formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                backgroundColor: '#eee',
                borderColor: '#aaa',
                borderWidth: 1,
                borderRadius: 4,
                // shadowBlur:3,
                // shadowOffsetX: 2,
                // shadowOffsetY: 2,
                // shadowColor: '#999',
                // padding: [0, 7],
                rich: {
                  a: {
                    color: '#999',
                    lineHeight: 22,
                    align: 'center'
                  },
                  // abg: {
                  //     backgroundColor: '#333',
                  //     width: '100%',
                  //     align: 'right',
                  //     height: 22,
                  //     borderRadius: [4, 4, 0, 0]
                  // },
                  hr: {
                    borderColor: '#aaa',
                    width: '100%',
                    borderWidth: 0.5,
                    height: 0
                  },
                  b: {
                    fontSize: 16,
                    lineHeight: 33
                  },
                  per: {
                    color: '#eee',
                    backgroundColor: '#334455',
                    padding: [2, 4],
                    borderRadius: 2
                  }
                }
              }
            },
            center: ['55%', '60%'],
            data: [
              {value: 335, name: 'Collect and Analyze Information'},
              {value: 310, name: 'Inject Unexpected Items'},
              {value: 234, name: 'Engage in Deceptive Interactions'},
              {value: 145, name: 'Manipulate Timing and State'},
              {value: 231, name: 'Abuse Existing Functionality'},
              {value: 423, name: 'Employ Probabilistic Techniques'},
              {value: 123, name: 'Subvert Access Control'},
              {value: 453, name: 'Manipulate Data Structures'},
              {value: 111, name: 'Manipulate System Resources'}
            ],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
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
    this.getType()
  }
}
</script>

<style scoped>
  .type-card-container {
    float: left;
    width: 100%;
    margin-bottom: 15px;
  }

  .type-chart-container {
    height: 400px;
    width: 100%;
  }
</style>
