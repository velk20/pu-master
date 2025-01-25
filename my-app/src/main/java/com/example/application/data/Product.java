package com.example.application.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "td_products")
public class Product extends AbstractEntity {

    @Column(name = "company_name")
    @NotEmpty(message = "Company name is required.")
    private String companyName;

    @Column(name = "company_short_name")
    private String companyShortName;

    @Column(name = "buy_price")
    private double buyPrice;

    @Column(name = "sell_price")
    private double sellPrice;

    @Column(name = "is_active")
    private int isActive = 1;
}
