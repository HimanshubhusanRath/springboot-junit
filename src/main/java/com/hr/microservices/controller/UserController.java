package com.hr.microservices.controller;

import com.hr.microservices.domain.User;
import com.hr.microservices.exceptions.UserNotFoundException;
import com.hr.microservices.repository.UserRepository;
import com.hr.microservices.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "{userId}")
    public User getUser(@PathVariable("userId") final Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@RequestBody final User user) {
        user.setId(System.currentTimeMillis());
        System.out.println("Received user: " + user); // Add this line
        user.setId(System.currentTimeMillis());
        User savedUser = userRepository.save(user);
        System.out.println("Saved user: " + savedUser); // Add this line
        return savedUser;
    }

    @PutMapping
    public User updateUser(@RequestBody final User user) {
        if (null == user || null == user.getId()) {
            throw new RuntimeException("user not found");
        }

        final User userRecord = userRepository.findById(user.getId()).orElse(null);
        if (null != userRecord) {
            userRecord.setName(user.getName());
            userRecord.setCity(user.getCity());
            return userRepository.save(userRecord);
        }
        return user;
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") final Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<String> getUserDetails(@PathVariable("userId")final String userId) {
        return ResponseEntity.ok(externalApiService.getUserDetails(userId));
    }

}