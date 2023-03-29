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
          <deleteOrder :id="order.id" @orderDeleted="onOrderDeleted"></deleteOrder>

          <div v-if="order.status == 'Отправлен' && order.id !== idUpdate">
            <completeOrder :id="order.id" @orderCompleted="onOrderCompleted"></completeOrder>
          </div>

          <div v-if="idUpdate == order.id">
            <updateOrder :order="order" @orderUpdated="onOrderUpdated"></updateOrder>
          </div>

          <button type="button" class="btn btn-info" style="margin-bottom: 5px;" @click="gotoOrderDetails(order.id)">
            <svg width="20" height="20" fill="currentColor" class="bi bi-info-square" viewBox="0 0 16 16">
              <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
              <path d="m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533L8.93 6.588zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
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

import DeleteOrder from "./DeleteOrder.vue";
import CompleteOrder from './CompleteOrder.vue';
import UpdateOrder from './UpdateOrder.vue';

export default {
  name: 'ListOrders',
  components: {
    DeleteOrder,
    CompleteOrder,
    UpdateOrder,
  },

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

    gotoOrderDetails(id) {
      this.$emit('gotoOrderDetails', id);
    },

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

    onOrderDeleted(isOrderDeleted) {
      this.getAllOrders();

      this.isDeleted = isOrderDeleted;

      setTimeout(() => {
          this.isDeleted = false;
      }, 5000);
    },

    onOrderCompleted(isOrderCompleted) {
      this.getAllOrders();

      this.isCompleted = isOrderCompleted;

      setTimeout(() => {
          this.isCompleted = false;
      }, 5000);
    },

    onOrderUpdated(isOrderUpdated) {
      this.idUpdate = null;

      this.isUpdate = isOrderUpdated;

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
  