package com.quitq.service;

import com.quitq.dto.CartItemResponseDTO;
import com.quitq.dto.CartRequestDTO;
import com.quitq.dto.CartResponseDTO;
import com.quitq.enums.ProductStatus;
import com.quitq.model.Cart;
import com.quitq.model.CartItem;
import com.quitq.model.Product;
import com.quitq.model.User;
import com.quitq.repository.CartItemRepository;
import com.quitq.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    // add to cart
    public CartResponseDTO addToCart(CartRequestDTO dto, String name) {

        // step 1 - find user
        User user = userService.getLoggedInUser(name);

        // step 2 - find product
        Product product = productService.getProductById_Entity(dto.productId());

        // step 3 - check product is available
        if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
            throw new RuntimeException("Product is out of stock!");
        }

        // step 4 - find cart by userId or create new cart
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // step 5 - check if product already in cart
        Optional<CartItem> existingItem = cartItemRepository
                .findByCart_IdAndProduct_Id(cart.getId(), dto.productId());

        if (existingItem.isPresent()) {
            // step 6a - product already in cart, update quantity
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + dto.quantity());
            cartItemRepository.save(cartItem);
        } else {
            // step 6b - product not in cart, create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(dto.quantity());
            cartItemRepository.save(cartItem);
        }

        // step 7 - return updated cart
        return getCart(name);
    }

    // get cart by userId
    public CartResponseDTO getCart(String name) {
        User user=userService.getLoggedInUser(name);
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart is empty!"));

        // map cart items to response
        List<CartItemResponseDTO> items = cart.getItems().stream()
                .map(item -> new CartItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getProduct().getPrice() * item.getQuantity(),
                        item.getProduct().getImageUrl()
                )).toList();

        // calculate total price
        Double totalPrice = items.stream()
                .mapToDouble(CartItemResponseDTO::subTotal)
                .sum();

        return new CartResponseDTO(
                cart.getId(),
                user.getId(),
                items,
                totalPrice

        );
    }
    // clear cart after order placed
    public void clearCart(String name) {
        User user = userService.getLoggedInUser(name);

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found!"));

        // delete all items from cart
        cartItemRepository.deleteAll(cart.getItems());

        // clear the items list on cart object too
        cart.getItems().clear();
        cartRepository.save(cart);
    }
    public CartResponseDTO removeFromCart(Long productId, String username) {
        // step 1 - find user
        User user = userService.getLoggedInUser(username);

        // step 2 - find cart
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // step 3 - find cart item and remove
        CartItem itemToRemove = cartItemRepository
                .findByCart_IdAndProduct_Id(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        cartItemRepository.delete(itemToRemove);

        // step 4 - return updated cart
        return getCart(username);
    }


    // update quantity
    public CartResponseDTO updateQuantity(String name, Long productId, Integer quantity) {
        User user=userService.getLoggedInUser(name);
       Cart cart= cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found!"));

        CartItem cartItem = cartItemRepository
                .findByCart_IdAndProduct_Id(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart!"));

        if (quantity == 0) {
            // if quantity is 0 remove item
            cartItemRepository.delete(cartItem);
        } else {
            // update quantity
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return getCart(name);
    }


}