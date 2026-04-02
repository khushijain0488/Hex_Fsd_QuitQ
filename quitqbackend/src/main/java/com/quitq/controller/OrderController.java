package com.quitq.controller;

import com.quitq.dto.OrderRequestDTO;
import com.quitq.dto.OrderResponseDTO;
import com.quitq.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // POST /orders/place
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @RequestBody @Valid Order

                    RequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(dto));
    }

    // GET /orders/{orderId}
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // GET /orders/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // GET /orders/seller/{sellerId}
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersBySeller(
            @PathVariable Long sellerId) {
        return ResponseEntity.ok(orderService.getOrdersBySeller(sellerId));
    }

    // PUT /orders/status/{orderId}?status=SHIPPED
    @PutMapping("/status/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}