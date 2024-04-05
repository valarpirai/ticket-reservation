package com.bee.reservation.service;

import com.bee.reservation.exception.UserAlreadyExistsException;
import com.bee.reservation.pojo.UserPojo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import com.bee.reservation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    void testCreateNewUser() throws Exception {
        var user = userService.createUser(buildUserPojo());
        assertThat(user, is(notNullValue()));
        assertTrue(user.getId() > 0);
        assertEquals("test", user.getFirstName());
        assertEquals("user", user.getLastName());
        assertEquals("test@hotmail.com", user.getEmail());
    }

    @Test
    void testCreateNewUserThrowsError() throws UserAlreadyExistsException {
        userService.createUser(buildUserPojo());

        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(buildUserPojo());
        });
        assertTrue(exception.getMessage().contains("User with email already exists"));
    }

    @Test
    void testFindOrCreateUser() {
        var user = userService.findOrCreateUser(buildUserPojo());
        assertTrue(user.getId() > 0);
        assertEquals("test", user.getFirstName());

        var user1 = userService.findOrCreateUser(buildUserPojo());
        assertEquals(user1.getId(), user.getId());
    }

    @Test
    void testUserExists() {
        userService.findOrCreateUser(buildUserPojo());

        var userExists = userService.userExists("test@hotmail.com");
        assertTrue(userExists);
    }

    @Test
    void testGetUser() {
        var user = userService.findOrCreateUser(buildUserPojo());

        var user1 = userService.getUser(user.getId());
        assertThat(user1, is(notNullValue()));
    }

    @Test
    void testGetAllUser() {
        List<UserPojo> users =  userService.getAllUsers();
        assertTrue(users.size() == 0);

        var user = userService.findOrCreateUser(buildUserPojo());

        users =  userService.getAllUsers();
        assertTrue(users.size() == 1);
        assertEquals(users.get(0).getId(), user.getId());
    }

    private UserPojo buildUserPojo() {
        var pojo = new UserPojo();
        pojo.setFirstName("test");
        pojo.setLastName("user");
        pojo.setEmail("test@hotmail.com");
        return pojo;
    }
}
