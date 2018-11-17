package com.mccivilizations.civilizations.database.specific;

import com.mccivilizations.civilizations.Civilizations;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.sql.DataSource;

@DBSpecific("SQLite")
public class SQLiteDB implements IDBSpecific {
    @Override
    public void initializeDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            Civilizations.INSTANCE.getLogger().error(e);
        }
    }

    @Override
    public DataSource createDataSource(String connectionInfo) {
        SQLiteConnectionPoolDataSource dataSource = new SQLiteConnectionPoolDataSource();
        connectionInfo = connectionInfo.replace("${minecraft}",
                Civilizations.INSTANCE.getMinecraftFolder().getAbsolutePath());
        dataSource.setUrl("jdbc:sqlite:" + connectionInfo);
        return dataSource;
    }
}
