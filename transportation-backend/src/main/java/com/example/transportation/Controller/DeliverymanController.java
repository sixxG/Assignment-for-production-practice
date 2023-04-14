package com.example.transportation.Controller;

import com.example.transportation.Model.Deliveryman;
import com.example.transportation.Service.DeliverymanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/deliveryman")
@CrossOrigin
public class DeliverymanController {

    @Autowired
    private DeliverymanService deliverymanService;

    @GetMapping("/getAllDeliveryman")
    public Map<String, Object> getAllDeliveryman(@RequestParam(required = false, defaultValue = "5") int countItems,
                                           @RequestParam(required = false, defaultValue = "") String sortBy,
                                           @RequestParam int page) {
        return deliverymanService.listDeliveryman(sortBy, countItems, page);
    }
    @GetMapping("/getDeliverymansFIO")
    public Set<String> getDeliverymansFIO() {
        return deliverymanService.getSetFIO();
    }
    @GetMapping("/getDiliverymansByFIO")
    public Map<String, Object> getDiliverymansByFIO(@RequestParam(required = false, defaultValue = "") String fio,
                                               @RequestParam(required = false, defaultValue = "5") int countItems,
                                               @RequestParam int page) {
        return deliverymanService.getByFIO(fio, countItems, page);
    }
    @GetMapping("/searchDeliveryman")
    public Map<String, Object> searchDeliveryman(@RequestParam(required = false, defaultValue = "") String phone,
                                            @RequestParam(required = false, defaultValue = "") String vehicle) {
        return deliverymanService.search(phone, vehicle);
    }
    @PostMapping("/save")
    public String saveDeliveryman(@RequestBody Deliveryman deliveryman) {
        return deliverymanService.save(deliveryman);
    }
    @PostMapping("/update")
    public String updateDeliveryman(@RequestBody Deliveryman deliveryman) {
        return deliverymanService.update(deliveryman);
    }
    @DeleteMapping("/delete/{id}")
    public boolean deleteDeliveryman(@PathVariable(value = "id") long id) {
        return deliverymanService.delete(id);
    }

}
