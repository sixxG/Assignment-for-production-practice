<template>

<div style="display: block; margin: 0 auto; width: 90%; justify-content: center;  margin-top: 5%;">

    <button class="btn btn-success" v-on:click="isShowAddOrder = !isShowAddOrder">Создать заказ</button>

    <form v-if="isShowAddOrder">

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
                    <option v-for="deliveryman in deliverymans" v-bind:key="deliveryman"> {{ deliveryman }}</option>
                </datalist>
            
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationDefaultNote">Комментарий к заказу</label>
                <div class="input-group">
                    <input type="text" v-model="note" class="form-control" id="validationDefaultNote" placeholder="Комментарий к заказу" required>
                </div>
            </div>
        </div>

        <AddCargo @cargoAdded="onCargoAdded"></AddCargo>

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
                                <DeleteCargo :id="cargo.id" @cargoDeleted="onCargoDeleted(cargo.id)"></DeleteCargo>
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
                <button class="btn btn-primary" v-on:click="saveOrder($event)">Submit form</button>
            </div>
            <div class="col-md-1 mb-3">
                <button class="btn btn-danger" type="reset" v-on:click="resetForm">Clear form</button>
            </div>
        </div>
    </form>
</div>

</template>
        
<script>
import axios from "axios"
import AddCargo from "../Cargo/AddCargo.vue"
import DeleteCargo from "../Cargo/DeleteCargo.vue"

export default {
    name: 'AddOrder',
    components: {
        AddCargo,
        DeleteCargo,
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
            cargosListToOrder: [],
            isShowAddOrder: false,
        }
    },

    methods: {

        resetForm() {
            this.errors = [];
        },
        
        getAllDeliverymansFIO() {
            axios
                .get('http://localhost:8075/api/v1/deliveryman/getDeliverymansFIO')
                .then((response) => {
                this.deliverymans = response.data;
                })
        },

        async getAddedCargo(idCreated) {
            const response=await axios
            .get('http://localhost:8075/api/v1/cargo/getById?id=' + idCreated);
            this.cargosListToOrder.push(response.data);
        },

        async onCargoAdded(idCreated) {
            await this.getAddedCargo(idCreated);
                                
            this.cargos = this.cargos + idCreated + " ";

            idCreated = null;
        },

        onCargoDeleted(id) {
            this.cargosListToOrder = this.cargosListToOrder.filter((e)=>e.id !== id )
            const arrId = this.cargos.split(" ");

            const filteredArrId = arrId.filter(item => item != id);

            this.cargos = filteredArrId.join(" ");
        },

        saveOrder(event) {
            event.preventDefault();
            if(this.number && this.fromLocation && this.toLocation
                && this.cargos != "" && this.deliveryman && this.note) {

                const orderDTO = {
                    number: this.number,
                    fromLocation: this.fromLocation,
                    toLocation: this.toLocation,
                    cargos: this.cargos,
                    deliveryman: this.deliveryman,
                    note: this.note,
                }
    
                axios.post('http://localhost:8075/api/v1/order/save', orderDTO)
                .then(response => {
                    this.$emit('orderCreated', response.data);
                })
                .catch(error => {
                    this.$emit('orderCreated', -1);
                    console.error(error);
                });
    
                this.number = null,
                this.fromLocation = null,
                this.toLocation = null,
                this.statu = null,
                this.cargos = "",
                this.deliveryman = null,
                this.note = null

                this.errors = [],
                this.deliverymans = [],
                this.cargosListToOrder = [],
                this.idCreated = null
            } else {
                if(!this.number) this.errors.push("Number required.")
                if(!this.fromLocation) this.errors.push("FromLocation required.")
                if(!this.toLocation) this.errors.push("ToLocation required.")
                if(this.cargos == "") this.errors.push("Cargos required.")
                if(!this.deliveryman) this.errors.push("Deliveryman required.")
                if(!this.note) this.errors.push("Note required.")
            }
        }

    },

    beforeMount() {
        this.getAllDeliverymansFIO()
    }

}
</script>
    
<style>

</style>
    