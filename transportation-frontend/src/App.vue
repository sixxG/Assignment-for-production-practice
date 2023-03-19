<template>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

  <ListDeliveryman/>
  <AddDeliveryman/>
  <hr>
  <ListOrdes/>
  <AddOrders/>
  <hr>
  <ListCargo/>
  <AddCargo/>
  <hr>
</template>

<script>
import ListDeliveryman from './components/Deliveryman/ListDeliveryman.vue'
import AddDeliveryman from './components/Deliveryman/AddDeliveryman.vue';

import ListCargo from './components/Cargo/ListCargo.vue';
import AddCargo from './components/Cargo/AddCargo.vue';

import ListOrdes from './components/Order/ListOrdes.vue';
import AddOrders from './components/Order/AddOrders.vue';

export default {
  name: 'App',
  components: {
    ListDeliveryman,
    ListOrdes,
    ListCargo,
    AddCargo,
    AddDeliveryman,
    AddOrders
  }
}

</script>

<style>

</style>
