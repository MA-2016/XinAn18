<template>
  <div class="detector-card">
    <Card>
      <p slot="title">
        <Icon type="shuffle" size="15"></Icon>
        实时攻击事件监测
      </p>
      <div id="detector" class="detector-wrapper"></div>
    </Card>
  </div>
</template>

<script>
let echarts = require('echarts/lib/echarts')
echarts.dataTool = require('echarts/extension/dataTool')
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')
require('echarts/lib/chart/graph')
require('echarts/lib/chart/gauge')
require('echarts/lib/chart/radar')
export default {
  name: 'detector',
  data () {
    return {
      apiPrefix: '',
      nodes: [],
      links: []
    }
  },
  methods: {
    getDetector () {
      let myChart = echarts.init(document.getElementById('detector'))
      let option
      this.axios.get('/static/les-miserables.gexf').then(xml => {
        console.log(xml)
        var graph = echarts.dataTool.gexf.parse(xml.data)
        var categories = [
          {name: 'Category'},
          {name: 'MetaAttackPattern'},
          {name: 'StandardAttackPattern'},
          {name: 'DetailedAttackPattern'}
        ]
        graph.nodes.forEach(function (node) {
          node.itemStyle = null
          node.symbolSize = 10
          node.value = node.symbolSize
          node.category = node.attributes.modularity_class
          // Use random x, y
          node.x = node.y = null
          node.draggable = true
        })
        console.log(graph.nodes)
        console.log(graph.links)
        option = {
          title: {
            text: '病毒知识图谱',
            subtext: 'Default layout',
            top: 'bottom',
            left: 'right'
          },
          tooltip: {},
          legend: [{
            // selectedMode: 'single',
            data: categories.map(function (a) {
              return a.name
            })
          }],
          animation: false,
          series: [
            {
              name: 'Attack',
              type: 'graph',
              layout: 'force',
              data: this.nodes,
              links: this.links,
              categories: categories,
              legendHoverLink: true,
              roam: true,
              label: {
                show: true,
                position: 'right',
                color: 'auto'
              },
              force: {
                repulsion: 100
              }
            }
          ]
        }
        console.log({nodes: this.nodes, links: this.links})
        myChart.setOption(option)
      })
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    },
    getVirusGraph () {
      var categories = ['Category', 'MetaAttackPattern', 'StandardAttackPattern', 'DetailedAttackPattern']
      this.axios.get(this.apiPrefix + '/graph/getVirusGraph')
        .then(result => {
          console.log(result)
          if (result.data.code === 200) {
            const graph = result.data.data
            let nodesId = []
            for (let i = 0; i < graph.length; i++) {
              let nodeStart = {}
              let nodeEnd = {}
              let link = {}
              nodeStart.attributes = {modularity_class: categories.indexOf(graph[i].start.eventType)}
              nodeEnd.attributes = {modularity_class: categories.indexOf(graph[i].end.eventType)}
              nodeStart.category = categories.indexOf(graph[i].start.eventType)
              nodeEnd.category = categories.indexOf(graph[i].end.eventType)
              nodeStart.draggable = nodeEnd.draggable = true
              nodeStart.id = graph[i].start.eventId
              nodeEnd.id = graph[i].end.eventId
              nodeStart.name = graph[i].start.name
              nodeEnd.name = graph[i].end.name
              nodeStart.itemStyle = nodeEnd.itemStyle = null
              nodeStart.symbolSize = nodeEnd.symbolSize = 10
              nodeStart.value = graph[i].start.eventId
              nodeEnd.value = graph[i].end.eventId
              nodeStart.x = nodeEnd.x = null
              nodeStart.y = nodeEnd.y = null
              link.id = i
              link.source = graph[i].start.eventId
              link.target = graph[i].end.eventId
              if (nodesId.indexOf(nodeStart.id) === -1) {
                nodesId.push(nodeStart.id)
                this.nodes.push(nodeStart)
              }
              if (nodesId.indexOf(nodeEnd.id) === -1) {
                nodesId.push(nodeEnd.id)
                this.nodes.push(nodeEnd)
              }
              this.links.push(link)
            }
            this.getDetector()
          } else {
            this.$Message.error('提取病毒库失败')
          }
        })
    }
  },
  mounted: function () {
    this.apiPrefix = this.$store.state.apiPrefix
    this.getVirusGraph()
  }
}
</script>

<style scoped>
  .detector-card {
    width: 100%;
    margin-right: 1%;
    margin-bottom: 15px;
  }

  .detector-wrapper {
    height: 500px;
    width: 100%;
  }
</style>
