<template>

    <div style="display: block; margin: 0 auto; width: 70%; justify-content: center;">
    
        <button class="btn btn-success" v-on:click="isShowAddDeliveryman = !isShowAddDeliveryman">Создать Доставщика</button>
    
        <form v-if="isShowAddDeliveryman" v-on:submit.prevent="saveDeliveryman">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="validationDefaultName">ФИО</label>
                    <input type="text" v-model="fio" class="form-control" id="validationDefaultАio" placeholder="ФИО доставщика" required>
                    </div>
                    <div class="col-md-4 mb-3">
                    <label for="validationDefaultPrice">Телефон</label>
                    <input type="phone" v-model="phone" class="form-control" id="validationDefaultЗhone" placeholder="Телефон" required step="any">
                    </div>
                    <div class="col-md-4 mb-3">
                    <label for="validationDefaultCount">Транспортное средство</label>
                    <div class="input-group">
                        <input type="text" v-model="vehicle" class="form-control" id="validationDefaultМehicle" placeholder="ТС" required>
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
                <div class="col-md-1 mb-3">
                    <button class="btn btn-danger" type="reset">Clear form</button>
                </div>
            </div>
        </form>
    </div>
    
    </template>
        
    <script>
    import axios from "axios"
    
    export default {
        name: 'AddDeliveryman',
    
    
        data() {
        return {
            fio: null,
            phone: null,
            vehicle: null,
            errors: [],
            idCreated: null,
            isShowAddDeliveryman: false,
        }
        },
    
        methods: {
    
        saveDeliveryman() {
    
            if(this.fio && this.phone && this.vehicle) {
                const deliveryman = {
                    fio: this.fio,
                    phone: this.phone,
                    vehicle: this.vehicle
                }
    
                axios.post('http://localhost:8075/api/v1/deliveryman/save', deliveryman)
                .then(response => {
                    this.idCreated = response.data;
                    this.$emit('deliverymanAdded', this.idCreated);
                })
                .catch(error => {
                    console.error(error);
                });
    
                setTimeout(() => {
                    this.idCreated = null;
                }, 5000);
    
                this.fio = null
                this.phone = null
                this.vehicle = null
            } else {
                if(!this.fio) this.errors.push("FIO required.")
                if(!this.phone) this.errors.push("Phone required.")
                if(!this.vehicle) this.errors.push("Vehicle required.")
            }
        }
    
        }
    
    }
    </script>
        
    <style>
    
    </style>
        