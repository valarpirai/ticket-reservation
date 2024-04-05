package com.bee.reservation.service;

import com.bee.reservation.api.UserServiceApi;
import com.bee.reservation.exception.UserAlreadyExistsException;
import com.bee.reservation.model.User;
import com.bee.reservation.pojo.UserPojo;
import com.bee.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends UserServiceApi {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserPojo userPojo) throws UserAlreadyExistsException {
        if (userExists(userPojo.getEmail()))
            throw new UserAlreadyExistsException("User with email already exists");
        User user = new User();
        user.setFirstName(userPojo.getFirstName());
        user.setLastName(userPojo.getLastName());
        user.setEmail(userPojo.getEmail());
        userRepository.save(user);
        return user;
    }

    // Paginate and Sort Results
    public List<UserPojo> getAllUsers() {
        List<UserPojo> users = new ArrayList<UserPojo>();
        for(User user: userRepository.findAll()) {
            users.add(mapToPojo(user));
        }
        return users;
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }
    public User findOrCreateUser(UserPojo userPojo) {
        var userRecord = userRepository.findByEmail(userPojo.getEmail());
        if (userRecord.isPresent())
            return userRecord.get();
        return userRepository.save(new User(userPojo.getFirstName(), userPojo.getLastName(), userPojo.getEmail()));
    }

    public UserPojo mapToPojo(User user) {
        return new UserPojo(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }
}
