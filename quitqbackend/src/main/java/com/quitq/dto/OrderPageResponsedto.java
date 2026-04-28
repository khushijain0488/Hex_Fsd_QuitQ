package com.quitq.dto;

import java.util.List;

public record OrderPageResponsedto(
        List<OrderResponseDTO> data,
        Long totalelement,
        int totalPages
) {
}
