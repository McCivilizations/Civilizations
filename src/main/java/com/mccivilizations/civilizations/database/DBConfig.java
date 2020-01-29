package com.mccivilizations.civilizations.database;

import com.mccivilizations.civilizations.Civilizations;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid = Civilizations.MODID, name = Civilizations.MODID, category = "database")
public class DBConfig {
    @RequiresMcRestart
    @Comment("Database Type (SQLite)")
    public static String databaseType = "SQLite";

    @RequiresMcRestart
    @Comment("Connection Info for the JDBC Driver. Note that this is only the connection info")
    public static String connectionInfo = "${minecraft}/civilizations.db";
}
