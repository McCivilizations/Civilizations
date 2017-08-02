package mccivilizations.civilizations.server;

import mccivilizations.civilizations.api.LocalizationHandler;
import mccivilizations.civilizations.common.IProxy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerProxy implements IProxy {
    private static LocalizationHandler SERVER_LOCAL_HANDLER = new ServerLocalizationHandler();

    @Override
    public LocalizationHandler getLocalizationHandler() {
        return SERVER_LOCAL_HANDLER;
    }
}