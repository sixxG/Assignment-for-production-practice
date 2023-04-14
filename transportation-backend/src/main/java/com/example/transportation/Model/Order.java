package com.example.transportation.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "orderTB")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long number;
    private String fromLocation;
    private String toLocation;

    private String status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "orderTB_cargo",
            joinColumns = @JoinColumn(name = "orderTB_id"),
            inverseJoinColumns = @JoinColumn(name = "cargo_id")
    )
    private Set<Cargo> cargos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryman_id")
    private Deliveryman deliveryman;

    private String note;
}
