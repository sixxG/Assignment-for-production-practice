package com.example.transportation.Controller;

import com.example.transportation.Model.Deliveryman;
import com.example.transportation.repository.CargoRepository;
import com.example.transportation.repository.DeliverymanRepository;
import com.example.transportation.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class MainController {

    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private DeliverymanRepository deliverymanRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/cargos")
    public String cargosPage() {

        return cargoRepository.findAll().toString();
    }

    @GetMapping("/deliverymans")
    public List<Deliveryman> deliverymanPage() {

        return deliverymanRepository.findAll();
    }

    @GetMapping("/orders")
    public String ordersPage() {

        return orderRepository.findAll().toString();
    }

}
