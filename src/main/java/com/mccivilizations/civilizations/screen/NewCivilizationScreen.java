package com.mccivilizations.civilizations.screen;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.container.NewCivilizationContainer;
import com.mccivilizations.civilizations.network.NewCivilizationPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class NewCivilizationScreen extends ContainerScreen<NewCivilizationContainer> {
    private TextFieldWidget nameField;
    private Button createButton;

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
                    container.getBannerPos()
            ));
        }));
        this.setFocusedDefault(this.nameField);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        RenderSystem.disableBlend();
        this.nameField.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 256) {
            this.minecraft.player.closeScreen();
        }

        return this.nameField.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) || this.nameField.canWrite() ||
                super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
        String s = this.nameField.getText();
        this.init(p_resize_1_, p_resize_2_, p_resize_3_);
        this.nameField.setText(s);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
