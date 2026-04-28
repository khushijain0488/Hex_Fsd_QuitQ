package com.quitq.service;

import com.quitq.model.Category;
import com.quitq.repository.CategoryRepository;
import com.quitq.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;


    @Test
    public void getByIdTestWhenCategoryExists() {

        Assertions.assertNotNull(categoryService);

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Electronics", result.getName());
        Assertions.assertEquals("All electronic items", result.getDescription());

        Mockito.verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void getByIdTestWhenNotFound() {

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(RuntimeException.class, () -> {
            categoryService.getById(99L);
        });

        Assertions.assertEquals("The given id is invalid", e.getMessage());

        Mockito.verify(categoryRepository, times(1)).findById(99L);
    }

    @Test
    public void getAllCategoryTest() {

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");
        category1.setDescription("All electronic items");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Clothing");
        category2.setDescription("All clothing items");

        List<Category> mockCategoryList = List.of(category1, category2);

        when(categoryRepository.findAll()).thenReturn(mockCategoryList);

        List<Category> result = categoryService.getAllCategory();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Electronics", result.get(0).getName());
        Assertions.assertEquals("Clothing", result.get(1).getName());

        Mockito.verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void getAllCategoryTestWhenEmpty() {

        when(categoryRepository.findAll()).thenReturn(List.of());

        List<Category> result = categoryService.getAllCategory();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        Mockito.verify(categoryRepository, times(1)).findAll();
    }
}