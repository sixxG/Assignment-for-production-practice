<template>

<div style="width: 90%; justify-content: center;margin: 0 auto; margin-top: 5%;">

    <button type="button" class="btn btn-dark" v-on:click="backFromOrderDetails">Back</button>
    <br>
    <br>
    <!-- CRUD info message -->
    <div class="alert alert-success" role="alert"
      v-if="isDeleted || isCompleted || isUpdate">
      <p v-if="isDeleted">Заказ был успешно удалён</p>
      <p v-if="isCompleted">Заказ был успешно завершён</p>
      <p v-if="isUpdate">Заказ был успешно обновлён</p>
      <hr>
      <p class="mb-0">Это сообщение автоматически пропадёт через 3 секунды.</p>
    </div>

    <table class="table table-striped">
    
        <!-- Order field name -->
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Number</th>
                <th scope="col">From Location</th>
                <th scope="col">To Location</th>
                <th scope="col">Status</th>
                <th>Текущее местоположение груза</th>
                <th scope="col">Cargos</th>
                <th scope="col">Deliveryman</th>
                <th scope="col">Note</th>
                <th></th>
            </tr>
        </thead>

        <!-- List orders -->
        <tbody v-if="order">
            <tr>
                <th scope="row">{{ order.id }}</th>
                <td>{{ order.number }}</td>
                <td>{{ order.fromLocation }}</td>
                <td>{{ order.toLocation }}</td>
                <td>
                    <select class="form-control" v-model="order.status">
                        <option selected>{{ order.status }}</option>
                        <option v-for="status in orderStatusWithoutSelected(order.status)" v-bind:key="status">{{ status }}</option>
                    </select>
                </td>

                <td>{{ currentLocation }}</td>

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
                <textarea class="form-control" v-model="order.note"></textarea>
                </td>

            </tr>
        </tbody>

    </table>

    <div v-if="order">
        <deleteOrder :id="order.id" @orderDeleted="onOrderDeleted" style="margin-right: 15px;"></deleteOrder>
        <completeOrder :id="order.id" @orderCompleted="onOrderCompleted" style="margin-right: 15px;"></completeOrder>
        <UpdateOrder :order="order" @orderUpdated="onOrderUpdated" ref="UpdateOrder" style="margin-bottom: 5px;"></UpdateOrder>

        <button @click="changeStatus()" type="button" class="btn btn-info" style="margin-left: 15px; width: 20%; margin-bottom: 5px;">{{ action }}</button>
    </div>

</div>

</template>

<script>
import axios from "axios"

import DeleteOrder from "./DeleteOrder.vue";
import CompleteOrder from './CompleteOrder.vue';
import UpdateOrder from "./UpdateOrder.vue";

export default {
    name: 'DetailsOrder',

    components: {
        DeleteOrder,
        CompleteOrder,
        UpdateOrder,
    },

    data() {
        return {
            order: null,
            orderID: null,
            action: null,

            currentStatus: null,
            currentLocation: null,

            isUpdate: false,
            isCompleted: false,
            isDeleted: false,
        }
    },

    methods: {

        changeStatus () {
            switch (this.order.status) {
                case 'Создан':
                    this.order.status = 'Комплектуется';
                    this.currentStatus = this.order.status;
                    break;
                case 'Комплектуется':
                    this.order.status = 'Готов к отправке';
                    this.currentStatus = this.order.status;
                    break;
                case 'Готов к отправке':
                    this.order.status = 'Отправлен';
                    this.currentStatus = this.order.status;
                    break;
                case 'Отправлен':
                    this.order.status = 'Доставлен';
                    this.currentStatus = this.order.status;
                    break;
                case 'Доставлен':
                    this.order.status = 'Создан';
                    this.currentStatus = this.order.status;
                    break;
            }

            this.$refs.UpdateOrder.update(this.order);

            this.isUpdate  = true;
            setTimeout(() => {
                this.isUpdate = false;
            }, 3000);
        },

        moveCargo () {
            switch (this.order.status) {
                case 'Создан':
                    this.currentLocation = 'Заказ отдыхает на складе в ' + this.order.fromLocation + 'e';
                    this.action = "Начать комплектовку";
                    break;
                case 'Комплектуется':
                    this.currentLocation = 'Собираем заказ на складе в НП ' + this.order.fromLocation;
                    this.action = "Приготовить к отправке";
                    break;
                case 'Готов к отправке':
                    this.currentLocation = 'Ожидает отправки со склада в НП ' + this.order.fromLocation;
                    this.action = "Отправить заказ";
                    break;
                case 'Отправлен':
                    this.currentLocation = 'На пол пути в НП ' + this.order.toLocation;
                    this.action = "Подтвердить доставку";
                    break;
                case 'Доставлен':
                    this.currentLocation = 'Прибыл в ' + this.order.toLocation;
                    this.action = "Переделать заказ";//
                    break;
                default: this.currentLocation = "Где-то";
            }
        },

        onOrderDeleted(isDeleted) {
            this.isDeleted = isDeleted;

            setTimeout(() => {
                this.isDeleted = false;
                this.backFromOrderDetails();
            }, 3000);
        },

        onOrderCompleted(isCompleted) {
            this.isCompleted = isCompleted;
            this.getOrderById(this.orderID);
            setTimeout(() => {
                this.isCompleted = false;
            }, 3000);
        },

        onOrderUpdated(isUpdate) {
            this.isUpdate = isUpdate;
            this.getOrderById(this.orderID);
            setTimeout(() => {
                this.isUpdate = false;
            }, 3000);
        },

        getOrderById(id) {
            axios
            .get('http://localhost:8075/api/v1/order/getById/' + id)
            .then((response) => {
                this.order = response.data;
                this.currentStatus = this.order.status;
                this.moveCargo();
            })
        },

        orderStatusWithoutSelected(statusWithout) {
            const statuses = ["Создан", "Комплектуется", "Готов к отправке", "Отправлен", "Доставлен"];

            return statuses.filter(status => status !== statusWithout);
        },


        backFromOrderDetails() {
            this.$emit('backFromOrderDetails', true);
        }
    },

    watch: {
        currentStatus() {
            this.moveCargo();
        },
    },

    created() {
        const windowData = Object.fromEntries(
            new URL(window.location).searchParams.entries()
        );

        if (windowData.id) {
            this.orderID = windowData.id;
        }

        this.getOrderById(this.orderID);
    },
}
</script>

<style>

</style>
