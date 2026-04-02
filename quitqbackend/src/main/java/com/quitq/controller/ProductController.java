package com.quitq.controller;

import com.quitq.dto.ProductRequestDTO;
import com.quitq.dto.ProductResponseDTO;
import com.quitq.dto.Productdetailsresdto;
import com.quitq.model.Product;
import com.quitq.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

   private final ProductService productService;

    // GET /products?page=0&size=10&sort=price,asc
   @GetMapping
    public Productdetailsresdto getAllProducts(
            @RequestParam(value="page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "size", defaultValue = "10",required = false) int size) {
       return productService.getAllProducts(page,size);
    }

    // GET /products/search?keyword=phone&page=0&size=10
    @GetMapping("/search")
    public Productdetailsresdto searchByName(
            @RequestParam String keyword,
            @RequestParam(value="page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "size", defaultValue = "10",required = false) int size) {


        return productService.searchByName(keyword,page,size);
    }

    // GET /products/filter/category/{categoryId}
    @GetMapping("/filter/category/{categoryId}")
    public Productdetailsresdto filterByCategory(
            @PathVariable Long categoryId,
            @RequestParam(value="page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "size", defaultValue = "10",required = false) int size) {


       return productService.filterByCategory(categoryId,page,size);
    }

    // GET /products/filter/price?min
    @GetMapping("/filter/price")
    public Productdetailsresdto filterByPrice(
            @RequestParam Double min,
            @RequestParam Double max,
            @RequestParam(value="page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "size", defaultValue = "10",required = false) int size) {

        return productService.filterByPrice(min, max, page,size);
    }

    // GET /products/{id}
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // POST /products
    @PostMapping("/add")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody @Valid ProductRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(dto));
    }

    // PUT /products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    // DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}

