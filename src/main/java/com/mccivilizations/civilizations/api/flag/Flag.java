package com.mccivilizations.civilizations.api.flag;

import net.minecraft.util.ResourceLocation;

public class Flag {
    private ResourceLocation flagLocation;
    private int[][] pixelMap;

    public ResourceLocation getFlagLocation() {
        return flagLocation;
    }

    public void setFlagLocation(ResourceLocation flagLocation) {
        this.flagLocation = flagLocation;
    }

    public int[][] getPixelMap() {
        return pixelMap;
    }

    public void setPixelMap(int[][] pixelMap) {
        this.pixelMap = pixelMap;
    }
}
