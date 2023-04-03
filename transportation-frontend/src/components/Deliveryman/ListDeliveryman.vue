<template>

<div style="width: 90%; justify-content: center; margin: 0 auto; margin-top: 5%;">
  <AddDeliveryman @deliverymanAdded="onDeliverymanAdded"/>
  <hr>

  <button v-on:click="getDeliverymans(this.page)" type="button" class="btn btn-dark mr-2">Get All Deliverymans</button>
  <button v-on:click="deliverymans = []" type="button" class="btn btn-danger">Hide</button>

  <!-- CRUD info message -->
  <div class="alert alert-success" role="alert"
    v-if="isDeleted || isUpdate || idCreated != null">
    <p v-if="isDeleted">Доставщик был успешно удалён</p>
    <p v-if="isUpdate">Доставщик был успешно обновлён</p>
    <p v-if="idCreated != null">Новый Доставщик с ID: {{ idCreated }} был успешно добавлен</p>
    <hr>
    <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
  </div>

  <div class="form-row">
    <!-- Deliveryman FIO -->
    <div class="col-md-4 mb-3">
      <label for="nameCargo">ФИО Доставщика</label>
      <input v-model="fioDeliveryman" list="deliverymansFIO" class="form-control" v-on:change="page = 1">
      <datalist id="deliverymansFIO">
        <option v-for="fio in deliverymansFIO" v-bind:key="fio">{{ fio }}</option>
      </datalist>
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
      <div class="form-group col-md-3">
        <label for="phone">Телефон</label>
        <input type="tel" v-model="phone" class="form-control" id="phone" v-mask="['7 ### ###-##-##']" placeholder="+7 800 555 35 35">
      </div>
      <div class="form-group col-md-3">
        <label for="Vehicle">Транспорт</label>
        <input type="text" v-model="vehicle" class="form-control" id="Vehicle" placeholder="НЛО">
      </div>
    </div>

    <div class="form-row">
      <div class="form-group col-md-2">
        <button v-on:click="findDeliveryman()" class="btn btn-success form-control" id="search">Найти</button>
      </div>
      <div class="form-group col-md-2">
        <button v-on:click="clearFormSearch()" class="btn btn-danger form-control" id="search">Сбросить</button>
      </div>
    </div>

  </div>

  <!-- Pagination -->
  <div class="form-row" style="width: 100%; display: block;">
    <div class="col mb-3"  v-if="fioDeliveryman === null">
      <button v-for="page in pages()" v-bind:key="page" 
        v-on:click="getDeliverymans(page, this.sortBy)"
        :class="{'page_selected': page == this.page}"
        :disabled="page === '...'" class="btn btn-info mr-2">{{ page }}</button>
    </div>
    <div v-if="fioDeliveryman != null" class="col mb-3">
      <button v-for="page in pages()" v-bind:key="page" 
        v-on:click="getDiliverymansByFIO(this.fioDeliveryman, page)"
        :class="{'page_selected': page == this.page}"
        :disabled="page === '...'" class="btn btn-info mr-2">{{ page }}</button>
    </div>
  </div>

  <table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">
        <div style="display: flex; align-items: center;">
          #
          <div style="display: flex; flex-direction: column;">
            <svg width="20" height="16" fill="currentColor"
              v-on:click="sortBy = 'id_up'"
              :class="{'sort_selected': sortBy === 'id_up'}">
              <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
            </svg>
            <svg width="20" height="16" fill="currentColor"
              v-on:click="sortBy = 'id_down'"
              :class="{'sort_selected': sortBy === 'id_down'}">>
              <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
            </svg>
          </div>
        </div>
      </th>
      <th scope="col">
        <div style="display: flex; align-items: center;">
          FIO
          <div style="display: flex; flex-direction: column;">
            <svg width="20" height="16" fill="currentColor"
              v-on:click="sortBy = 'fio_up'"
              :class="{'sort_selected': sortBy === 'fio_up'}">
              <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
            </svg>
            <svg width="20" height="16" fill="currentColor"
              v-on:click="sortBy = 'fio_down'"
              :class="{'sort_selected': sortBy === 'fio_down'}">>
              <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
            </svg>
          </div>
        </div>
      </th>
      <th scope="col">
        <div style="display: flex; align-items: center;">
          Phone
          <div style="display: flex; flex-direction: column;">
            <svg width="20" height="16" fill="currentColor"
              v-on:click="sortBy = 'phone_up'"
              :class="{'sort_selected': sortBy === 'phone_up'}">
              <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
            </svg>
            <svg width="20" height="16" fill="currentColor"
              v-on:click="sortBy = 'phone_down'"
              :class="{'sort_selected': sortBy === 'phone_down'}">>
              <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
            </svg>
          </div>
        </div>
      </th>
      <th scope="col">
        <div style="display: flex; align-items: center;">
          Vehicle
          <div style="display: flex; flex-direction: column;">
            <svg width="20" height="16" fill="currentColor"
              v-on:click="sortBy = 'vehicle_up'"
              :class="{'sort_selected': sortBy === 'vehicle_up'}">
              <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
            </svg>
            <svg width="20" height="16" fill="currentColor"
              v-on:click="sortBy = 'vehicle_down'"
              :class="{'sort_selected': sortBy === 'vehicle_down'}">>
              <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
            </svg>
          </div>
        </div>
      </th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="deliveryman in deliverymans" v-bind:key="deliveryman.id">
      <th scope="row">{{ deliveryman.id }}</th>
      <td>
        <input type="text" class="form-control" v-model="deliveryman.fio" v-on:change="idUpdate = deliveryman.id"/>
      </td>
      <td>
        <input type="text" class="form-control" v-mask="['+7 ### ###-##-##']" v-model="deliveryman.phone" v-on:change="idUpdate = deliveryman.id"/>
      </td>
      <td>
        <input type="text" class="form-control" v-model="deliveryman.vehicle" v-on:change="idUpdate = deliveryman.id"/>
      </td>
      <td>

        <DeleteDeliveryman :id="deliveryman.id" @deliverymanDeleted="onDeliverymanDeleted"></DeleteDeliveryman>
        
        <div v-if="idUpdate === deliveryman.id">
          <UpdateDeliveryman :deliveryman="deliveryman" @deliverymanUpdated="onDeliverymanUpdated"></UpdateDeliveryman>
        </div>
      </td>
    </tr>
  </tbody>
