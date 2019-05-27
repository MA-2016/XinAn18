<template>
  <div class="num-card-container">
    <Card>
      <p slot="title">
        <Icon type="calculator" size="15"></Icon>
        事件统计
      </p>
      <div slot="extra">
        <Icon type="refresh" color="#2d8cf0"></Icon>
        <a>近一周</a>
      </div>
      <Table stripe height="300" :columns="columns" :data="data2"></Table>
      <div style="margin: 10px;overflow: hidden">
        <div style="float: right;">
          <Page :total="total" @on-change="fetchAllEventsAdmin"></Page>
        </div>
      </div>
    </Card>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: 'number',
  data () {
    return {
      apiPrefix: '',
      total: 0,
      columns: [
        {
          title: '序号',
          key: 'index',
          type: 'index',
          width: 80
        },
        {
          title: '源IP',
          key: 'source',
          render: (h, params) => {
            const row = params.row
            const text = row.source
            return h('Poptip', {
              props: {
                trigger: 'hover',
                placement: 'top-start',
                title: 'IP定位'
              }
            }, [
              h('Tag', {
                props: {
                  type: 'dot',
                  color: 'red'
                }
              }, text),
              h('div', {
                slot: 'content'
              }, [
                h('div', {
                  style: {
                    margin: '10px',
                    fontFamily: 'Microsoft YaHei',
                    fontSize: '14px'
                  }
                }, '四川省成都市')
              ])
            ])
          }
        },
        {
          title: '目的IP',
          key: 'destination',
          render: (h, params) => {
            const row = params.row
            const text = row.destination
            return h('Poptip', {
              props: {
                trigger: 'hover',
                placement: 'top-start',
                title: 'IP定位'
              }
            }, [
              h('Tag', {
                props: {
                  type: 'dot',
                  color: 'blue'
                }
              }, text),
              h('div', {
                slot: 'content'
              }, [
                h('div', {
                  style: {
                    margin: '10px',
                    fontFamily: 'Microsoft YaHei',
                    fontSize: '14px'
                  }
                }, '北京市')
              ])
            ])
          }
        },
        {
          title: '攻击时间',
          key: 'time'
        },
        {
          title: '事件类型',
          key: 'type'
        },
        {
          title: '操作',
          key: 'operation',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary'
                  // size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.viewAttack(params)
                  }
                }
              }, '查看')
            ])
          }
        }
      ],
      data2: []
    }
  },
  methods: {
    viewAttack (params) {
      console.log(params)
      this.$Modal.confirm({
        title: '攻击事件详情',
        render: (h) => {
          return h('div', {
            style: {
              fontSize: '13px',
              fontFamily: 'Microsoft Yahei'
            }
          }, [
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '20px'
              }
            }, '事件ID : ' + params.row.id),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '20px'
              }
            }, '事件名 : ' + params.row.name),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '10px'
              }
            }, '攻击源地址 : 北京'),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '10px'
              }
            }, '攻击目的地址 : 成都'),
            h('div', {
              style: {
                marginTop: '10px'
              }
            }, [
              h('div', {
                style: {
                  display: 'inline-block',
                  width: '50%'
                }
              }, [
                h('div', '源IP'),
                h('Tag', {
                  props: {
                    type: 'dot',
                    color: 'red'
                  }
                }, params.row.source)
              ]),
              h('div', {
                style: {
                  display: 'inline-block',
                  width: '50%'
                }
              }, [
                h('div', '目的IP'),
                h('Tag', {
                  props: {
                    type: 'dot',
                    color: 'blue'
                  }
                }, params.row.destination)
              ])
            ]),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '10px'
              }
            }, 'type : ' + params.row.type),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '10px'
              }
            }, '是否为终节点 : ' + params.row.beScene),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '10px'
              }
            }, '是否攻击成功 : ' + params.row.attackSuccess),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '10px'
              }
            }, '攻击发起时间 : ' + moment(params.row.time).format('YYYY-MM-DD'))
          ])
        }
      })
    },
    fetchAllEventsAdmin (pageNum = 1) {
      this.data2 = []
      this.axios.get(this.apiPrefix + '/event/fetchAllEventsAdmin?pageNum=' + pageNum + '&pageSize=10')
        .then(result => {
          console.log(result)
          let list = result.data.list
          this.total = result.data.total
          for (let i = 0; i < list.length; i++) {
            let temp = {}
            temp.source = list[i].sourceString
            temp.destination = list[i].targetString
            temp.time = moment(list[i].time).format('YYYY-MM-DD')
            temp.type = list[i].eventType
            temp.attackSuccess = list[i].attackSuccess
            temp.beScene = list[i].beScene
            temp.id = list[i].id
            temp.name = list[i].name
            this.data2.push(temp)
          }
        })
    }
  },
  mounted: function () {
    this.apiPrefix = this.$store.state.apiPrefix
    this.fetchAllEventsAdmin()
  }
}
</script>

<style scoped>
  .num-card-container {
    float: left;
    width: 100%;
  }

  .number-chart-container {
    height: 400px;
    width: 100%;
  }
</style>
