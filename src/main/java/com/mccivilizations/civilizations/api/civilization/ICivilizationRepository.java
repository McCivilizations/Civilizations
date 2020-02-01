package com.mccivilizations.civilizations.api.civilization;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ICivilizationRepository {
    CompletableFuture<Optional<Civilization>> getCivilizationByName(String name);

    CompletableFuture<Optional<Civilization>> getCivilizationById(long id);

    CompletableFuture<Civilization> createCivilization(Civilization civilization);
}
