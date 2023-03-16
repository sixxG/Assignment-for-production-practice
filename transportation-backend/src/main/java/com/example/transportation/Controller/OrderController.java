package com.example.transportation.Controller;

import com.example.transportation.Model.Order;
import com.example.transportation.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrder() {

        return orderRepository.findAll();
    }

}
