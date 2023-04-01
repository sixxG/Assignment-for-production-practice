package com.example.transportation.repository;

import com.example.transportation.Model.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);
    List<Order> findAllByStatus(String status, PageRequest pageRequest);

    List<Order> findAllByStatus(String status);

    Long countByStatus(String status);
}
