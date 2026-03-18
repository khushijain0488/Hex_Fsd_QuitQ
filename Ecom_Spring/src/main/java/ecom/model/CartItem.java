package ecom.model;

import java.math.BigDecimal;

public class CartItem {
    private int id;
    private String name;
    private BigDecimal price;
    private int qty;
    private User user; // In DB you will add user_id

    public CartItem() {}

    public CartItem(int id, String name, BigDecimal price, int qty, User user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.user = user;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "CartItem{id=" + id + ", name='" + name + "', price=" + price +
                ", qty=" + qty + ", user=" + user + "}";
    }
}
