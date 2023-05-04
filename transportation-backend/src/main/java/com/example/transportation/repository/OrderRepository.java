package com.example.transportation.repository;

import com.example.transportation.Model.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);

    List<Order> findAllByStatus(String status, PageRequest pageRequest);

    Long countByStatus(String status);

    @Query("SELECT o FROM Order o WHERE CAST(o.number AS string) LIKE CONCAT('%', :number, '%') " +
            "AND lower(o.fromLocation) LIKE lower(concat('%', :fromLocation, '%')) " +
            "AND lower(o.toLocation) LIKE lower(concat('%', :toLocation, '%')) " +
            "AND lower(o.status) LIKE lower(concat('%', :status, '%')) " +
            "AND lower(o.note) LIKE lower(concat('%', :note, '%')) " +
            "AND lower(o.deliveryman.fio) LIKE lower(concat('%', :deliveryman, '%')) " +
            "AND EXISTS (SELECT c FROM o.cargos c WHERE lower(c.name) LIKE lower(concat('%', :cargo, '%')))")
    List<Order> findByAllFieldsContainingIgnoreCase(String number, String fromLocation,
                                                    String toLocation, String status,
                                                    String note, String deliveryman, String cargo,
                                                    PageRequest pageRequest);
}
