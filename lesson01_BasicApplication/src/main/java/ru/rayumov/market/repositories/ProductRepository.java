package ru.rayumov.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rayumov.market.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
