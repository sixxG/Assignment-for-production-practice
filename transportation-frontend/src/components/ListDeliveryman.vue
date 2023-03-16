<template>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<div style="width: 70%; justify-content: center; margin: 0 auto; margin-top: 5%;">

  <button v-on:click="getAllDeliverymans()" type="button" class="btn btn-dark">Get All Deliverymans</button>
  <button v-on:click="deliverymans = []" type="button" class="btn btn-danger">Hide</button>

  <table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">FIO</th>
      <th scope="col">Phone</th>
      <th scope="col">Vehicle</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="deliveryman in deliverymans" v-bind:key="deliveryman.id">
      <th scope="row">{{ deliveryman.id }}</th>
      <td>{{ deliveryman.fio }}</td>
      <td>{{ deliveryman.phone }}</td>
      <td>{{ deliveryman.vehicle }}</td>
    </tr>
  </tbody>
</table>
</div>

</template>

<script>
import axios from "axios"

export default {
  name: 'ListDeliveryman',

  data() {
    return {
      deliverymans: [],
    }
  },

  methods: {

    getAllDeliverymans() {
      axios
        .get('http://localhost:8075/api/v1/deliveryman/getAllDeliverymans')
        .then((response) => {
          this.deliverymans = response.data;
          console.log(this.deliverymans);
        })
    }

  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

</style>
