package com.quitq.repository;

import com.quitq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("""
SELECT u FROM User u WHERE u.email = ?1
""")
    Optional<User> findByEmail(String email);
@Query("""
select u from User u where u.username=?1
""")
    UserDetails getUserByUsername(String username);

    @Query("""
select u from User u where u.username=?1
""")
    User loadByUsername(String username);


}
