package com.quitq.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(

        @NotNull(message = "User ID is required")
        Long userId,

        @NotBlank(message = "Shipping address is required")
        String shippingAddress,

        @NotNull(message = "Order items are required")
        List<OrderItemRequestDTO> items


) {}