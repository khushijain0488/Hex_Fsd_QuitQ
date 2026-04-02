package com.quitq.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long orderId,
        Long userId,
        List<OrderItemResponseDTO> items,
        Double totalAmount,
        String shippingAddress,
        String status,
        LocalDateTime orderedAt
) {}