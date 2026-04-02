package com.quitq.controller;

import com.quitq.dto.CategoryReqdto;
import com.quitq.dto.CategoryResdto;
import com.quitq.model.Category;
import com.quitq.repository.CategoryRepository;
import com.quitq.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    //    finding all category
    @GetMapping("/get-all")
    public CategoryResdto getAllCategory
    (@RequestParam(value="page",required = false,defaultValue = "0")int page,
     @RequestParam(value = "size",required = false,defaultValue = "5")int size){
        return categoryService.getAllCategory(page,size);

    }
//    finding category by its id
    @GetMapping("/get/{id}")
    public Category getCategoryById(@PathVariable long id){
        return categoryService.getCategoryById(id);
    }
//    creating new categories and adding in db
    @PostMapping("/add")
    public ResponseEntity<?>AddingCategories(@RequestBody Category category){
        categoryService.AddingCategories(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//    updating the details of categories based on given id
    @PutMapping("/update/{id}")
    public ResponseEntity<?>UpdatingCategoriesById(@PathVariable long id,
                                                   @RequestBody CategoryReqdto categoryReqdto){
        categoryService.UpdatingCategoriesById(id,categoryReqdto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
//deleteing the category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteCategoryById(@PathVariable long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
