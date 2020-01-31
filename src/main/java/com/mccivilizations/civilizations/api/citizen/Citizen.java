package com.mccivilizations.civilizations.api.citizen;

import com.mccivilizations.civilizations.api.civilization.Civilization;

public class Citizen implements ICitizen {
    private Civilization civilization;

    @Override
    public Civilization getCivilization() {
        return civilization;
    }

    @Override
    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }
}
