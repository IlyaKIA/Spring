package com.example.SpringMVC.repository;


import com.example.SpringMVC.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();
    Optional<Product> findById(long id);
    Product addProduct(Product product);


}
