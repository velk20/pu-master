package com.fmi.master.p1_rent_a_car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.master.p1_rent_a_car.controllers.CarController;
import com.fmi.master.p1_rent_a_car.exceptions.EntityNotFoundException;
import com.fmi.master.p1_rent_a_car.models.Car;
import com.fmi.master.p1_rent_a_car.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

public class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void testGetById_success() throws Exception {
        Car car = new Car(1, "Toyota", "Auris", "Plovdiv", 2012, 55);

        when(carService.getCarById(1)).thenReturn(car);

        mockMvc.perform(get("/cars/{id}", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.brand").value("Toyota"))
                .andExpect(jsonPath("$.data.model").value("Auris"));
    }


    @Test
    public void testGetAll_success() throws Exception {
        Car car1 = new Car(1, "Toyota", "Auris", "Plovdiv", 2012, 55);
        Car car2 = new Car(2, "BMW", "E60", "Plovdiv", 2008, 65);


        List<Car> cars = List.of(car1, car2);

        when(carService.getAllCarsByCity(1)).thenReturn(cars);

        mockMvc.perform(get("/cars/user/{userId}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[0].brand").value("Toyota"))
                .andExpect(jsonPath("$.data[1].brand").value("BMW"));
    }

    @Test
    public void testCreate_success() throws Exception {
        Car car1 = new Car(1, "Toyota", "Auris", "Plovdiv", 2012, 55);


        when(carService.createCar(any(Car.class))).thenReturn(true);
        // Convert User to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(car1);

        // Act: Perform the PUT request
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Car successfully created"));
    }

    @Test
    public void testCreate_badRequest() throws Exception {
        Car car1 = new Car(1, "Toyota", "Auris", "Plovdiv", 2012, 55);
        car1.setCity(null);
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(car1);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("city is required."));
    }

    @Test
    public void testUpdate_success() throws Exception {
        Car car1 = new Car(1, "Toyota", "Auris", "Plovdiv", 2012, 55);

        when(carService.updateCar(eq(1), any(Car.class))).thenReturn(true);
        when(carService.getCarById(1)).thenReturn(car1);

        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(car1);

        // Act: Perform the PUT request
        mockMvc.perform(put("/cars/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                // Assert: Verify the response
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.message").value("Car successfully updated"))
                .andExpect(jsonPath("$.data.brand").value("Toyota"))
                .andExpect(jsonPath("$.data.model").value("Auris"));
    }


    @Test
    @ExceptionHandler(EntityNotFoundException.class)
    public void testUpdate_notFound() throws Exception {
        Car car1 = new Car(1, "Toyota", "Auris", "Plovdiv", 2012, 55);
        car1.setModel(null);

        when(carService.updateCar(eq(1), any(Car.class))).thenReturn(false);
        when(carService.getCarById(1)).thenThrow(EntityNotFoundException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(car1);

        mockMvc.perform(put("/cars/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDelete_success() throws Exception {
        when(carService.deleteCar(1)).thenReturn(true);

        mockMvc.perform(delete("/cars/{id}", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.message").value("Car successfully deleted"));
    }

    @Test
    public void testDelete_notFound() throws Exception {
        when(carService.getCarById(1)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}
