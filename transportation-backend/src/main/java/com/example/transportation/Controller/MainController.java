package com.example.transportation.Controller;

import com.example.transportation.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping()
    public String mainPage() {

        return cargoRepository.findAll().toString();
    }

}
