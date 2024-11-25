package com.fmi.master.p1_rent_a_car.utils.db;

import org.springframework.jdbc.core.JdbcTemplate;

public class InsertQueryBuilder {
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    public InsertQueryBuilder(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    public InsertQueryBuilder into(String tableName) {
        initNewQueryOperation();
        queryBuilder.append("INSERT INTO ").append(tableName);

        return this;
    }

    public InsertQueryBuilder withValue(String columnName, Object value) {
        setQueryColumnValuePair(columnName, value);

        return this;
    }

    public boolean insert() {
        String columnDefinition = String.join(",", columnCollection);
        String valueDefinition = String.join(",", placeHolderCollection);

        queryBuilder.append("(").append(columnDefinition).append(")")
                .append("VALUES").append("(").append(valueDefinition).append(")");

        String sqlQuery = queryBuilder.toString();

        int resultCount = this.jdbcTemplate.update(sqlQuery, valueCollection.toArray());
        return resultCount > 0;
    }
}
