package com.example.transportation.Service;

import com.example.transportation.DTO.OrderDTO_Create;
import com.example.transportation.DTO.OrderDTO_Update;
import com.example.transportation.Model.Cargo;
import com.example.transportation.Model.Deliveryman;
import com.example.transportation.Model.Order;
import com.example.transportation.Model.OrderStatus;
import com.example.transportation.repository.CargoRepository;
import com.example.transportation.repository.DeliverymanRepository;
import com.example.transportation.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DeliverymanRepository deliverymanRepository;
    @Autowired
    private CargoRepository cargoRepository;

    public List<Order> listOrder(String sortBy, int countItems, int page) {
        long countRows = orderRepository.count();
        double countPage = Math.ceil(countRows / (float) countItems);

        int start = 0;
        int end = countItems;
        Page<Order> orders = orderRepository.findAll(PageRequest.of(0, (end-start)));

        if (page <= countPage) {
            start = ((page - 1) * countItems);
            end = (page * countItems);

            switch (sortBy) {
                case "" -> orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start)));
                case "id_up" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, "id")));
                case "id_down" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, "id")));
                case "number_up" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, "number")));
                case "number_down" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, "number")));
                case "fromLocation_up" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, "fromLocation")));
                case "fromLocation_down" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, "fromLocation")));
                case "toLocation_up" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, "toLocation")));
                case "toLocation_down" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, "toLocation")));
                case "status_up" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, "status")));
                case "status_down" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, "status")));
                case "cargos_up" ->
                        orders = orderRepository.findAllOrderByCargosCountDesc(PageRequest.of(page - 1, (end - start)));
                case "cargos_down" ->
                        orders = orderRepository.findAllOrderByCargosCountAsc(PageRequest.of(page - 1, (end - start)));
                case "deliveryman_up" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, "deliveryman")));
                case "deliveryman_down" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, "deliveryman")));
                case "note_up" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, "note")));
                case "note_down" ->
                        orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, "note")));
            }
        }

        return orders.stream().toList();
    }
    public List<Order>  getByStatus(String status, int countItems, int page) {
        int start = 0;
        int end = countItems;
        List<Order> ordersByStatus = orderRepository.findAllByStatus(status, PageRequest.of(0, (end-start)));

        long countRows = orderRepository.countByStatus(status);
        double countPage = Math.ceil(countRows / (float) countItems);

        if (page <= countPage) {
            start = ((page - 1) * countItems);
            end = (page * countItems);

            ordersByStatus = orderRepository.findAllByStatus(status, PageRequest.of(page - 1, (end - start)));
        }

        return ordersByStatus;
    }
    public long countOrders(String status) {
        if (!status.equals("") && !status.equals("All")) {
            return orderRepository.countByStatus(status);
        } else {
            return orderRepository.count();
        }
    }
    public Order getById(long id) {
        return orderRepository.findById(id);
    }
    public Map<String, Object> search(String fromLocation, String toLocation, String deliveryman,
                                      String note, String cargo, String number, String status,
                                      int countItems, int page) {
        if (status.equals("All")) status = "";

        int start = 0;
        int end = countItems;
        List<Order> searchedOrders = orderRepository.findByAllFieldsContainingIgnoreCase(number, fromLocation,
                toLocation, status,
                note, deliveryman, cargo, PageRequest.of(0, Integer.MAX_VALUE));

        long countRows = searchedOrders.size();
        double countPage = Math.ceil(countRows / (float) countItems);

        if (page <= countPage) {
            start = ((page - 1) * countItems);
            end = (page * countItems);
            searchedOrders = orderRepository.findByAllFieldsContainingIgnoreCase(number, fromLocation,
                    toLocation, status,
                    note, deliveryman, cargo,
                    PageRequest.of(page - 1, (end - start)));
        } else {
            searchedOrders = orderRepository.findByAllFieldsContainingIgnoreCase(number, fromLocation,
                    toLocation, status,
                    note, deliveryman, cargo,
                    PageRequest.of(0, (end - start)));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orders", searchedOrders);
        response.put("count", countRows);

        return response;
    }
    public boolean complete(long id) {
        Order order = orderRepository.findById(id);
        order.setStatus(OrderStatus.DELIVERED.toString());

        orderRepository.save(order);

        return orderRepository.findById(id) != null;
    }
    public String save(OrderDTO_Create orderDTO) {
        Order order = new Order();

        order.setNumber(orderDTO.getNumber());
        order.setFromLocation(orderDTO.getFromLocation());
        order.setToLocation(orderDTO.getToLocation());
        order.setStatus(OrderStatus.CREATED.toString());
        order.setDeliveryman(deliverymanRepository.findByFio(orderDTO.getDeliveryman()));
        order.setNote(orderDTO.getNote());

        List<String> cargosId = Arrays.stream(orderDTO.getCargos().split(" ")).toList();
        Set<Cargo> cargos = cargosId.stream().map(cargo -> cargoRepository.findById(Long.parseLong(cargo))).collect(Collectors.toSet());

        order.setCargos(cargos);

        return String.valueOf(orderRepository.save(order).getId());
    }
    public boolean delete(long id) {
        orderRepository.deleteById(id);
        return orderRepository.findById(id) == null;
    }
    public boolean update(OrderDTO_Update orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId());
        order.setStatus(orderDTO.getStatus());
        order.setNote(orderDTO.getNote());

        orderRepository.save(order);

        return orderRepository.findById(orderDTO.getId()) != null;
    }
}
