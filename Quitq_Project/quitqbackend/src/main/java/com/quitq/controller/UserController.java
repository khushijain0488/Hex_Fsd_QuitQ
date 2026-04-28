package com.quitq.controller;

import com.quitq.Mapper.UserMapper;
import com.quitq.dto.UserRequestDto;
import com.quitq.dto.UserResponsedto;
import com.quitq.model.User;
import com.quitq.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all")
    public List<UserResponsedto> getAllUsers(){
       return userService.getAllUsers();
    }
//get user by id
    @GetMapping("api/v1/getUserById/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.getById(userId);
    }
    //    api version two getting user by username
    @GetMapping("/api/v2/loggedInUser")
    public User getLoggedInUser(Principal principal){
        return userService.getLoggedInUser(principal.getName());
    }

}