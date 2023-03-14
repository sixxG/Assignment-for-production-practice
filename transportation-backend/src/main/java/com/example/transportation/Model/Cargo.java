package com.example.transportation.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double price;
    private int count;

    public Cargo() {}

    public Cargo(String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
