<template>
  <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"> -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

  <!-- Nav tabs -->
  <nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" v-on:click="showOrders">Заказы</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" v-on:click="showDeliverymans">Доставщики</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" v-on:click="showCargos">Грузы</a>
        </li>
      </ul>
    </div>
    <br>
  </nav>

  <!-- Tab panes -->
  <div @scroll="handleScroll">
    <div v-if="isShowOrders">
      <AddOrders/>
      <hr>
      <ListOrdes/>
    </div>
    <div v-if="isShowDeliverymans">
      <ListDeliveryman/>
      <hr>
      <AddDeliveryman/>
    </div>
    <div v-if="isShowCargos">
      <ListCargo/>
      <hr>
      <AddCargo/>
    </div>

    <BackToTop />
  </div>

</template>

<script>
import ListDeliveryman from './components/Deliveryman/ListDeliveryman.vue'
import AddDeliveryman from './components/Deliveryman/AddDeliveryman.vue';

import ListCargo from './components/Cargo/ListCargo.vue';
import AddCargo from './components/Cargo/AddCargo.vue';

import ListOrdes from './components/Order/ListOrdes.vue';
import AddOrders from './components/Order/AddOrders.vue';

import BackToTop from './components/BackToTop.vue';

export default {
  name: 'App',
  components: {
    ListDeliveryman,
    ListOrdes,
    ListCargo,
    AddCargo,
    AddDeliveryman,
    AddOrders,
    BackToTop
  },
  
  data() {
    return {
      isShowOrders: true,
      isShowDeliverymans: false,
      isShowCargos: false
    }
  },

  methods: {

    showOrders() {
      this.isShowCargos = false;
      this.isShowDeliverymans = false;
      this.isShowOrders = true
    },

    showDeliverymans() {
      this.isShowCargos = false;
      this.isShowDeliverymans = true;
      this.isShowOrders = false
    },

    showCargos() {
      this.isShowCargos = true;
      this.isShowDeliverymans = false;
      this.isShowOrders = false
    },

    handleScroll() {
      if (window.pageYOffset > 100) {
        document.body.classList.add('scroll-show');
      } else {
        document.body.classList.remove('scroll-show');
      }
    }

  }
} 

</script>

<style>

</style>
