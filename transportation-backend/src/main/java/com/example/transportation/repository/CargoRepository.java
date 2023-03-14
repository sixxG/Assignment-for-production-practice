package com.example.transportation.repository;

import com.example.transportation.Model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Cargo findById(long id);
}
