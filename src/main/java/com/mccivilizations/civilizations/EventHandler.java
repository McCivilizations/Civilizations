package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.data.CivilizationData;
import com.mccivilizations.civilizations.container.CreateCivilizationContainerProvider;
import com.mccivilizations.civilizations.container.ManageCivilizationContainerProvider;
import com.mccivilizations.civilizations.content.CivEnchants;
import com.mccivilizations.civilizations.content.VanillaContent;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Optional;
import java.util.function.Supplier;

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
                    CivilizationData data = CivilizationsAPI.getCivilizationData(world);
                    Civilization civilization = data.getCivilizationForEntity(playerEntity);
                    if (civilization == null) {
                        CompoundNBT compoundNBT = getFlagPattern(world, rightClickBlockEvent.getPos());
                        if (compoundNBT != null) {
                            NetworkHooks.openGui((ServerPlayerEntity) playerEntity,
                                    new CreateCivilizationContainerProvider(compoundNBT),
                                    packetBuffer -> packetBuffer.writeCompoundTag(compoundNBT));
                        }

                    } else {
                        BlockState newBlockState = null;
                        DyeColor flagColor = DyeColor.byId(civilization.getFlag()
                                .getInt("baseColor"));
                        if (blockState.getBlock() instanceof BannerBlock) {
                            newBlockState = BannerBlock.forColor(flagColor)
                                    .getDefaultState()
                                    .with(BannerBlock.ROTATION, blockState.get(BannerBlock.ROTATION));
                        } else if (blockState.getBlock() instanceof WallBannerBlock) {
                            newBlockState = Optional.ofNullable(VanillaContent.WALL_BANNERS.get(flagColor))
                                    .map(Supplier::get)
                                    .map(Block::getDefaultState)
                                    .map(bannerState -> bannerState.with(WallBannerBlock.HORIZONTAL_FACING,
                                            blockState.get(WallBannerBlock.HORIZONTAL_FACING)))
                                    .orElse(null);
                        }

                        if (newBlockState != null) {
                            world.setBlockState(rightClickBlockEvent.getPos(), newBlockState, 3);
                            TileEntity tileEntity = world.getTileEntity(rightClickBlockEvent.getPos());
                            if (tileEntity instanceof BannerTileEntity) {
                                CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
                                compoundNBT.put("Patterns", civilization.getFlag().getList("patterns", 10));
                                tileEntity.read(compoundNBT);
                                world.notifyBlockUpdate(rightClickBlockEvent.getPos(), newBlockState, newBlockState, 3);
                            }
                        }

                        NetworkHooks.openGui((ServerPlayerEntity) playerEntity,
                                new ManageCivilizationContainerProvider());
                    }
                }
            }
        }
    }

    private static CompoundNBT getFlagPattern(World world, BlockPos pos) {
        CompoundNBT compoundNBT = new CompoundNBT();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof BannerTileEntity) {
            CompoundNBT tileEntityNbt = tileEntity.write(new CompoundNBT());
            compoundNBT.put("patterns", tileEntityNbt.getList("Patterns", 10));
        }
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof BannerBlock) {
            compoundNBT.putInt("baseColor", ((BannerBlock) block).getColor().getId());
        }
        if (compoundNBT.keySet().size() == 2) {
            return compoundNBT;
        } else {
            return null;
        }
    }
}
