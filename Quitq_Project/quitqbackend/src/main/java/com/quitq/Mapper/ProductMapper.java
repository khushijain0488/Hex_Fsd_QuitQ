package com.quitq.Mapper;

import com.quitq.dto.ProductRequestDTO;
import com.quitq.dto.ProductResponseDTO;
import com.quitq.enums.ProductStatus;
import com.quitq.model.Category;
import com.quitq.model.Product;

import com.quitq.model.User;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

//    // RequestDTO → Entity
public static Product toEntity(ProductRequestDTO dto, Category category, User seller, String imageUrl) {
    Product product = new Product();
    product.setName(dto.name());
    product.setDescription(dto.description());
    product.setPrice(dto.price());
    product.setStockQuantity(dto.stockQuantity());
    product.setImageUrl(imageUrl);
    product.setCategory(category);
    product.setSeller(seller);
    product.setStatus(ProductStatus.AVAILABLE);
    return product;
}

    // Entity → ResponseDTO
    public static ProductResponseDTO toDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getCategory().getName(),
                product.getSeller().getUsername(),
                product.getStatus().name(),
                product.getCreatedAt()
        );
    }
}