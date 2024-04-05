package com.bee.reservation.service;

import com.bee.reservation.exception.UserAlreadyExistsException;
import com.bee.reservation.pojo.UserPojo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import com.bee.reservation.repository.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;

    @BeforeEach
    void init() {}

    @Test
    void testCreateNewUser() throws Exception {
        var email = randomEmailAddress();
        var user = userService.createUser(buildUserPojo(email));
        assertThat(user, is(notNullValue()));
        assertTrue(user.getId() > 0);
        assertEquals("test", user.getFirstName());
        assertEquals("user", user.getLastName());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testCreateNewUserThrowsError() throws UserAlreadyExistsException {
        var email = randomEmailAddress();
        userService.createUser(buildUserPojo(email));

        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(buildUserPojo(email));
        });
        assertTrue(exception.getMessage().contains("User with email already exists"));
    }

    @Ignore
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
        var email = randomEmailAddress();
        List<UserPojo> users =  userService.getAllUsers();
        var initialSize = users.size();

        var user = userService.findOrCreateUser(buildUserPojo(email));

        users =  userService.getAllUsers();
        assertEquals(initialSize + 1, users.size());
        assertEquals(users.get(initialSize).getId(), user.getId());
    }

    private String randomEmailAddress() {
        int randomNum = ThreadLocalRandom.current().nextInt(  10000);
        return "test" + randomNum + "@hotmail.com";
    }
    private UserPojo buildUserPojo() {
        return buildUserPojo("test@hotmail.com");
    }
    private UserPojo buildUserPojo(String email) {
        var pojo = new UserPojo();
        pojo.setFirstName("test");
        pojo.setLastName("user");
        pojo.setEmail(email);
        return pojo;
    }
}
