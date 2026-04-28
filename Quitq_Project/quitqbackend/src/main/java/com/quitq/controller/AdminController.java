package com.quitq.controller;

import com.quitq.model.User;
import com.quitq.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    private final AdminService adminService;
//    get all user
    @GetMapping("/get-all-user")
    public ResponseEntity<List<User>>getAllUser(){
        return ResponseEntity.ok(adminService.getAllUser());
    }

}
