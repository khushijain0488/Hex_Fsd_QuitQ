package com.quitq.controller;

import com.quitq.dto.CartRequestDTO;
import com.quitq.dto.CartResponseDTO;
import com.quitq.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    // POST /cart/add
    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(
            @RequestBody @Valid CartRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addToCart(dto));
    }

    // GET /cart/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // DELETE /cart/remove/{cartId}/{productId}
    @DeleteMapping("/remove/{cartId}/{productId}")
    public ResponseEntity<CartResponseDTO> removeFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeFromCart(cartId, productId));
    }

    // PUT /cart/update/{cartId}/{productId}
    @PutMapping("/update/{cartId}/{productId}")
    public ResponseEntity<CartResponseDTO> updateQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(cartId, productId, quantity));
    }
}