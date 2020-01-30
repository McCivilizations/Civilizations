package com.mccivilizations.civilizations.repository.civilization;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.repository.ICivilizationRepository;
import com.mccivilizations.civilizations.functional.MappableFuture;
import com.mccivilizations.database.Database;
import org.apache.commons.dbutils.ResultSetHandler;

import java.util.Optional;
import java.util.concurrent.Future;

public class ServerCivilizationRepository implements ICivilizationRepository {
    private final ResultSetHandler<Civilization> civilizationResultSetHandler = new CivilizationResultSetHandler();

    @Override
    public Future<Optional<Civilization>> getCivilizationByName(String name) {
        return Database.getInstance().query("select id, name, iso_code, flag_pattern from civilizations where name = ?",
                civilizationResultSetHandler, name);
    }

    @Override
    public Future<Optional<Civilization>> getCivilizationById(long id) {
        return Database.getInstance().query("select id, name, iso_code, flag_pattern from civilizations where id = ?",
                civilizationResultSetHandler, id);
    }

    @Override
    public Future<Civilization> createCivilization(Civilization civilization) {
        Database.getInstance().insert("insert into civilizations(name, iso_code, flag_pattern) values(?, ?, ?)");
        return new MappableFuture<>(this.getCivilizationByName(civilization.getName()), Optional::get);
    }
}
