package com.fmi.master.p1_rent_a_car.repositories;

import com.fmi.master.p1_rent_a_car.dtos.CreateOfferDTO;
import com.fmi.master.p1_rent_a_car.models.Offer;
import com.fmi.master.p1_rent_a_car.mappers.OfferRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OfferRepository {

    private final String GET_OFFER_BY_ID = "SELECT * FROM tb_offers WHERE is_active = 1 AND id = ?";
    private final String GET_ALL_OFFERS_BY_USER_ID = "SELECT * FROM tb_offers WHERE is_active = 1 AND user_id = ?";
    private final String CREATE_OFFER = """
                                        INSERT INTO tb_offers (
                                        car_id, 
                                        user_id, 
                                        price, 
                                        additional_price, 
                                        total_price, 
                                        start_date, 
                                        end_date, 
                                        is_accepted) 
                                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                                        """;
    private final String ACCEPT_OFFER = "UPDATE tb_offers SET is_accepted = TRUE WHERE is_active = 1 AND id = ?";
    private final String DELETE_OFFER = "UPDATE tb_offers SET is_active = ? WHERE id = ?";

    private final JdbcTemplate db;

    public OfferRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<Offer> getOfferById(int id) {
        List<Offer> offers = db.query(GET_OFFER_BY_ID, ps -> ps.setInt(1, id), new OfferRowMapper());
        return offers.stream().findFirst();
    }

    public List<Offer> getAllOffersByUserId(int userId) {
        return db.query(GET_ALL_OFFERS_BY_USER_ID, ps -> ps.setInt(1, userId), new OfferRowMapper());
    }

    public boolean createOffer(CreateOfferDTO createOfferDTO, double price, double additionalPrices, double total) {
        int rows = db.update(CREATE_OFFER,
                createOfferDTO.getCarId(),
                createOfferDTO.getUserId(),
                price,
                additionalPrices,
                total,
                createOfferDTO.getStartDate(),
                createOfferDTO.getEndDate(),
                false //initially the offer is NOT accepted
        );
        return rows > 0;
    }

    public boolean acceptOffer(int id) {
        int rows = db.update(ACCEPT_OFFER, id);
        return rows > 0;
    }

    public boolean deleteOffer(int id) {
        int rows = db.update(DELETE_OFFER, 0, id);
        return rows > 0;
    }
}
