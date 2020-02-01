package com.mccivilizations.civilizations.api;

import com.mccivilizations.civilizations.api.citizen.ICitizen;
import com.mccivilizations.civilizations.api.civilization.ICivilizationRepository;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.Objects;

public class CivilizationsAPI {
    @CapabilityInject(ICitizen.class)
    public static Capability<ICitizen> CITIZEN_CAP;

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
