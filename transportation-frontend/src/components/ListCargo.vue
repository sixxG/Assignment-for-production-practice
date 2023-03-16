<template>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    
    <div style="width: 70%; justify-content: center; margin: 0 auto; margin-top: 5%;">
    
      <button v-on:click="getAllCargo()" type="button" class="btn btn-dark">Get All Cargos</button>
      <button v-on:click="cargos = []" type="button" class="btn btn-danger">Hide</button>
    
      <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">Price</th>
          <th scope="col">Count</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="cargo in cargos" v-bind:key="cargo.id">
          <th scope="row">{{ cargo.id }}</th>
          <td>{{ cargo.name }}</td>
          <td>{{ cargo.price }}</td>
          <td>{{ cargo.count }}</td>
        </tr>
      </tbody>
    </table>
    </div>
    
    </template>
    
    <script>
    import axios from "axios"
    
    export default {
      name: 'ListCargo',
    
      data() {
        return {
          cargos: [],
        }
      },
    
      methods: {
    
        getAllCargo() {
          axios
            .get('http://localhost:8075/api/v1/cargo/getAllCargos')
            .then((response) => {
              this.cargos = response.data;
              console.log(this.cargos);
            })
        }
    
      }
    
    }
    </script>
    
    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style>
    
    </style>
    