package com.example.transportation.repository;

import com.example.transportation.Model.Deliveryman;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DeliverymanRepository extends JpaRepository<Deliveryman, Long> {

    Deliveryman findById(long id);

    @Query("SELECT DISTINCT d.fio FROM Deliveryman d")
    Set<String> findAllDistinctFIO();

    Long countByFioContaining(String fio);

    @Query("SELECT d FROM Deliveryman d WHERE lower(d.fio) LIKE lower(concat('%', :fio, '%')) ")
    List<Deliveryman> findByFio(String fio, PageRequest pageRequest);

    @Query("SELECT d FROM Deliveryman d WHERE lower(d.phone) LIKE CONCAT('%', lower(:phone), '%') AND lower(d.vehicle) LIKE CONCAT('%', lower(:vehicle), '%')")
    List<Deliveryman> findByPhoneAndVehicleContainingIgnoreCase(String phone, String vehicle);
}
