package com.example.transportation.Controller;

import com.example.transportation.Model.Cargo;
import com.example.transportation.repository.CargoRepository;
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
@RequestMapping("/api/v1/cargo")
@CrossOrigin
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping("/getAllCargos")
    public Map<String, Object> getAllCargo(@RequestParam(required = false, defaultValue = "5") int countItems,
                                           @RequestParam(required = false, defaultValue = "") String sortBy,
                                           @RequestParam int page) {

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

    @GetMapping("/getCargosName")
    public Set<String> getCargosName() {
        return cargoRepository.findAllDistinctNames();
    }

    @GetMapping("/getCargosByName")
    public Map<String, Object> getCargosByName(@RequestParam(required = false, defaultValue = "") String name,
                                       @RequestParam(required = false, defaultValue = "5") int countItems,
                                       @RequestParam int page) {

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

    @GetMapping("/searchCargo")
    public Map<String, Object> searchCargos(@RequestParam(required = false, defaultValue = "") String priceFromParam,
                                            @RequestParam(required = false, defaultValue = "") String priceToParam,
                                            @RequestParam(required = false, defaultValue = "") String countFromParam,
                                            @RequestParam(required = false, defaultValue = "") String countToParam,
                                            @RequestParam(required = false, defaultValue = "5") int countItems,
                                            @RequestParam int page) {
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

    @PostMapping("/save")
    public String saveDeliveryman(@RequestBody Cargo cargo) {
        long id = cargoRepository.save(cargo).getId();
        return String.valueOf(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteDeliveryman(@PathVariable(value = "id") long id) {
        cargoRepository.deleteById(id);
        return cargoRepository.findById(id) == null;
    }

    @PostMapping("/update")
    public boolean updateCargo(@RequestBody Cargo cargo) {
        cargoRepository.save(cargo);
        return cargoRepository.findById(cargo.getId()) != null;
    }

}
