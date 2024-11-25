package com.fmi.master.p1_rent_a_car.utils.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryProcessor<T> {
    private final JdbcTemplate jdbcTemplate;

    private StringBuilder queryBuilder;
    private List<String> columnCollection;
    private List<String> placeHolderCollection;
    private List<Object> valueCollection;

    public QueryProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StringBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public List<Object> getValueCollection() {
        return valueCollection;
    }

    public List<String> getPlaceHolderCollection() {
        return placeHolderCollection;
    }

    public List<String> getColumnCollection() {
        return columnCollection;
    }

    public int processQuery() {
        String sqlQuery = this.queryBuilder.toString();

        return this.jdbcTemplate.update(sqlQuery, this.getValueCollection().toArray());
    }

    public List<T> processSelectList(RowMapper<T> rowMapper) {
        String sqlQuery = this.queryBuilder.toString();

        return this.jdbcTemplate.query(sqlQuery, this.getValueCollection().toArray(), rowMapper);
    }

//    public T processSelect(RowMapper<T> rowMapper) {
//        return processSelectList(rowMapper).stream().findFirst().orElse(null);
//    }

    public void initNewQueryOperation() {
        this.columnCollection = new ArrayList<>();
        this.placeHolderCollection = new ArrayList<>();
        this.valueCollection = new ArrayList<>();
        this.queryBuilder = new StringBuilder();
    }

    public void setQueryColumnValuePair(String columnName, Object value) {
        columnCollection.add(columnName);
        placeHolderCollection.add("?");
        valueCollection.add(value);
    }

    public void buildColumnValuePair(String columnName, String operator) {
        this.queryBuilder.append(columnName).append(operator).append("?");
    }

    public void buildColumnValuePair(String columnName) {
        this.buildColumnValuePair(columnName, "=");
    }

}
