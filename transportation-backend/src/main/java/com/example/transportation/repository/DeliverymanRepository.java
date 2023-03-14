package com.example.transportation.repository;

import com.example.transportation.Model.Deliveryman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverymanRepository extends JpaRepository<Deliveryman, Long> {

    Deliveryman findById(long id);

}
