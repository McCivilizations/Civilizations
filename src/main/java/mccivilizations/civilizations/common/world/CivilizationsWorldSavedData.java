package mccivilizations.civilizations.common.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class CivilizationsWorldSavedData extends WorldSavedData {

    private static final String DATA_NAME = "CivilizationsData";

    public CivilizationsWorldSavedData() {
        super(DATA_NAME);
    }

    public CivilizationsWorldSavedData(String name) {
        super(name);
    }

    public static CivilizationsWorldSavedData getData(World world) {
        MapStorage storage = world.getMapStorage();
        CivilizationsWorldSavedData instance = (CivilizationsWorldSavedData) (storage != null ? storage.getOrLoadData(CivilizationsWorldSavedData.class, DATA_NAME) : null);
        if (instance == null) {
            instance = new CivilizationsWorldSavedData();
            if (storage != null) {
                storage.setData(DATA_NAME, instance);
            }
        }
        return instance;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return null;
    }
}
