package com.quitq.service;

import com.quitq.Mapper.OrderMapper;
import com.quitq.dto.*;
import com.quitq.enums.OrderStatus;
import com.quitq.enums.ProductStatus;
import com.quitq.model.Order;
import com.quitq.model.OrderItem;
import com.quitq.model.Product;
import com.quitq.model.User;
import com.quitq.repository.OrderRepository;
import com.quitq.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;

    // place order

    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO dto, String name) {

        log.info("Placing order for user: {}", name);

        // step-1 finding the user
        User user = userService.getLoggedInUser(name);
        log.debug("Found user with id: {}", user.getId());

        // step-2 create the order
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(user.getAddress());
        order.setTotalAmount(0.0);
        order.setStatus(OrderStatus.PENDING);
        log.debug("Order object created for user id: {}", user.getId());

        // step-3 save order in db
        Order savedOrder = orderRepository.save(order);
        log.info("Initial order saved in DB with id: {}", savedOrder.getId());

        // step-4 iterating through order items
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (OrderItemRequestDTO listitems : dto.items()) {

            log.debug("Looking up product with id: {}", listitems.productId());

            Product product = productRepository.findById(listitems.productId())
                    .orElseThrow(() -> {
                        log.error("Product not found with id: {}", listitems.productId());
                        return new RuntimeException("The product is not available for this particular id");
                    });

            log.debug("Product found: '{}', current status: {}", product.getName(), product.getStatus());

            // if the product status is out of stock
            if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
                log.warn("Product '{}' is OUT_OF_STOCK, cannot proceed with order", product.getName());
                throw new RuntimeException("The product is not available: " + product.getName());
            }

            // if the product quantity is less than required
            if (listitems.quantity() > product.getStockQuantity()) {
                log.warn("Insufficient stock for product '{}'. Requested: {}, Available: {}",
                        product.getName(), listitems.quantity(), product.getStockQuantity());
                throw new RuntimeException("The stock of this product is not available");
            }

            // creating order item
            OrderItem orderitem = new OrderItem();
            orderitem.setOrder(savedOrder);
            orderitem.setProduct(product);
            orderitem.setQuantity(listitems.quantity());
            orderitem.setPriceAtPurchase(product.getPrice());
            orderItems.add(orderitem);
            log.debug("OrderItem created — product: '{}', quantity: {}, priceAtPurchase: {}",
                    product.getName(), listitems.quantity(), product.getPrice());

            // calculating total amount
            totalAmount += product.getPrice() * listitems.quantity();
            log.debug("Running total after adding '{}': {}", product.getName(), totalAmount);

            // deducting stock quantity
            product.setStockQuantity(product.getStockQuantity() - listitems.quantity());
            log.debug("Stock deducted for product '{}'. Remaining stock: {}",
                    product.getName(), product.getStockQuantity());

            if (product.getStockQuantity() == 0) {
                product.setStatus(ProductStatus.OUT_OF_STOCK);
                log.warn("Product '{}' stock reached 0, marking as OUT_OF_STOCK", product.getName());
            }

            productRepository.save(product);
            log.debug("Product '{}' saved with updated stock", product.getName());
        }

        // setting final values on saved order
        savedOrder.setTotalAmount(totalAmount);
        savedOrder.setOrderItems(orderItems);

        // final save
        Order finalOrder = orderRepository.save(savedOrder);
        log.info("Order placed successfully — OrderId: {}, UserId: {}, TotalAmount: {}",
                finalOrder.getId(), user.getId(), totalAmount);

        return OrderMapper.ToDTO(finalOrder);
    }

    // get order by id
    public OrderResponseDTO getOrderById(Long orderId) {
        log.info("Fetching order with id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", orderId);
                    return new RuntimeException("Order not found with id: " + orderId);
                });
        return OrderMapper.ToDTO(order);
    }

    // get all orders by user
    public List<OrderResponseDTO> getOrdersByUser(String name) {
        log.info("Fetching all orders for user: {}", name);
        User user=userService.getLoggedInUser(name);
        List<Order> orders = orderRepository.findByUser_Id(user.getId());
        return orders.stream().map(OrderMapper::ToDTO).toList();
    }

    // get all orders by seller
    public List<OrderResponseDTO> getOrdersBySeller(String username) {
        log.info("Fetching all orders for seller: {}", username);
        User seller=userService.getLoggedInUser(username);
        List<Order> orders = orderRepository.findBySellerId(seller.getId());
        return orders.stream().map(OrderMapper::ToDTO).toList();
    }

    // update order status
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {

        log.info("Request received to update order status — OrderId: {}, NewStatus: {}", orderId, status);

        // finding the order by id
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", orderId);
                    return new RuntimeException("Order not found with id: " + orderId);
                });

        log.debug("Order found — OrderId: {}, CurrentStatus: {}", order.getId(), order.getStatus());

        // capturing old status for logging
        OrderStatus oldStatus = order.getStatus();

        // updating the status
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        log.debug("Order status changed — OrderId: {}, OldStatus: {}, NewStatus: {}",
                orderId, oldStatus, order.getStatus());

        // saving the updated order
        Order updatedOrder = orderRepository.save(order);
        log.info("Order status updated successfully — OrderId: {}, UpdatedStatus: {}",
                updatedOrder.getId(), updatedOrder.getStatus());

        return OrderMapper.ToDTO(order);
    }

    public List<Order> getOrdersBySellerId(Long sellerId) {
        log.info("Fetching orders by sellerId: {}", sellerId);
    User seller =userService.getById(sellerId);
    List<Order>orders=orderRepository.findBySellerId(sellerId);
    return orders;
    }


    public List<OrderResponseDTO> GetAllOrder() {
        List<Order>orders= orderRepository.findAll();
        List<OrderResponseDTO>orderResponseDTOList=orders.stream().map(OrderMapper::ToDTO).toList();
        return orderResponseDTOList;
    }

    public OrderPageResponsedto getAllOrderV2(int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Order> orderList=orderRepository.getAllOrderV2(pageable);
        List<OrderResponseDTO>orderResponseDTOList=orderList.toList().stream().map(OrderMapper::ToDTO).toList();
        return new OrderPageResponsedto(orderResponseDTOList,orderList.getTotalElements(),orderList.getTotalPages());
    }
}

