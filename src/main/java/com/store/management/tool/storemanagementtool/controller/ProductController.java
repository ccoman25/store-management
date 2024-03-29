package com.store.management.tool.storemanagementtool.controller;

import com.store.management.tool.storemanagementtool.entity.Product;
import com.store.management.tool.storemanagementtool.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/findAll")
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return service.getProductsByCategory(category);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/id/{id}")
    public Product getProductBId(@PathVariable Integer id) {
        return service.getProductById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/insert")
    public Product addProduct(@RequestBody @Valid Product product) {
        return service.addProduct(product);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody @Valid Product newProduct, @PathVariable Integer id) {
        return service.updateProduct(newProduct, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
