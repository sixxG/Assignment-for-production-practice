<template>

    <div style="width: 70%; justify-content: center; margin: 0 auto; margin-top: 5%;">
    
      <button v-on:click="getAllOrders()" type="button" class="btn btn-dark">Get All Orders</button>
      <button v-on:click="orders = []" type="button" class="btn btn-danger">Hide</button>
    
      <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Number</th>
          <th scope="col">From Location</th>
          <th scope="col">To Location</th>
          <th scope="col">Status</th>
          <th scope="col">Cargos</th>
          <th scope="col">Delivery man</th>
          <th scope="col">Note</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" v-bind:key="order.id">
          <th scope="row">{{ order.id }}</th>
          <td>{{ order.number }}</td>
          <td>{{ order.fromLocation }}</td>
          <td>{{ order.toLocation }}</td>
          <td>{{ order.status }}</td>
          
          <td>
            <div v-for="cargo in order.cargos" v-bind:key="cargo.id">
                {{ cargo.name }}<br>
                Count: {{ cargo.count }}шт<br>
                Price: {{ cargo.price }}<br>
                Result: {{ cargo.price * cargo.count }}₽ <br>
                <hr v-if="order.cargos.length > 1">
            </div>
          </td>
          
          <td>{{ order.deliveryman.fio }}</td>
          <td>{{ order.note }}</td>
        </tr>
      </tbody>
    </table>
    </div>
    
    </template>
    
    <script>
    import axios from "axios"
    
    export default {
      name: 'ListOrders',
    
      data() {
        return {
          orders: [],
        }
      },
    
      methods: {
    
        getAllOrders() {
          axios
            .get('http://localhost:8075/api/v1/order/getAllOrders')
            .then((response) => {
              this.orders = response.data;
              console.log(this.orders);
            })
        }
    
      }
    
    }
    </script>
    
    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style>
    
    </style>
    