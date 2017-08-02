package mccivilizations.civilizations.api;

import mccivilizations.civilizations.Civilizations;

public interface LocalizationHandler {
    static LocalizationHandler getLocalizationHandler() {
        return Civilizations.PROXY.getLocalizationHandler();
    }

    String format(String s, Object... args);

    boolean exists(String s);
}