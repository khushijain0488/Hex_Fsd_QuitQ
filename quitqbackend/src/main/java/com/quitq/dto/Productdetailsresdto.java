package com.quitq.dto;

import com.quitq.model.Product;

import java.util.List;

public record Productdetailsresdto(
        List<ProductResponseDTO> data,
        long totalElement,
        int totalPages
) {
}
