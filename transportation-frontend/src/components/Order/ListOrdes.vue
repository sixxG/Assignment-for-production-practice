<template>

    <div style="width: 90%; justify-content: center; margin: 0 auto; margin-top: 5%;">
    
      <button v-on:click="getAllOrders()" type="button" class="btn btn-dark mr-2">Get All Orders</button>
      <button v-on:click="AllOrdersList = []" type="button" class="btn btn-danger">Hide</button>
    
      <div class="alert alert-success" role="alert"
        v-if="isDeleted">
        <p>Заказ был успешно удалён</p>
        <hr>
        <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
      </div>

      <div class="alert alert-success" role="alert"
        v-if="isCompleted">
        <p>Заказ был успешно завершён</p>
        <hr>
        <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
      </div>

      <div class="form-row">
        <div class="col-md-4 mb-3">
          <label for="orderStatus">Статус заказа</label>
          <select v-model="orderStatus" id="orderStatus" class="form-control" v-on:change="page = 1">
            <option selected>All</option>
            <option>Создан</option>
            <option>Комплектуется</option>
            <option>Готов к отправке</option>
            <option>Отправлен</option>
            <option>Доставлен</option>
          </select>
        </div>
        <div class="col-md-3 mb-3">
          <label style="margin-bottom: 15px;">Кол-во элементов</label>
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

        <div class="col-md-4 mb-3">
          <button v-on:click="morePointForSearch = !morePointForSearch" type="button" class="btn btn-info" style="margin-top: 31px;">
            Расширенный поиск
          </button>
        </div>
      </div>

      <div v-if="morePointForSearch">
        <div class="form-row">
          <div class="form-group col-md-6">
            <label for="fromLocation">From location</label>
            <input type="text" v-model="fromLocation" class="form-control" id="fromLocation" placeholder="Москва">
          </div>
          <div class="form-group col-md-6">
            <label for="toLocation">To Location</label>
            <input type="text" v-model="toLocation" class="form-control" id="toLocation" placeholder="Владивосток">
          </div>
        </div>
        <div class="form-row">
          <div class="form-group col-md-6">
            <label for="deliveryman">Deliveryman</label>
            <input type="text" v-model="deliveryman" class="form-control" id="deliveryman" placeholder="Иванов Иван Иванович">
          </div>
          <div class="form-group col-md-6">
            <label for="note">Note</label>
            <input type="text" v-model="note" class="form-control" id="note" placeholder="Примечания">
          </div>
        </div>
        <div class="form-row">
          <div class="form-group col-md-4">
            <label for="cargo">Cargo</label>
            <input type="text" v-model="cargo" class="form-control" id="cargo" placeholder="Стол, стул, или дверь">
          </div>
          <div class="form-group col-md-2">
            <label for="number">Number</label>
            <input type="text" v-model="number" class="form-control" id="number" placeholder="1234">
          </div>
          <div class="form-group col-md-2">
            <button v-on:click="clearFormSearch()" class="btn btn-success form-control" id="search" style="margin-top: 31px;">Сбросить</button>
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
            <button type="button" class="btn btn-danger" v-on:click="deleteOrder(order.id)" style="margin-bottom: 5px;">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
              </svg>
            </button>
            <div v-if="order.status == 'Отправлен'">
              <button type="button" v-on:click="completeOrder(order.id)" class="btn btn-success" data-toggle="tooltip" data-html="true" title="Завершить доставку">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-clipboard2-check-fill" viewBox="0 0 16 16">
                  <path d="M10 .5a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5.5.5 0 0 1-.5.5.5.5 0 0 0-.5.5V2a.5.5 0 0 0 .5.5h5A.5.5 0 0 0 11 2v-.5a.5.5 0 0 0-.5-.5.5.5 0 0 1-.5-.5Z"/>
                  <path d="M4.085 1H3.5A1.5 1.5 0 0 0 2 2.5v12A1.5 1.5 0 0 0 3.5 16h9a1.5 1.5 0 0 0 1.5-1.5v-12A1.5 1.5 0 0 0 12.5 1h-.585c.055.156.085.325.085.5V2a1.5 1.5 0 0 1-1.5 1.5h-5A1.5 1.5 0 0 1 4 2v-.5c0-.175.03-.344.085-.5Zm6.769 6.854-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7.5 9.793l2.646-2.647a.5.5 0 0 1 .708.708Z"/>
                </svg>
              </button>
            </div>
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
          AllOrdersList: [],
          filteredOrdersList: [],
          isDeleted: false,
          isCompleted: false,

          morePointForSearch: false,
          fromLocation: null,
          toLocation: null,
          deliveryman: null,
          note: null,
          cargo: null,
          number: null,
        }
      },
    
      methods: {

        filteredOrders() {
          this.countPage = Math.round(this.AllOrdersList.length / this.countItem);

          const start = (this.page - 1) * this.countItem;
          const end = this.page * this.countItem;

          this.filteredOrdersList = this.AllOrdersList;

          if (this.orderStatus && this.orderStatus != "All") {
            this.filteredOrdersList = this.filteredOrdersList.filter(order => order.status === this.orderStatus);
            const length = this.filteredOrdersList.length; 
            this.countPage = Math.round(length / this.countItem);
          }
          if (this.fromLocation) {
            this.filteredOrdersList = this.filteredOrdersList.filter(order => order.fromLocation.includes(this.fromLocation));
          }
          if (this.toLocation) {
            this.filteredOrdersList = this.filteredOrdersList.filter(order => order.toLocation.includes(this.toLocation));
          }
          if (this.deliveryman) {
            this.filteredOrdersList = this.filteredOrdersList.filter(order => order.deliveryman.fio.toString().includes(this.deliveryman));
          }
          if (this.cargo) {
            this.filteredOrdersList = this.filteredOrdersList.filter(order =>
              order.cargos.some(cargo => cargo.name.includes(this.cargo))
            );
          }
          if (this.note) {
            this.filteredOrdersList = this.filteredOrdersList.filter(order => order.note.includes(this.note));
          }
          if (this.number) {
            this.filteredOrdersList = this.filteredOrdersList.filter(order => order.number.toString().includes(this.number.toString()));
          }
          return this.filteredOrdersList.slice(start, end);
        },

        clearFormSearch() {
          this.fromLocation = null,
          this.toLocation = null,
          this.deliveryman = null,
          this.note = null,
          this.cargo = null,
          this.number = null
        },
    
        getAllOrders() {
            axios
            .get('http://localhost:8075/api/v1/order/getAllOrders')
            .then((response) => {
                this.AllOrdersList = response.data;
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

        completeOrder(id) {
          axios.post('http://localhost:8075/api/v1/order/complete/' + id)
            .then(response => {
                this.isCompleted = response.data;
                this.getAllOrders();
            })
            .catch(error => {
                console.error(error);
            });

          setTimeout(() => {
              this.isCompleted = false;
          }, 5000);
        },

        sortById() {
          this.AllOrdersList.sort((a, b) => b.id - a.id);
          this.filteredOrdersList.sort((a, b) => b.id - a.id);
        },

        sortByNumber() {
          this.AllOrdersList.sort((a, b) => a.number - b.number);
          this.filteredOrdersList.sort((a, b) => a.number - b.number);
        },

        sortByFromLocation() {
          this.AllOrdersList.sort((a, b) => a.fromLocation.localeCompare(b.fromLocation));
          this.filteredOrdersList.sort((a, b) => a.fromLocation.localeCompare(b.fromLocation));
        },

        sortByToLocation() {
          this.AllOrdersList.sort((a, b) => a.toLocation.localeCompare(b.toLocation));
          this.filteredOrdersList.sort((a, b) => a.toLocation.localeCompare(b.toLocation));
        },

        sortByStatus() {
          this.AllOrdersList.sort((a, b) => a.status.localeCompare(b.status));
          this.filteredOrdersList.sort((a, b) => a.status.localeCompare(b.status));
        },

        sortByCargos() {
          this.AllOrdersList.sort((a, b) => a.cargos.length - b.cargos.length);
          this.filteredOrdersList.sort((a, b) => a.cargos.length - b.cargos.length);
        },

        sortByDeliveryMan() {
          this.AllOrdersList.sort((a, b) => a.deliveryman.fio.localeCompare(b.deliveryman.fio));
          this.filteredOrdersList.sort((a, b) => a.deliveryman.fio.localeCompare(b.deliveryman.fio));
        },

        sortByNote() {
          this.AllOrdersList.sort((a, b) => a.note.localeCompare(b.note));
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
    