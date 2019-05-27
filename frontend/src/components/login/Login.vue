<template>
  <div class="login" @keydown.enter="handleSubmit">
    <div class="login-con">
       <Card :bordered="false">
        <p slot="title">
          <Icon type="log-in"></Icon>
          欢迎登录
        </p>
        <div class="form-con">
          <Form ref="loginForm" :model="form" :rules="rules">
            <FormItem prop="userName">
              <Input v-model="form.userName" placeholder="请输入用户名">
                <span slot="prepend">
                  <Icon :size="16" type="person"></Icon>
                </span>
              </Input>
            </FormItem>
            <FormItem prop="password">
              <Input type="password" v-model="form.password" placeholder="请输入密码">
                <span slot="prepend">
                  <Icon :size="14" type="locked"></Icon>
                </span>
              </Input>
            </FormItem>
            <FormItem>
              <Button @click="handleSubmit" type="primary" style="display: inline-block; width: 65%">登录</Button>
              <Button @click="toSign" type="ghost" style="display: inline-block; width: 30%; margin-left: 10px">注册</Button>
            </FormItem>
          </Form>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
// eslint-disable-next-line
export default {
  name: 'login',
  data () {
    return {
      apiPrefix: '',
      // regist: false,
      form: {
        userName: '',
        password: ''
      },
      rules: {
        userName: [
          { required: true, message: '账号不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.apiPrefix = this.$store.state.apiPrefix
  },
  methods: {
    handleSubmit () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          try {
            this.axios.get(this.apiPrefix + '/user/validate?account=' + this.form.userName + '&password=' + this.form.password)
              .then(result => {
                console.log(result)
                if (result.data.code === 200) {
                  this.$router.push('/home/attack-detector')
                } else {
                  this.$Message.error('账号或密码错误')
                }
              })
          } catch (err) {
            console.log(err)
            this.$Message.error('服务器出错')
          }
        }
      })
    },
    toSign () {
      this.$router.push('/register')
    }
  }
}
</script>

<style lang="less" scoped>
  @import "login.less";
</style>
