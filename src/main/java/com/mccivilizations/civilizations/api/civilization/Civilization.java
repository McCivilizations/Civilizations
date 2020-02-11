package com.mccivilizations.civilizations.api.civilization;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class Civilization implements INBTSerializable<CompoundNBT> {
    private String name;
    private String isoCode;
    private String team;
    private CompoundNBT flag;
    private UUID uniqueId;

    public Civilization() {
        this.uniqueId = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public CompoundNBT getFlag() {
        return flag;
    }

    public void setFlag(CompoundNBT flag) {
        this.flag = flag;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void encode(PacketBuffer packetBuffer) {

    }

    public void decode(PacketBuffer packetBuffer) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("name", this.getName());
        nbt.putString("isoCode", this.getIsoCode());
        nbt.putString("team", this.getTeam());
        nbt.put("flag", this.getFlag());
        nbt.putUniqueId("uniqueId", this.getUniqueId());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.setName(nbt.getString("name"));
        this.setIsoCode(nbt.getString("isoCode"));
        this.setTeam(nbt.getString("team"));
        this.setFlag(nbt.getCompound("flag"));
        this.uniqueId = nbt.getUniqueId("uniqueId");
    }

    public static Civilization load(CompoundNBT nbt) {
        Civilization civilization = new Civilization();
        civilization.deserializeNBT(nbt);
        return civilization;
    }
}
