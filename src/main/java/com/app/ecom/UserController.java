package com.app.ecom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private  UserService userService;

    //create user
    @PostMapping
    public ResponseEntity<String> createUsers(@RequestBody User user) {
        userService.addUsers(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    //fetch all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    //fetch user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    //update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updateUser =  userService.updateUser(id, user);

        if (updateUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updateUser);

    }


}
