package com.example.transportation.DTO;

import lombok.Data;

@Data
public class OrderDTO_Create {
    private long number;
    private String fromLocation;
    private String toLocation;
    private String cargos;
    private long deliveryman;
    private String note;
}
