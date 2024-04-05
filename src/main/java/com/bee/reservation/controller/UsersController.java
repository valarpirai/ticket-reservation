package com.bee.reservation.controller;

import com.bee.reservation.exception.UserAlreadyExistsException;
import com.bee.reservation.model.Response;
import com.bee.reservation.model.User;
import com.bee.reservation.pojo.UserPojo;
import com.bee.reservation.repository.UserRepository;
import com.bee.reservation.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/users")
    List<UserPojo> allUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/user")
    ResponseEntity createUser(@RequestBody UserPojo userPojo) throws UserAlreadyExistsException {
        User user = userService.createUser(userPojo);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}")
    ResponseEntity getUser(@PathVariable Long userId) {
        var optionalUser = userService.getUser(userId);
        if(optionalUser.isPresent())
            return new ResponseEntity(optionalUser.get(), HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
