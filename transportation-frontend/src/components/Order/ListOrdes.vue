<template>

    <div style="width: 85%; justify-content: center; margin: 0 auto; margin-top: 5%;">
    
      <button v-on:click="getAllOrders()" type="button" class="btn btn-dark mr-2">Get All Orders</button>
      <button v-on:click="orders = []" type="button" class="btn btn-danger">Hide</button>
    
      <div class="alert alert-success" role="alert"
        v-if="isDeleted">
        <p>Заказ был успешно удалён</p>
        <hr>
        <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
      </div>

      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="validationServer01">Статус заказа</label>
          <select v-model="orderStatus" class="form-control" v-on:change="page = 1">
            <option selected>All</option>
            <option>CREATED</option>
            <option>COMPLETING</option>
            <option>READY_TO_SHIP</option>
            <option>SHIPPED</option>
            <option>DELIVERED</option>
          </select>
        </div>
        <div class="col-md-4 mb-3">
          <label for="validationServer02">Кол-во элементов</label>
          <br>
          <div class="custom-control custom-radio custom-control-inline">
            <input v-on:click="countItem = 2; page = 1" type="radio" id="customRadioInline1" name="customRadioInline1" class="custom-control-input">
            <label class="custom-control-label" for="customRadioInline1">2</label>
          </div>
          <div class="custom-control custom-radio custom-control-inline">
            <input v-on:click="countItem = 5; page = 1"  type="radio" id="customRadioInline2" name="customRadioInline1" class="custom-control-input" checked>
            <label class="custom-control-label" for="customRadioInline2">5</label>
          </div>
          <div class="custom-control custom-radio custom-control-inline">
            <input v-on:click="countItem = 15; page = 1" type="radio" id="customRadioInline3" name="customRadioInline1" class="custom-control-input">
            <label class="custom-control-label" for="customRadioInline3">15</label>
          </div>
          <div class="custom-control custom-radio custom-control-inline">
            <input v-on:click="countItem = 20; page = 1"  type="radio" id="customRadioInline4" name="customRadioInline1" class="custom-control-input">
            <label class="custom-control-label" for="customRadioInline4">20</label>
          </div>
        </div>
      </div>

      <div class="form-row">
        <div class="col-md-4 mb-3">
          <button v-for="x in countPage" v-bind:key="x" v-on:click="page = x" 
            :class="{'page_selected': x === page}" class="btn btn-info mr-2">{{ x }}</button>
        </div>
      </div>

      <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">
            <p class="sorted-icon" v-on:click="sortById()">
              #
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-up ml-2" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.5 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L11 2.707V14.5a.5.5 0 0 0 .5.5zm-7-14a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L4 13.293V1.5a.5.5 0 0 1 .5-.5z"/>
              </svg>
            </p>
          </th>
          <th scope="col">
            <p class="sorted-icon" v-on:click="sortByNumber()">
              Number
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-up ml-2" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.5 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L11 2.707V14.5a.5.5 0 0 0 .5.5zm-7-14a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L4 13.293V1.5a.5.5 0 0 1 .5-.5z"/>
              </svg>
            </p>
          </th>
          <th scope="col">
            <p class="sorted-icon" v-on:click="sortByFromLocation()">
              From Location
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-up ml-2" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.5 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L11 2.707V14.5a.5.5 0 0 0 .5.5zm-7-14a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L4 13.293V1.5a.5.5 0 0 1 .5-.5z"/>
              </svg>
            </p>
          </th>
          <th scope="col">
            <p class="sorted-icon" v-on:click="sortByToLocation()">
              To Location
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-up ml-2" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.5 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L11 2.707V14.5a.5.5 0 0 0 .5.5zm-7-14a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L4 13.293V1.5a.5.5 0 0 1 .5-.5z"/>
              </svg>
            </p>
          </th>
          <th scope="col">
            <p class="sorted-icon" v-on:click="sortByStatus()">
              Status
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-up ml-2" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.5 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L11 2.707V14.5a.5.5 0 0 0 .5.5zm-7-14a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L4 13.293V1.5a.5.5 0 0 1 .5-.5z"/>
              </svg>
            </p>
          </th>
          <th scope="col">
            <p class="sorted-icon" v-on:click="sortByCargos()">
              Cargos
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-up ml-2" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.5 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L11 2.707V14.5a.5.5 0 0 0 .5.5zm-7-14a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L4 13.293V1.5a.5.5 0 0 1 .5-.5z"/>
              </svg>
            </p>
          </th>
          <th scope="col">
            <p class="sorted-icon" v-on:click="sortByDeliveryMan()">
              Delivery man
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-up ml-2" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.5 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L11 2.707V14.5a.5.5 0 0 0 .5.5zm-7-14a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L4 13.293V1.5a.5.5 0 0 1 .5-.5z"/>
              </svg>
            </p>
          </th>
          <th scope="col">
            <p class="sorted-icon" v-on:click="sortByNote()">
              Note
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-up ml-2" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.5 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L11 2.707V14.5a.5.5 0 0 0 .5.5zm-7-14a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L4 13.293V1.5a.5.5 0 0 1 .5-.5z"/>
              </svg>
            </p>
          </th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in filteredOrders()" v-bind:key="order.id">
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

          <td>
            <button type="button" class="btn btn-danger" v-on:click="deleteOrder(order.id)">
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
      name: 'ListOrders',
    
      data() {
        return {
          orderStatus: null,
          countItem: 5,
          countPage: 1,
          page: 1,
          orders: [],
          filteredOrdersList: [],
          isDeleted: false
        }
      },
    
      methods: {

        filteredOrders() {
          this.countPage = Math.round(this.orders.length / this.countItem);

          const start = (this.page - 1) * this.countItem;
          const end = this.page * this.countItem;

          if (this.orderStatus && this.orderStatus != "All") {
            this.filteredOrdersList = this.orders.filter(order => order.status === this.orderStatus);
            const length = this.filteredOrdersList.length; 
            this.countPage = Math.round(length / this.countItem);

            return this.filteredOrdersList.slice(start, end);
          }
          else {
            return this.orders.slice(start, end);
          }
        },
    
        getAllOrders() {
            axios
            .get('http://localhost:8075/api/v1/order/getAllOrders')
            .then((response) => {
                this.orders = response.data;
            })
        },

        deleteOrder(id) {
          axios.delete('http://localhost:8075/api/v1/order/delete/' + id)
            .then(response => {
                this.isDeleted = response.data;
                this.getAllOrders();
            })
            .catch(error => {
                console.error(error);
            });

          setTimeout(() => {
              this.isDeleted = false;
          }, 5000);
          console.log(id);
        },

        sortById() {
          this.orders.sort((a, b) => b.id - a.id);
          this.filteredOrdersList.sort((a, b) => b.id - a.id);
        },

        sortByNumber() {
          this.orders.sort((a, b) => a.number - b.number);
          this.filteredOrdersList.sort((a, b) => a.number - b.number);
        },

        sortByFromLocation() {
          this.orders.sort((a, b) => a.fromLocation.localeCompare(b.fromLocation));
          this.filteredOrdersList.sort((a, b) => a.fromLocation.localeCompare(b.fromLocation));
        },

        sortByToLocation() {
          this.orders.sort((a, b) => a.toLocation.localeCompare(b.toLocation));
          this.filteredOrdersList.sort((a, b) => a.toLocation.localeCompare(b.toLocation));
        },

        sortByStatus() {
          this.orders.sort((a, b) => a.status.localeCompare(b.status));
          this.filteredOrdersList.sort((a, b) => a.status.localeCompare(b.status));
        },

        sortByCargos() {
          this.orders.sort((a, b) => a.cargos.length - b.cargos.length);
          this.filteredOrdersList.sort((a, b) => a.cargos.length - b.cargos.length);
        },

        sortByDeliveryMan() {
          this.orders.sort((a, b) => a.deliveryman.fio.localeCompare(b.deliveryman.fio));
          this.filteredOrdersList.sort((a, b) => a.deliveryman.fio.localeCompare(b.deliveryman.fio));
        },

        sortByNote() {
          this.orders.sort((a, b) => a.note.localeCompare(b.note));
          this.filteredOrdersList.sort((a, b) => a.note.localeCompare(b.note));
        },
    
      },

      created() {
        this.getAllOrders();
      }
    }
    </script>
    
    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style>
    .page_selected {
      width: 80px;
      box-shadow:
       inset 0 -3em 3em rgba(0,0,0,0.1),
             0 0  0 2px rgb(255,255,255),
             0.3em 0.3em 1em rgba(0,0,0,0.3);
    }

    .sorted-icon {
      display: flex; 
      justify-content: center; 
      align-items: center;
    }
    </style>
    