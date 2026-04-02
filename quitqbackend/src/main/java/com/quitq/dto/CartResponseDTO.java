package com.quitq.dto;

import java.util.List;

public record CartResponseDTO(
        Long cartId,
        Long userId,
        List<CartItemResponseDTO> items,
        Double totalPrice
) {}