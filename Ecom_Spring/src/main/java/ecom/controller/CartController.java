package ecom.controller;

import ecom.model.CartItem;
import ecom.model.User;
import ecom.repository.CartItemRepository;
import ecom.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CartController {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    // Constructor Injection
    public CartController(CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    // 1. Add Item + User details in DB
    @Transactional
    public void addItemWithUser(User user, CartItem item) {
        userRepository.addUser(user);
        item.setUser(user);
        cartItemRepository.addItem(item);
        System.out.println("Added user and cart item successfully.");
    }

    // 2. Fetch All Items
    public void fetchAllItems() {
        List<CartItem> items = cartItemRepository.fetchAllItems();
        System.out.println("---- All Cart Items ----");
        items.forEach(System.out::println);
    }

    // 3. Fetch Items by Username
    public void fetchItemsByUsername(String username) {
        List<CartItem> items = cartItemRepository.fetchItemsByUsername(username);
        System.out.println("---- Items for user: " + username + " ----");
        items.forEach(System.out::println);
    }

    // 4. Delete an Item
    @Transactional
    public void deleteItem(int itemId) {
        cartItemRepository.deleteItem(itemId);
        System.out.println("Deleted item with id: " + itemId);
    }
}
