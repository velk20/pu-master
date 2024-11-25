package com.fmi.master.p1_rent_a_car.utils.db;

import com.fmi.master.p1_rent_a_car.utils.db.operations.DeleteQueryBuilder;
import com.fmi.master.p1_rent_a_car.utils.db.operations.InsertQueryBuilder;
import com.fmi.master.p1_rent_a_car.utils.db.operations.SelectQueryBuilder;
import com.fmi.master.p1_rent_a_car.utils.db.operations.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class QueryBuilder<T> {
    private final QueryProcessor<T> queryProcessor;


    public QueryBuilder(QueryProcessor<T> queryProcessor) {
        this.queryProcessor = queryProcessor;
    }

    public SelectQueryBuilder<T> select(String... cols) {
        return new SelectQueryBuilder<T>(queryProcessor, cols);
    }

    public SelectQueryBuilder<T> selectAll() {
        return new SelectQueryBuilder<T>(queryProcessor, "*");
    }

    public InsertQueryBuilder into(String tableName) {
        return new InsertQueryBuilder(this.queryProcessor, tableName);
    }

    public UpdateQueryBuilder update(String tableName) {
        return new UpdateQueryBuilder(this.queryProcessor, tableName);
    }

    public DeleteQueryBuilder delete(String tableName) {
        return new DeleteQueryBuilder(this.queryProcessor, tableName);
    }

}
