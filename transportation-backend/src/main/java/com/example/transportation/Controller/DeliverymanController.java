package com.example.transportation.Controller;

import com.example.transportation.Model.Deliveryman;
import com.example.transportation.repository.DeliverymanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveryman")
@CrossOrigin
public class DeliverymanController {

    @Autowired
    private DeliverymanRepository deliverymanRepository;

    @PostMapping("/save")
    public String saveDeliveryman(@RequestBody Deliveryman deliveryman) {

        long id = deliverymanRepository.save(deliveryman).getId();

        return String.valueOf(id);
    }

    @GetMapping("/getAllDeliverymans")
    public List<Deliveryman> getAllDeliveryman() {

        return deliverymanRepository.findAll();
    }

    @PostMapping("/update")
    public String updateDeliveryman(@RequestBody Deliveryman deliveryman) {

        long id = deliverymanRepository.save(deliveryman).getId();

        return String.valueOf(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteDeliveryman(@PathVariable(value = "id") long id) {

        deliverymanRepository.deleteById(id);

        return deliverymanRepository.findById(id) == null;
    }

}
