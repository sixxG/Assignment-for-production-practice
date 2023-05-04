package com.example.transportation.Service;

import com.example.transportation.Model.Cargo;
import com.example.transportation.repository.CargoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public Map<String, Object> getListCargos(String sortBy, int countItemOnPage, int page) {
        long countRows = cargoRepository.count();
        double countPage = Math.ceil(countRows / (float) countItemOnPage);

        int start = 0;
        int end = countItemOnPage;

        List<Cargo> cargos;

        if (!sortBy.equals("")) {
            cargos = sortCargos(page, countPage, countItemOnPage, end, start, sortBy);
        } else {
            if (page <= countItemOnPage) {
                start = ((page - 1) * countItemOnPage);
                end = (page * countItemOnPage);

                cargos = cargoRepository.findAll(PageRequest.of(page - 1, (end - start))).stream().toList();
            } else {
                cargos = cargoRepository.findAll(PageRequest.of(0, (end-start))).stream().toList();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("cargos", cargos);
        response.put("count", countRows);

        return response;
    }

    public Set<String> getNamesCargos() {
        return cargoRepository.findAllDistinctNames();
    }

    public Map<String, Object> getCargosByName(String name, int countItems, int page) {
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

    public Cargo getCargoById(long id) {
        return cargoRepository.findById(id);
    }

    public Map<String, Object> searchCargos(String priceFromParam, String priceToParam, String countFromParam,
                                            String countToParam, int countItems, int page) {
        double priceFrom = priceFromParam.equals("") ? 0 : Double.parseDouble(priceFromParam);
        double priceTo = priceToParam.equals("") ? Double.MAX_VALUE : Double.parseDouble(priceToParam);
        int countFrom = countFromParam.equals("") ? 0 : Integer.parseInt(countFromParam);
        int countTo = countToParam.equals("") ? Integer.MAX_VALUE : Integer.parseInt(countToParam);

        int start = 0;
        int end = countItems;
        Set<Cargo> searchedCargos = cargoRepository.findByAllFields(priceFrom, priceTo, countFrom,
                countTo, PageRequest.of(0, Integer.MAX_VALUE));

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

    public String saveCargo(Cargo cargo) {
        return String.valueOf(cargoRepository.save(cargo).getId());
    }

    public boolean deleteCargo(long id) {
        cargoRepository.deleteById(id);
        return cargoRepository.findById(id) == null;
    }

    public boolean updateCargo(Cargo cargo) {
        cargoRepository.save(cargo);
        return cargoRepository.findById(cargo.getId()) != null;
    }

    public List<Cargo> sortCargos(int page,double countPage, int countItemOnPage, int end, int start, String sortBy) {

        String sortField = sortBy.split("_")[0];
        String sortType = sortBy.split("_")[1];

        Page<Cargo> cargos = cargoRepository.findAll(PageRequest.of(0, (end-start)));

        if (page <= countPage) {
            start = ((page - 1) * countItemOnPage);
            end = (page * countItemOnPage);

            if (sortType.equals("up")) {
                cargos = cargoRepository
                        .findAll(PageRequest
                                .of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, sortField)));
            } else {
                cargos = cargoRepository
                        .findAll(PageRequest
                                .of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, sortField)));
            }
        }

        return cargos.stream().toList();
    }
}
