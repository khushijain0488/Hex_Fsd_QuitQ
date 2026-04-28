package com.quitq.repository;

import com.quitq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<User,Long> {
}
