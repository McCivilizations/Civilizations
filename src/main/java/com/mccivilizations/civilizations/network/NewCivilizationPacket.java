package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class NewCivilizationPacket {
    private final String name;
    private final String isoCode;
    private final BlockPos bannerPos;

    public NewCivilizationPacket(String name, String isoCode, BlockPos bannerPos) {
        this.name = name;
        this.isoCode = isoCode;
        this.bannerPos = bannerPos;
    }

    public void encode(PacketBuffer packetBuffer) {
        packetBuffer.writeString(name);
        packetBuffer.writeString(isoCode);
        packetBuffer.writeBlockPos(bannerPos);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> CivilizationsAPI.getInstance().getCivilizationRepository()
                .createCivilization(new Civilization(
                        -1,
                        name,
                        isoCode,
                        getFlagInfo(Objects.requireNonNull(contextSupplier.get().getSender()))
                )));
        contextSupplier.get().setPacketHandled(true);
    }

    private CompoundNBT getFlagInfo(ServerPlayerEntity sender) {
        TileEntity tileEntity = sender.getEntityWorld().getTileEntity(bannerPos);
        CompoundNBT compoundNBT = new CompoundNBT();
        if (tileEntity instanceof BannerTileEntity) {
            BannerTileEntity bannerTileEntity = ((BannerTileEntity) tileEntity);
            compoundNBT.putInt("baseColor", bannerTileEntity.getBaseColor(tileEntity::getBlockState).getId());
            compoundNBT.put("pattern", bannerTileEntity.write(new CompoundNBT()).getList("Patterns", 10));
        }
        return compoundNBT;
    }

    public static NewCivilizationPacket decode(PacketBuffer packetBuffer) {
        return new NewCivilizationPacket(
                packetBuffer.readString(32767),
                packetBuffer.readString(32767),
                packetBuffer.readBlockPos()
        );
    }


}
