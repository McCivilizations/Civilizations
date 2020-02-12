package com.mccivilizations.civilizations.api;

import com.mccivilizations.civilizations.api.data.CivilizationData;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;

import java.util.Objects;

public class CivilizationsAPI {
    private static final CivilizationData clientData = new CivilizationData();

    public static CivilizationData getCivilizationData(World world) {
        if (world instanceof ServerWorld) {
            ServerWorld overWorld = Objects.requireNonNull(world.getServer())
                    .getWorld(DimensionType.OVERWORLD);

            DimensionSavedDataManager storage = overWorld.getSavedData();
            return storage.getOrCreate(CivilizationData::new, CivilizationData.NAME);
        } else {
            return clientData;
        }
    }
}
