package ru.rayumov.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rayumov.market.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
