package com.mccivilizations.civilizations.api.repository;

import com.mccivilizations.civilizations.api.civilization.Civilization;

import java.util.Optional;
import java.util.concurrent.Future;

public interface ICivilizationRepository {
    Future<Optional<Civilization>> getCivilizationByName(String name);

    Future<Optional<Civilization>> getCivilizationById(long id);

    Future<Civilization> createCivilization(Civilization civilization);
}
