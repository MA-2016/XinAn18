<template>
  <div>
    <TypeCard/>
    <ResultCard/>
    <DetailCard/>
    <NumCard/>
  </div>
</template>

<script>
import DetailCard from './detail-card/DetailCard'
import NumCard from './number-card/NumberCard'
import ResultCard from './result-card/ResultCard'
import TypeCard from './type-card/TypeCard'
export default {
  name: 'overview',
  data () {
    return {}
  },
  components: {
    DetailCard,
    NumCard,
    ResultCard,
    TypeCard
  }
}

</script>

<style scoped>
</style>
