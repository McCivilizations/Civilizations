package com.mccivilizations.civilizations.proxy;

import net.minecraft.server.MinecraftServer;

import java.io.File;

public class ClientProxy implements IProxy {
    @Override
    public File getSaveFolder(MinecraftServer minecraftServer) {
        return new File(minecraftServer.getFile("saves"), minecraftServer.getFolderName());
    }
}
