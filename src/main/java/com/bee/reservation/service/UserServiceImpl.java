package com.bee.reservation.service;

import com.bee.reservation.api.UserServiceApi;
import com.bee.reservation.model.User;
import com.bee.reservation.pojo.UserPojo;
import com.bee.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends UserServiceApi {

    @Autowired
    private UserRepository userRepository;

    User findOrGetUserByEmail(UserPojo userPojo) {
        var userRecord = userRepository.findByEmail(userPojo.getEmail());
        if (userRecord.isPresent())
            return userRecord.get();
        return new User(userPojo.getFirstName(), userPojo.getLastName(), userPojo.getEmail());
    }
}
