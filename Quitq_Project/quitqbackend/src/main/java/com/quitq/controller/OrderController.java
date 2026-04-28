package com.quitq.controller;

import com.quitq.dto.OrderPageResponsedto;
import com.quitq.dto.OrderRequestDTO;
import com.quitq.dto.OrderResponseDTO;
import com.quitq.dto.Statdto;
import com.quitq.model.Order;
import com.quitq.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    // POST /orders/place
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @Valid  @RequestBody OrderRequestDTO dto, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(dto,principal.getName()));
    }
//get all orders
    @GetMapping("/v1/getAll")
    public ResponseEntity<List<OrderResponseDTO>>GetAllOrder(){
        return ResponseEntity.ok(orderService.GetAllOrder());
    }
    @GetMapping("/v2/getAll")
    public ResponseEntity<OrderPageResponsedto>getAllOrderV2(@RequestParam(value = "page",defaultValue = "0",required = false)int page,
                                                             @RequestParam(value = "size",defaultValue = "5",required = false)int size){
        return ResponseEntity.ok(orderService.getAllOrderV2(page,size));
    }
    // GET /orders/{orderId}
    @GetMapping("/getById/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

//    getting orderes of logged in user
    @GetMapping("/getAllOrdersOfUser")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(
           Principal prinicipal) {
        return ResponseEntity.ok(orderService.getOrdersByUser(prinicipal.getName()));
    }

    // GET /orders/seller/{sellerId}
    @GetMapping("/api/v1/seller/{sellerId}")
    public ResponseEntity<List<Order>> getOrdersBySellerId(
            @PathVariable Long sellerId) {
        return ResponseEntity.ok(orderService.getOrdersBySellerId(sellerId));
    }
    @GetMapping("/api/v2/loggedInseller")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersBySeller(
           Principal principal) {
        return ResponseEntity.ok(orderService.getOrdersBySeller(principal.getName()));
    }

    // PUT /orders/status/{orderId}?status=SHIPPED
    @PutMapping("/status/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

}