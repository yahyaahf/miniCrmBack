package com.example.minicrm.repository;

import com.example.minicrm.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
}
