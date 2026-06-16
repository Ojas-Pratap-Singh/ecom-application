package com.app.ecom.repository;

import com.app.ecom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
