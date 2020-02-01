package com.mccivilizations.civilizations.repository.civilization;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.ICivilizationRepository;
import com.mccivilizations.database.Database;
import net.minecraft.world.IWorldReader;
import org.apache.commons.dbutils.ResultSetHandler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CivilizationRepository implements ICivilizationRepository {
    private final ResultSetHandler<Civilization> civilizationResultSetHandler = new CivilizationResultSetHandler();

    @Override
    public CompletableFuture<Optional<Civilization>> getCivilizationByName(String name) {
        return Database.getInstance().query("select id, name, iso_code, flag_pattern from civilizations where name = ?",
                civilizationResultSetHandler, name);
    }

    @Override
    public CompletableFuture<Optional<Civilization>> getCivilizationById(long id) {
        return Database.getInstance().query("select id, name, iso_code, flag_pattern, team_name from civilizations where id = ?",
                civilizationResultSetHandler, id);
    }

    @Override
    public CompletableFuture<Civilization> createCivilization(Civilization civilization) {
        Database.getInstance().insert("insert into civilizations(name, iso_code, flag_pattern, team_name) values(?, ?, ?, ?)",
                civilization.getName(), civilization.getIsoCode(), civilization.getFlagPattern().toString(),
                civilization.getTeamName());
        return this.getCivilizationByName(civilization.getName()).thenApplyAsync(Optional::get);
    }
}
