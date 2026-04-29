package com.fx.repository.jpa;

import com.fx.dto.jpa.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
