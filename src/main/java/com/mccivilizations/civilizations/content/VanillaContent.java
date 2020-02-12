package com.mccivilizations.civilizations.content;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.function.Supplier;

public class VanillaContent {
    public static EnumMap<DyeColor, Supplier<WallBannerBlock>> WALL_BANNERS = getWallBanners();

    private static EnumMap<DyeColor, Supplier<WallBannerBlock>> getWallBanners() {
        EnumMap<DyeColor, Supplier<WallBannerBlock>> enumMap = Maps.newEnumMap(DyeColor.class);
        for (DyeColor dyeColor: DyeColor.values()) {
            enumMap.put(dyeColor, () -> {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft",
                        dyeColor.getName().toLowerCase() + "_wall_banner"));
                if (block instanceof WallBannerBlock) {
                    return (WallBannerBlock) block;
                } else {
                    return null;
                }
            });
        }
        return enumMap;
    }
}
