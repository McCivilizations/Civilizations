package com.mccivilizations.civilizations.proxy;

import com.mccivilizations.civilizations.api.civilization.ICivilizationHandler;
import com.mccivilizations.civilizations.api.localization.ILocalizationHandler;

public interface IProxy {
    ILocalizationHandler getLocalizationHandler();

    ICivilizationHandler getCivilizationHandler();
}
