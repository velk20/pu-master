package com.fmi.master.p1_rent_a_car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.master.p1_rent_a_car.controllers.UserController;
import com.fmi.master.p1_rent_a_car.exceptions.GlobalExceptionHandler;
import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetById_success() throws Exception {
        User user = new User(1, "John", "Doe","Plovdiv", "0878284322", 22, false);

        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"));
    }


    @Test
    public void testGetAll_success() throws Exception {
        User user = new User(1, "John", "Doe","Plovdiv", "0878284322", 22, false);

        List<User> users = Collections.singletonList(user);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].firstName").value("John"));
    }

    @Test
    public void testCreate_success() throws Exception {
        User user = new User(1, "John", "Doe","Plovdiv", "0878284322", 22, false);


        when(userService.createUser(any(User.class))).thenReturn(true);
        // Convert User to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        // Act: Perform the PUT request
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User created successfully"));
    }

    @Test
    public void testCreate_badRequest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"\", \"lastName\":\"\", \"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdate_success() throws Exception {
        // Arrange: Create a mock user
        User updatedUser = new User(1, "John", "Doe", "Plovdiv", "0878284322", 22, false);

        // Mock the service behavior
        when(userService.updateUser(eq(1), any(User.class))).thenReturn(true);
        when(userService.getUserById(1)).thenReturn(updatedUser);

        // Convert User to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(updatedUser);

        // Act: Perform the PUT request
        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                // Assert: Verify the response
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.city").value("Plovdiv"))
                .andExpect(jsonPath("$.data.phone").value("0878284322"))
                .andExpect(jsonPath("$.data.years").value(22))
                .andExpect(jsonPath("$.data.previousAccidents").value(false));
    }


    @Test
    public void testUpdate_notFound() throws Exception {
        when(userService.updateUser(eq(1), any(User.class))).thenReturn(false);

        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDelete_success() throws Exception {
        when(userService.deleteUser(1)).thenReturn(true);

        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }

    @Test
    public void testDelete_notFound() throws Exception {
        when(userService.deleteUser(1)).thenReturn(false);

        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
