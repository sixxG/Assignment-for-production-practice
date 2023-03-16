<template>
  <ListDeliveryman/>
  <hr>
  <ListOrdes/>
  <hr>
  <ListCargo/>
</template>

<script>
import ListDeliveryman from './components/ListDeliveryman.vue'
import ListOrdes from './components/ListOrdes.vue';
import ListCargo from './components/ListCargo.vue';

export default {
  name: 'App',
  components: {
    ListDeliveryman,
    ListOrdes,
    ListCargo
  }
}
</script>

<style>

</style>
