package com.store.management.tool.storemanagementtool.service;

import com.store.management.tool.storemanagementtool.entity.Product;
import com.store.management.tool.storemanagementtool.exception.ProductMalformatException;
import com.store.management.tool.storemanagementtool.exception.ProductNotFoundException;
import com.store.management.tool.storemanagementtool.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;

    public List<Product> getProducts() {
        LOG.info("Retrieve all products");
        return repository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        LOG.info("Retrieve product by category: {}", category);

        if (!category.matches("[a-zA-Z]+")) throw new ProductMalformatException(category);
        return repository.getProductsByCategory(category);
    }

    public Product getProductById(Integer id) {
        LOG.info("Retrieve product by id: {}", id);

        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product addProduct(Product product) {
        LOG.info("Add product with id: {}", product.getId());
        return repository.save(product);
    }

    public Product updateProduct(Product newProduct, Integer id) {
        LOG.info("Update product with id: {}", id);
        return repository.findById(id)
                .map(product -> {
                    buildProduct(newProduct, product);
                    return repository.save(product);
                }).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public void deleteById(Integer id) {
        LOG.info("Delete product with id: {}", id);
        repository.deleteById(id);
    }

    private void buildProduct(Product newProduct, Product product) {
        product.setCategory(newProduct.getCategory());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setName(newProduct.getName());
    }
}
