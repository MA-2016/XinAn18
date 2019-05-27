<template>
  <div class="detail-card-container">
    <Card>
      <p slot="title">
        <Icon type="ios-list" size="15"></Icon>
        病毒详情统计
      </p>
      <div slot="extra">
        <Icon type="refresh" color="#2d8cf0"></Icon>
        <a>近一周</a>
      </div>
      <div class="table-container">
        <Table stripe height="300" :columns="columns1" :data="data2"></Table>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
  name: 'detail',
  data () {
    return {
      columns1: [
        {
          title: 'ID',
          key: 'id',
          type: 'index'
        },
        {
          title: 'IP',
          key: 'ip',
          render: (h, params) => {
            const row = params.row
            const text = row.ip
            return h('Tag', {
              props: {
                type: 'dot',
                color: 'green'
              }
            }, text)
          }
        },
        {
          title: '攻击状态',
          key: 'status',
          render: (h, params) => {
            const row = params.row
            const text = row.status
            const process = row.process
            return h('Poptip', {
              props: {
                trigger: 'hover',
                placement: 'top-start',
                title: text === '成功' ? '攻击结果' : '失败事件'
              }
            }, [
              h('Tag', {
                props: {
                  type: 'dot',
                  color: text === '成功' ? 'green' : 'red'
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
                }, [
                  h('Tag', {
                    props: {
                      type: 'dot',
                      color: text === '成功' ? 'green' : 'red'
                    }
                  }, text === '成功' ? '攻击成功' : process)
                ])
              ])
            ])
          }
        },
        {
          title: '操作',
          key: 'operation',
          render: (h, params) => {
            return h('Button', {
              props: {
                type: 'primary'
              },
              on: {
                click: () => {
                  this.seeDetail(params)
                }
              }
            }, '查看')
          }
        }
      ],
      data2: [
        {
          status: '失败',
          ip: '11.130.10.15',
          process: 'Principal Sproof'
        },
        {
          status: '成功',
          ip: '128.144.17.1',
          process: 'Host Discovery'
        },
        {
          status: '失败',
          ip: '149.122.11.12',
          process: 'Force Use of Corrupted File'
        },
        {
          status: '失败',
          ip: '79.34.13.21',
          process: 'Hardware Integer Attack'
        },
        {
          status: '成功',
          ip: '65.1.44.35',
          process: 'WSDL Scanning'
        },
        {
          status: '失败',
          ip: '19.5.5.3',
          process: 'Targeted Malware'
        },
        {
          status: '失败',
          ip: '21.22.5.12',
          process: 'XML Ping of the Death'
        }
      ]
    }
  },
  methods: {
    seeDetail (params) {
      this.$Modal.confirm({
        title: '攻击详情',
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
            }, '疑似病毒种类 :  Subvert Access Control'),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '20px'
              }
            }, '攻击发起时间 : 5-18 22:12'),
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
                marginTop: '20px'
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
                }, '145.12.44.17')
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
                }, params.row.ip)
              ])
            ]),
            h('div', {
              style: {
                marginTop: '20px'
              }
            }, [
              h('div', '攻击进度'),
              h('Progress', {
                props: {
                  percent: params.row.status === '失败' ? 45 : 100,
                  status: 'wrong'
                }
              })
            ]),
            h('div', {
              style: {
                marginTop: '20px'
              }
            }, [
              h('div', '失败事件'),
              h('Tag', {
                props: {
                  type: 'dot',
                  color: 'blue'
                }
              }, params.row.process)
            ])
          ])
        }
      })
    }
  },
  mounted: function () {
  }
}
</script>

<style scoped>
  .detail-card-container {
    float: left;
    width: 60%;
    margin-bottom: 15px;
  }

  .table-container {}
</style>
