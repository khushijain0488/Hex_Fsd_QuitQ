package com.quitq.dto;

import com.quitq.enums.OrderStatus;

public record Statdto(
        OrderStatus status,
        Long size
) {
}
