<template>

<div style="display: block; margin: 0 auto; width: 70%; justify-content: center;">

    <div class="alert alert-success" role="alert"
        v-if="idCreated != null">
        <p>Новый товар с ID: {{ idCreated }} был успешно добавлен</p>
        <hr>
        <p class="mb-0">Это сообщение автоматически пропадёт через 5 секунд.</p>
    </div>

    <form v-on:submit.prevent="saveOrder">
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationDefaultName">Наименование</label>
                <input type="text" list="CargosName" v-model="name" class="form-control" id="validationDefaultName" placeholder="Название товара" required>
                
                <datalist id="CargosName">
                        <option v-for="cargo in cargos" v-bind:key="cargo.name"> {{ cargo.name }}</option>
                </datalist>
                
                </div>
                <div class="col-md-4 mb-3">
                <label for="validationDefaultPrice">Цена</label>
                <input type="number" v-model="price" class="form-control" id="validationDefaultPrice" placeholder="Цена" required step="any">
                </div>
                <div class="col-md-4 mb-3">
                <label for="validationDefaultCount">Количество</label>
                <div class="input-group">
                    <input type="number" v-model="count" class="form-control" id="validationDefaultCount" placeholder="кол-во" required>
                </div>
            </div>
        </div>

        <p v-if="errors.length">
            <b>Please correct the following error(s):</b>
            <ul>
                <li v-for="error in errors" v-bind:key="error.idx">{{ error }}</li>
            </ul>
        </p>

        <div class="form-row">
            <div class="col-md-14 mr-1">
                <button class="btn btn-primary" type="submit">Submit form</button>
            </div>
            <div class="col-md-5 mb-3">
                <button class="btn btn-danger" type="reset">Clear form</button>
            </div>
        </div>
    </form>
</div>

</template>
    
<script>
import axios from "axios"

export default {
    name: 'AddCargo',


    data() {
        return {
            name: null,
            price: null,
            count: null,
            errors: [],
            cargos: [],
            idCreated: null,
        }
    },

    methods: {
        async getAllCargo() {
            const response=await axios
            .get('http://localhost:8075/api/v1/cargo/getAllCargos')
            this.cargos=response.data
        },

        saveOrder() {

            if(this.name && this.price && this.count) {
                const cargo = {
                    name: this.name,
                    price: this.price,
                    count: this.count
                }

                console.log(cargo);

                axios.post('http://localhost:8075/api/v1/cargo/save', cargo)
                .then(response => {
                    this.idCreated = response.data;
                    // передать данные обратно в AddOrder
                    this.$emit('cargoAdded', this.idCreated);
                })
                .catch(error => {
                    console.error(error);
                });

                setTimeout(() => {
                    this.idCreated = null;
                }, 5000);

                this.name = null
                this.price = null
                this.count = null
            } else {
                if(!this.name) this.errors.push("Name required.")
                if(!this.price) this.errors.push("Price required.")
                if(!this.count) this.errors.push("Count required.")
            }
        }

    },

    beforeMount() {
        this.getAllCargo()
    }

}
</script>
    
<style>

</style>
    