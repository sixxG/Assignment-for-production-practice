<template>

<div style="display: block; margin: 0 auto; width: 90%; justify-content: center;  margin-top: 5%;">

    <button class="btn btn-success" v-on:click="isShowAddOrder = !isShowAddOrder">Создать заказ</button>

    <div class="alert alert-success" role="alert"
        v-if="idCreated != null">
        <p>Новый заказ с ID: {{ idCreated }} был успешно добавлен</p>
        <hr>
        <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
    </div>

    <form v-if="isShowAddOrder" v-on:submit.prevent="saveOrder">

        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationDefaultNumber">Номер</label>
                <input type="text" v-model="number" class="form-control" id="validationDefaultNumber" placeholder="Номер" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationDefaultFromLocation">Место отправления</label>
                <input type="text" v-model="fromLocation" class="form-control" id="validationDefaultFromLocation" placeholder="Место отправления" required step="any">

            </div>
            <div class="col-md-4 mb-3">
                <label for="validationDefaultToLocation">Место доставки</label>
                <div class="input-group">
                    <input type="text" v-model="toLocation" class="form-control" id="validationDefaultToLocation" placeholder="Место доставки" required>
                </div>
            </div>
        </div>

        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationDefaultDeliveryman">Доставщик</label>
                <input type="text" list="Deliverymans" v-model="deliveryman" class="form-control" id="validationDefaultDeliveryman" placeholder="Доставщик" required>
                
                <datalist id="Deliverymans">
                    <option v-for="deliveryman in deliverymans" v-bind:key="deliveryman.id"> {{ deliveryman.fio }}</option>
                </datalist>
            
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationDefaultNote">Комментарий к заказу</label>
                <div class="input-group">
                    <input type="text" v-model="note" class="form-control" id="validationDefaultNote" placeholder="Комментарий к заказу" required>
                </div>
            </div>
        </div>

        <!-- кнопка, которая открывает модальное окно AddCargo -->
        <button type="button" class="btn btn-primary" v-on:click="isShowAddCargo = !isShowAddCargo">Добавить товар</button>

        <!-- модальное окно для добавления товара -->
        <AddCargo v-if="isShowAddCargo" @cargoAdded="onCargoAdded"></AddCargo>

        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationDefaultСargos">Груз</label>
                <table class="table" v-if="cargosListToOrder.length > 0">
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
                        <tr v-for="cargo in cargosListToOrder" v-bind:key="cargo.id">
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
        </div>

        <hr>

        <p v-if="errors.length">
            <b>Please correct the following error(s):</b>
            <ul>
                <li v-for="error in errors" v-bind:key="error.idx">{{ error }}</li>
            </ul>
        </p>

        <div class="form-row">
            <div class="col-md-1 mr-5">
                <button class="btn btn-primary" type="submit">Submit form</button>
            </div>
            <div class="col-md-1 mb-3">
                <button class="btn btn-danger" type="reset">Clear form</button>
            </div>
        </div>
    </form>
</div>

</template>
        
<script>
import axios from "axios"
import AddCargo from "../Cargo/AddCargo.vue"

export default {
    name: 'AddOrder',
    components: {
        AddCargo,
    },

    data() {
        return {
            number: null,
            fromLocation: null,
            toLocation: null,
            cargos: "",
            deliveryman: null,
            note: null,
            
            errors: [],
            deliverymans: [],
            cargosListAll: [],
            cargosListToOrder: [],
            isShowAddCargo: false,
            isShowAddOrder: false,
            idCreated: null,
        }
    },

    methods: {
        
        getAllDeliverymans() {
            axios
                .get('http://localhost:8075/api/v1/deliveryman/getAllDeliverymans')
                .then((response) => {
                this.deliverymans = response.data;
                })
        },

        async getAllCargo() {
            const response=await axios
            .get('http://localhost:8075/api/v1/cargo/getAllCargos')
            this.cargosListAll=response.data
        },

        async onCargoAdded(idCreated) {
            await this.getAllCargo();
                                
            this.cargos = this.cargos + idCreated + " ";

            this.cargosListToOrder.push(this.cargosListAll.find(item => item.id == idCreated));
            idCreated = null;
        },

        deleteCargo(id) {
            this.cargosListToOrder = this.cargosListToOrder.filter((e)=>e.id !== id )
            const arrId = this.cargos.split(" ");

            const filteredArrId = arrId.filter(item => item != id);

            this.cargos = filteredArrId.join(" ");
        },

        async saveOrder() {

            if(this.number && this.fromLocation && this.toLocation
                && this.cargos && this.deliveryman && this.note) {

                const orderDTO = {
                    number: this.number,
                    fromLocation: this.fromLocation,
                    toLocation: this.toLocation,
                    cargos: this.cargos,
                    deliveryman: this.deliverymans.find(item => item.fio === this.deliveryman).id,
                    note: this.note,
                }
    
                axios.post('http://localhost:8075/api/v1/order/save', orderDTO)
                .then(response => {
                    this.idCreated = response.data;
                })
                .catch(error => {
                    console.error(error);
                });
    
                setTimeout(() => {
                    this.idCreated = null;
                }, 5000);
    
                this.number = null,
                this.fromLocation = null,
                this.toLocation = null,
                this.statu = null,
                this.cargos = "",
                this.deliveryman = null,
                this.note = null

                this.errors = [],
                this.deliverymans = [],
                this.cargosListAl = [],
                this.cargosListToOrder = [],
                this.isShowAddCargo = false,
                this.idCreated = null
            } else {
                if(!this.number) this.errors.push("Number required.")
                if(!this.fromLocation) this.errors.push("FromLocation required.")
                if(!this.toLocation) this.errors.push("ToLocation required.")
                if(!this.cargos) this.errors.push("Cargos required.")
                if(!this.deliveryman) this.errors.push("Deliveryman required.")
                if(!this.note) this.errors.push("Note required.")
            }
        }

    },

    beforeMount() {
        this.getAllDeliverymans()
    }

}
</script>
    
<style>

</style>
    