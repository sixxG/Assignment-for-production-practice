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
      <ListOrdes  @gotoOrderDetails="showOrderDetails"/>
    </div>

    <DetailsOrder v-if="isShowOrderDetails" @backFromOrderDetails="backFromOrderDetails"></DetailsOrder>
    
    <div v-if="isShowDeliverymans">
      <ListDeliveryman/>
      <hr>
      <AddDeliveryman/>
    </div>
    <div v-if="isShowCargos">
      <ListCargo/>
    </div>

    <BackToTop />
  </div>

</template>

<script>
import ListDeliveryman from './components/Deliveryman/ListDeliveryman.vue'
import AddDeliveryman from './components/Deliveryman/AddDeliveryman.vue';

import ListCargo from './components/Cargo/ListCargo.vue';

import ListOrdes from './components/Order/ListOrdes.vue';
import DetailsOrder from './components/Order/DetailsOrder.vue';

import BackToTop from './components/BackToTop.vue';

export default {
  name: 'App',
  components: {
    ListDeliveryman,
    ListOrdes,
    ListCargo,
    AddDeliveryman,
    BackToTop,
    DetailsOrder,
  },
  
  data() {
    return {
      isShowOrders: true,
      isShowDeliverymans: false,
      isShowCargos: false,

      isShowOrderDetails: false,
      idOrderForDetails: null,
    }
  },

  methods: {

    showOrders() {
      this.isShowCargos = false;
      this.isShowDeliverymans = false;
      this.isShowOrders = true;
      this.isShowOrderDetails = false;
    },

    showDeliverymans() {
      this.isShowCargos = false;
      this.isShowDeliverymans = true;
      this.isShowOrders = false;
      this.isShowOrderDetails = false;
    },

    showCargos() {
      this.isShowCargos = true;
      this.isShowDeliverymans = false;
      this.isShowOrders = false;
      this.isShowOrderDetails = false;
    },

    showOrderDetails(id) {
      this.isShowOrderDetails = true;
      this.idOrderForDetails = id;

      window.history.pushState(
        null,
        document.title, 
        `${window.location.pathname}?id=${this.idOrderForDetails}`);

      this.isShowCargos = false;
      this.isShowDeliverymans = false;
      this.isShowOrders = false
    },

    backFromOrderDetails(isBack) {
      if (isBack) {
        window.history.pushState(
          null,
          document.title, 
          `${window.location.pathname}`);

          this.showOrders()
      }
    },

    handleScroll() {
      if (window.pageYOffset > 100) {
        document.body.classList.add('scroll-show');
      } else {
        document.body.classList.remove('scroll-show');
      }
    }
  },

  created() {
    const windowData = Object.fromEntries(
      new URL(window.location).searchParams.entries()
    );

    if (windowData.id) {
      this.showOrderDetails(windowData.id)
    }
  }
} 

</script>

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
