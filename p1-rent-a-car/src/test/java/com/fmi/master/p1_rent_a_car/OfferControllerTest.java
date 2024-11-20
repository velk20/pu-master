package com.fmi.master.p1_rent_a_car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fmi.master.p1_rent_a_car.controllers.CarController;
import com.fmi.master.p1_rent_a_car.controllers.OfferController;
import com.fmi.master.p1_rent_a_car.dtos.CreateOfferDTO;
import com.fmi.master.p1_rent_a_car.exceptions.EntityNotFoundException;
import com.fmi.master.p1_rent_a_car.models.Car;
import com.fmi.master.p1_rent_a_car.models.Offer;
import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.services.CarService;
import com.fmi.master.p1_rent_a_car.services.OfferService;
import com.fmi.master.p1_rent_a_car.services.UserService;
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

import java.time.LocalDate;
import java.util.List;

public class OfferControllerTest {

    @Mock
    private OfferService offerService;
    @Mock
    private UserService userService;
    @Mock
    private CarService carService;

    @InjectMocks
    private OfferController offerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(offerController).build();
    }

    @Test
    public void testGetById_success() throws Exception {
        Offer offer = new Offer(1, 1, 1,20,12,32, LocalDate.MIN, LocalDate.MAX, false);
        Car car = new Car(1, "Toyota", "Auris", "Plovdiv", 2012, 55);
        User user = new User(1, "John", "Doe","Plovdiv", "0878284322", 22, false);

        when(offerService.getOfferById(1)).thenReturn(offer);

        mockMvc.perform(get("/offers/{id}", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.carId").value(1))
                .andExpect(jsonPath("$.data.userId").value(1));
    }


    @Test
    public void testGetAllOffersByUserId_success() throws Exception {
        Offer offer1 = new Offer(1, 1, 1,20,12,32, LocalDate.MIN, LocalDate.MAX, false);
        Offer offer2 = new Offer(2, 1, 1,20,12,32, LocalDate.MIN, LocalDate.MAX, false);

        List<Offer> offers = List.of(offer1, offer2);

        when(offerService.getAllOffersByUserId(1)).thenReturn(offers);

        mockMvc.perform(get("/offers/users/{userId}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[0].carId").value(1))
                .andExpect(jsonPath("$.data[1].carId").value(1));
    }

    @Test
    public void testCreate_success() throws Exception {
        CreateOfferDTO createOfferDTO = new CreateOfferDTO(1, 1, LocalDate.of(2001,1,1), LocalDate.of(2002,1,1));

        when(offerService.createOffer(any(CreateOfferDTO.class))).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String offerJson = objectMapper.writeValueAsString(createOfferDTO);

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(offerJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Offer successfully created"));
    }

    @Test
    public void testCreate_badRequest() throws Exception {
        CreateOfferDTO createOfferDTO = new CreateOfferDTO(1, 1, LocalDate.of(2001,1,1), LocalDate.of(2002,1,1));
        createOfferDTO.setCarId(0);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String offerJson = objectMapper.writeValueAsString(createOfferDTO);

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(offerJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("carId is not valid."));
    }

    @Test
    public void testAcceptOffer_success() throws Exception {
        CreateOfferDTO createOfferDTO = new CreateOfferDTO(1, 1, LocalDate.of(2001,1,1), LocalDate.of(2002,1,1));

        when(offerService.acceptOffer(eq(1))).thenReturn(true);

        // Act: Perform the PUT request
        mockMvc.perform(put("/offers/{offerId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert: Verify the response
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.message").value("Offer successfully accepted"));
    }


    @Test
    public void testDelete_success() throws Exception {
        when(offerService.deleteOffer(1)).thenReturn(true);

        mockMvc.perform(delete("/offers/{id}", 1))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.message").value("Offer successfully deleted"));
    }

    @Test
    public void testDelete_notFound() throws Exception {
        when(offerService.deleteOffer(1)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(delete("/users/{offerId}", 1))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}
