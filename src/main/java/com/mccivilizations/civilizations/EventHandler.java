package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.citizen.CitizenCapabilityProvider;
import com.mccivilizations.civilizations.container.NewCivilizationContainerProvider;
import com.mccivilizations.civilizations.content.CivEnchants;
import com.mccivilizations.database.Database;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
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
        BlockState blockState = rightClickBlockEvent.getWorld().getBlockState(rightClickBlockEvent.getPos());
        if (blockState.getBlock() instanceof AbstractBannerBlock) {
            if (EnchantmentHelper.getEnchantmentLevel(CivEnchants.LEADERSHIP.get(), rightClickBlockEvent.getItemStack()) > 0) {
                if (!rightClickBlockEvent.getWorld().isRemote()) {
                    rightClickBlockEvent.getPlayer().getCapability(CivilizationsAPI.CITIZEN_CAP)
                            .ifPresent(citizen -> {
                                if (citizen.getCivilization() == null) {
                                    NetworkHooks.openGui((ServerPlayerEntity) rightClickBlockEvent.getPlayer(),
                                            new NewCivilizationContainerProvider(rightClickBlockEvent.getPos()),
                                            packetBuffer -> packetBuffer.writeBlockPos(rightClickBlockEvent.getPos()));
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
