package com.mccivilizations.civilizations.api.repository;

import com.mccivilizations.civilizations.api.civilization.Civilization;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface ICivilizationRepository {
    CompletableFuture<Optional<Civilization>> getCivilizationByName(String name);

    CompletableFuture<Optional<Civilization>> getCivilizationById(long id);

    CompletableFuture<Civilization> createCivilization(Civilization civilization);
}
