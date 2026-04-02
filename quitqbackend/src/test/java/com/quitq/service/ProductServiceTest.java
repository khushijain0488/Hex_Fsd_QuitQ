package com.quitq.service;

import com.quitq.dto.ProductResponseDTO;
import com.quitq.dto.Productdetailsresdto;
import com.quitq.Mapper.ProductMapper;
import com.quitq.model.Category;
import com.quitq.model.Product;
import com.quitq.model.User;
import com.quitq.enums.ProductStatus;
import com.quitq.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @Mock
    private ProductMapper productMapper;

//helper finction which build product
    private Product buildProduct(Long id, String name, Double price, Long categoryId, String categoryName) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        User seller = new User();
        seller.setId(1L);
        seller.setName("Test Seller");

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription("Test description");
        product.setPrice(price);
        product.setStockQuantity(10);
        product.setImageUrl("http://image.url");
        product.setCategory(category);
        product.setSeller(seller);
        product.setStatus(ProductStatus.AVAILABLE);
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

//function to create dto of product
    private ProductResponseDTO buildResponseDTO(Long id, String name, Double price) {
        return new ProductResponseDTO(
                id, name, "Test description", price,
                10, "http://image.url",
                "Electronics", "Test Seller",
                ProductStatus.AVAILABLE.name(),
                LocalDateTime.now()
        );
    }



    @Test
    public void getAllProducts_ReturnsCorrectPageData() {
        Product p1 = buildProduct(1L, "Phone", 999.0, 1L, "Electronics");
        Product p2 = buildProduct(2L, "Laptop", 1499.0, 1L, "Electronics");
        List<Product> products = List.of(p1, p2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Product> page = new PageImpl<>(products, pageable, 2);

        when(productRepository.findAll(pageable)).thenReturn(page);

        Productdetailsresdto result = productService.getAllProducts(0, 2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.data().size());
        Assertions.assertEquals(2L, result.totalElement());
        Assertions.assertEquals(1, result.totalPages());

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    public void getAllProducts_WhenEmpty_ReturnsEmptyList() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(productRepository.findAll(pageable)).thenReturn(emptyPage);

        Productdetailsresdto result = productService.getAllProducts(0, 5);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.data().size());
        Assertions.assertEquals(0L, result.totalElement());
    }



    @Test
    public void getProductById_WhenExists_ReturnsProduct() {
        Product product = buildProduct(1L, "Phone", 999.0, 1L, "Electronics");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Phone", result.getName());
        Assertions.assertEquals(999.0, result.getPrice());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void getProductById_WhenNotFound_ThrowsException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            productService.getProductById(99L);
        });

        Assertions.assertEquals("Product not found with id: 99", ex.getMessage());
        verify(productRepository, times(1)).findById(99L);
    }



    @Test
    public void searchByName_WhenKeywordMatches_ReturnsProducts() {
        Product p1 = buildProduct(1L, "iPhone", 999.0, 1L, "Electronics");
        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> page = new PageImpl<>(List.of(p1), pageable, 1);

        when(productRepository.findByNameContainingIgnoreCase("iphone", pageable)).thenReturn(page);

        Productdetailsresdto result = productService.searchByName("iphone", 0, 5);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.data().size());
        Assertions.assertEquals(1L, result.totalElement());

        verify(productRepository, times(1)).findByNameContainingIgnoreCase("iphone", pageable);
    }

    @Test
    public void searchByName_WhenNoMatch_ReturnsEmpty() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(productRepository.findByNameContainingIgnoreCase("xyz", pageable)).thenReturn(emptyPage);

        Productdetailsresdto result = productService.searchByName("xyz", 0, 5);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.data().size());
        Assertions.assertEquals(0L, result.totalElement());
    }




}