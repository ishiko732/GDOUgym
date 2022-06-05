<template>
<div>
  场地安排
</div>
</template>

<script>
export default {
  name: "siteArrange",
  data(){
    return{

    }
  },
  created() {
    console.log(this.$route.query.id)
  }
}
</script>

<style scoped>

</style>