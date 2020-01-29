package com.mccivilizations.civilizations.api;

import java.util.Objects;

public class CivilizationsAPI {
    private static CivilizationsAPI instance;

    public static CivilizationsAPI getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CivilizationsAPI();
        }
        return instance;
    }
}
