<template>
  <div class="login" @keydown.enter="handleSubmit">
    <div class="login-con">
      <Card :bordered="false">
        <p slot="title">
          <Icon type="log-in"></Icon>
          欢迎加入我们
        </p>
        <div class="form-con">
          <Form ref="loginForm" :model="form" :rules="rules">
            <FormItem prop="userName">
              <Input v-model="form.userName" placeholder="请输入邮箱">
              <span slot="prepend">
              <Icon :size="16" type="email"></Icon>
            </span>
              </Input>
            </FormItem>
            <FormItem prop="checkCode">
              <Input v-model="form.checkCode" placeholder="验证码" style="display: inline-block; width: 65%">
                <!--<span slot="prepend">-->
                  <!--<Icon :size="16" type="checkmark-circled"></Icon>-->
                <!--</span>-->
              </Input>
              <Button v-if="!isSending" type="primary" @click="sendCode" style="display: inline-block; width: 30%; margin-left: 10px">获取</Button>
              <Button v-if="isSending" disabled style="display: inline-block; width: 30%; margin-left: 10px">{{wait}} S</Button>
            </FormItem>
            <FormItem prop="password">
              <Input type="password" v-model="form.password" placeholder="请输入密码">
              <span slot="prepend">
              <Icon :size="14" type="locked"></Icon>
            </span>
              </Input>
            </FormItem>
            <FormItem prop="checkPasswd">
              <Input type="password" v-model="form.checkPasswd" placeholder="请确认密码">
              <span slot="prepend">
              <Icon :size="14" type="locked"></Icon>
            </span>
              </Input>
            </FormItem>
            <FormItem>
              <Button @click="handleSubmit" type="primary" style="width: 65%; display: inline-block;">注册</Button>
              <Button @click="toLogin" type="ghost" style="width: 30%; display: inline-block; margin-left: 10px">返回登陆</Button>
            </FormItem>
          </Form>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
function getAuthCode (that) {
  that.isSending = true
  that.wait = 60
  var authTimer = setInterval(() => {
    that.wait--
    if (that.wait <= 0) {
      that.isSending = false
      clearInterval(authTimer)
    }
  }, 1000)
}
export default {
  name: 'register',
  data () {
    const validatePasswd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('密码不能为空'))
      } else {
        if (this.form.checkPasswd !== '') {
          this.$refs.loginForm.validateField('checkPasswd')
        }
        callback()
      }
    }
    const validateCheckPasswd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }
    return {
      apiPreifx: '',
      isSending: false,
      wait: 0,
      form: {
        userName: '',
        checkCode: '',
        password: '',
        checkPasswd: ''
      },
      rules: {
        userName: [
          { required: true, message: '账号不能为空', trigger: 'blur' }
        ],
        password: [
          { validator: validatePasswd, trigger: 'blur' }
        ],
        checkCode: [
          {required: true, message: '验证码不能为空', trigger: 'blur'}
        ],
        checkPasswd: [
          {validator: validateCheckPasswd, trigger: 'blur'}
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
            this.axios.get(this.apiPrefix + '/user/regis?account=' + this.form.userName + '&password=' + this.form.password + '&checkCode=' + this.form.checkCode)
              .then((result) => {
                console.log(result.data)
                if (result.data.code === 200) {
                  this.$Message.success('注册成功')
                  this.$router.replace('/')
                } else {
                  this.$Message.error(result.data.desc)
                }
              })
          } catch (err) {
            console.log(err)
            this.$Message.error('服务器出错')
          }
        }
      })
    },
    toLogin () {
      this.$router.replace('/')
    },
    sendCode () {
      this.$refs.loginForm.validateField('userName', (callback) => {
        if (callback !== '账号不能为空') {
          try {
            this.axios.get(this.apiPrefix + '/user/sendMailCode?account=' + this.form.userName)
              .then(result => {
                console.log(result)
                if (result.data.code === 200) {
                  this.$Message.success('发送成功')
                  getAuthCode(this)
                } else {
                  this.$Message.error(result.data.desc)
                }
              })
          } catch (err) {
            console.log(err)
            this.$Message.error('服务器出错')
          }
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
  @import "login.less";
</style>
