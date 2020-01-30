package com.landslide.redsteelmod.blocks.screen;

import com.landslide.redsteelmod.RedSteelMain;
import com.landslide.redsteelmod.blocks.screen.container.CombinationSmelterContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CombinationSmelterScreen extends ContainerScreen<CombinationSmelterContainer> {

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        font.drawString(title.getFormattedText(), 8.0F, 6.0F, 4210752);
        font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }

    private ResourceLocation guiLocation = new ResourceLocation(RedSteelMain.MODID, "textures/gui/combination_smelter_gui.png");

    public CombinationSmelterScreen(CombinationSmelterContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        getMinecraft().getTextureManager().bindTexture(guiLocation);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(relX, relY, 0, 0, this.xSize, this.ySize);
    }
}
