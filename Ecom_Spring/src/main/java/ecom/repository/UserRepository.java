package ecom.repository;

import ecom.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ecom.enums.UserMembership;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (id, name, membership) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getMembership().name());
    }

    public User getUserByName(String name) {
        String sql = "SELECT * FROM users WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), name);
    }

    // RowMapper for User
    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setMembership(UserMembership.valueOf(rs.getString("membership")));
            return user;
        }
    }
}
