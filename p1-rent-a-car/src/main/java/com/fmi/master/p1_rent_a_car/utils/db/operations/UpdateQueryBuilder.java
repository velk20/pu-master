package com.fmi.master.p1_rent_a_car.utils.db.operations;

import com.fmi.master.p1_rent_a_car.utils.db.QueryProcessor;

public class UpdateQueryBuilder extends  WhereQueryBuilder<UpdateQueryBuilder> {
    private final QueryProcessor queryProcessor;
    private final String tableName;

    public UpdateQueryBuilder(QueryProcessor queryProcessor, String tableName) {
        super(queryProcessor);
        this.queryProcessor = queryProcessor;
        this.tableName = tableName;

        this.queryProcessor.initNewQueryOperation();
        this.queryProcessor.getQueryBuilder().append("UPDATE ").append(tableName).append(" SET ");

    }

    public UpdateQueryBuilder set(String columnName, Object value) {
        if (!this.queryProcessor.getColumnCollection().isEmpty()) {
            this.queryProcessor.getQueryBuilder().append(", ");
        }

        this.queryProcessor.buildColumnValuePair(columnName);
        this.queryProcessor.setQueryColumnValuePair(columnName, value);

        return this;
    }

    public int update() {
        return this.queryProcessor.processQuery();
    }

}
