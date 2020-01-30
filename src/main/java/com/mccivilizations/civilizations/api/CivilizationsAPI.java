package com.mccivilizations.civilizations.api;

import com.mccivilizations.civilizations.api.repository.ICivilizationRepository;

import java.util.Objects;

public class CivilizationsAPI {
    private static CivilizationsAPI instance;

    private ICivilizationRepository civilizationRepository;

    public static CivilizationsAPI getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CivilizationsAPI();
        }
        return instance;
    }

    public ICivilizationRepository getCivilizationRepository() {
        return civilizationRepository;
    }

    public void setCivilizationRepository(ICivilizationRepository civilizationRepository) {
        this.civilizationRepository = civilizationRepository;
    }
}
