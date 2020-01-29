package com.mccivilizations.civilizations.database.operation;

import com.mccivilizations.civilizations.database.Database;
import com.mccivilizations.civilizations.functional.ThrowingConsumer;
import com.mccivilizations.civilizations.functional.ThrowingFunction;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class QueryOperation<T> implements ISQLOperation {
    private final String query;
    private final ThrowingConsumer<PreparedStatement, SQLException> parameterSetter;
    private final ThrowingFunction<ResultSet, T, SQLException> resultParser;
    private final Consumer<List<T>> handleResults;

    public QueryOperation(String query, ThrowingConsumer<PreparedStatement, SQLException> parameterSetter,
                          ThrowingFunction<ResultSet, T, SQLException> resultParser,
                          Consumer<List<T>> handleResults) {
        this.query = query;
        this.parameterSetter = parameterSetter;
        this.resultParser = resultParser;
        this.handleResults = handleResults;
    }

    @Override
    public void handle(Database database) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() ->
                handleResults.accept(database.query(query, parameterSetter, resultParser)));
    }
}
