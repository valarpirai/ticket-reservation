package com.bee.reservation.controller;

import com.bee.reservation.model.User;
import com.bee.reservation.pojo.UserPojo;
import com.bee.reservation.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    List<User> allUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    ResponseEntity createUser(@RequestBody UserPojo userPojo) {
        logger.info(userPojo.toString());
        User user = new User();
        user.setFirstName(userPojo.getFirstName());
        user.setLastName(userPojo.getLastName());
        user.setEmail(userPojo.getEmail());
        userRepository.save(user);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}")
    ResponseEntity getUser(@PathVariable Long userId) {
        var optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent())
            return new ResponseEntity(optionalUser.get(), HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
