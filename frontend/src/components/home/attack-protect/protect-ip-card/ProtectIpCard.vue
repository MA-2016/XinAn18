<template>
  <div>
    <Card style="width: 49%; margin-right: 1%; float: left">
      <p slot="title">隔离保护IP</p>
      <Table stripe :data="tableData" :columns="protectColumns" height="550"></Table>
      <div style="margin: 10px;overflow: hidden">
        <div style="float: right;">
          <Page :total="protectIpTotal" @on-change="changeProtectPage"></Page>
        </div>
      </div>
    </Card>
    <Card style="width: 49%; margin-right: 1%; float: left">
      <p slot="title">黑名单</p>
      <Table stripe :data="blackData" :columns="blackColumns" height="550"></Table>
      <div style="margin: 10px;overflow: hidden">
        <div style="float: right;">
          <Page :total="blackIpTotal" @on-change="changeBlackIpPage"></Page>
        </div>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
  name: 'protect-ip',
  data () {
    return {
      apiPrefix: '',
      protectIpTotal: 0,
      blackIpTotal: 0,
      protectColumns: [
        {
          title: '序号',
          key: 'index',
          type: 'index',
          width: 80
        },
        {
          title: 'IP',
          key: 'ip',
          render: (h, params) => {
            return h('Tag', {
              props: {
                type: 'dot',
                color: 'green'
              }
            }, params.row.ip)
          }
        },
        {
          title: '操作',
          key: 'operation',
          render: (h, params) => {
            return h('Button', {
              props: {
                type: 'error'
                // size: 'small'
              },
              on: {
                click: () => {
                  this.deleteProtectIp(params)
                }
              }
            }, '删除')
          }
        }
      ],
      tableData: [],
      blackColumns: [
        {
          title: '序号',
          key: 'index',
          type: 'index',
          width: 80
        },
        {
          title: 'IP',
          key: 'ip',
          render: (h, params) => {
            return h('Tag', {
              props: {
                type: 'dot',
                color: 'red'
              }
            }, params.row.ip)
          }
        },
        {
          title: '操作',
          key: 'operation',
          render: (h, params) => {
            return h('Button', {
              props: {
                type: 'primary'
                // size: 'small'
              },
              on: {
                click: () => {
                  this.deleteBlackIp(params)
                }
              }
            }, '删除')
          }
        }
      ],
      blackData: []
    }
  },
  methods: {
    getProtectIp () {
      this.axios.get(this.apiPrefix + '/ip/getProtectedIp?pageNum=1&pageSize=10')
        .then(result => {
          console.log(result)
          let list = result.data.list
          for (let i = 0; i < list.length; i++) {
            this.tableData.push({ip: list[i]})
          }
          this.protectIpTotal = result.data.total
        })
    },
    getBlackIp () {
      this.axios.get(this.apiPrefix + '/ip/getBlackIp?pageNum=1&pageSize=10')
        .then(result => {
          let list = result.data.list
          for (let i = 0; i < list.length; i++) {
            this.blackData.push({ip: list[i]})
          }
          this.blackIpTotal = result.data.total
        })
    },
    changeProtectPage (pageNum) {
      this.tableData = []
      this.axios.get(this.apiPrefix + '/ip/getProtectedIp?pageNum=' + pageNum + '&pageSize=10')
        .then(result => {
          let list = result.data.list
          for (let i = 0; i < list.length; i++) {
            this.tableData.push({ip: list[i]})
          }
        })
    },
    changeBlackIpPage (pageNum) {
      this.blackData = []
      this.axios.get(this.apiPrefix + '/ip/getBlackIp?pageNum=' + pageNum + '&pageSize=10')
        .then(result => {
          let list = result.data.list
          for (let i = 0; i < list.length; i++) {
            this.blackData.push({ip: list[i]})
          }
        })
    },
    deleteProtectIp (params) {
      console.log(params)
      this.$Modal.confirm({
        title: '删除',
        onOk: () => {
          this.axios.get(this.apiPrefix + '/ip/delProtectedIp?ip=' + params.row.ip).then(result => {
            console.log(result)
            if (result.data.code === 200) {
              this.tableData.splice(params.index, 1)
              this.$Message.success('删除成功')
            } else {
              this.$Message.error('删除失败')
            }
          })
        },
        content: '是否确认删除保护IP ' + params.row.ip
      })
    }, /* ****************************************************** */
    deleteBlackIp (params) {
      this.$Modal.confirm({
        title: '删除',
        onOk: () => {
          this.axios.get(this.apiPrefix + '/ip/delBlackIp?ip=' + params.row.ip).then(result => {
            if (result.data.code === 200) {
              this.blackData.splice(params.index, 1)
              this.$Message.success('删除成功')
            } else {
              this.$Message.error('删除失败')
            }
          })
        },
        content: '是否确认将IP ' + params.row.ip + ' 移出黑名单'
      })
    }
  },
  mounted () {
    this.apiPrefix = this.$store.state.apiPrefix
    this.getProtectIp()
    this.getBlackIp()
  }
}
</script>

<style scoped>
</style>
