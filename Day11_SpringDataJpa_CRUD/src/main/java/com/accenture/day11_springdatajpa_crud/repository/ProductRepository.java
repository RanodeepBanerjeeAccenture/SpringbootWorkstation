package com.accenture.day11_springdatajpa_crud.repository;

import com.accenture.day11_springdatajpa_crud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
