package com.quitq.dto;

import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        Integer stockQuantity,
        String imageUrl,
        String categoryName,
        String sellerName,
        String status,
        LocalDateTime createdAt
) {}