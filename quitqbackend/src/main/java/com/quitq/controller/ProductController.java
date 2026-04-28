package com.quitq.controller;

import com.quitq.dto.ProductRequestDTO;
import com.quitq.dto.ProductResponseDTO;
import com.quitq.dto.Productdetailsresdto;
import com.quitq.model.Product;
import com.quitq.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

   private final ProductService productService;

//   simple get all products
    @GetMapping("/v1/get")
    public List<Product> getAll(){
        return productService.getAll();
    }

    // GET /products?page=0&size=10&sort=price,asc
   @GetMapping("/v2/get")
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
    @GetMapping("/get-by-id/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }





    // DELETE /products/{id}
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeProduct(
            @PathVariable Long productId,
            Principal principal) {
        productService.deactivateProduct(productId, principal.getName());
        return ResponseEntity.ok("Product deactivated successfully");
    }
//    getting all product by seller username
    @GetMapping("/get-product")
    public ResponseEntity<List<Product>>getAllProductBySeller(Principal principal){
        return ResponseEntity.ok(productService.getAllProductBySeller(principal.getName()));
    }

//uploading document
@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<ProductResponseDTO> createProduct(
        @RequestPart("product") @Valid ProductRequestDTO dto,
        @RequestPart(value = "image", required = false) MultipartFile image,
        Principal principal
) {
    return ResponseEntity.ok(productService.createProduct(dto, image, principal.getName()));
}

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") @Valid ProductRequestDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(productService.updateProduct(id, dto, image));
    }
}

