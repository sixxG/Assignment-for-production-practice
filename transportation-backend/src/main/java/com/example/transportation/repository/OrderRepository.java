package com.example.transportation.repository;

import com.example.transportation.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findById(long id);

}
