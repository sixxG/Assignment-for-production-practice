<template>

  <div style="width: 90%; justify-content: center; margin: 0 auto;">
  
    <button v-on:click="getAllOrders()" type="button" class="btn btn-dark mr-2">Get All Orders</button>
    <button v-on:click="AllOrdersList = []" type="button" class="btn btn-danger">Hide</button>
  
    <!-- CRUD info message -->
    <div class="alert alert-success" role="alert"
      v-if="isDeleted || isCompleted || isUpdate">
      <p v-if="isDeleted">Заказ был успешно удалён</p>
      <p v-if="isCompleted">Заказ был успешно завершён</p>
      <p v-if="isUpdate">Заказ был успешно обновлён</p>
      <hr>
      <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
    </div>

    <div class="form-row">
      <!-- Order Status -->
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

      <!-- Count item on page -->
      <div class="col-md-3 mb-3">
        <label style="margin-bottom: 15px;">Кол-во элементов</label>
        <br>
        <div class="custom-control custom-radio custom-control-inline">
          <input v-on:click="countItem = 1; page = 1" type="radio" id="customRadioInline0" name="customRadioInline0"
            class="custom-control-input" v-bind:checked="countItem == 1">
          <label class="custom-control-label" for="customRadioInline0">1</label>
        </div>

        <div class="custom-control custom-radio custom-control-inline">
          <input v-on:click="countItem = 2; page = 1" type="radio" id="customRadioInline1" name="customRadioInline1"
            class="custom-control-input" v-bind:checked="countItem == 2">
          <label class="custom-control-label" for="customRadioInline1">2</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
          <input v-on:click="countItem = 5; page = 1"  type="radio" id="customRadioInline2" name="customRadioInline2" 
            class="custom-control-input" v-bind:checked="countItem == 5">
          <label class="custom-control-label" for="customRadioInline2">5</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
          <input v-on:click="countItem = 15; page = 1" type="radio" id="customRadioInline3" name="customRadioInline3" 
            class="custom-control-input" v-bind:checked="countItem == 15">
          <label class="custom-control-label" for="customRadioInline3">15</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
          <input v-on:click="countItem = 20; page = 1"  type="radio" id="customRadioInline4" name="customRadioInline4" 
            class="custom-control-input" v-bind:checked="countItem == 20">
          <label class="custom-control-label" for="customRadioInline4">20</label>
        </div>
      </div>

      <!-- Best Search button on-->
      <div class="col-md-4 mb-3">
        <button v-on:click="morePointForSearch = !morePointForSearch" type="button" class="btn btn-info" style="margin-top: 31px;">
          Расширенный поиск
        </button>
      </div>
    </div>

    <!-- Best Search form -->
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

    <!-- Pagination -->
    <div class="form-row" style="width: 100%; display: block;">
      <div class="col mb-3">
        <button v-for="x in pages()" v-bind:key="x" 
          v-on:click="page = x" 
          :class="{'page_selected': x == page}"
          :disabled="x === '...'" class="btn btn-info mr-2">{{ x }}</button>
      </div>
    </div>

    <table class="table table-striped">
    
    <!-- Order field name -->
    <thead>
      <tr>

        <th scope="col">
          <div style="display: flex; align-items: center;">
            #
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortById = !ifSortById"
                :class="{'sort_selected': ifSortById}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortById = !ifSortById"
                :class="{'sort_selected': !ifSortById}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </div>
        </th>

        <th scope="col">
          <div style="display: flex; align-items: center;">
            Number
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByNumber = !ifSortByNumber"
                :class="{'sort_selected': ifSortByNumber}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByNumber = !ifSortByNumber"
                :class="{'sort_selected': !ifSortByNumber}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </div>
        </th>

        <th scope="col">
          <div style="display: flex; align-items: center;">
            From Location
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByFromLocation = !ifSortByFromLocation"
                :class="{'sort_selected': ifSortByFromLocation}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByFromLocation = !ifSortByFromLocation"
                :class="{'sort_selected': !ifSortByFromLocation}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </div>
        </th>

        <th scope="col">
          <div style="display: flex; align-items: center;">
            To Location
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByToLocation = !ifSortByToLocation"
                :class="{'sort_selected': ifSortByToLocation}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByToLocation = !ifSortByToLocation"
                :class="{'sort_selected': !ifSortByToLocation}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </div>
        </th>

        <th scope="col">
          <div style="display: flex; align-items: center;">
            Status
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByStatus = !ifSortByStatus"
                :class="{'sort_selected': ifSortByStatus}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByStatus = !ifSortByStatus"
                :class="{'sort_selected': !ifSortByStatus}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </div>
        </th>

        <th scope="col">
          <div style="display: flex; align-items: center;">
            Cargos
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByCargos = !ifSortByCargos"
                :class="{'sort_selected': ifSortByCargos}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByCargos = !ifSortByCargos"
                :class="{'sort_selected': !ifSortByCargos}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </div>
        </th>

        <th scope="col">
          <div style="display: flex; align-items: center;">
            Deliveryman
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByDeliveryMan = !ifSortByDeliveryMan"
                :class="{'sort_selected': ifSortByDeliveryMan}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByDeliveryMan = !ifSortByDeliveryMan"
                :class="{'sort_selected': !ifSortByDeliveryMan}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </div>
        </th>

        <th scope="col">
          <div style="display: flex; align-items: center;">
            Note
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByNote = !ifSortByNote"
                :class="{'sort_selected': ifSortByNote}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="ifSortByNote = !ifSortByNote"
                :class="{'sort_selected': !ifSortByNote}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </div>
        </th>

        <th></th>

      </tr>
    </thead>

    <!-- List orders -->
    <tbody>
      <tr v-for="order in filteredOrders()" v-bind:key="order.id">
        <th scope="row">{{ order.id }}</th>
        <td>{{ order.number }}</td>
        <td>{{ order.fromLocation }}</td>
        <td>{{ order.toLocation }}</td>
        <td>
          <select class="form-control" v-model="order.status" v-on:change="idUpdate = order.id">
            <option selected>{{ order.status }}</option>
            <option v-for="status in orderStatusWithoutSelected(order.status)" v-bind:key="status">{{ status }}</option>
          </select>
        </td>

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
        <td>
          <textarea class="form-control" v-model="order.note" v-on:change="idUpdate = order.id"></textarea>
        </td>

        <td>
          <button type="button" class="btn btn-danger" v-on:click="deleteOrder(order.id)" style="margin-bottom: 5px;">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
              <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
            </svg>
          </button>

          <div v-if="order.status == 'Отправлен' && order.id !== idUpdate">
            <button type="button" v-on:click="completeOrder(order.id)" class="btn btn-success" style="margin-bottom: 5px;" data-toggle="tooltip" data-html="true" title="Завершить доставку">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-clipboard2-check-fill" viewBox="0 0 16 16">
                <path d="M10 .5a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5.5.5 0 0 1-.5.5.5.5 0 0 0-.5.5V2a.5.5 0 0 0 .5.5h5A.5.5 0 0 0 11 2v-.5a.5.5 0 0 0-.5-.5.5.5 0 0 1-.5-.5Z"/>
                <path d="M4.085 1H3.5A1.5 1.5 0 0 0 2 2.5v12A1.5 1.5 0 0 0 3.5 16h9a1.5 1.5 0 0 0 1.5-1.5v-12A1.5 1.5 0 0 0 12.5 1h-.585c.055.156.085.325.085.5V2a1.5 1.5 0 0 1-1.5 1.5h-5A1.5 1.5 0 0 1 4 2v-.5c0-.175.03-.344.085-.5Zm6.769 6.854-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7.5 9.793l2.646-2.647a.5.5 0 0 1 .708.708Z"/>
              </svg>
            </button>
          </div>

          <div v-if="idUpdate == order.id">
            <button type="button" v-on:click="updateOrder(order.id)" class="btn btn-success" data-toggle="tooltip" data-html="true" title="Сохранить изменения">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-save" viewBox="0 0 16 16">
                <path d="M2 1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H9.5a1 1 0 0 0-1 1v7.293l2.646-2.647a.5.5 0 0 1 .708.708l-3.5 3.5a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L7.5 9.293V2a2 2 0 0 1 2-2H14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h2.5a.5.5 0 0 1 0 1H2z"/>
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
      isUpdate: false,

      morePointForSearch: false,
      fromLocation: null,
      toLocation: null,
      deliveryman: null,
      note: null,
      cargo: null,
      number: null,
      idUpdate: null,

      ifSortById: false,
      ifSortByNumber: false,
      ifSortByFromLocation: false,
      ifSortByToLocation: false,
      ifSortByStatus: false,
      ifSortByCargos: false,
      ifSortByDeliveryMan: false,
      ifSortByNote: false,
    }
  },

  methods: {

    filteredOrders() {
      this.countPage = Math.ceil(this.AllOrdersList.length / this.countItem);

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
        const length = this.filteredOrdersList.length; 
        this.countPage = Math.round(length / this.countItem);
      }
      if (this.toLocation) {
        this.filteredOrdersList = this.filteredOrdersList.filter(order => order.toLocation.includes(this.toLocation));
        const length = this.filteredOrdersList.length; 
        this.countPage = Math.round(length / this.countItem);
      }
      if (this.deliveryman) {
        this.filteredOrdersList = this.filteredOrdersList.filter(order => order.deliveryman.fio.toString().includes(this.deliveryman));
        const length = this.filteredOrdersList.length; 
        this.countPage = Math.round(length / this.countItem);
      }
      if (this.cargo) {
        this.filteredOrdersList = this.filteredOrdersList.filter(order =>
          order.cargos.some(cargo => cargo.name.includes(this.cargo))
        );
        const length = this.filteredOrdersList.length; 
        this.countPage = Math.round(length / this.countItem);
      }
      if (this.note) {
        this.filteredOrdersList = this.filteredOrdersList.filter(order => order.note.includes(this.note));
        const length = this.filteredOrdersList.length; 
        this.countPage = Math.round(length / this.countItem);
      }
      if (this.number) {
        this.filteredOrdersList = this.filteredOrdersList.filter(order => order.number.toString().includes(this.number.toString()));
        const length = this.filteredOrdersList.length; 
        this.countPage = Math.round(length / this.countItem);
      }
      return this.filteredOrdersList.slice(start, end);
    },

    pages() {
      let left = Math.max(1, this.page - 2);
      let right = Math.min(this.countPage, this.page + 2);
      let pages = [];

      for (let i = left; i <= right; i++) {
        pages.push(i);
      }

      if (left > 1) {
        pages.unshift('...');
        pages.unshift(1);
      }

      if (right < this.countPage) {
        pages.push('...');
        pages.push(this.countPage);
      }

      return pages;
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

    updateOrder(id) {
      const order = this.AllOrdersList.filter(order => order.id === id);
      const orderDTO = {
                    id: order[0].id,
                    status: order[0].status,
                    note: order[0].note,
                };

      axios.post('http://localhost:8075/api/v1/order/update', orderDTO)
        .then(response => {
            this.isUpdate = response.data;
            this.getAllOrders();
        })
        .catch(error => {
            console.error(error);
        });

      this.idUpdate = null;

      setTimeout(() => {
          this.isUpdate = false;
      }, 5000);
    },

    sortById() {
      if (this.ifSortById) {
        this.AllOrdersList.sort((a, b) => b.id - a.id);
        this.filteredOrdersList.sort((a, b) => b.id - a.id);
      } else {
        this.AllOrdersList.sort((a, b) => a.id - b.id);
        this.filteredOrdersList.sort((a, b) => a.id - b.id);
      }
      this.page = 1;
    },

    sortByNumber() {
      if (this.ifSortByNumber) {
        this.AllOrdersList.sort((a, b) => a.number - b.number);
        this.filteredOrdersList.sort((a, b) => a.number - b.number);
      } else {
        this.AllOrdersList.sort((a, b) => b.number - a.number);
        this.filteredOrdersList.sort((a, b) => b.number - a.number);
      }
      this.page = 1;
    },

    sortByFromLocation() {
      if (this.ifSortByFromLocation) {
        this.AllOrdersList.sort((a, b) => a.fromLocation.localeCompare(b.fromLocation));
        this.filteredOrdersList.sort((a, b) => a.fromLocation.localeCompare(b.fromLocation));
      } else {
        this.AllOrdersList.sort((a, b) => b.fromLocation.localeCompare(a.fromLocation));
        this.filteredOrdersList.sort((a, b) => b.fromLocation.localeCompare(a.fromLocation));
      }
      this.page = 1;
    },

    sortByToLocation() {
      if (this.ifSortByToLocation) {
        this.AllOrdersList.sort((a, b) => a.toLocation.localeCompare(b.toLocation));
        this.filteredOrdersList.sort((a, b) => a.toLocation.localeCompare(b.toLocation));
      } else {
        this.AllOrdersList.sort((a, b) => b.toLocation.localeCompare(a.toLocation));
        this.filteredOrdersList.sort((a, b) => b.toLocation.localeCompare(a.toLocation));
      }
      this.page = 1;
    },

    sortByStatus() {
      if (this.ifSortByStatus) { 
        this.AllOrdersList.sort((a, b) => a.status.localeCompare(b.status));
        this.filteredOrdersList.sort((a, b) => a.status.localeCompare(b.status)); 
      } else {
        this.AllOrdersList.sort((a, b) => b.status.localeCompare(a.status));
        this.filteredOrdersList.sort((a, b) => b.status.localeCompare(a.status));
      }
      this.page = 1;
    },

    sortByCargos() {
      if (this.ifSortByCargos) { 
        this.AllOrdersList.sort((a, b) => a.cargos.length - b.cargos.length);
        this.filteredOrdersList.sort((a, b) => a.cargos.length - b.cargos.length);
      } else {
        this.AllOrdersList.sort((a, b) => b.cargos.length - a.cargos.length);
        this.filteredOrdersList.sort((a, b) => b.cargos.length - a.cargos.length);
      }
      this.page = 1;
    },

    sortByDeliveryMan() {
      if (this.ifSortByDeliveryMan) { 
        this.AllOrdersList.sort((a, b) => a.deliveryman.fio.localeCompare(b.deliveryman.fio));
        this.filteredOrdersList.sort((a, b) => a.deliveryman.fio.localeCompare(b.deliveryman.fio));
      } else {
        this.AllOrdersList.sort((a, b) => b.deliveryman.fio.localeCompare(a.deliveryman.fio));
        this.filteredOrdersList.sort((a, b) => b.deliveryman.fio.localeCompare(a.deliveryman.fio));
      }
      this.page = 1;
    },

    sortByNote() {
      if (this.ifSortByNote) {  
        this.AllOrdersList.sort((a, b) => a.note.localeCompare(b.note));
        this.filteredOrdersList.sort((a, b) => a.note.localeCompare(b.note));
      } else {
        this.AllOrdersList.sort((a, b) => b.note.localeCompare(a.note));
        this.filteredOrdersList.sort((a, b) => b.note.localeCompare(a.note));
      }
      this.page = 1;
    },

    orderStatusWithoutSelected(statusWithout) {
        const statuses = ["Создан", "Комплектуется", "Готов к отправке", "Отправлен", "Доставлен"];

        return statuses.filter(status => status !== statusWithout);
    },
  },

  created() {
    const windowData = Object.fromEntries(
      new URL(window.location).searchParams.entries()
    );

    if (windowData.page) {
      this.page = windowData.page;
    }

    if (windowData.orderStatus) {
      this.orderStatus = windowData.orderStatus;
    }

    if (windowData.countItem) {
      this.countItem = windowData.countItem;
    }

    this.getAllOrders();
  },

  watch: {
    page() {
      window.history.pushState(
        null,
        document.title, 
        `${window.location.pathname}?orderStatus=${this.orderStatus}&countItem=${this.countItem}&page=${this.page}`);
    },

    orderStatus() {
      window.history.pushState(
        null,
        document.title, 
        `${window.location.pathname}?orderStatus=${this.orderStatus}&countItem=${this.countItem}&page=${this.page}`);
    },

    countItem() {
      window.history.pushState(
        null,
        document.title, 
        `${window.location.pathname}?orderStatus=${this.orderStatus}&countItem=${this.countItem}&page=${this.page}`);
    },

    ifSortById() {
      this.sortById();
    },

    ifSortByNumber() {
      this.sortByNumber();
    },

    ifSortByFromLocation() {
      this.sortByFromLocation();
    },
    
    ifSortByToLocation() {
      this.sortByToLocation();
    },

    ifSortByStatus() {
      this.sortByStatus();
    },

    ifSortByCargos() {
      this.sortByCargos();
    },

    ifSortByDeliveryMan() {
      this.sortByDeliveryMan();
    },

    ifSortByNote() { 
      this.sortByNote();
    }
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

  th svg {
    margin-left: 5px;
  }

  .sort_selected {
    color: #00ff00;
  }
</style>
  