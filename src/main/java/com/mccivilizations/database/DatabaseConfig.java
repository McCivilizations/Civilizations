package com.mccivilizations.database;

import com.mccivilizations.civilizations.Civilizations;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;

@Config(modid = Civilizations.MODID, name = "database")
public class DatabaseConfig {
    @Comment("JDBC URL")
    public static String jdbcName = "jdbc:sqlite:mc.db";
    @Comment("Driver Name")
    public static String driverName = "org.sqlite.JDBC";
}
