package com.igor.fake_api.infrastructure.repository;

import com.igor.fake_api.infrastructure.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByTitle(String title);
    Product findByTitle(String title);

}
