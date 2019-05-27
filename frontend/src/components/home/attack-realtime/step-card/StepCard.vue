<template>
  <div class="step-card">
    <Card>
      <p slot="title">
        <Icon type="android-share-alt" size="15"></Icon>
        攻击流程检测
      </p>
      <div class="step-wrapper">
        <Steps :current="1">
          <Step title="攻击端口" content="攻击445端口"></Step>
          <Step title="HTTP请求" content="调用加密算法库，进行HTTP请求"></Step>
          <Step title="下载文件" content="通过脚本文件下载文件"></Step>
          <Step title="读取服务器文件" content="读取远程服务器文件"></Step>
          <Step title="执行文件" content="通过wscript执行文件"></Step>
          <Step title="收集信息" content="收集计算机信息"></Step>
          <Step title="篡改文件" content="遍历文件，修改信息"></Step>
        </Steps>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
  name: 'step',
  data () {
    return {}
  }
}
</script>

<style scoped>
  .step-card {
    float: left;
    width: 69%;
    margin-right: 1%;
  }

  .step-wrapper {
    height: 150px;
    display: flex;
    display: -webkit-flex;
    align-items: center;
  }
</style>
