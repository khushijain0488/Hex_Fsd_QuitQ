package com.quitq.service;

import com.quitq.dto.CategoryReqdto;
import com.quitq.dto.CategoryResdto;
import com.quitq.execption.ResourceNotFoundException;
import com.quitq.model.Category;

import com.quitq.model.Product;
import com.quitq.repository.CategoryRepository;
import com.quitq.repository.ProductRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public Category getById(@NotNull(message = "Category ID is required") Long id) {
        return categoryRepository.findById(id).orElseThrow(
                ()->new RuntimeException("The given id is invalid")
        );
    }

    public CategoryResdto getAllCategory(int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Category> categories_of_product= categoryRepository.findAll(pageable);
        return new CategoryResdto(categories_of_product.toList(),categories_of_product.getTotalElements(),
                categories_of_product.getTotalPages());
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("The given id is invalid"));
    }

    public void AddingCategories(Category category) {
        categoryRepository.save(category);
    }

    public void UpdatingCategoriesById(long id,CategoryReqdto categoryReqdto) {
//        finding with id the categiry is exist or not
        Category category=getCategoryById(id);
//        if it got then update the category
        category.setName(categoryReqdto.name());
        category.setDescription(categoryReqdto.desciption());
//        save this updation in db
        categoryRepository.save(category);

    }


    public void deleteCategoryById(long id) {
        // check category exists first
        getCategoryById(id);

        // get all products linked to this category
        List<Product> products = productRepository.findByCat_id(id);
        System.out.println(products);

        // if no products linked then delete
        if (products.isEmpty()) {
            categoryRepository.deleteById(id);
        } else {

            throw new RuntimeException("Cannot delete category because products are linked to it");
        }
    }
}