</table>
</div>

</template>

<script>
import axios from "axios"
import {mask} from 'vue-the-mask'
import DeleteDeliveryman from "./DeleteDeliveryman.vue"
import AddDeliveryman from "./AddDeliveryman.vue"
import UpdateDeliveryman from './UpdateDeliveryman.vue'

export default {
  name: 'ListDeliveryman',
  directives:{mask},
  components: {
    AddDeliveryman,
    DeleteDeliveryman,
    UpdateDeliveryman,
  },

  data() {
    return {
      deliverymans: [],
      deliverymansFIO: [],

      fioDeliveryman: null,
      sortBy: null,
      morePointForSearch: false,

      countItem: 5,
      countPage: 1,
      page: 1,

      phone: null,
      vehicle: null,

      isDeleted: false,
      isUpdate: false,
      idCreated: null,
      idUpdate: null,
      ifSearch: false,
    }
  },

  methods: {
    getDeliverymans(page, sortBy) {
      axios
      .get('http://localhost:8075/api/v1/deliveryman/getAllDeliveryman',  {
        params: {
          page: page,
          countItems: this.countItem,
          sortBy: sortBy,
        }
      })
      .then((response) => {
          this.page = page;
          this.deliverymans = response.data.deliverymans;
          this.countPage = Math.ceil(response.data.count / this.countItem);
      })
    },

    getDiliverymansByFIO(deliverymanFio, page) {
      axios
      .get('http://localhost:8075/api/v1/deliveryman/getDiliverymansByFIO',  {
        params: {
          page: page,
          countItems: this.countItem,
          fio: deliverymanFio,
        }
      })
      .then((response) => {
          this.page = page;
          this.deliverymans = response.data.diliverymans;
          this.countPage = Math.ceil(response.data.count / this.countItem);
      })
    },

    getDeliverymansFIO() {
      axios
      .get('http://localhost:8075/api/v1/deliveryman/getDeliverymansFIO')
      .then((response) => {
          this.deliverymansFIO = response.data;
      })
    },

    findDeliveryman() {
      axios
      .get('http://localhost:8075/api/v1/deliveryman/searchDeliveryman',  {
        params: {
          phone: this.phone,
          vehicle: this.vehicle,
        }
      })
      .then((response) => {
          this.deliverymans = response.data.deliverymans;
          this.countDeliveryman = response.data.count;
          this.countPage = Math.ceil(this.countDeliveryman / this.countItem);

          this.ifSearch = true;
      })
    },

    pages() {
      let left = Math.max(1, this.page - 2);
      let right = Math.min(this.countPage, (Number(this.page) + 2));

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

    onDeliverymanAdded(id) {
      this.getDeliverymans();

      this.idCreated = id;

      setTimeout(() => {
          this.idCreated = null;
      }, 5000);
    },

    onDeliverymanUpdated(isDeliverymanUpdated) {
      this.idUpdate = null;

      this.isUpdate = isDeliverymanUpdated;
      this.getDeliverymans(this.page);

      setTimeout(() => {
          this.isUpdate = false;
      }, 5000);
    },

    onDeliverymanDeleted(isDeliverymanDeleted) {
      this.getDeliverymans();

      this.isDeleted = isDeliverymanDeleted;

      setTimeout(() => {
          this.isDeleted = false;
      }, 5000);
    },

    clearFormSearch() {
      this.phone = null;
      this.vehicle = null;

      this.getDeliverymans(1);
      this.ifSearch = false;
    } 

  },

  created() {
    this.getDeliverymans(this.page, this.sortBy);
    this.getDeliverymansFIO();
  },

  watch: {
    countItem() {
      if (this.fioDeliveryman === null) {
        this.getDeliverymans(1);
      } else {
        this.getDiliverymansByFIO(this.fioDeliveryman, 1);
      }
    },

    fioDeliveryman() {
      if (this.fioDeliveryman === null) {
        this.getDeliverymans(this.page);
      } else {
        this.getDiliverymansByFIO(this.fioDeliveryman, this.page)
      }
    },

    sortBy() {
      if (this.fioDeliveryman === null) {
        this.getDeliverymans(1, this.sortBy);
      } else {
        this.getDiliverymansByFIO(this.fioDeliveryman, this.page)
      }
    },
  }
}
</script>

<style>

</style>
