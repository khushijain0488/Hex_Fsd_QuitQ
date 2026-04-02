package com.quitq.service;

import com.quitq.Mapper.OrderMapper;
import com.quitq.dto.OrderItemRequestDTO;
import com.quitq.dto.OrderItemResponseDTO;
import com.quitq.dto.OrderRequestDTO;
import com.quitq.dto.OrderResponseDTO;
import com.quitq.enums.OrderStatus;
import com.quitq.enums.ProductStatus;
import com.quitq.model.Order;
import com.quitq.model.OrderItem;
import com.quitq.model.Product;
import com.quitq.model.User;
import com.quitq.repository.OrderRepository;
import com.quitq.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;

    // place order
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO dto) {
//step-1 finding the user
        User user = userService.getById(dto.userId());
//        step-2 after getting user create the order
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(dto.shippingAddress());
        order.setTotalAmount(0.0);
        order.setStatus(OrderStatus.PENDING);
//        step-3 after creating the order lets save in db
        Order savedOrder = orderRepository.save(order);
//        step-4 iterating through order item
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;
        for (OrderItemRequestDTO listitems : dto.items()) {
            Product product = productRepository.findById(listitems.productId()).orElseThrow(
                    () -> new RuntimeException("The product is not avalibale for this particular id")
            );
//            if the productstatus id out of stock then throw an exception
            if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
                throw new RuntimeException("The product is not avalible" + product.getName());
            }
//            if the product quantity is less than required
            if (listitems.quantity() > product.getStockQuantity()) {
                throw new RuntimeException("The stock of this product is not available");
            }
            OrderItem orderitem = new OrderItem();
            orderitem.setOrder(savedOrder);
            orderitem.setProduct(product);
            orderitem.setQuantity(listitems.quantity());
            orderitem.setPriceAtPurchase(product.getPrice());
            orderItems.add(orderitem);
//            calulating the totalAmount
            totalAmount += product.getPrice() * listitems.quantity();
//            deducting the stock quantity
            product.setStockQuantity(
                    product.getStockQuantity() - listitems.quantity()

            );
            if (product.getStockQuantity() == 0) {
                product.setStatus(ProductStatus.OUT_OF_STOCK);
            }
            productRepository.save(product);

        }
        savedOrder.setTotalAmount(totalAmount);
        savedOrder.setOrderItems(orderItems);
//        i have to do save also this in db
        Order finalOrder = orderRepository.save(savedOrder);

        return orderMapper.ToDTO(finalOrder);


    }

    // get order by id
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException(
                        "Order not found with id: " + orderId));
        return orderMapper.ToDTO(order);
    }

    // get all orders by user
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUser_Id(userId);
        return orders.stream().map(orderMapper::ToDTO).toList();
    }

    // get all orders by seller
    public List<OrderResponseDTO> getOrdersBySeller(Long sellerId) {
        List<Order> orders = orderRepository.findBySellerId(sellerId);
        return orders.stream().map(orderMapper::ToDTO).toList();
    }

    // update order status
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException(
                        "Order not found with id: " + orderId));

        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        return orderMapper.ToDTO(orderRepository.save(order));
    }
}

