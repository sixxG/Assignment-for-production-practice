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
}
