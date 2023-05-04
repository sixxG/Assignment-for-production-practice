package com.example.transportation.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "deliveryman")
public class Deliveryman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fio;
    private String phone;
    private String vehicle;

    public Deliveryman() {
    }
}
