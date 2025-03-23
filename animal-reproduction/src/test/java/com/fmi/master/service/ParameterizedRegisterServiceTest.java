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
public class ParameterizedRegisterServiceTest {

    private static final String EXISTING_EMAIL = "unique@gmail.com";
    private static final String EXISTING_USERNAME = "unique";
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterService registerService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Parameterized.Parameters(name = "Test register user - parameters: username: {0}, password: {1}, password2: {2}, email:{3}, expected: {4}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, "123123", "123123", "angel@gmail.com", "Invalid username\n"},
                {"12", "123123", "123123", "angel@gmail.com", "Invalid username\n"},
                {
                        "111111111111111111111111111111111112",
                        "123123",
                        "123123",
                        "angel@gmail.com",
                        "Invalid username\n"
                },
                {"angel2001", "123123", "123123", null,"Invalid email\n"},
                {"angel", null, "321321", "angel@gmail.com","Invalid password\n"},
                {"angel", "1231231", "123123", "angel@gmail.com","Passwords do not match\n"},
                {"angel", "123123", "123123", "angel@gmail.com","User successfully registered"},
                {EXISTING_USERNAME, "123123", "123123", "angel@gmail.com","User with this username already exists\n"},
                {"angel", "123123", "123123", EXISTING_EMAIL,"User with this email already exists\n"}
        });
    }

    @Parameterized.Parameter(0)
    public String username;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String password2;
    @Parameterized.Parameter(3)
    public String email;
    @Parameterized.Parameter(4)
    public String expectedMessage;

    @Test
    public void testRegister() {
        Mockito.when(userRepository.findByEmail(EXISTING_EMAIL)).thenReturn(new User());
        Mockito.when(userRepository.findByUsername(EXISTING_USERNAME)).thenReturn(new User());
        String message = registerService.register(username, password, password2, email);
        assertEquals(expectedMessage, message);
    }


}
