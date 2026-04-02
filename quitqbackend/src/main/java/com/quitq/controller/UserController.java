package com.quitq.controller;

import com.quitq.Mapper.UserMapper;
import com.quitq.dto.UserResponsedto;
import com.quitq.model.User;
import com.quitq.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(user));
    }
    @GetMapping("/get-all")
    public List<UserResponsedto> getAllUsers(){
       return userService.getAllUsers();
    }
}