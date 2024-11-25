package com.fmi.master.p1_rent_a_car.utils.db.operations;

import com.fmi.master.p1_rent_a_car.utils.db.QueryProcessor;

public class InsertQueryBuilder {
    private QueryProcessor queryProcessor;
    private String tableName;

    public InsertQueryBuilder(QueryProcessor queryProcessor, String tableName) {
        this.queryProcessor = queryProcessor;
        this.tableName = tableName;
        this.queryProcessor.initNewQueryOperation();
        queryProcessor.getQueryBuilder().append("INSERT INTO ").append(tableName);

    }


    public InsertQueryBuilder withValue(String columnName, Object value) {
        this.queryProcessor.setQueryColumnValuePair(columnName, value);

        return this;
    }

    public boolean insert() {
        String columnDefinition = String.join(",", this.queryProcessor.getColumnCollection());
        String valueDefinition = String.join(",", this.queryProcessor.getPlaceHolderCollection());

        this.queryProcessor.getQueryBuilder()
                .append("(").append(columnDefinition).append(")")
                .append("VALUES")
                .append("(").append(valueDefinition).append(")");

        int resultCount = this.queryProcessor.processQuery();
        return resultCount > 0;
    }
}
