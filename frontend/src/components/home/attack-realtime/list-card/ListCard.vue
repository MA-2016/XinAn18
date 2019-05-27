<template>
  <div class="attack-list-card">
    <Card>
      <p slot="title">
        <Icon type="android-list" size="15"></Icon>
        实时攻击列表
      </p>
      <div>
        <Table stripe height="300" :columns="columns1" :data="data2"></Table>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
  name: 'list',
  data () {
    return {
      columns1: [
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
                }, row.sCity)
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
                }, row.dCity)
              ])
            ])
          }
        },
        {
          title: '当前进程',
          key: 'status',
          render: (h, params) => {
            return h('Tag', {
              props: {
                type: 'dot',
                color: 'green'
              }
            }, params.row.status)
          }
        },
        {
          title: '操作',
          key: 'operation',
          minWidth: 90,
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
                    this.viewAttack(params.row)
                  }
                }
              }, '查看'),
              h('Button', {
                props: {
                  type: 'warning',
                  disabled: params.row.isProtected
                  // size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.protect(params)
                  }
                }
              }, '防护'),
              h('Button', {
                props: {
                  type: 'error',
                  disabled: params.row.isSeperated
                  // size: 'small'
                },
                on: {
                  click: () => {
                    this.addBlackList(params)
                  }
                }
              }, '隔离')
            ])
          }
        }
      ],
      data2: [
        {
          source: '10.1.35.34',
          destination: '24.113.10.15',
          dCity: '北京',
          sCity: '常州',
          status: 'Phishing',
          percent: 40,
          isProtected: false,
          isSeperated: false
        },
        {
          source: '125.1.1.34',
          destination: '157.10.1.04',
          dCity: '成都',
          sCity: '南宁',
          status: 'Identity Spoofing',
          percent: 32,
          isProtected: false,
          isSeperated: false
        },
        {
          source: '203.1.124.10',
          destination: '122.1.1.04',
          dCity: '南昌',
          sCity: '广州',
          status: 'Format String Injection',
          percent: 80,
          isProtected: false,
          isSeperated: false
        },
        {
          source: '37.4.1.65',
          destination: '101.2.5.07',
          dCity: '成都',
          sCity: '长春',
          status: 'URL Encoding',
          percent: 56,
          isProtected: false,
          isSeperated: false
        },
        {
          source: '109.4.32.24',
          destination: '24.15.11.74',
          dCity: '重庆',
          sCity: '成都',
          status: 'Forced Integer Overflow',
          percent: 55,
          isProtected: false,
          isSeperated: false
        }
      ]
    }
  },
  methods: {
    viewAttack (row) {
      this.$Modal.confirm({
        title: '攻击详情',
        render: (h, params) => {
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
            }, '疑似病毒种类 :  勒索病毒'),
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
            }, '攻击源地址 : ' + row.sCity),
            h('div', {
              style: {
                display: 'inline-block',
                width: '50%',
                marginTop: '10px'
              }
            }, '攻击目的地址 : ' + row.dCity),
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
                }, row.source)
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
                }, row.destination)
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
                  percent: row.percent,
                  status: 'active'
                }
              })
            ]),
            h('div', {
              style: {
                marginTop: '20px'
              }
            }, [
              h('div', '攻击进程'),
              h('div', [
                h('Tag', {
                  props: {
                    type: 'dot',
                    color: 'green'
                  }
                }, row.status)
              ])
            ])
          ])
        }
      })
    },
    protect (params) {
      this.$Modal.success({
        title: '防护',
        content: '受攻击IP成功加入防护名单'
      })
      this.data2[params.index].isProtected = true
    },
    addBlackList (params) {
      this.$Modal.success({
        title: '隔离主机',
        content: '成功隔离攻击源'
      })
      this.data2[params.index].isSeperated = true
    }
  },
  mounted: function () {
  }
}
</script>

<style scoped>
  .attack-list-card {
    width: 100%;
  }
</style>
