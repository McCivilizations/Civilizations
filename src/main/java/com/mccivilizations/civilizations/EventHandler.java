package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.data.CivilizationDataCapabilityProvider;
import com.mccivilizations.civilizations.container.CreateCivilizationContainerProvider;
import com.mccivilizations.civilizations.container.ManageCivilizationContainerProvider;
import com.mccivilizations.civilizations.content.CivEnchants;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.NetworkHooks;

@EventBusSubscriber(modid = Civilizations.ID, bus = Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock rightClickBlockEvent) {
        World world = rightClickBlockEvent.getWorld();
        BlockState blockState = world.getBlockState(rightClickBlockEvent.getPos());
        PlayerEntity playerEntity = rightClickBlockEvent.getPlayer();
        if (blockState.getBlock() instanceof AbstractBannerBlock) {
            if (EnchantmentHelper.getEnchantmentLevel(CivEnchants.LEADERSHIP.get(), rightClickBlockEvent.getItemStack()) > 0) {
                if (!world.isRemote()) {
                    world.getCapability(CivilizationsAPI.CIV_DATA)
                            .ifPresent(data -> {
                                Civilization civilization = data.get(playerEntity);
                                if (civilization == null) {
                                    NetworkHooks.openGui((ServerPlayerEntity) playerEntity,
                                            new CreateCivilizationContainerProvider(rightClickBlockEvent.getPos()),
                                            packetBuffer -> packetBuffer.writeBlockPos(rightClickBlockEvent.getPos()));
                                } else {
                                    BlockState newBlockState = blockState;
                                    if (blockState.getBlock() instanceof BannerBlock) {
                                        newBlockState = BannerBlock.forColor(DyeColor.byId(civilization.getFlag()
                                                .getInt("baseColor")))
                                                .getDefaultState()
                                                .with(BannerBlock.ROTATION, blockState.get(BannerBlock.ROTATION));
                                    }
                                    world.setBlockState(rightClickBlockEvent.getPos(), newBlockState, 3);
                                    TileEntity tileEntity = world.getTileEntity(rightClickBlockEvent.getPos());
                                    if (tileEntity instanceof BannerTileEntity) {
                                        CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
                                        compoundNBT.put("Patterns", civilization.getFlag().getList("pattern", 10));
                                        tileEntity.read(compoundNBT);
                                        world.notifyBlockUpdate(rightClickBlockEvent.getPos(), newBlockState, newBlockState, 3);
                                    }

                                    NetworkHooks.openGui((ServerPlayerEntity) playerEntity,
                                            new ManageCivilizationContainerProvider());
                                }
                            });
                }
            }
        }
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<World> attachCapabilitiesEvent) {
        if (attachCapabilitiesEvent.getObject().getDimension().getType() == DimensionType.OVERWORLD) {
            attachCapabilitiesEvent.addCapability(new ResourceLocation(Civilizations.ID, "citizen"),
                    new CivilizationDataCapabilityProvider());
        }
    }
}
