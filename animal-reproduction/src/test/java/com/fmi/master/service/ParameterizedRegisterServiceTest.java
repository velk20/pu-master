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
        Mockito.when(userRepository.findByEmail(EXISTING_EMAIL)).thenReturn(new User());
        Mockito.when(userRepository.findByUsername(EXISTING_USERNAME)).thenReturn(new User());
    }

    @Parameterized.Parameters(name = "Test register user - parameters: username: {0}, password: {1}, password2: {2}, email:{3}, expected: {4}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, "123123", "123123", "ange11l@gmail.com", "Invalid username\n", 0},
                {"12", "123123", "123123", "angel1111@gmail.com", "Invalid username\n", 0},
                {
                        "111111111111111111111111111111111112",
                        "123123",
                        "123123",
                        "angel@gmail.com",
                        "Invalid username\n",
                        0
                },
                {"angel2001", "123123", "123123", null,"Invalid email\n", 0},
                {"angel", null, "321321", "angel11@gmail.com","Invalid password\n", 0},
                {"angel", "1231231", "123123", "angel11111@gmail.com","Passwords do not match\n", 0},
                {"angel", "123123", "123123", "angel@gmail.com","User successfully registered", 1},
                {EXISTING_USERNAME, "123123", "123123", "angel@gmail.com","User with this username already exists\n", 0},
                {"angel", "123123", "123123", EXISTING_EMAIL,"User with this email already exists\n", 0}
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
    @Parameterized.Parameter(5)
    public int saveCount;

    @Test
    public void testRegister() {
        String message = registerService.register(username, password, password2, email);
        assertEquals(expectedMessage, message);
        User user = new User(username, email, password);
        Mockito.verify(userRepository, Mockito.times(saveCount)).save(Mockito.eq(user));
    }
}
