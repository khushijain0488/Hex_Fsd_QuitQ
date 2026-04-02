package com.quitq.service;


import com.quitq.repository.CategoryRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Test
    public void getCategoryById_WhenExists_ReturnsCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            categoryService.getCategoryById(1L);
        });

        Assertions.assertEquals("Category  found with id: 1", ex.getMessage());
    }
}
