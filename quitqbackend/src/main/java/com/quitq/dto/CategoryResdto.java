package com.quitq.dto;

import com.quitq.model.Category;

import java.util.List;

public record CategoryResdto(
        List<Category> data,
        long totalElements,
        int totalPages

) {
}
