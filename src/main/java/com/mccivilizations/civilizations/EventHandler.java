package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.block.StandingCivilizationMarkerBlock;
import com.mccivilizations.civilizations.content.CivBlocks;
import com.mccivilizations.civilizations.tileentity.CivilizationMarkerTileEntity;
import com.mccivilizations.database.Database;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.Objects;

@EventBusSubscriber(modid = Civilizations.MODID, bus = Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> Database.getInstance().insert(
                "insert into player(name, uuid) values(?, ?)", event.getPlayer().getName().getFormattedText(),
                event.getPlayer().getUniqueID().toString()
        ));
    }

    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock rightClickBlockEvent) {
        BlockState blockState = rightClickBlockEvent.getWorld().getBlockState(rightClickBlockEvent.getPos());
        if (blockState.getBlock() instanceof AbstractBannerBlock) {
            if (rightClickBlockEvent.getItemStack().getItem() == Items.STICK) {
                rightClickBlockEvent.setUseBlock(Event.Result.DENY);
                rightClickBlockEvent.setUseItem(Event.Result.ALLOW);
                TileEntity tileEntity = rightClickBlockEvent.getWorld().getTileEntity(rightClickBlockEvent.getPos());
                if (tileEntity instanceof BannerTileEntity) {
                    BannerTileEntity bannerTileEntity = (BannerTileEntity) tileEntity;
                    AbstractBannerBlock abstractBannerBlock = (AbstractBannerBlock) blockState.getBlock();
                    if (abstractBannerBlock instanceof BannerBlock) {
                        rightClickBlockEvent.getWorld().setBlockState(rightClickBlockEvent.getPos(),
                                Objects.requireNonNull(CivBlocks.STANDING_MARKER.get()).getDefaultState()
                                        .with(StandingCivilizationMarkerBlock.ROTATION, blockState.get(BannerBlock.ROTATION)));
                    }

                    TileEntity newTileEntity = rightClickBlockEvent.getWorld().getTileEntity(rightClickBlockEvent.getPos());
                    if (newTileEntity instanceof CivilizationMarkerTileEntity) {
                        CivilizationMarkerTileEntity civMarkerTileEntity = (CivilizationMarkerTileEntity) newTileEntity;
                        civMarkerTileEntity.setPatternFromBanner(abstractBannerBlock, bannerTileEntity.serializeNBT());
                    }
                }
            }
        }
    }
}
