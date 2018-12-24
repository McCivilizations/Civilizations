package com.mccivilizations.civilizations.database.operation;

import com.mccivilizations.civilizations.database.Database;
import com.mccivilizations.civilizations.functional.ThrowingConsumer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateOperation implements ISQLOperation {
    private final String sql;
    private final ThrowingConsumer<PreparedStatement, SQLException> statementConsumer;

    public UpdateOperation(String sql, ThrowingConsumer<PreparedStatement, SQLException> statementConsumer) {
        this.sql = sql;
        this.statementConsumer = statementConsumer;
    }

    @Override
    public void handle(Database database) {
        database.update(sql, statementConsumer);
    }
}
