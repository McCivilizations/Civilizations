package com.mccivilizations.civilizations.screen;

import com.hrznstudio.titanium.client.screen.container.BasicContainerScreen;
import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.container.ManageCivilizationContainer;
import com.mccivilizations.civilizations.network.LeaveCivilizationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Objects;

public class ManageCivilizationScreen extends BasicContainerScreen<ManageCivilizationContainer> {

    public ManageCivilizationScreen(ManageCivilizationContainer screenContainer, PlayerInventory inv, ITextComponent title) {
        super(screenContainer, inv, title);
    }

    @Override
    protected void init() {
        this.addButton(new Button(this.width / 2 + 2, this.height / 2 - 16, 48, 20,
                new TranslationTextComponent("button.civilizations.save").getFormattedText(),
                (button) -> {
                    Objects.requireNonNull(Minecraft.getInstance().player)
                            .getCapability(CivilizationsAPI.CITIZEN_CAP)
                            .ifPresent(citizen -> citizen.setCivilization(null));
                    Civilizations.instance.networkHandler.sendPacket(new LeaveCivilizationPacket());
                    Minecraft.getInstance().displayGuiScreen(null);
                }));
    }
}
