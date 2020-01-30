package com.mccivilizations.civilizations.content;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.block.CivilizationMarkerBlock;
import com.mccivilizations.civilizations.block.MountedCivilizationMarkerBlock;
import com.mccivilizations.civilizations.block.StandingCivilizationMarkerBlock;
import com.mccivilizations.civilizations.tileentity.CivilizationMarkerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CivBlocks {
    private static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Civilizations.MODID);
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Civilizations.MODID);

    public static RegistryObject<StandingCivilizationMarkerBlock> STANDING_MARKER = BLOCKS.register("standing_civilization_marker",
            () -> new StandingCivilizationMarkerBlock(Block.Properties.create(Material.WOOL)));
    public static RegistryObject<MountedCivilizationMarkerBlock> MOUNTED_MARKER = BLOCKS.register("mounted_civilization_marker",
            () -> new MountedCivilizationMarkerBlock(Block.Properties.create(Material.WOOL)));

    @SuppressWarnings("ConstantConditions")
    public static RegistryObject<TileEntityType<CivilizationMarkerTileEntity>> MARKER_TYPE = TILE_ENTITY_TYPES.register("civilization_marker",
            () -> TileEntityType.Builder.create(CivilizationMarkerTileEntity::new,
                    STANDING_MARKER.get(), MOUNTED_MARKER.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        TILE_ENTITY_TYPES.register(eventBus);
    }
}
