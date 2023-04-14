package com.example.transportation.Service;

import com.example.transportation.Model.Deliveryman;
import com.example.transportation.repository.DeliverymanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DeliverymanService {
    @Autowired
    private DeliverymanRepository deliverymanRepository;

    public Map<String, Object> listDeliveryman(String sortBy, int countItems, int page) {
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
    public Set<String> getSetFIO() {
        return deliverymanRepository.findAllDistinctFIO();
    }
    public Map<String, Object> getByFIO(String fio, int countItems, int page) {
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
    public Map<String, Object> search(String phone, String vehicle) {
        List<Deliveryman> searchedDeliveryman = deliverymanRepository.findByPhoneAndVehicleContainingIgnoreCase(phone, vehicle);

        long countRows = searchedDeliveryman.size();

        Map<String, Object> response = new HashMap<>();
        response.put("deliverymans", searchedDeliveryman);
        response.put("count", countRows);

        return response;
    }
    public String save(Deliveryman deliveryman) {
        return String.valueOf(deliverymanRepository.save(deliveryman).getId());
    }
    public boolean delete(long id) {
        deliverymanRepository.deleteById(id);
        return deliverymanRepository.findById(id) == null;
    }
    public String update(Deliveryman deliveryman) {
        deliverymanRepository.save(deliveryman);
        return String.valueOf(deliverymanRepository.save(deliveryman));
    }
}
