package com.fmi.master.p1_rent_a_car.services;

import com.fmi.master.p1_rent_a_car.dtos.CreateOfferDTO;
import com.fmi.master.p1_rent_a_car.exceptions.EntityNotFoundException;
import com.fmi.master.p1_rent_a_car.exceptions.InvalidCityException;
import com.fmi.master.p1_rent_a_car.models.Car;
import com.fmi.master.p1_rent_a_car.models.Offer;
import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final CarService carService;
    private final UserService userService;

    public OfferService(OfferRepository offerRepository, CarService carService, UserService userService) {
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.userService = userService;
    }

    public Offer getOfferById(int id) {
        return this.offerRepository
                .getOfferById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer with id:" + id + " not found"));
    }

    public List<Offer> getAllOffersByUserId(int userId) {
        this.userService.getUserById(userId);

        return this.offerRepository.getAllOffersByUserId(userId);
    }

    public boolean createOffer(CreateOfferDTO createOfferDTO) {
        int userId = createOfferDTO.getUserId();
        int carId = createOfferDTO.getCarId();
        LocalDate startDate = createOfferDTO.getStartDate();
        LocalDate endDate = createOfferDTO.getEndDate();

        User user = this.userService.getUserById(userId);
        Car car = this.carService.getCarById(carId);

        if (!user.getCity().equalsIgnoreCase(car.getCity())) {
            throw new InvalidCityException("User's city does not match car's city");
        }

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        double price = daysBetween * car.getPricePerDay();
        double additionalPrices = calculateAnyAdditionalPrices(user, startDate, endDate);
        double total = price + additionalPrices;

        return this.offerRepository.createOffer(createOfferDTO, price, additionalPrices, total);
    }

    public boolean acceptOffer(int id) {
        getOfferById(id);

        return this.offerRepository.acceptOffer(id);
    }

    public boolean deleteOffer(int id) {
        getOfferById(id);

        return this.offerRepository.deleteOffer(id);
    }

    private double calculateAnyAdditionalPrices(User user, LocalDate startDate, LocalDate endDate) {
        double additionalPrices = 0;
        if (user.getPreviousAccidents()) {
            additionalPrices += 200;
        }
        if (includesWeekend(startDate, endDate)) {
            additionalPrices += additionalPrices * 0.10; //Additional 10 %
        }
        return additionalPrices;
    }

    private static boolean includesWeekend(LocalDate startDate, LocalDate endDate) {
        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {
            // Check if the current date is a Saturday or Sunday
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return true;
            }
            // Move to the next day
            date = date.plusDays(1);
        }

        return false; // No weekends found in the range
    }
}
