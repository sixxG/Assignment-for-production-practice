package com.example.transportation.Controller;

import com.example.transportation.DTO.OrderDTO_Create;
import com.example.transportation.DTO.OrderDTO_Update;
import com.example.transportation.Model.Cargo;
import com.example.transportation.Model.Order;
import com.example.transportation.Model.OrderStatus;
import com.example.transportation.repository.CargoRepository;
import com.example.transportation.repository.DeliverymanRepository;
import com.example.transportation.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DeliverymanRepository deliverymanRepository;
    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    public Order getById(@PathVariable(value = "id") long id) {
        return orderRepository.findById(id);
    }

    @PostMapping("/save")
    public String saveOrder(@RequestBody OrderDTO_Create orderDTO) {
        Order order = new Order();

        order.setNumber(orderDTO.getNumber());
        order.setFromLocation(orderDTO.getFromLocation());
        order.setToLocation(orderDTO.getToLocation());
        order.setStatus(OrderStatus.CREATED.toString());
        order.setDeliveryman(deliverymanRepository.findById(orderDTO.getDeliveryman()));
        order.setNote(orderDTO.getNote());

        List<String> cargosId = Arrays.stream(orderDTO.getCargos().split(" ")).toList();
        Set<Cargo> cargos = cargosId.stream().map(cargo -> cargoRepository.findById(Long.parseLong(cargo))).collect(Collectors.toSet());

        order.setCargos(cargos);

        long id = orderRepository.save(order).getId();

        return String.valueOf(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteOrder(@PathVariable(value = "id") long id) {
        orderRepository.deleteById(id);

        return orderRepository.findById(id) == null;
    }

    @PostMapping("/complete/{id}")
    public boolean completeOrder(@PathVariable(value = "id") long id) {

        Order order = orderRepository.findById(id);
        order.setStatus(OrderStatus.DELIVERED.toString());

        orderRepository.save(order);

        return orderRepository.findById(id) != null;
    }

    @PostMapping("/update")
    public boolean updateOrder(@RequestBody OrderDTO_Update orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId());
        order.setStatus(orderDTO.getStatus());
        order.setNote(orderDTO.getNote());

        orderRepository.save(order);

        return orderRepository.findById(orderDTO.getId()) != null;
    }

}
