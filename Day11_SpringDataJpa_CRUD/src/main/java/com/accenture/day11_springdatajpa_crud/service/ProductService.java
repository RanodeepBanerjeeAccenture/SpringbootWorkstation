package com.accenture.day11_springdatajpa_crud.service;

import com.accenture.day11_springdatajpa_crud.model.Product;
import com.accenture.day11_springdatajpa_crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    public Product save(Product product) {
        return productRepository.save(product);
    }
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

}
