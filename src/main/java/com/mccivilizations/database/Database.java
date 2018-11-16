package com.mccivilizations.database;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class Database {
    private static Database instance;

    private Connection connection;
    private Logger logger;

    private Database() {
        logger = LogManager.getLogger("database");
        try {
            Class.forName(DatabaseConfig.driverName);
            connection = DriverManager.getConnection(DatabaseConfig.jdbcName);
        } catch (Exception e) {
            logger.error("Couldn't Create DB", e);
        }
    }

    public static Database getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Database();
        }
        return instance;
    }

    public <T> Optional<T> query(String query, ResultSetHandler<T> handler, Object... parameters) {
        try {
            return Optional.ofNullable(new QueryRunner().query(connection, query, handler, parameters));
        } catch (SQLException e) {
            logger.error(e);
            return Optional.empty();
        }
    }

    public <T> Optional<T> insert(String insert, ResultSetHandler<T> handler, Object... parameters) {
        try {
            return Optional.ofNullable(new QueryRunner().insert(connection, insert, handler, parameters));
        } catch (SQLException e) {
            logger.error(e);
            return Optional.empty();
        }
    }
}
