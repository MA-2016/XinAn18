<template>
  <div id="main" style="width: 100%; min-height: 650px;"></div>
</template>

<script>
import 'echarts/map/js/china'
import 'echarts/map/js/world'

/* function handleAttack (that, data) {
  console.log(data)
  if (data.srcPos.countryId === 'China') {
    let attackLine = {}
    attackLine.fromName = data.srcPos.countryId
    attackLine.toName = data.tarPos.countryId
    attackLine.coords = []
    attackLine.coords.push([])
    that.attackData.chinaData.lineData.push()
  }
} */

let echarts = require('echarts/lib/echarts')
echarts.dataTool = require('echarts/extension/dataTool')
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')
require('echarts/lib/component/geo')
require('echarts/lib/chart/map')
require('echarts/lib/chart/line')
export default {
  name: 'detector',
  data () {
    return {
      apiPrefix: '',
      interval: null,
      attackData: {
        chinaData: {
          lineData: []
        },
        worldData: {}
      },
      planePath: 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z'
    }
  },
  methods: {
    getMapWorld () {
      clearInterval(this.interval)
      function convertAttackRouteWorld (data) {
        let res = []
        data.forEach((item, i) => {
          res.push({
            fromName: item[0].name,
            toName: item[1].name,
            coords: [coordMapWorld[item[0].name], coordMapWorld[item[1].name]]
          })
        })
        console.log(res)
        return res
      }
      let coordMapWorld = {
        '华盛顿': [-77.045, 38.5023],
        '洛杉矶': [-118.15, 34.03],
        '旧金山': [-122.25, 37.48],
        '新加坡': [103.51, 1.17],
        '慕尼黑': [11.35, 48.08],
        '柏林': [13.20, 52.31],
        '伦敦': [0.1, 51.50],
        '北京': [116.23, 39.54],
        '成都': [104.06, 30.67],
        '莫斯科': [37.37, 55.45],
        '巴黎': [2.20, 42.51],
        '纽约': [-74.0, 40.43],
        '上海': [121.48, 31.22]
      }
      let attackRouteWorld = [
        [{name: '新加坡'}, {name: '成都', value: 1}],
        [{name: '莫斯科'}, {name: '北京', value: 1}],
        [{name: '纽约'}, {name: '成都', value: 1}],
        [{name: '旧金山'}, {name: '上海', value: 1}],
        [{name: '莫斯科'}, {name: '上海', value: 1}],
        [{name: '华盛顿'}, {name: '成都', value: 1}],
        [{name: '华盛顿'}, {name: '上海', value: 1}],
        [{name: '纽约'}, {name: '北京', value: 1}],
        [{name: '柏林'}, {name: '成都', value: 1}],
        [{name: '伦敦'}, {name: '北京', value: 1}],
        [{name: '慕尼黑'}, {name: '上海', value: 1}],
        [{name: '旧金山'}, {name: '成都', value: 1}],
        [{name: '巴黎'}, {name: '北京', value: 1}]
      ]
      let series = [
        {
          name: '攻击线尾',
          type: 'lines',
          zlevel: 1,
          effect: {
            show: true,
            period: 10,
            trailLength: 0.7,
            color: '#fff',
            symbolSize: 3
          },
          lineStyle: {
            normal: {
              color: 'red',
              width: 0,
              curveness: 0.2
            }
          },
          data: convertAttackRouteWorld(attackRouteWorld)
        },
        {
          name: '危险指数',
          type: 'map',
          geoIndex: 0,
          data: [
            {name: 'Germany', value: 2},
            {name: 'China', value: 90},
            {name: 'United States', value: 99},
            {name: 'Sigapore', value: 1},
            {name: 'France', value: 10},
            {name: 'Russia', value: 33},
            {name: 'United Kingdom', value: 54},
            {name: 'Canada', value: 5},
            {name: 'Greenland', value: 0},
            {name: 'Mexico', value: 4},
            {name: 'Brazil', value: 10},
            {name: 'Australia', value: 20},
            {name: 'India', value: 88},
            {name: 'Egypt', value: 8},
            {name: 'Argentina', value: 3},
            {name: 'Algeria', value: 2},
            {name: 'Chad', value: 10},
            {name: 'Madagascar', value: 20}
          ]
        },
        {
          name: '攻击路线',
          type: 'lines',
          zlevel: 2,
          symbol: ['none', 'arrow'],
          symbolSize: 10,
          tooltip: {
            formatter: function (params) {
              return params.seriesName + '<br/>' + params.data.fromName + '->' + params.data.toName
            }
          },
          effect: {
            show: true,
            period: 10,
            trailLength: 0,
            symbol: this.planePath,
            symbolSize: 15
          },
          lineStyle: {
            normal: {
              color: 'red',
              width: 1,
              opacity: 0.6,
              curveness: 0.2
            }
          },
          data: convertAttackRouteWorld(attackRouteWorld)
        },
        {
          name: '危险指数',
          type: 'effectScatter',
          coordinateSystem: 'geo',
          zlevel: 2,
          tooltip: {
            formatter: function (params) {
              return params.seriesName + '<br/>' + params.name + ': ' + params.value[2]
            }
          },
          rippleEffect: {
            brushType: 'stroke'
          },
          label: {
            normal: {
              show: true,
              position: 'right',
              formatter: '{b}'
            }
          },
          symbolSize: 10,
          itemStyle: {
            normal: {
              color: 'yellow'
            }
          },
          data: attackRouteWorld.map(function (dataItem) {
            return {
              name: dataItem[1].name,
              value: coordMapWorld[dataItem[1].name].concat([dataItem[1].value])
            }
          })
        },
        {
          name: '攻击数量',
          type: 'bar',
          itemStyle: {
            color: '#db6c68'
          },
          data: (function () {
            var res = []
            var len = 10
            while (len--) {
              res.push(Math.round(Math.random() * 1000))
            }
            return res
          })()
        }
      ]
      let option = {
        backgroundColor: '#404a59',
        title: [
          {
            text: '网络安全态势',
            subtext: '网络安全监控中心',
            left: 'center',
            textStyle: {
              color: '#fff'
            }
          },
          {
            text: '国际实时攻击数量统计',
            left: '12%',
            bottom: '45%',
            textStyle: {
              color: '#ffffff',
              fontStyle: 'normal',
              fontWeight: 'bolder',
              fontFamily: 'sans-serif',
              fontSize: 12
            }
          }
        ],
        tooltip: {
          trigger: 'item'
        },
        graphic: {
          id: 'renturn-btn',
          $action: 'remove'
        },
        visualMap: {
          type: 'continuous',
          min: 0,
          max: 100,
          left: 'right',
          text: ['High', 'Low'],
          seriesIndex: [1],
          realtime: false,
          calculable: true,
          color: ['orangered', 'yellow', 'lightskyblue']
        },
        grid: {
          left: '1%',
          right: '70%',
          top: '60%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: true,
          axisLabel: {
            show: true
          },
          data: (function () {
            var now = new Date()
            var res = []
            var len = 10
            while (len--) {
              res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''))
              now = new Date(now - 2000)
            }
            return res
          })()
        },
        yAxis: {
          name: '攻击数量',
          type: 'value',
          max: 1200,
          min: 0,
          axisLabel: {
            show: true,
            textStyle: {
              color: '#ddd'
            }
          },
          splitLine: {
            show: false
          },
          boundaryGap: [0.2, 0.2]
        },
        geo: {
          map: 'world',
          roam: true
        },
        series: series
      }
      let myChart = echarts.init(document.getElementById(('main')))
      myChart.setOption(option)
      myChart.on('click', (params) => {
        if (params.name === 'China') {
          this.getMapChina()
        }
      })
      window.addEventListener('resize', function () {
        myChart.resize()
      })
      this.interval = setInterval(function () {
        var axisData = (new Date()).toLocaleTimeString().replace(/^\D*/, '')
        let data = option.series[4].data
        data.shift()
        data.push(Math.round(Math.random() * 1000))
        option.xAxis.data.shift()
        option.xAxis.data.push(axisData)
        myChart.setOption(option)
      }, 2100)
    },
    getMapChina () {
      clearInterval(this.interval)
      function convertAttackRouteChina (data) {
        let res = []
        data.forEach((item, i) => {
          res.push({
            fromName: item[0].name,
            toName: item[1].name,
            coords: [coordMapChina[item[0].name], coordMapChina[item[1].name]]
          })
        })
        return res
      }
      let coordMapChina = {
        '慕尼黑': [11.35, 48.08],
        '上海': [121.4648, 31.2891],
        '东莞': [113.8953, 22.901],
        '东营': [118.7073, 37.5513],
        '中山': [113.4229, 22.478],
        '临汾': [111.4783, 36.1615],
        '临沂': [118.3118, 35.2936],
        '丹东': [124.541, 40.4242],
        '丽水': [119.5642, 28.1854],
        '乌鲁木齐': [87.9236, 43.5883],
        '佛山': [112.8955, 23.1097],
        '保定': [115.0488, 39.0948],
        '兰州': [103.5901, 36.3043],
        '包头': [110.3467, 41.4899],
        '北京': [116.4551, 40.2539],
        '北海': [109.314, 21.6211],
        '南京': [118.8062, 31.9208],
        '南宁': [108.479, 23.1152],
        '南昌': [116.0046, 28.6633],
        '南通': [121.1023, 32.1625],
        '厦门': [118.1689, 24.6478],
        '台州': [121.1353, 28.6688],
        '合肥': [117.29, 32.0581],
        '呼和浩特': [111.4124, 40.4901],
        '咸阳': [108.4131, 34.8706],
        '哈尔滨': [127.9688, 45.368],
        '唐山': [118.4766, 39.6826],
        '嘉兴': [120.9155, 30.6354],
        '大同': [113.7854, 39.8035],
        '大连': [122.2229, 39.4409],
        '天津': [117.4219, 39.4189],
        '太原': [112.3352, 37.9413],
        '威海': [121.9482, 37.1393],
        '宁波': [121.5967, 29.6466],
        '宝鸡': [107.1826, 34.3433],
        '宿迁': [118.5535, 33.7775],
        '常州': [119.4543, 31.5582],
        '广州': [113.5107, 23.2196],
        '廊坊': [116.521, 39.0509],
        '延安': [109.1052, 36.4252],
        '张家口': [115.1477, 40.8527],
        '徐州': [117.5208, 34.3268],
        '德州': [116.6858, 37.2107],
        '惠州': [114.6204, 23.1647],
        '成都': [103.9526, 30.7617],
        '扬州': [119.4653, 32.8162],
        '承德': [117.5757, 41.4075],
        '拉萨': [91.1865, 30.1465],
        '无锡': [120.3442, 31.5527],
        '日照': [119.2786, 35.5023],
        '昆明': [102.9199, 25.4663],
        '杭州': [119.5313, 29.8773],
        '枣庄': [117.323, 34.8926],
        '柳州': [109.3799, 24.9774],
        '株洲': [113.5327, 27.0319],
        '武汉': [114.3896, 30.6628],
        '汕头': [117.1692, 23.3405],
        '江门': [112.6318, 22.1484],
        '沈阳': [123.1238, 42.1216],
        '沧州': [116.8286, 38.2104],
        '河源': [114.917, 23.9722],
        '泉州': [118.3228, 25.1147],
        '泰安': [117.0264, 36.0516],
        '泰州': [120.0586, 32.5525],
        '济南': [117.1582, 36.8701],
        '济宁': [116.8286, 35.3375],
        '海口': [110.3893, 19.8516],
        '淄博': [118.0371, 36.6064],
        '淮安': [118.927, 33.4039],
        '深圳': [114.5435, 22.5439],
        '清远': [112.9175, 24.3292],
        '温州': [120.498, 27.8119],
        '渭南': [109.7864, 35.0299],
        '湖州': [119.8608, 30.7782],
        '湘潭': [112.5439, 27.7075],
        '滨州': [117.8174, 37.4963],
        '潍坊': [119.0918, 36.524],
        '烟台': [120.7397, 37.5128],
        '玉溪': [101.9312, 23.8898],
        '珠海': [113.7305, 22.1155],
        '盐城': [120.2234, 33.5577],
        '盘锦': [121.9482, 41.0449],
        '石家庄': [114.4995, 38.1006],
        '福州': [119.4543, 25.9222],
        '秦皇岛': [119.2126, 40.0232],
        '绍兴': [120.564, 29.7565],
        '聊城': [115.9167, 36.4032],
        '肇庆': [112.1265, 23.5822],
        '舟山': [122.2559, 30.2234],
        '苏州': [120.6519, 31.3989],
        '莱芜': [117.6526, 36.2714],
        '菏泽': [115.6201, 35.2057],
        '营口': [122.4316, 40.4297],
        '葫芦岛': [120.1575, 40.578],
        '衡水': [115.8838, 37.7161],
        '衢州': [118.6853, 28.8666],
        '西宁': [101.4038, 36.8207],
        '西安': [109.1162, 34.2004],
        '贵阳': [106.6992, 26.7682],
        '连云港': [119.1248, 34.552],
        '邢台': [114.8071, 37.2821],
        '邯郸': [114.4775, 36.535],
        '郑州': [113.4668, 34.6234],
        '鄂尔多斯': [108.9734, 39.2487],
        '重庆': [107.7539, 30.1904],
        '金华': [120.0037, 29.1028],
        '铜川': [109.0393, 35.1947],
        '银川': [106.3586, 38.1775],
        '镇江': [119.4763, 31.9702],
        '长春': [125.8154, 44.2584],
        '长沙': [113.0823, 28.2568],
        '长治': [112.8625, 36.4746],
        '阳泉': [113.4778, 38.0951],
        '青岛': [120.4651, 36.3373],
        '韶关': [113.7964, 24.7028]
      }
      let attackRouteChina = [
        [{name: '北京'}, {name: '广州', value: 90}],
        [{name: '北京'}, {name: '大连', value: 80}],
        [{name: '大连'}, {name: '南宁', value: 70}],
        [{name: '北京'}, {name: '南昌', value: 60}],
        [{name: '乌鲁木齐'}, {name: '拉萨', value: 50}],
        [{name: '成都'}, {name: '长春', value: 40}],
        [{name: '北京'}, {name: '包头', value: 30}],
        [{name: '广州'}, {name: '重庆', value: 20}],
        [{name: '北京'}, {name: '常州', value: 10}],
        [{name: '重庆'}, {name: '成都', value: 20}]
      ]
      let series = [
        {
          name: '攻击线尾',
          type: 'lines',
          zlevel: 1,
          effect: {
            show: true,
            period: 6,
            trailLength: 0.7,
            color: '#fff',
            symbolSize: 3
          },
          lineStyle: {
            normal: {
              color: 'red',
              width: 0,
              curveness: 0.2
            }
          },
          data: convertAttackRouteChina(attackRouteChina)
        },
        {
          name: '危险指数',
          type: 'map',
          // mapType: 'China',
          geoIndex: 0,
          data: [
            {name: '北京', value: 88},
            {name: '重庆', value: 34},
            {name: '西藏', value: 5},
            {name: '广西', value: 17},
            {name: '广东', value: 56},
            {name: '江西', value: 24},
            {name: '浙江', value: 45},
            {name: '吉林', value: 39},
            {name: '四川', value: 47},
            {name: '内蒙古', value: 16},
            {name: '新疆', value: 2},
            {name: '江苏', value: 66},
            {name: '辽宁', value: 47},
            {name: '河北', value: 23},
            {name: '山东', value: 22}
          ]
        },
        {
          name: '攻击路线',
          type: 'lines',
          zlevel: 2,
          symbol: ['none', 'arrow'],
          symbolSize: 10,
          tooltip: {
            formatter: function (params) {
              return params.seriesName + '<br/>' + params.data.fromName + '->' + params.data.toName
            }
          },
          effect: {
            show: true,
            period: 6,
            trailLength: 0,
            symbol: this.planePath,
            symbolSize: 15
          },
          lineStyle: {
            normal: {
              color: 'red',
              width: 1,
              opacity: 0.6,
              curveness: 0.2
            }
          },
          data: convertAttackRouteChina(attackRouteChina)
        },
        {
          name: '危险指数',
          type: 'effectScatter',
          coordinateSystem: 'geo',
          zlevel: 2,
          tooltip: {
            formatter: function (params) {
              return params.seriesName + '<br/>' + params.name + ': ' + params.value[2]
            }
          },
          rippleEffect: {
            brushType: 'stroke'
          },
          label: {
            normal: {
              show: true,
              position: 'right',
              formatter: '{b}'
            }
          },
          symbolSize: 10,
          itemStyle: {
            normal: {
              color: 'red'
            }
          },
          data: attackRouteChina.map(function (dataItem) {
            return {
              name: dataItem[1].name,
              value: coordMapChina[dataItem[1].name].concat([dataItem[1].value])
            }
          })
        },
        {
          name: '攻击数量',
          type: 'bar',
          itemStyle: {
            color: '#db6c68'
          },
          data: (function () {
            var res = []
            var len = 10
            while (len--) {
              res.push(Math.round(Math.random() * 1000))
            }
            return res
          })()
        }
      ]
      let option = {
        backgroundColor: '#404a59',
        title: [
          {
            text: '网络安全态势',
            subtext: '网络安全监控中心',
            left: 'center',
            textStyle: {
              color: '#fff'
            }
          },
          {
            text: '中国实时攻击数量统计',
            left: '12%',
            bottom: '45%',
            textStyle: {
              color: '#ffffff',
              fontStyle: 'normal',
              fontWeight: 'bolder',
              fontFamily: 'sans-serif',
              fontSize: 12
            }
          }
        ],
        tooltip: {
          trigger: 'item'
        },
        graphic: {
          id: 'renturn-btn',
          type: 'circle',
          shape: {
            r: 20
          },
          style: {
            text: '返回',
            fill: '#eee'
          },
          left: 10,
          top: 70,
          onclick: () => {
            this.getMapWorld()
          }
        },
        visualMap: {
          type: 'continuous',
          min: 0,
          max: 100,
          left: 'right',
          text: ['High', 'Low'],
          realtime: false,
          calculable: true,
          color: ['orangered', 'yellow', 'lightskyblue']
        },
        grid: {
          left: '1%',
          right: '70%',
          top: '60%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: true,
          axisLabel: {
            show: true
          },
          data: (function () {
            var now = new Date()
            var res = []
            var len = 10
            while (len--) {
              res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''))
              now = new Date(now - 2000)
            }
            return res
          })()
        },
        yAxis: {
          name: '攻击数量',
          type: 'value',
          max: 1200,
          min: 0,
          axisLabel: {
            show: true,
            textStyle: {
              color: '#ddd'
            }
          },
          splitLine: {
            show: false
          },
          boundaryGap: [0.2, 0.2]
        },
        geo: {
          map: 'china',
          roam: true
        },
        series: series
      }
      let myChart = echarts.init(document.getElementById('main'))
      myChart.setOption(option)
      this.interval = setInterval(function () {
        var axisData = (new Date()).toLocaleTimeString().replace(/^\D*/, '')
        let data = option.series[4].data
        data.shift()
        data.push(Math.round(Math.random() * 1000))
        option.xAxis.data.shift()
        option.xAxis.data.push(axisData)
        myChart.setOption(option)
      }, 2100)
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    }
    /* getDetector () {
      this.getMapWorld()
      let myChart = echarts.init(document.getElementById('main'))
      myChart.setOption(this.optionWorld)
      myChart.on('click', (params) => {
        if (params.name === 'China') {
          this.getMapChina()
          myChart.resize()
          myChart.setOption(this.optionChina)
        }
      })
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    } */
  },
  mounted: function () {
    this.apiPrefix = this.$store.state.apiPrefix
    /* let ws = new WebSocket('ws://192.168.1.110:8001/websocket')
    ws.onopen = () => {
      console.log('success')
      ws.send('send')
    }
    ws.onmessage = (ms) => {
      // console.log(ms)
      const realtimeData = JSON.parse(ms.data)
      handleAttack(this, realtimeData)
    }
    ws.onclose = () => {
      this.$Message.error('实时传输通道关闭')
    }  */
    this.getMapWorld()
  }
}
</script>

<style scoped>
</style>
