package com.quitq.service;

import com.quitq.model.User;
import com.quitq.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    public List<User> getAllUser() {
        return adminRepository.findAll();
    }
}
