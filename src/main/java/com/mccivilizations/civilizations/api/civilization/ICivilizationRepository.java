package com.mccivilizations.civilizations.api.civilization;

import com.mccivilizations.civilizations.api.repository.IRepository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ICivilizationRepository extends IRepository<Civilization> {
    CompletableFuture<Optional<Civilization>> getByName(String name);
}
