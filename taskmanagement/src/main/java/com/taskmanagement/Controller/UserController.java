package com.taskmanagement.Controller;

import com.taskmanagement.Dto.UserGetResponsedto;
import com.taskmanagement.Dto.UserRequestDto;
import com.taskmanagement.Model.User;
import com.taskmanagement.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
       userService.registerUser(user);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//    get all user
    @GetMapping("/get-all")
    public ResponseEntity<UserGetResponsedto>getAllUser(@RequestParam(value = "page",required = false,defaultValue = "0")int page,
                                                        @RequestParam(value = "size",required = false,defaultValue = "5")int size){
        return ResponseEntity.ok(userService.getAllUser(page,size));
    }
//    get the user with id
    @GetMapping("/get/{id}")
    public ResponseEntity<User>getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateUserById(@PathVariable Long id,@RequestBody UserRequestDto userRequestDto){
        userService.updateUserById(id,userRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated successfully.");
    }
//    delete the user by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
