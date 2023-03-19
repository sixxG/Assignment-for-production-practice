<template>

<div style="width: 70%; justify-content: center; margin: 0 auto; margin-top: 5%;">

  <button v-on:click="getAllDeliverymans()" type="button" class="btn btn-dark mr-2">Get All Deliverymans</button>
  <button v-on:click="deliverymans = []" type="button" class="btn btn-danger">Hide</button>

  <div class="alert alert-success" role="alert"
    v-if="isDeleted">
    <p>Доставщик был успешно удалён</p>
    <hr>
    <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
  </div>

  <table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">FIO</th>
      <th scope="col">Phone</th>
      <th scope="col">Vehicle</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="deliveryman in deliverymans" v-bind:key="deliveryman.id">
      <th scope="row">{{ deliveryman.id }}</th>
      <td>{{ deliveryman.fio }}</td>
      <td>{{ deliveryman.phone }}</td>
      <td>{{ deliveryman.vehicle }}</td>
      <td>

        <button type="button" class="btn btn-danger" v-on:click="deleteDeliveryman(deliveryman.id)">
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
  name: 'ListDeliveryman',

  data() {
    return {
      deliverymans: [],
      isDeleted: false
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
    },

    deleteDeliveryman(id) {
      console.log(id);
          axios.delete('http://localhost:8075/api/v1/deliveryman/delete/' + id)
            .then(response => {
                this.isDeleted = response.data;
                this.getAllDeliverymans();
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
