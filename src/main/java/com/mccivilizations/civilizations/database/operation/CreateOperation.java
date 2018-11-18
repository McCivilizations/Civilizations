package com.mccivilizations.civilizations.database.operation;

import com.mccivilizations.civilizations.database.Database;
import com.mccivilizations.civilizations.functional.ThrowingConsumer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateOperation implements ISQLOperation {
    private final String sql;
    private final ThrowingConsumer<PreparedStatement, SQLException> statementConsumer;

    public CreateOperation(String sql, ThrowingConsumer<PreparedStatement, SQLException> statementConsumer) {
        this.sql = sql;
        this.statementConsumer = statementConsumer;
    }

    @Override
    public void handle(Database database) {
        database.create(sql, statementConsumer);
    }
}
