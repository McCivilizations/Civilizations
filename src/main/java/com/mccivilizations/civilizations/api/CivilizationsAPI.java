package com.mccivilizations.civilizations.api;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.ICivilizationHandler;
import com.mccivilizations.civilizations.api.date.MinecraftDate;
import com.mccivilizations.civilizations.api.localization.ILocalizationHandler;

import java.util.Objects;

public class CivilizationsAPI {
    private static CivilizationsAPI instance;

    private final ICivilizationHandler civilizationHandler;
    private final ILocalizationHandler localizationHandler;

    public static final Civilization THE_FORGOTTEN = createTheForgotten();



    public static CivilizationsAPI getInstance() {
        return Objects.requireNonNull(instance, "Called Civilizations API before it was setup");
    }

    public static void createInstance(ICivilizationHandler civilizationHandler, ILocalizationHandler localizationHandler) {
        if (Objects.isNull(instance)) {
            instance = new CivilizationsAPI(civilizationHandler, localizationHandler);
        } else {
            throw new IllegalStateException("Civilizations API already exists. THIS SHOULD NOT HAPPEN!");
        }
    }

    private CivilizationsAPI(ICivilizationHandler civilizationHandler, ILocalizationHandler localizationHandler) {
        this.civilizationHandler = civilizationHandler;
        this.localizationHandler = localizationHandler;
    }

    public ICivilizationHandler getCivilizationHandler() {
        return civilizationHandler;
    }

    public ILocalizationHandler getLocalizationHandler() {
        return localizationHandler;
    }

    private static Civilization createTheForgotten() {
        Civilization forgotten = new Civilization();
        forgotten.setCreationDate(new MinecraftDate(0));
        forgotten.setDestructionDate(new MinecraftDate(0));
        forgotten.setMotto("Forgotten, but not Gone");
        forgotten.setLeader();
    }
}
