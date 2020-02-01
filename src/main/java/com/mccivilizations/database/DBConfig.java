package com.mccivilizations.database;

import net.minecraftforge.common.ForgeConfigSpec;

public class DBConfig {
    public static DBConfig instance;

    public final ForgeConfigSpec.ConfigValue<String> databaseType;
    public final ForgeConfigSpec.ConfigValue<String> connectionInfo;

    public final ForgeConfigSpec spec;

    public DBConfig(ForgeConfigSpec.Builder builder) {
        databaseType = builder.define("Database Type", "SQLite");
        connectionInfo = builder.define("Connection Info", "database.db");
        this.spec = builder.build();
    }

    public static ForgeConfigSpec initialize() {
        DBConfig config = new DBConfig(new ForgeConfigSpec.Builder());
        instance = config;
        return config.spec;
    }
}
