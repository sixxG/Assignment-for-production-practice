package com.example.transportation.Controller;

import com.example.transportation.Model.Cargo;
import com.example.transportation.Model.Order;
import com.example.transportation.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cargo")
@CrossOrigin
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping("/getAllCargos")
    public List<Cargo> getAllCargo() {

        return cargoRepository.findAll();
    }

}
