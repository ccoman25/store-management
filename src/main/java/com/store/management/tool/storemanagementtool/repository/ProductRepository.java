package com.store.management.tool.storemanagementtool.repository;

import com.store.management.tool.storemanagementtool.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {
    List<Product> getProductsByCategory(String category);
}
