package com.bee.reservation.service;


import com.bee.reservation.pojo.UserPojo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import com.bee.reservation.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Test
    void testCreateNewUser() throws Exception {
        var user = userService.createUser(getUserPojo());
        assertThat(user, is(notNullValue()));
        assertEquals("test", user.getFirstName());
        assertEquals("user", user.getLastName());
        assertEquals("test@hotmail.com", user.getEmail());
    }

    @Test
    void testCreateNewUserThrowsError() {

    }

    @Test
    void testGetAllUsers() {

    }

    @Test
    void testGetUserByEmail() {

    }

    private UserPojo getUserPojo() {
        var pojo = new UserPojo();
        pojo.setFirstName("test");
        pojo.setLastName("user");
        pojo.setEmail("test@hotmail.com");
        return pojo;
    }
}
