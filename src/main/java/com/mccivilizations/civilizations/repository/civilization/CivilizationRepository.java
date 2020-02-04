package com.mccivilizations.civilizations.repository.civilization;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.ICivilizationRepository;
import com.mccivilizations.civilizations.api.repository.IResultSetHandler;
import com.mccivilizations.civilizations.repository.ResultSetWrapper;
import com.mccivilizations.database.Database;
import org.apache.commons.dbutils.ResultSetHandler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CivilizationRepository implements ICivilizationRepository {
    private final IResultSetHandler<Civilization> civilizationResultSetHandler = new CivilizationResultSetHandler();
    private final ResultSetHandler<Civilization> resultSetHandler = new ResultSetWrapper<>(civilizationResultSetHandler);

    @Override
    public CompletableFuture<Optional<Civilization>> getByName(String name) {
        return Database.getInstance().query("select id, name, iso_code, flag_pattern, team_name from civilizations where name = ?",
                resultSetHandler, name);
    }

    @Override
    public CompletableFuture<Optional<Civilization>> getById(long id) {
        return Database.getInstance().query("select id, name, iso_code, flag_pattern, team_name from civilizations where id = ?",
                resultSetHandler, id);
    }

    @Override
    public CompletableFuture<Integer> delete(Civilization value) {
        return Database.getInstance().update("delete from civilizations where id = ?", value.getId());
    }

    @Override
    public IResultSetHandler<Civilization> getResultSetHandler() {
        return civilizationResultSetHandler;
    }

    @Override
    public CompletableFuture<Civilization> create(Civilization civilization) {
        Database.getInstance().insert("insert into civilizations(name, iso_code, flag_pattern, team_name) values(?, ?, ?, ?)",
                civilization.getName(), civilization.getIsoCode(), civilization.getFlagPattern().toString(),
                civilization.getTeamName());
        return this.getByName(civilization.getName()).thenComposeAsync(optional ->
                optional.map(CompletableFuture::completedFuture)
                        .orElseThrow(() -> new IllegalStateException("Failed to Find Civilization after Creation")));
    }

    @Override
    public CompletableFuture<Integer> update(Civilization value) {
        return Database.getInstance().update("update civilization ste name = ?, iso_code = ?, flag_patter = ?, " +
                        "team_name = ? where id = ?", value.getName(), value.getIsoCode(),
                value.getFlagPattern().toString(), value.getTeamName(), value.getId());
    }
}
