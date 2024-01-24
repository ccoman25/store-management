package com.store.management.tool.storemanagementtool.service;

import com.store.management.tool.storemanagementtool.entity.Product;
import com.store.management.tool.storemanagementtool.exception.ProductMalformatException;
import com.store.management.tool.storemanagementtool.exception.ProductNotFoundException;
import com.store.management.tool.storemanagementtool.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        if (!category.matches("[a-zA-Z]+")) throw new ProductMalformatException(category);
        return repository.getProductsByCategory(category);
    }

    public Product getProductById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Product newProduct, Integer id) {
        return repository.findById(id)
                .map(product -> {
                    buildProduct(newProduct, product);
                    return repository.save(product);
                }).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private void buildProduct(Product newProduct, Product product) {
        product.setCategory(newProduct.getCategory());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setName(newProduct.getName());
    }
}
