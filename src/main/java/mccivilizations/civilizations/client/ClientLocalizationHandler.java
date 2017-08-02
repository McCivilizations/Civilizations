package mccivilizations.civilizations.client;

import mccivilizations.civilizations.api.LocalizationHandler;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientLocalizationHandler implements LocalizationHandler {
    @Override
    public String format(String s, Object... args) {
        return I18n.format(s, args);
    }

    @Override
    public boolean exists(String s) {
        return I18n.hasKey(s);
    }
}