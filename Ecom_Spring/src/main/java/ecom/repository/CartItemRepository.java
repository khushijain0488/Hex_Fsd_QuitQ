package ecom.repository;

import ecom.model.CartItem;
import ecom.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ecom.enums.UserMembership;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CartItemRepository {

    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public CartItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Add Item
    public void addItem(CartItem item) {
        String sql = "INSERT INTO cart_items (id, name, price, qty, user_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getQty(),
                item.getUser().getId());
    }

    // 2. Fetch All Items
    public List<CartItem> fetchAllItems() {
        String sql = "SELECT ci.*, u.name AS user_name, u.membership " +
                "FROM cart_items ci JOIN users u ON ci.user_id = u.id";
        return jdbcTemplate.query(sql, new CartItemRowMapper());
    }

    // 3. Fetch Items by Username
    public List<CartItem> fetchItemsByUsername(String username) {
        String sql = "SELECT ci.*, u.name AS user_name, u.membership " +
                "FROM cart_items ci JOIN users u ON ci.user_id = u.id " +
                "WHERE u.name = ?";
        return jdbcTemplate.query(sql, new CartItemRowMapper(), username);
    }

    // 4. Delete an Item
    public void deleteItem(int itemId) {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        jdbcTemplate.update(sql, itemId);
    }

    // RowMapper for CartItem
    public static class CartItemRowMapper implements RowMapper<CartItem> {
        @Override
        public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("user_name"));
            user.setMembership(UserMembership.valueOf(rs.getString("membership")));

            CartItem item = new CartItem();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setPrice(rs.getBigDecimal("price"));
            item.setQty(rs.getInt("qty"));
            item.setUser(user);
            return item;
        }
    }
}
