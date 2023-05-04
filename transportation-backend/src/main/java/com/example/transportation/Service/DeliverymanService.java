package com.example.transportation.Service;

import com.example.transportation.Model.Deliveryman;
import com.example.transportation.repository.DeliverymanRepository;
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
    private final DeliverymanRepository deliverymanRepository;

    public DeliverymanService(DeliverymanRepository deliverymanRepository) {
        this.deliverymanRepository = deliverymanRepository;
    }

    public Map<String, Object> getListDeliveryman(String sortBy, int countItemOnPage, int page) {
        long countRows = deliverymanRepository.count();
        double countPage = Math.ceil(countRows / (float) countItemOnPage);

        int start = 0;
        int end = countItemOnPage;
        List<Deliveryman> deliverymans;

        if (!sortBy.equals("")) {
            deliverymans = sortDeliverymans(page, countPage, countItemOnPage, end, start, sortBy);
        } else {
            if (page <= countItemOnPage) {
                start = ((page - 1) * countItemOnPage);
                end = (page * countItemOnPage);

                deliverymans = deliverymanRepository.findAll(PageRequest.of(page - 1, (end - start))).stream().toList();
            } else {
                deliverymans = deliverymanRepository.findAll(PageRequest.of(0, (end-start))).stream().toList();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("deliverymans", deliverymans);
        response.put("count", countRows);

        return response;
    }

    public Set<String> getSetFioDeliverymans() {
        return deliverymanRepository.findAllDistinctFIO();
    }

    public Map<String, Object> getDeliverymanByFIO(String fio, int countItems, int page) {
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

    public Map<String, Object> searchDeliverymans(String phone, String vehicle) {
        List<Deliveryman> searchedDeliveryman = deliverymanRepository.findByPhoneAndVehicleContainingIgnoreCase(phone, vehicle);

        long countRows = searchedDeliveryman.size();

        Map<String, Object> response = new HashMap<>();
        response.put("deliverymans", searchedDeliveryman);
        response.put("count", countRows);

        return response;
    }

    public String saveDeliveryman(Deliveryman deliveryman) {
        return String.valueOf(deliverymanRepository.save(deliveryman).getId());
    }

    public boolean deleteDeliveryman(long id) {
        deliverymanRepository.deleteById(id);
        return deliverymanRepository.findById(id) == null;
    }

    public String updateDeliveryman(Deliveryman deliveryman) {
        deliverymanRepository.save(deliveryman);
        return String.valueOf(deliverymanRepository.save(deliveryman));
    }

    public List<Deliveryman> sortDeliverymans(int page, double countPage, int countItemOnPage,
                                              int end, int start, String sortBy) {

        String sortField = sortBy.split("_")[0];
        String sortType = sortBy.split("_")[1];

        Page<Deliveryman> deliverymans = deliverymanRepository.findAll(PageRequest.of(0, (end-start)));

        if (page <= countPage) {
            start = ((page - 1) * countItemOnPage);
            end = (page * countItemOnPage);

            if (sortType.equals("up")) {
                deliverymans = deliverymanRepository
                        .findAll(PageRequest
                                .of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, sortField)));
            } else {
                deliverymans = deliverymanRepository
                        .findAll(PageRequest
                                .of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, sortField)));
            }
        }

        return deliverymans.stream().toList();
    }

}
