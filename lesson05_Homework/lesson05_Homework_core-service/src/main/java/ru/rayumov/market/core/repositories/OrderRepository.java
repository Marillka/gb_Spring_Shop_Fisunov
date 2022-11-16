package ru.rayumov.market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rayumov.market.core.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
