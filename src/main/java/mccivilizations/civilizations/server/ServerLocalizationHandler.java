package mccivilizations.civilizations.server;

import mccivilizations.civilizations.api.LocalizationHandler;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerLocalizationHandler implements LocalizationHandler {
    @Override
    public String format(String s, Object... args) {
        return I18n.translateToLocalFormatted(s, args);
    }

    @Override
    public boolean exists(String s) {
        return I18n.canTranslate(s);
    }
}