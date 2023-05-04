package com.example.transportation.Service;

import com.example.transportation.DTO.OrderDTO_Create;
import com.example.transportation.DTO.OrderDTO_Update;
import com.example.transportation.Model.Cargo;
import com.example.transportation.Model.Order;
import com.example.transportation.Model.OrderStatus;
import com.example.transportation.repository.CargoRepository;
import com.example.transportation.repository.DeliverymanRepository;
import com.example.transportation.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliverymanRepository deliverymanRepository;
    private final CargoRepository cargoRepository;

    public OrderService(OrderRepository orderRepository, DeliverymanRepository deliverymanRepository, CargoRepository cargoRepository) {
        this.orderRepository = orderRepository;
        this.deliverymanRepository = deliverymanRepository;
        this.cargoRepository = cargoRepository;
    }

    public List<Order> getListOrder(String sortBy, int countItemOnPage, int page) {
        long countRows = orderRepository.count();
        double countPage = Math.ceil(countRows / (float) countItemOnPage);

        int start = 0;
        int end = countItemOnPage;
        List<Order> orders;

        if (!sortBy.equals("")) {
            orders = sortOrders(page, countPage, countItemOnPage, end, start, sortBy);
        } else {
            if (page <= countItemOnPage) {
                start = ((page - 1) * countItemOnPage);
                end = (page * countItemOnPage);

                orders = orderRepository.findAll(PageRequest.of(page - 1, (end - start))).stream().toList();
            } else {
                orders = orderRepository.findAll(PageRequest.of(0, (end-start))).stream().toList();
            }
        }

        return orders;
    }

    public List<Order>  getOrdersByStatus(String status, int countItems, int page) {
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

    public long getCountOrders(String status) {
        if (!status.equals("") && !status.equals("All")) {
            return orderRepository.countByStatus(status);
        } else {
            return orderRepository.count();
        }
    }

    public Order getOrderById(long id) {
        return orderRepository.findById(id);
    }

    public Map<String, Object> searchOrder(String fromLocation, String toLocation, String deliveryman,
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

    public boolean completeOrder(long id) {
        Order order = orderRepository.findById(id);
        order.setStatus(OrderStatus.DELIVERED.toString());

        orderRepository.save(order);

        return orderRepository.findById(id) != null;
    }

    public String saveOrder(OrderDTO_Create orderDTO) {
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

    public boolean deleteOrder(long id) {
        orderRepository.deleteById(id);
        return orderRepository.findById(id) == null;
    }

    public boolean updateOrder(OrderDTO_Update orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId());
        order.setStatus(orderDTO.getStatus());
        order.setNote(orderDTO.getNote());

        orderRepository.save(order);

        return orderRepository.findById(orderDTO.getId()) != null;
    }

    public List<Order> sortOrders(int page, double countPage, int countItemOnPage,
                                  int end, int start, String sortBy) {
        String sortField = sortBy.split("_")[0];
        String sortType = sortBy.split("_")[1];

        Page<Order> orders = orderRepository.findAll(PageRequest.of(0, (end-start)));

        if (page <= countPage) {
            start = ((page - 1) * countItemOnPage);
            end = (page * countItemOnPage);

            if (sortType.equals("up")) {
                orders = orderRepository
                        .findAll(PageRequest
                                .of(page - 1, (end - start), Sort.by(Sort.Direction.ASC, sortField)));
            } else {
                orders = orderRepository
                        .findAll(PageRequest
                                .of(page - 1, (end - start), Sort.by(Sort.Direction.DESC, sortField)));
            }
        }

        return orders.stream().toList();
    }
}
