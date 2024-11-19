package com.fmi.master.p1_rent_a_car.repositories;

import com.fmi.master.p1_rent_a_car.dtos.CreateOfferDTO;
import com.fmi.master.p1_rent_a_car.models.Offer;
import com.fmi.master.p1_rent_a_car.mappers.OfferRowMapper;
import com.fmi.master.p1_rent_a_car.utils.OfferSqlUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OfferRepository {
    private final JdbcTemplate db;

    public OfferRepository(JdbcTemplate db) {
        this.db = db;
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

    public boolean createOffer(CreateOfferDTO createOfferDTO, double price, double additionalPrices, double total) {
        String sql = String.format(OfferSqlUtil.CREATE_OFFER,
                createOfferDTO.getCarId(),
                createOfferDTO.getUserId(),
                price,
                additionalPrices,
                total,
                createOfferDTO.getStartDate(),
                createOfferDTO.getEndDate(),
                "FALSE"
        );

        db.execute(sql);
        return true;
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
