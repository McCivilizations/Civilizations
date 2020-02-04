package com.mccivilizations.civilizations.repository.civilization;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.repository.IResultSetHandler;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.JsonToNBT;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CivilizationResultSetHandler implements IResultSetHandler<Civilization> {
    @Override
    public Civilization handle(ResultSet rs) throws SQLException {
        try {
            return new Civilization(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("iso_code"),
                    rs.getString("team_name"),
                    JsonToNBT.getTagFromJson(rs.getString("flag_pattern"))
            );
        } catch (CommandSyntaxException e) {
            throw new SQLException("Failed to find valid flag_pattern");
        }
    }
}
