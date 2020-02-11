package com.mccivilizations.civilizations.api;

import com.mccivilizations.civilizations.api.civilization.data.ICivilizationData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CivilizationsAPI {
    @CapabilityInject(ICivilizationData.class)
    public static Capability<ICivilizationData> CIV_DATA;
}
