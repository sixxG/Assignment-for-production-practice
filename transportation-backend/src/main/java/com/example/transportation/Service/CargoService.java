package com.example.transportation.Service;

import com.example.transportation.Model.Cargo;
import com.example.transportation.repository.CargoRepository;
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
public class CargoService {
    @Autowired
    private CargoRepository cargoRepository;

    public Map<String, Object> listCargos(String sortBy, int countItems, int page) {
        long countRows = cargoRepository.count();
        double countPage = Math.ceil(countRows / (float) countItems);

        int start = 0;
        int end = countItems;
        Page<Cargo> cargos = cargoRepository.findAll(PageRequest.of(0, (end-start)));

        if (page <= countPage) {
            start = ((page - 1) * countItems);
            end = (page * countItems);

            switch (sortBy) {
                case "" -> cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start)));
                case "id_up" ->
                        cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.ASC, "id")));
                case "id_down" ->
                        cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.DESC, "id")));
                case "name_up" ->
                        cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.ASC, "name")));
                case "name_down" ->
                        cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.DESC, "name")));
                case "price_up" ->
                        cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.ASC, "price")));
                case "price_down" ->
                        cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.DESC, "price")));
                case "count_up" ->
                        cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.ASC, "count")));
                case "count_down" ->
                        cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start),
                                Sort.by(Sort.Direction.DESC, "count")));
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("cargos", cargos.stream().toList());
        response.put("count", countRows);

        return response;
    }
    public Set<String> getNames() {
        return cargoRepository.findAllDistinctNames();
    }
    public Map<String, Object> getByName(String name, int countItems, int page) {
        Long countRows = cargoRepository.countByName(name);
        double countPage = Math.ceil(countRows / (float) countItems);

        int start = 0;
        int end = countItems;
        List<Cargo> cargos = cargoRepository.findByName(name, PageRequest.of(0, (end-start)));

        if (page <= countPage) {
            start = ((page - 1) * countItems);
            end = (page * countItems);

            cargos = cargoRepository.findByName(name, PageRequest.of(page - 1, (end - start)));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("cargos", cargos.stream().toList());
        response.put("count", countRows);

        return response;
    }
    public Cargo getById(long id) {
        return cargoRepository.findById(id);
    }
    public Map<String, Object> search(String priceFromParam, String priceToParam, String countFromParam, String countToParam,
                             int countItems, int page) {
        double priceFrom = priceFromParam.equals("") ? 0 : Double.parseDouble(priceFromParam);
        double priceTo = priceToParam.equals("") ? Double.MAX_VALUE : Double.parseDouble(priceToParam);
        int countFrom = countFromParam.equals("") ? 0 : Integer.parseInt(countFromParam);
        int countTo = countToParam.equals("") ? Integer.MAX_VALUE : Integer.parseInt(countToParam);

        int start = 0;
        int end = countItems;
        Set<Cargo> searchedCargos = cargoRepository.findByAllFields(priceFrom, priceTo,
                countFrom, countTo, PageRequest.of(0, Integer.MAX_VALUE));

        long countRows = searchedCargos.size();
        double countPage = Math.ceil(countRows / (float) countItems);

        if (page <= countPage) {
            start = ((page - 1) * countItems);
            end = (page * countItems);
            searchedCargos = cargoRepository.findByAllFields(priceFrom, priceTo,
                    countFrom, countTo, PageRequest.of(page - 1, (end - start)));
        } else {
            searchedCargos = cargoRepository.findByAllFields(priceFrom, priceTo,
                    countFrom, countTo, PageRequest.of(0, (end - start)));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("cargos", searchedCargos);
        response.put("count", countRows);

        return response;
    }
    public String save(Cargo cargo) {
        return String.valueOf(cargoRepository.save(cargo).getId());
    }
    public boolean delete(long id) {
        cargoRepository.deleteById(id);
        return cargoRepository.findById(id) == null;
    }
    public boolean update(Cargo cargo) {
        cargoRepository.save(cargo);
        return cargoRepository.findById(cargo.getId()) != null;
    }
}
