package com.quitq.dto;

public record OrderItemResponseDTO(
        Long productId,
        String productName,
        Integer quantity,
        Double priceAtPurchase,
        Double subTotal
) {}