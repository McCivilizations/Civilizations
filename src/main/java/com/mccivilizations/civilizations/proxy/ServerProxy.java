package com.mccivilizations.civilizations.proxy;

import com.mccivilizations.civilizations.api.civilization.ICivilizationHandler;
import com.mccivilizations.civilizations.api.localization.ILocalizationHandler;
import com.mccivilizations.civilizations.civilization.CivilizationClientHandler;
import com.mccivilizations.civilizations.civilization.CivilizationServerHandler;
import com.mccivilizations.civilizations.localization.ServerLocalizationHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerProxy implements IProxy {
    private final ILocalizationHandler localizationHandler;
    private final ICivilizationHandler civilizationHandler;

    public ServerProxy() {
        localizationHandler = new ServerLocalizationHandler();
        civilizationHandler = new CivilizationServerHandler();
    }

    @Override
    public ILocalizationHandler getLocalizationHandler() {
        return localizationHandler;
    }

    @Override
    public ICivilizationHandler getCivilizationHandler() {
        return civilizationHandler;
    }
}