package com.store.management.tool.storemanagementtool.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "Product")
public class Product {
    @Id
    private Integer id;
    @NotNull(message = "Product name should not be null")
    private String name;
    @Min(100)
    private Double price;
    @Pattern(regexp = "[a-zA-Z]+", message = "Category name should contain only letters")
    private String category;
    private String description;
}