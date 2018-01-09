package com.mccivilizations.civilizations.api.localization;

public interface ILocalizationHandler {
    String format(String s, Object... args);

    boolean exists(String s);
}