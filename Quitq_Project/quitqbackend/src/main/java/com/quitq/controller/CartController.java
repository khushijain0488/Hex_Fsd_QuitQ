package com.quitq.controller;

import com.quitq.dto.CartRequestDTO;
import com.quitq.dto.CartResponseDTO;
import com.quitq.model.Cart;
import com.quitq.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;

    // POST /cart/add
    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(
            @RequestBody @Valid CartRequestDTO dto, Principal principal) {
        cartService.addToCart(dto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(getCart(principal));
    }


//getting cart by username or loggedinUser
@GetMapping("/api/v2/get")
    public CartResponseDTO getCart(Principal principal) {
        return (cartService.getCart(principal.getName()));
    }

    // DELETE /cart/clear
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Principal principal) {
        cartService.clearCart(principal.getName());
        return ResponseEntity.ok("Cart cleared successfully");
    }
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartResponseDTO> removeFromCart(
            @PathVariable Long productId,
            Principal principal) {

        CartResponseDTO cart = cartService.removeFromCart(productId, principal.getName());
        return ResponseEntity.ok(cart);
    }


    // PUT /cart/update/{cartId}/{productId}
    @PutMapping("/update/{productId}")
    public ResponseEntity<CartResponseDTO> updateQuantity(
            Principal principal,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(principal.getName(), productId, quantity));
    }
}