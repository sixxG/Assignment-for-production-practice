package com.example.transportation.Controller;

import com.example.transportation.Model.Cargo;
import com.example.transportation.Service.CargoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/cargo")
@CrossOrigin
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/getAllCargos")
    public Map<String, Object> getAllCargo(@RequestParam(required = false, defaultValue = "5") int countItems,
                                           @RequestParam(required = false, defaultValue = "") String sortBy,
                                           @RequestParam int page) {
        return cargoService.getListCargos(sortBy, countItems, page);
    }

    @GetMapping("/getCargosName")
    public Set<String> getCargosName() {
        return cargoService.getNamesCargos();
    }

    @GetMapping("/getCargosByName")
    public Map<String, Object> getCargosByName(@RequestParam(required = false, defaultValue = "") String name,
                                               @RequestParam(required = false, defaultValue = "5") int countItems,
                                               @RequestParam int page) {
        return cargoService.getCargosByName(name, countItems, page);
    }

    @GetMapping("/getById")
    public Cargo getCargoById(@RequestParam long id) {
        return cargoService.getCargoById(id);
    }

    @GetMapping("/searchCargo")
    public Map<String, Object> searchCargos(@RequestParam(required = false, defaultValue = "") String priceFromParam,
                                            @RequestParam(required = false, defaultValue = "") String priceToParam,
                                            @RequestParam(required = false, defaultValue = "") String countFromParam,
                                            @RequestParam(required = false, defaultValue = "") String countToParam,
                                            @RequestParam(required = false, defaultValue = "5") int countItems,
                                            @RequestParam int page) {
        return cargoService.searchCargos(priceFromParam, priceToParam, countFromParam, countToParam, countItems, page);
    }

    @PostMapping("/save")
    public String saveCargo(@RequestBody Cargo cargo) {
        return cargoService.saveCargo(cargo);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCargo(@PathVariable(value = "id") long id) {
        return cargoService.deleteCargo(id);
    }

    @PostMapping("/update")
    public boolean updateCargo(@RequestBody Cargo cargo) {
        return cargoService.updateCargo(cargo);
    }

}
