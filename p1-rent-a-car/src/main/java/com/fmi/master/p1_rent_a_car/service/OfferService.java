package com.fmi.master.p1_rent_a_car.service;

import com.fmi.master.p1_rent_a_car.entity.Car;
import com.fmi.master.p1_rent_a_car.entity.Offer;
import com.fmi.master.p1_rent_a_car.entity.User;
import com.fmi.master.p1_rent_a_car.exceptions.CarNotFoundException;
import com.fmi.master.p1_rent_a_car.exceptions.UserNotFoundException;
import com.fmi.master.p1_rent_a_car.mappers.OfferRowMapper;
import com.fmi.master.p1_rent_a_car.util.OfferSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final Logger logger = LoggerFactory.getLogger(OfferService.class);
    private final JdbcTemplate db;
    private final CarService carService;
    private final UserService userService;

    public OfferService(JdbcTemplate db, CarService carService, UserService userService) {
        this.db = db;
        this.carService = carService;
        this.userService = userService;
    }

    public Optional<Offer> getOfferById(int id) {
        String sql = String.format(OfferSqlUtil.GET_OFFER_BY_ID, id);
        List<Offer> offers = db.query(sql, new OfferRowMapper());
        return offers.stream().findFirst();
    }

    public List<Offer> getAllOffersByUserId(int userId) {
        String sql = String.format(OfferSqlUtil.GET_ALL_OFFERS_BY_USER_ID, userId);
        return db.query(sql, new OfferRowMapper());
    }

    public boolean createOffer(int userId, int carId, LocalDate startDate, LocalDate endDate) {
        User user = userService.getUserById(userId)
                .orElseThrow(()->new UserNotFoundException("User with id " + userId + " not found"));
        Car car = carService.getCarById(carId)
                .orElseThrow(()->new CarNotFoundException("Car with id " + carId + " not found"));

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        double price = daysBetween * car.getPricePerDay();
        double additionalPrices = calculateAnyAdditionalPrices(user, startDate, endDate);
        double total = price + additionalPrices;

        String sql = String.format(OfferSqlUtil.CREATE_OFFER,
                carId,
                userId,
                price,
                additionalPrices,
                total,
                startDate,
                endDate,
                "FALSE"
                );

        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;

    }

    public boolean acceptOffer(int id) {
        String sql = String.format(OfferSqlUtil.ACCEPT_OFFER, id);
        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean deleteOffer(int id) {
        String sql = String.format(OfferSqlUtil.DELETE_OFFER, 0, id);
        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private double calculateAnyAdditionalPrices(User user, LocalDate startDate, LocalDate endDate) {
        double additionalPrices = 0;
        if (user.isPreviousAccidents()) {
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
