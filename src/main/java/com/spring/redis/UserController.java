package com.spring.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
public class UserController {
 
    @Autowired
    private UserRepository userRepository;
 
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
 
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(name = "id") String id, @RequestBody User user) {
        Optional<User> std = userRepository.findById(id);
        if (std.isPresent()) {
            User userDB = std.get();
            userDB.setEmail(user.getEmail());
            userDB.setName(user.getName());
            User updatedUser = userRepository.save(userDB);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        }
        return null;
    }
 
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
 
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") String id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>("User with id:" + id + " deleted successfully", HttpStatus.OK);
    }
}
