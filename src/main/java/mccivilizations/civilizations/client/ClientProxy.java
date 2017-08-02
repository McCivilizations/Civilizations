package mccivilizations.civilizations.client;

import mccivilizations.civilizations.api.LocalizationHandler;
import mccivilizations.civilizations.common.IProxy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {
    private static LocalizationHandler CLIENT_LOCAL_HANDLER = new ClientLocalizationHandler();
    @Override
    public LocalizationHandler getLocalizationHandler() {
        return CLIENT_LOCAL_HANDLER;
    }
}
