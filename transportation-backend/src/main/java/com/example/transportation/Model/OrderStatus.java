package com.example.transportation.Model;

public enum OrderStatus {
    CREATED ("Создан"),
    COMPLETING ("Комплектуется"),
    READY_TO_SHIP ("Готов к отправке"),
    SHIPPED ("Отправлен"),
    DELIVERED ("Доставлен");

    private String title;
    OrderStatus(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title;
    }
}
