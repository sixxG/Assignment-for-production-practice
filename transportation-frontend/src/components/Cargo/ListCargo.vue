<template>

    <div style="width: 70%; justify-content: center; margin: 0 auto; margin-top: 5%;">
    
      <button v-on:click="getAllCargo()" type="button" class="btn btn-dark mr-2">Get All Cargos</button>
      <button v-on:click="cargos = []" type="button" class="btn btn-danger">Hide</button>
    
      <div class="alert alert-success" role="alert"
        v-if="isDeleted">
        <p>Товар был успешно удалён</p>
        <hr>
        <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
      </div>

      <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">Price</th>
          <th scope="col">Count</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="cargo in cargos" v-bind:key="cargo.id">
          <th scope="row">{{ cargo.id }}</th>
          <td>{{ cargo.name }}</td>
          <td>{{ cargo.price }}</td>
          <td>{{ cargo.count }}</td>
          <td>

            <button type="button" class="btn btn-danger" v-on:click="deleteCargo(cargo.id)">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
              </svg>
            </button>

          </td>
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
          isDeleted: false,
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
        },

        deleteCargo(id) {
          axios.delete('http://localhost:8075/api/v1/cargo/delete/' + id)
            .then(response => {
                this.isDeleted = response.data;
                this.getAllCargo();
            })
            .catch(error => {
                console.error(error);
            });

          setTimeout(() => {
              this.isDeleted = false;
          }, 5000);
        }
    
      }
    
    }
    </script>
    
    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style>

    </style>
    