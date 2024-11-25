package com.fmi.master.p1_rent_a_car.utils.db.operations;

import com.fmi.master.p1_rent_a_car.utils.db.QueryProcessor;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class SelectQueryBuilder<T> extends WhereQueryBuilder<SelectQueryBuilder<T>> {
    private final QueryProcessor<T> queryProcessor;

    public SelectQueryBuilder(QueryProcessor<T> queryProcessor, String... cols) {
        super(queryProcessor);
        this.queryProcessor = queryProcessor;
        this.queryProcessor.initNewQueryOperation();
        this.queryProcessor.getQueryBuilder()
                .append("SELECT ")
                .append(String.join(", ", cols));
    }

    public SelectQueryBuilder<T> from(String tableName) {
        this.queryProcessor.getQueryBuilder()
                .append(" FROM ")
                .append(tableName);

        return this;
    }

    public List<T> fetchAll(RowMapper<T> rowMapper) {
        return this.queryProcessor.processSelectList(rowMapper);
    }

    public T fetch(RowMapper<T> rowMapper) {
            return this.fetchAll(rowMapper).stream().findFirst().orElse(null);
    }
}
