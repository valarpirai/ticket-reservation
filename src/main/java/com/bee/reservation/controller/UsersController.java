package com.bee.reservation.controller;

import com.bee.reservation.model.User;
import com.bee.reservation.pojo.UserPojo;
import com.bee.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    List<User> allUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/")
    User createUser(@RequestBody UserPojo userPojo) {
        User user = new User();
        user.setFirstName(userPojo.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        userRepository.save(user);
        return user;
    }
    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") Long id) {
        var optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }
}
