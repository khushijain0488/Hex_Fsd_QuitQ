package ecom.config;

import ecom.controller.CartController;
import ecom.repository.CartItemRepository;
import ecom.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy   // Enables AOP
@EnableTransactionManagement  // Enables Transactions
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        var datasource = new DriverManagerDataSource();
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost:3307/Ecom_with_spring");
        datasource.setUsername("root");
        datasource.setPassword("123456");
        return datasource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate) {
        return new UserRepository(jdbcTemplate);
    }


    @Bean
    public CartItemRepository cartItemRepository(JdbcTemplate jdbcTemplate) {
        return new CartItemRepository(jdbcTemplate);
    }


    @Bean
    public CartController cartController(CartItemRepository cartItemRepository,
                                         UserRepository userRepository) {
        return new CartController(cartItemRepository, userRepository);
    }


    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}