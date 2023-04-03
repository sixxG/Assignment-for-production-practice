package com.example.transportation.repository;

import com.example.transportation.Model.Cargo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Cargo findById(long id);

    @Query("SELECT DISTINCT c.name FROM Cargo c")
    Set<String> findAllDistinctNames();
    Long countByName(String name);
    List<Cargo> findByName(String name, PageRequest pageRequest);

    @Query("SELECT c FROM Cargo c WHERE (:priceFrom IS NULL OR c.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR c.price <= :priceTo) " +
            "AND (:countFrom IS NULL OR c.count >= :countFrom) " +
            "AND (:countTo IS NULL OR c.count <= :countTo)")
    Set<Cargo> findByAllFields(Double priceFrom, Double priceTo,
                                   Integer countFrom, Integer countTo, PageRequest pageRequest);
}
