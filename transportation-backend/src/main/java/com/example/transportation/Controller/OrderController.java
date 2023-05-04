package com.example.transportation.Controller;

import com.example.transportation.DTO.OrderDTO_Create;
import com.example.transportation.DTO.OrderDTO_Update;
import com.example.transportation.Model.Order;
import com.example.transportation.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrder(@RequestParam(required = false, defaultValue = "5") int countItems,
                                   @RequestParam(required = false, defaultValue = "") String sortBy,
                                   @RequestParam int page) {
        return orderService.getListOrder(sortBy, countItems, page);
    }

    @GetMapping("/getOrdersByStatus")
    public List<Order> getOrdersByStatus(@RequestParam(required = false, defaultValue = "5") int countItems,
                                         @RequestParam String status,
                                         @RequestParam int page) {
        return orderService.getOrdersByStatus(status, countItems, page);
    }

    @GetMapping("/getCountOrders")
    public long getCountOrders(@RequestParam(required = false, defaultValue = "") String status) {
        return orderService.getCountOrders(status);
    }

    @GetMapping("/getById/{id}")
    public Order getById(@PathVariable(value = "id") long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("searchOrder")
    public Map<String, Object> searchOrders(@RequestParam(required = false, defaultValue = "") String fromLocation,
                                            @RequestParam(required = false, defaultValue = "") String toLocation,
                                            @RequestParam(required = false, defaultValue = "") String deliveryman,
                                            @RequestParam(required = false, defaultValue = "") String note,
                                            @RequestParam(required = false, defaultValue = "") String cargo,
                                            @RequestParam(required = false, defaultValue = "") String number,
                                            @RequestParam(required = false, defaultValue = "") String status,
                                            @RequestParam(required = false, defaultValue = "5") int countItems,
                                            @RequestParam int page) {
        return orderService.searchOrder(fromLocation, toLocation, deliveryman, note, cargo, number, status, countItems, page);
    }
    @PostMapping("/complete/{id}")
    public boolean completeOrder(@PathVariable(value = "id") long id) {
        return orderService.completeOrder(id);
    }

    @PostMapping("/save")
    public String saveOrder(@RequestBody OrderDTO_Create orderDTO) {
        return orderService.saveOrder(orderDTO);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteOrder(@PathVariable(value = "id") long id) {
        return orderService.deleteOrder(id);
    }

    @PostMapping("/update")
    public boolean updateOrder(@RequestBody OrderDTO_Update orderDTO) {
        return orderService.updateOrder(orderDTO);
    }

}
