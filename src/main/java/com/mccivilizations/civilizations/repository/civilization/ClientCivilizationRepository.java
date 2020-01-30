package com.mccivilizations.civilizations.repository.civilization;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.repository.ICivilizationRepository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ClientCivilizationRepository implements ICivilizationRepository {
    @Override
    public Future<Optional<Civilization>> getCivilizationByName(String name) {
        Civilizations.LOGGER.error("Querying on Client! This is an Error");
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public Future<Optional<Civilization>> getCivilizationById(long id) {
        Civilizations.LOGGER.error("Querying on Client! This is an Error");
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public Future<Civilization> createCivilization(Civilization civilization) {
        Civilizations.LOGGER.error("Creating on Client! This is an Error");
        return CompletableFuture.completedFuture(civilization);
    }
}
