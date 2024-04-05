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
import org.springframework.test.annotation.DirtiesContext;

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
    }

    @Test
    void testCreateNewUser() throws Exception {
        var user = userService.createUser(getUserPojo());
        assertThat(user, is(notNullValue()));
        assertTrue(user.getId() > 0);
        assertEquals("test", user.getFirstName());
        assertEquals("user", user.getLastName());
        assertEquals("test@hotmail.com", user.getEmail());
    }

    @Test
    void testCreateNewUserThrowsError() throws UserAlreadyExistsException {
        userService.createUser(getUserPojo());

        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(getUserPojo());
        });
        assertTrue(exception.getMessage().contains("User with email already exists"));
    }

    @Test
    void testFindOrCreateUser() {
        var user = userService.findOrCreateUser(getUserPojo());
        assertTrue(user.getId() > 0);
        assertEquals("test", user.getFirstName());

        var user1 = userService.findOrCreateUser(getUserPojo());
        assertEquals(user1.getId(), user.getId());
    }

    @Test
    void testUserExists() {
        userService.findOrCreateUser(getUserPojo());

        var userExists = userService.userExists("test@hotmail.com");
        assertTrue(userExists);
    }

    @Test
    void testGetUser() {
        var user = userService.findOrCreateUser(getUserPojo());

        var user1 = userService.getUser(user.getId());
        assertThat(user1, is(notNullValue()));
    }

    @Test
    void testGetAllUser() {
        List<UserPojo> users =  userService.getAllUsers();
        assertTrue(users.size() == 0);

        var user = userService.findOrCreateUser(getUserPojo());

        users =  userService.getAllUsers();
        assertTrue(users.size() == 1);
        assertEquals(users.get(0).getId(), user.getId());
    }

    private UserPojo getUserPojo() {
        var pojo = new UserPojo();
        pojo.setFirstName("test");
        pojo.setLastName("user");
        pojo.setEmail("test@hotmail.com");
        return pojo;
    }
}
