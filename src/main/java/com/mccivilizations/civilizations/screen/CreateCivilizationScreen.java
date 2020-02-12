package com.mccivilizations.civilizations.screen;

import com.hrznstudio.titanium.client.screen.addon.TextFieldScreenAddon;
import com.hrznstudio.titanium.client.screen.container.BasicContainerScreen;
import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.container.CreateCivilizationContainer;
import com.mccivilizations.civilizations.network.CreateCivilizationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CreateCivilizationScreen extends BasicContainerScreen<CreateCivilizationContainer> {
    private final TextFieldScreenAddon nameField;
    private final TextFieldScreenAddon isoCodeField;

    public CreateCivilizationScreen(CreateCivilizationContainer screenContainer, PlayerInventory inv, ITextComponent title) {
        super(screenContainer, inv, title);
        this.nameField = new TextFieldScreenAddon(10, 30);
        this.isoCodeField = new TextFieldScreenAddon(10, 50);
        this.getAddons().add(nameField);
        this.getAddons().add(isoCodeField);
    }

    @Override
    protected void init() {
        super.init();
        this.nameField.init(this.guiLeft, this.guiTop);
        this.isoCodeField.init(this.guiLeft, this.guiTop);
        Minecraft.getInstance().keyboardListener.enableRepeatEvents(true);
        this.children.add(this.nameField.getGuiListener());
        this.children.add(this.isoCodeField.getGuiListener());
        this.addButton(
                new Button(this.width / 2 + 16, this.height / 2 - 16, 48, 20,
                        new TranslationTextComponent("button.civilizations.create").getFormattedText(),
                        (button) -> {
                            Civilizations.instance.networkHandler.sendPacket(
                                    new CreateCivilizationPacket(
                                            new Civilization(
                                                    nameField.getText(),
                                                    isoCodeField.getText(),
                                                    this.getContainer().getFlagPattern()
                                            )
                                    )
                            );
                            Minecraft.getInstance().displayGuiScreen(null);
                        }
                )
        );
        this.setFocusedDefault(this.nameField.getGuiListener());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.getAddons().stream()
                .anyMatch(screenAddon -> screenAddon.keyPressed(keyCode, scanCode, modifiers)) ||
                super.keyPressed(keyCode, scanCode, modifiers);
    }
}
