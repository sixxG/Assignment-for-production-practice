package com.example.transportation.DTO;

import com.example.transportation.Model.Deliveryman;
import com.example.transportation.Model.OrderStatus;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDTO {

    private long number;
    private String fromLocation;
    private String toLocation;
//    private Set<String> cargos = new HashSet<>();
    private String cargos;
    private long deliveryman;
    private String note;
}
