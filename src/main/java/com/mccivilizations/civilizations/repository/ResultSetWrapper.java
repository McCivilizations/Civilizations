package com.mccivilizations.civilizations.repository;

import com.mccivilizations.civilizations.api.repository.IResultSetHandler;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetWrapper<T> implements ResultSetHandler<T> {
    private final IResultSetHandler<T> resultSetHandler;

    public ResultSetWrapper(IResultSetHandler<T> resultSetHandler) {
        this.resultSetHandler = resultSetHandler;
    }

    @Override
    public T handle(ResultSet rs) throws SQLException {
        return resultSetHandler.handle(rs);
    }
}
