package com.mccivilizations.civilizations.proxy;

import net.minecraft.server.MinecraftServer;

import java.io.File;

public class ServerProxy implements IProxy {
    @Override
    public File getSaveFolder(MinecraftServer minecraftServer) {
        return minecraftServer.getFile(minecraftServer.getFolderName());
    }
}
