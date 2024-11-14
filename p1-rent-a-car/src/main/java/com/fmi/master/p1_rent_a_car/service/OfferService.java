package com.fmi.master.p1_rent_a_car.service;

import com.fmi.master.p1_rent_a_car.entity.Car;
import com.fmi.master.p1_rent_a_car.entity.Offer;
import com.fmi.master.p1_rent_a_car.entity.User;
import com.fmi.master.p1_rent_a_car.mappers.OfferRowMapper;
import com.fmi.master.p1_rent_a_car.util.OfferSqlUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OfferService {
    private final JdbcTemplate db;
    private final CarService carService;
    private final UserService userService;

    public OfferService(JdbcTemplate db, CarService carService, UserService userService) {
        this.db = db;
        this.carService = carService;
        this.userService = userService;
    }

    public Offer getOfferById(int id) {
        String sql = String.format(OfferSqlUtil.GET_OFFER_BY_ID, id);
        List<Offer> offers = db.query(sql, new OfferRowMapper());
        if (offers.isEmpty()) {
            return null;
        }

        return offers.get(0);
    }

    public List<Offer> getAllOffersByUserId(int userId) {
        String sql = String.format(OfferSqlUtil.GET_ALL_OFFERS_BY_USER_ID, userId);
        return db.query(sql, new OfferRowMapper());
    }

    public boolean createOffer(int userId, int carId, LocalDate startDate, LocalDate endDate) {
        User user = userService.getUserById(userId);
        Car car = carService.getCarById(carId);

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        double price = daysBetween * car.getPricePerDay();
        double additionalPrices = calculateAnyAdditionalPrices(user, car, startDate, endDate);
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

        db.execute(sql);
        return true;

    }

    private double calculateAnyAdditionalPrices(User user, Car car, LocalDate startDate, LocalDate endDate) {
        //TODO
        return 0;
    }

    public boolean acceptOffer(int id) {
        String sql = String.format(OfferSqlUtil.ACCEPT_OFFER, id);
        db.execute(sql);
        return true;
    }

    public boolean deleteOffer(int id) {
        String sql = String.format(OfferSqlUtil.DELETE_OFFER, 0, id);
        db.execute(sql);
        return true;
    }
}
