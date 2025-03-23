package com.fmi.master.service;

import com.fmi.master.model.User;
import com.fmi.master.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterizedLoginServiceTest {
    private static final String VALID_USERNAME = "angel2001";
    private static final String VALID_PASSWORD = "ANGEL@))!1";
    @Mock
    UserRepository userRepository;

    @InjectMocks
    LoginService loginService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(new User());

    }

    @Parameterized.Parameters(name = "Test login user - parameters: username: {0}, password: {1}, expected: {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, "123123", "Invalid username\nUser with this username doesn't exists\n"},
                {"12", "123123", "Invalid username\nUser with this username doesn't exists\n"},
                {
                        "111111111111111111111111111111111112",
                        "123123",
                        "Invalid username\nUser with this username doesn't exists\n"
                },
                {"angel", null,"Invalid password\nUser with this username doesn't exists\n"},
                {VALID_USERNAME, VALID_PASSWORD,"Login Successful"},
        });
    }

    @Parameterized.Parameter(0)
    public String username;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String expectedMessage;

    @Test
    public void testRegister() {
        String message = loginService.loginUser(username, password);
        assertEquals(expectedMessage, message);
    }

}
