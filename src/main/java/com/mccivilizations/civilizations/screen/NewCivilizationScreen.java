package com.mccivilizations.civilizations.screen;

import com.hrznstudio.titanium.client.screen.container.BasicContainerScreen;
import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.container.NewCivilizationContainer;
import com.mccivilizations.civilizations.network.NewCivilizationPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.Objects;

public class NewCivilizationScreen extends BasicContainerScreen<NewCivilizationContainer> {
    private TextFieldWidget nameField;

    public NewCivilizationScreen(NewCivilizationContainer screenContainer, PlayerInventory inv, ITextComponent title) {
        super(screenContainer, inv, title);
    }

    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.nameField = new TextFieldWidget(this.font, i + 62, j + 24, 103, 12, "");
        this.nameField.setCanLoseFocus(false);
        this.nameField.changeFocus(true);
        this.nameField.setTextColor(-1);
        this.nameField.setDisabledTextColour(-1);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setMaxStringLength(35);
        this.children.add(this.nameField);
        this.addButton(new Button(this.width / 2 + 2, 196, 98, 20, "", (p_214204_1_) -> {
            this.minecraft.displayGuiScreen(null);
            Civilizations.instance.networkHandler.sendPacket(new NewCivilizationPacket(
                    nameField.getText(),
                    nameField.getText(),
                    this.getContainer().getBannerPos()
            ));
        }));
        this.setFocusedDefault(this.nameField);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        RenderSystem.disableBlend();
        this.nameField.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            Objects.requireNonNull(this.getMinecraft().player).closeScreen();
        }

        return this.nameField.keyPressed(keyCode, scanCode, modifiers) || this.nameField.canWrite() ||
                super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void resize(@Nonnull Minecraft minecraft, int width, int height) {
        String s = this.nameField.getText();
        this.init(minecraft, width, height);
        this.nameField.setText(s);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
