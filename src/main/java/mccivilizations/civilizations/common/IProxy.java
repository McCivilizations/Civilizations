package mccivilizations.civilizations.common;

import mccivilizations.civilizations.api.LocalizationHandler;

public interface IProxy {
    default void preInit() {}
    default void init() {}
    default void postInit() {}

    LocalizationHandler getLocalizationHandler();
}
