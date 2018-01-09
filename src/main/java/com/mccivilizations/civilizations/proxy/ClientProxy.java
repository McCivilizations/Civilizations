package com.mccivilizations.civilizations.proxy;

import com.mccivilizations.civilizations.api.civilization.ICivilizationHandler;
import com.mccivilizations.civilizations.civilization.CivilizationClientHandler;
import com.mccivilizations.civilizations.civilization.CivilizationServerHandler;
import com.mccivilizations.civilizations.localization.ClientLocalizationHandler;
import com.mccivilizations.civilizations.api.localization.ILocalizationHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {
    private final ILocalizationHandler localizationHandler;
    private final ICivilizationHandler civilizationHandler;

    public ClientProxy() {
        localizationHandler = new ClientLocalizationHandler();
        civilizationHandler = new CivilizationClientHandler();
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
