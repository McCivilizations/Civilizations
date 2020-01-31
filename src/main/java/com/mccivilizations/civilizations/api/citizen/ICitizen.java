package com.mccivilizations.civilizations.api.citizen;

import com.mccivilizations.civilizations.api.civilization.Civilization;

public interface ICitizen {
    Civilization getCivilization();

    void setCivilization(Civilization civilization);
}
