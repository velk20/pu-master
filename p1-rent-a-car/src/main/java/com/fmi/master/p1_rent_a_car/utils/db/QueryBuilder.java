package com.fmi.master.p1_rent_a_car.utils.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryBuilder {
    private final JdbcTemplate jdbcTemplate;

    private StringBuilder queryBuilder;
    private List<String> columnCollection;
    private List<String> placeHolderCollection;
    private List<Object> valueCollection;

    public QueryBuilder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InsertQueryBuilder into(String tableName) {
        return new InsertQueryBuilder(this.jdbcTemplate, tableName);
    }



    public boolean update() {
        String sqlQuery = queryBuilder.toString();

        int resultCount = this.jdbcTemplate.update(sqlQuery, valueCollection.toArray());
        return resultCount > 0;
    }



    public QueryBuilder updateTable(String tableName) {
        initNewQueryOperation();
        this.queryBuilder.append("UPDATE ").append(tableName).append(" SET ");

        return this;
    }

    public QueryBuilder set(String columnName, Object value) {
        if (!this.columnCollection.isEmpty()) {
            this.queryBuilder.append(", ");
        }

        buildColumnValuePair(columnName);
        setQueryColumnValuePair(columnName, value);

        return this;
    }


    public QueryBuilder where(String columnName, String operator, Object value) {
        this.queryBuilder.append(" WHERE ");
        buildColumnValuePair(columnName, operator);



        this.setQueryColumnValuePair(columnName, value);
        return this;
    }

    public QueryBuilder andWhere(String columnName, String operator, Object value) {
        this.queryBuilder.append(" AND (");

        buildColumnValuePair(columnName, operator);
        this.queryBuilder.append(" ) ");

        this.setQueryColumnValuePair(columnName, value);
        return this;
    }

    public QueryBuilder andWhere(String columnName, Object value) {
        return this.andWhere(columnName, "=", value);
    }

    public QueryBuilder orWhere(String columnName, String operator, Object value) {
        this.queryBuilder.append(" OR (");

        buildColumnValuePair(columnName, operator);
        this.queryBuilder.append(" ) ");

        this.setQueryColumnValuePair(columnName, value);
        return this;
    }

    public QueryBuilder orWhere(String columnName, Object value) {
        return this.andWhere(columnName, "=", value);
    }

    public QueryBuilder where(String columnName, Object value) {
        return this.where(columnName, "=", value);
    }

    private void initNewQueryOperation() {
        this.columnCollection = new ArrayList<>();
        this.placeHolderCollection = new ArrayList<>();
        this.valueCollection = new ArrayList<>();
        this.queryBuilder = new StringBuilder();
    }

    private void setQueryColumnValuePair(String columnName, Object value) {
        columnCollection.add(columnName);
        placeHolderCollection.add("?");
        valueCollection.add(value);
    }

    private void buildColumnValuePair(String columnName, String operator) {
        this.queryBuilder
                .append(columnName)
                .append(operator)
                .append("?");
    }

    private void buildColumnValuePair(String columnName) {
        this.buildColumnValuePair(columnName, "=");
    }
}
