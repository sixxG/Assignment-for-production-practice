package com.example.transportation.Controller;

import com.example.transportation.Model.Deliveryman;
import com.example.transportation.repository.DeliverymanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/deliveryman")
@CrossOrigin
public class DeliverymanController {

    @Autowired
    private DeliverymanRepository deliverymanRepository;

    @GetMapping("/getAllDeliveryman")
    public Map<String, Object> getAllDeliveryman(@RequestParam(required = false, defaultValue = "5") int countItems,
                                           @RequestParam(required = false, defaultValue = "") String sortBy,
                                           @RequestParam int page) {

        long countRows = deliverymanRepository.count();
        double countPage = Math.ceil(countRows / (float) countItems);

        int start = 0;
        int end = countItems;
        Page<Deliveryman> deliveryman = deliverymanRepository.findAll(PageRequest.of(0, (end-start)));

        if (page <= countPage) {
            start = ((page - 1) * countItems);
            end = (page * countItems);

            switch (sortBy) {
                case "" -> deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start)));
                case "id_up" ->
                        deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.ASC, "id")));
                case "id_down" ->
                        deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.DESC, "id")));
                case "fio_up" ->
                        deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.ASC, "fio")));
                case "fio_down" ->
                        deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.DESC, "fio")));
                case "phone_up" ->
                        deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.ASC, "phone")));
                case "phone_down" ->
                        deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.DESC, "phone")));
                case "vehicle_up" ->
                        deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.ASC, "vehicle")));
                case "vehicle_down" ->
                        deliveryman = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.DESC, "vehicle")));
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("deliverymans", deliveryman.stream().toList());
        response.put("count", countRows);

        return response;
    }

    @GetMapping("/getDeliverymansFIO")
    public Set<String> getDeliverymansFIO() {
        return deliverymanRepository.findAllDistinctFIO();
    }
    @GetMapping("/getDiliverymansByFIO")
    public Map<String, Object> getDiliverymansByFIO(@RequestParam(required = false, defaultValue = "") String fio,
                                               @RequestParam(required = false, defaultValue = "5") int countItems,
                                               @RequestParam int page) {

        Long countRows = deliverymanRepository.countByFioContaining(fio);
        double countPage = Math.ceil(countRows / (float) countItems);

        int start = 0;
        int end = countItems;
        List<Deliveryman> deliverymanList = deliverymanRepository.findByFio(fio, PageRequest.of(0, (end-start)));

        if (page <= countPage) {
            start = ((page - 1) * countItems);
            end = (page * countItems);

            deliverymanList = deliverymanRepository.findByFio(fio, PageRequest.of(page - 1, (end - start)));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("diliverymans", deliverymanList);
        response.put("count", countRows);

        return response;
    }
    @GetMapping("/searchDeliveryman")
    public Map<String, Object> searchDeliveryman(@RequestParam(required = false, defaultValue = "") String phone,
                                            @RequestParam(required = false, defaultValue = "") String vehicle) {
        List<Deliveryman> searchedDeliveryman = deliverymanRepository.findByPhoneAndVehicleContainingIgnoreCase(phone, vehicle);

        long countRows = searchedDeliveryman.size();

        Map<String, Object> response = new HashMap<>();
        response.put("deliverymans", searchedDeliveryman);
        response.put("count", countRows);

        return response;
    }
    @PostMapping("/save")
    public String saveDeliveryman(@RequestBody Deliveryman deliveryman) {
        long id = deliverymanRepository.save(deliveryman).getId();
        return String.valueOf(id);
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
