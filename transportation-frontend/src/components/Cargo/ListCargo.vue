<template>

    <div style="width: 90%; justify-content: center; margin: 0 auto; margin-top: 5%;">
      <AddCargo @cargoAdded="onCargoAdded"/>
      <hr>
    
      <button v-on:click="getCargos(this.page, this.nameCargo)" type="button" class="btn btn-dark mr-2">Get All Cargos</button>
      <button v-on:click="cargos = []" type="button" class="btn btn-danger">Hide</button>
    
      <!-- CRUD info message -->
      <div class="alert alert-success" role="alert"
        v-if="isDeleted || isUpdate || idCreated != null">
        <p v-if="isDeleted">Товар был успешно удалён</p>
        <p v-if="isUpdate">Груз был успешно обновлён</p>
        <p v-if="idCreated != null">Новый товар с ID: {{ idCreated }} был успешно добавлен</p>
        <hr>
        <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
      </div>

      <div class="form-row">
        <!-- Order Status -->
        <div class="col-md-4 mb-3">
          <label for="nameCargo">Название товара</label>
          <input v-model="nameCargo" list="cargosName" id="nameCargo" class="form-control" v-on:change="page = 1">
          <datalist id="cargosName">
            <option v-for="cargosName in cargosName" v-bind:key="cargosName">{{ cargosName }}</option>
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
            <label for="fromLocation">Цена от</label>
            <input type="number" v-model="priceFrom" class="form-control" id="fromLocation" placeholder="0">
          </div>
          <div class="form-group col-md-3">
            <label for="toLocation">Цена до</label>
            <input type="number" v-model="priceTo" class="form-control" id="toLocation" placeholder="100500">
          </div>
        </div>

        <div class="form-row">
          <div class="form-group col-md-3">
            <label for="deliveryman">Кол-во от</label>
            <input type="number" v-model="countFrom" class="form-control" id="deliveryman" placeholder="0">
          </div>
          <div class="form-group col-md-3">
            <label for="note">Кол-во до</label>
            <input type="number" v-model="countTo" class="form-control" id="note" placeholder="100500">
          </div>
        </div>

        <div class="form-row">
          <div class="form-group col-md-2">
            <button v-on:click="findCargos(1)" class="btn btn-success form-control" id="search">Найти</button>
          </div>
          <div class="form-group col-md-2">
            <button v-on:click="clearFormSearch()" class="btn btn-danger form-control" id="search">Сбросить</button>
          </div>
        </div>

      </div>

      <!-- Pagination -->
      <div class="form-row" style="width: 100%; display: block;">
        <div v-if="nameCargo === null && !ifSearch" class="col mb-3">
          <button v-for="page in pages()" v-bind:key="page" 
            v-on:click="getCargos(page, this.sortBy)"
            :class="{'page_selected': page == this.page}"
            :disabled="page === '...'" class="btn btn-info mr-2">{{ page }}</button>
        </div>
        <div v-if="nameCargo != null && this.sortBy === null && !ifSearch" class="col mb-3">
          <button v-for="page in pages()" v-bind:key="page" 
            v-on:click="getCargosByName(this.nameCargo, page, this.sortBy)"
            :class="{'page_selected': page == this.page}"
            :disabled="page === '...'" class="btn btn-info mr-2">{{ page }}</button>
        </div>
        <div v-if="this.ifSearch" class="col mb-3" id="findCargos">
          <button v-for="page in pages()" v-bind:key="page" 
            v-on:click="findCargos(page)"
            :class="{'page_selected': page == this.page}"
            :disabled="page === '...'" class="btn btn-info mr-2">{{ page }}</button>
        </div>
      </div>

      {{ idUpdate }}

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
              Name
              <div style="display: flex; flex-direction: column;">
                <svg width="20" height="16" fill="currentColor"
                  v-on:click="sortBy = 'name_up'"
                  :class="{'sort_selected': sortBy === 'name_up'}">
                  <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
                </svg>
                <svg width="20" height="16" fill="currentColor"
                  v-on:click="sortBy = 'name_down'"
                  :class="{'sort_selected': sortBy === 'name_down'}">>
                  <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
                </svg>
              </div>
            </div>
          </th>
          <th scope="col">
            Price
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="sortBy = 'price_up'"
                :class="{'sort_selected': sortBy === 'price_up'}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="sortBy = 'price_down'"
                :class="{'sort_selected': sortBy === 'price_down'}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </th>
          <th scope="col">
            Count
            <div style="display: flex; flex-direction: column;">
              <svg width="20" height="16" fill="currentColor"
                v-on:click="sortBy = 'count_up'"
                :class="{'sort_selected': sortBy === 'count_up'}">
                <path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
              </svg>
              <svg width="20" height="16" fill="currentColor"
                v-on:click="sortBy = 'count_down'"
                :class="{'sort_selected': sortBy === 'count_down'}">>
                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z"/>
              </svg>
            </div>
          </th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="cargo in cargos" v-bind:key="cargo.id">
          <th scope="row">{{ cargo.id }}</th>
          <td>
            <input type="text" class="form-control" v-model="cargo.name" v-on:change="idUpdate = cargo.id"/>
          </td>
          <td>
            <input type="number" class="form-control" v-model="cargo.price" v-on:change="idUpdate = cargo.id"/>
          </td>
          <td>
            <input type="number" class="form-control" v-model="cargo.count" v-on:change="idUpdate = cargo.id"/>
          </td>
          <td>

            <DeleteCargo :id="cargo.id" @cargoDeleted="onCargoDeleted"></DeleteCargo>

            <div v-if="idUpdate == cargo.id">
              <UpdateCargo :cargo="cargo" @cargoUpdated="onCargoUpdated"></UpdateCargo>
            </div>

          </td>
        </tr>
      </tbody>
    </table>
    </div>
    
    </template>
    
    <script>
    import axios from "axios"
    import AddCargo from "./AddCargo.vue"
    import UpdateCargo from './UpdateCargo.vue'
    import DeleteCargo from './DeleteCargo.vue'
    
    export default {
      name: 'ListCargo',
      components: {
        AddCargo,
        UpdateCargo,
        DeleteCargo,
      },
    
      data() {
        return {
          cargos: [],
          cargosName: [],

          nameCargo: null,
          sortBy: null,
          morePointForSearch: false,

          countItem: 5,
          countPage: 1,
          page: 1,

          priceFrom: null,
          priceTo: null,
          countFrom: null,
          countTo: null,

          isDeleted: false,
          isUpdate: false,
          idCreated: null,
          idUpdate: null,
          ifSearch: false,
        }
      },

      methods: {
    
        getCargos(page, sortBy) {
          axios
          .get('http://localhost:8075/api/v1/cargo/getAllCargos',  {
            params: {
              page: page,
              countItems: this.countItem,
              sortBy: sortBy,
            }
          })
          .then((response) => {
              this.page = page;
              this.cargos = response.data.cargos;
              this.countPage = Math.ceil(response.data.count / this.countItem);
          })
        },

        getCargosByName(cargosName, page) {
          axios
          .get('http://localhost:8075/api/v1/cargo/getCargosByName',  {
            params: {
              page: page,
              countItems: this.countItem,
              name: cargosName,
            }
          })
          .then((response) => {
              this.page = page;
              this.cargos = response.data.cargos;
              this.countPage = Math.ceil(response.data.count / this.countItem);
          })
        },

        getCargosName() {
          axios
          .get('http://localhost:8075/api/v1/cargo/getCargosName')
          .then((response) => {
              this.cargosName = response.data;
          })
        },

        findCargos(pageNumber) {
          axios
          .get('http://localhost:8075/api/v1/cargo/searchCargo',  {
            params: {
              priceFromParam: this.priceFrom,
              priceToParam: this.priceTo,
              countFromParam: this.countFrom,
              countToParam: this.countTo,
              countItems: this.countItem,

              page: pageNumber,
            }
          })
          .then((response) => {
              this.page = pageNumber;
              this.cargos = response.data.cargos;
              this.countOrders = response.data.count;
              this.countPage = Math.ceil(this.countOrders / this.countItem);

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

        onCargoAdded(id) {
          this.getCargos(this.page);

          this.idCreated = id;
          this.nameCargo = null;

          setTimeout(() => {
              this.idCreated = null;
          }, 5000);
        },

        onCargoUpdated(isCargoUpdated) {
          this.idUpdate = null;

          this.isUpdate = isCargoUpdated;
          this.getCargos(this.page);

          setTimeout(() => {
              this.isUpdate = false;
          }, 5000);
        },

        onCargoDeleted(isCargoDeleted) {
          this.getCargos(this.page);

          this.isDeleted = isCargoDeleted;

          setTimeout(() => {
              this.isDeleted = false;
          }, 5000);
        },

        clearFormSearch() {
          this.priceFrom = null;
          this.priceTo = null;
          this.countFrom = null;
          this.countTo = null;

          this.getCargos(1);
          this.ifSearch = false;
        } 
    
      },

      created() {
        this.getCargos(this.page);
        this.getCargosName();
      },

      watch: {
        countItem() {
            this.getCargos(1);
        },

        nameCargo() {
          if (this.nameCargo === "") {
            this.getCargos(this.page);
          } else {
            this.getCargosByName(this.nameCargo, this.page)
          }
        },

        sortBy() {
          if (this.nameCargo === null) {
            this.getCargos(1, this.sortBy);
          } else {
            this.getCargosByName(this.nameCargo, this.page, this.sortBy)
          }
        },
      }
    
    }
    </script>
    
    <style>

    </style>
    