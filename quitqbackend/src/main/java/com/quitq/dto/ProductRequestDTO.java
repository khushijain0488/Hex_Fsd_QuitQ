package com.quitq.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDTO(

//        @NotBlank(message = "Product name is required")
        String name,

        String description,

        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price cannot be negative")
        Double price,

        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock cannot be negative")
        Integer stockQuantity,

        String imageUrl,

        @NotNull(message = "Category ID is required")
        Long categoryId,

        @NotNull(message = "Seller ID is required")
        Long sellerId
) {}