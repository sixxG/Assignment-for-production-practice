package com.example.transportation.Controller;

import com.example.transportation.Model.Cargo;
import com.example.transportation.Model.Deliveryman;
import com.example.transportation.Model.Order;
import com.example.transportation.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public String saveDeliveryman(@RequestBody Cargo cargo) {

        long id = cargoRepository.save(cargo).getId();

        return String.valueOf(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteDeliveryman(@PathVariable(value = "id") long id) {

        cargoRepository.deleteById(id);
        boolean deleteCargo = cargoRepository.findById(id) == null;

        return deleteCargo;
    }

}
