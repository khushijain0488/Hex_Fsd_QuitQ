package com.quitq.Mapper;

import com.quitq.dto.OrderItemResponseDTO;
import com.quitq.dto.OrderResponseDTO;
import com.quitq.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class OrderMapper {
    // mapper
    public  OrderResponseDTO ToDTO(Order order) {
        List<OrderItemResponseDTO> items = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtPurchase(),
                        item.getPriceAtPurchase() * item.getQuantity()
                )).toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getId(),
                items,
                order.getTotalAmount(),
                order.getShippingAddress(),
                order.getStatus().name(),
                order.getOrderedAt()
        );
    }
}

