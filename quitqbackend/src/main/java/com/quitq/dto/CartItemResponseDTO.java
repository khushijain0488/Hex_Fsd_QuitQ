package com.quitq.dto;

public record CartItemResponseDTO(
        Long productId,
        String productName,
        Double price,
        Integer quantity,
        Double subTotal
) {}