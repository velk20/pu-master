package com.fmi.master.service;

import com.fmi.master.model.User;
import com.fmi.master.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterService registerService;

    @Test
    public void testRegisterWhenUsernameIsNullShouldReturnErrorMessage() {

        String message = registerService.register(null,
                "123123",
                "123123",
                "angel@gmail.com");
        assertEquals("Invalid username\n", message);
    }

    @Test
    public void testRegisterWhenUsernameLengthLowerThatThreeShouldReturnErrorMessage() {

        String message = registerService.register("12",
                "123123",
                "123123",
                "angel@gmail.com");
        assertEquals("Invalid username\n", message);
    }

    @Test
    public void testRegisterWhenUsernameLengthMoreThatFifteenShouldReturnErrorMessage() {

        String message = registerService.register("111111111111111111111111111111111112",
                "123123",
                "123123",
                "angel@gmail.com");
        assertEquals("Invalid username\n", message);
    }

    @Test
    public void testRegisterWithInvalidEmailShouldReturnErrorMessage() {

        String message = registerService.register("angel2001", "123123", "123123", null);
        assertEquals("Invalid email\n", message);

        message = registerService.register("angel2001", "123123", "123123", "angelgmail.com");
        assertEquals("Invalid email\n", message);

        message = registerService.register("angel", "123123", "123123", "@gmail.com");
        assertEquals("Invalid email\n", message);

        message = registerService.register("angel", "123123", "123123", "angel@gmail");
        assertEquals("Invalid email\n", message);
    }

    @Test
    public void testRegisterWithInvalidPasswordShouldReturnErrorMessage() {

        String message = registerService.register("angel", null, "321321", "angel@gmail.com");
        assertEquals("When password is null","Invalid password\n", message);

        message = registerService.register("angel", "12", "123123", "angel@gmail.com");
        assertEquals("When password length is less than 3","Invalid password\n", message);

        message = registerService.register("angel", "111111111111111111111", "111111111111111111111", "angel@gmail.com");
        assertEquals("When password length is more than 20","Invalid password\n", message);
    }

    @Test
    public void testRegisterWithMismatchPasswordShouldReturnErrorMessage() {
        String message = registerService.register("angel", "1231231", "123123", "angel@gmail.com");
        assertEquals("When passwords didn't match","Passwords do not match\n", message);
    }

    @Test
    public void testRegisterSuccessfully(){
        String message = registerService.register("angel", "123123", "123123", "angel@gmail.com");
        assertEquals("User successfully registered", message);
    }

    @Test
    public void testRegisterWithUsernameAlreadyTaken() {
        Mockito.when(userRepository.findByUsername("angel")).thenReturn(new User());
        String message = registerService.register("angel", "123123", "123123", "angel@gmail.com");
        assertEquals("User with this username already exists\n", message);
    }

    @Test
    public void testRegisterWithEmailAlreadyTaken() {
        Mockito.when(userRepository.findByEmail("angel@gmail.com")).thenReturn(new User());
        String message = registerService.register("angel", "123123", "123123", "angel@gmail.com");
        assertEquals("User with this email already exists\n", message);
    }
}