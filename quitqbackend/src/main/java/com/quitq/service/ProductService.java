package com.quitq.service;

import com.quitq.dto.ProductRequestDTO;
import com.quitq.dto.ProductResponseDTO;
import com.quitq.dto.Productdetailsresdto;
import com.quitq.enums.ProductStatus;
import com.quitq.Mapper.ProductMapper;
import com.quitq.model.Category;
import com.quitq.model.Product;
import com.quitq.model.User;

import com.quitq.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
   private final UserService userService;
    private final ProductMapper productMapper;

    // Get all products
    public Productdetailsresdto getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productList = productRepository.findAll(pageable);
        long totalElements = productList.getTotalElements();
        int totalPages = productList.getTotalPages();
        List<ProductResponseDTO> track = productList.toList().stream().map(ProductMapper::toDTO).toList();
        return new Productdetailsresdto(track, totalElements, totalPages);


    }

    // Search by name
    public Productdetailsresdto searchByName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productList = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        List<ProductResponseDTO>track=productList.toList().stream().map(ProductMapper::toDTO).toList();
        return new Productdetailsresdto(track,productList.getTotalElements(),productList.getTotalPages());

    }

    // Filter by category
    public Productdetailsresdto filterByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productList = productRepository.findByCategory_Id(categoryId, pageable);
        List<ProductResponseDTO>track=productList.toList().stream().map(ProductMapper::toDTO).toList();
        return new Productdetailsresdto(track,productList.getTotalElements(),productList.getTotalPages());
    }

    // Filter by price range
    public Productdetailsresdto filterByPrice(Double min, Double max, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productList= productRepository.findByPriceBetween(min, max, pageable);
        List<ProductResponseDTO>track=productList.toList().stream().map(ProductMapper::toDTO).toList();
        return new Productdetailsresdto(track,productList.getTotalElements(),productList.getTotalPages());
    }

    // Get single product
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
return product;
    }

    // Create product
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Category category = categoryService.getById(dto.categoryId());

        User seller = userService.getById(dto.sellerId());

        Product product = productMapper.toEntity(dto, category, seller);
        return productMapper.toDTO(productRepository.save(product));
    }

    // Update product
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        Category category = categoryService.getById(dto.categoryId());

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(category);
        product.setStatus(dto.stockQuantity() == 0
                ? ProductStatus.OUT_OF_STOCK
                : ProductStatus.AVAILABLE);

        return productMapper.toDTO(productRepository.save(product));
    }

    // Delete product
    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
        return "Product deleted successfully";
    }
    // get product entity by id (used by CartService)
    public Product getProductById_Entity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }


}
