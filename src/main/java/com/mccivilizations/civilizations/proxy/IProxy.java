package com.mccivilizations.civilizations.proxy;

import net.minecraft.server.MinecraftServer;

import java.io.File;

public interface IProxy {
    File getSaveFolder(MinecraftServer minecraftServer);
}
