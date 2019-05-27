<template>
  <div class="content-wrapper">
    <DetectorCard/>
    <!--<WarningCard/>-->
    <ListCard/>
    <!--<StepCard/>-->
  </div>
</template>

<script>
import DetectorCard from './detector-card/DetectorCard'
import ListCard from './list-card/ListCard'
import StepCard from './step-card/StepCard'
import WarningCard from './warning-card/WarningCard'
export default {
  name: 'attack-overview',
  data () {
    return {
    }
  },
  components: {
    DetectorCard,
    ListCard,
    StepCard,
    WarningCard
  }
}
</script>

<style scoped>
  .content-wrapper {
    width: 100%;
  }
</style>
