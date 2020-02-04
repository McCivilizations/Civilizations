package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.citizen.CitizenCapabilityProvider;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.container.NewCivilizationContainerProvider;
import com.mccivilizations.civilizations.content.CivEnchants;
import com.mccivilizations.database.Database;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.NetworkHooks;

@EventBusSubscriber(modid = Civilizations.MODID, bus = Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Database.getInstance().insert(
                "insert into players(name, uuid) values(?, ?)",
                event.getPlayer().getName().getFormattedText(),
                event.getPlayer().getUniqueID().toString()
        );
    }

    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock rightClickBlockEvent) {
        World world = rightClickBlockEvent.getWorld();
        BlockState blockState = world.getBlockState(rightClickBlockEvent.getPos());
        if (blockState.getBlock() instanceof AbstractBannerBlock) {
            if (EnchantmentHelper.getEnchantmentLevel(CivEnchants.LEADERSHIP.get(), rightClickBlockEvent.getItemStack()) > 0) {
                if (!world.isRemote()) {
                    rightClickBlockEvent.getPlayer().getCapability(CivilizationsAPI.CITIZEN_CAP)
                            .ifPresent(citizen -> {
                                if (citizen.getCivilization() == null) {
                                    NetworkHooks.openGui((ServerPlayerEntity) rightClickBlockEvent.getPlayer(),
                                            new NewCivilizationContainerProvider(rightClickBlockEvent.getPos()),
                                            packetBuffer -> packetBuffer.writeBlockPos(rightClickBlockEvent.getPos()));
                                } else {
                                    Civilization civilization = citizen.getCivilization();
                                    BlockState newBlockState = blockState;
                                    if (blockState.getBlock() instanceof BannerBlock) {
                                        newBlockState = BannerBlock.forColor(DyeColor.byId(civilization.getFlagPattern()
                                                .getInt("baseColor")))
                                                .getDefaultState()
                                                .with(BannerBlock.ROTATION, blockState.get(BannerBlock.ROTATION));
                                    }
                                    world.setBlockState(rightClickBlockEvent.getPos(), newBlockState, 3);
                                    TileEntity tileEntity = world.getTileEntity(rightClickBlockEvent.getPos());
                                    if (tileEntity instanceof BannerTileEntity) {
                                        CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
                                        compoundNBT.put("Patterns", civilization.getFlagPattern().getList("pattern", 10));
                                        tileEntity.read(compoundNBT);
                                        world.notifyBlockUpdate(rightClickBlockEvent.getPos(), newBlockState, newBlockState, 3);
                                    }
                                }
                            });
                }
            }
        }
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> attachCapabilitiesEvent) {
        if (attachCapabilitiesEvent.getObject() instanceof PlayerEntity) {
            attachCapabilitiesEvent.addCapability(new ResourceLocation(Civilizations.MODID, "citizen"),
                    new CitizenCapabilityProvider());
        }
    }
}
