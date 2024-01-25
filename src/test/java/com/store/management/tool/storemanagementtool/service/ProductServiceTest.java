package com.store.management.tool.storemanagementtool.service;

import com.store.management.tool.storemanagementtool.entity.Product;
import com.store.management.tool.storemanagementtool.exception.ProductMalformatException;
import com.store.management.tool.storemanagementtool.exception.ProductNotFoundException;
import com.store.management.tool.storemanagementtool.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository mockRepository;

    @InjectMocks
    private ProductService service;

    @Test
    public void getProducts_ok() {
        when(mockRepository.findAll()).thenReturn(buildProducts());

        var actualProducts = service.getProducts();

        assertEquals(2, actualProducts.size());
        verify(mockRepository).findAll();
    }

    @Test
    public void getProductsByCategory_ok() {
        var category = "phone";
        when(mockRepository.getProductsByCategory(category))
                .thenReturn(List.of(buildProducts().get(0)));

        var actualResult = service.getProductsByCategory(category);

        assertEquals(1, actualResult.size());
        assertEquals(category, actualResult.get(0).getCategory());
        verify(mockRepository).getProductsByCategory(category);
    }

    @Test(expected = ProductMalformatException.class)
    public void getProductsByCategory_throwException_whenProductMalformatException() {
        var category = "phone1";

        service.getProductsByCategory(category);

        verify(mockRepository).getProductsByCategory(category);
    }

    @Test
    public void getProductById_ok() {
        var product = buildProducts().get(0);
        var id = 1;
        when(mockRepository.findById(id)).thenReturn(Optional.of(product));

        var actualResult = service.getProductById(id);

        assertNotNull(actualResult);
        assertEquals(product.getCategory(), actualResult.getCategory());
        verify(mockRepository).findById(id);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductById_throwException_whenProductNotFound() {
        var id = 1;
        when(mockRepository.findById(id)).thenThrow(ProductNotFoundException.class);

        service.getProductById(id);

        verify(mockRepository).findById(id);
    }

    @Test
    public void addProduct_ok() {
        var product = buildProducts().get(0);
        when(mockRepository.save(product)).thenReturn(product);

        var actualProduct = service.addProduct(product);

        assertNotNull(actualProduct);
        assertEquals(product, actualProduct);
        verify(mockRepository).save(product);
    }

    @Test
    public void updateProduct_ok() {
        var product = buildProducts().get(0);
        var newProduct = product;
        newProduct.setCategory("newCategory");
        var id = 1;
        when(mockRepository.findById(id)).thenReturn(Optional.of(product));
        when(mockRepository.save(newProduct)).thenReturn(newProduct);

        var actualResult = service.updateProduct(newProduct, id);

        assertNotNull(actualResult);
        assertEquals(newProduct.getCategory(), actualResult.getCategory());
        verify(mockRepository).findById(id);
        verify(mockRepository).save(newProduct);
    }

    @Test(expected = ProductNotFoundException.class)
    public void updateProduct_throwException_whenProductNotFound() {
        var product = buildProducts().get(0);
        when(mockRepository.findById(product.getId())).thenThrow(ProductNotFoundException.class);

        service.updateProduct(product, product.getId());

        verify(mockRepository).findById(product.getId());
        verify(mockRepository, never()).save(product);
    }

    @Test
    public void deleteById_ok() {
        var id = 1;

        service.deleteById(id);

        verify(mockRepository).deleteById(id);
    }

    private List<Product> buildProducts() {
        var product1 = Product.builder()
                .id(1)
                .category("phone")
                .name("OnePlus")
                .description("A smart phone")
                .price(400.0)
                .build();
        var product2 = Product.builder()
                .id(2)
                .category("furniture")
                .name("chair")
                .description("It ensure high standards of quality, safety, resistance and reliability")
                .price(100.0)
                .build();
        return List.of(product1, product2);
    }
}
