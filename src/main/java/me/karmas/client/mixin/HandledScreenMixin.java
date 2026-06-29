package me.karmas.client.mixin;

import me.karmas.client.KarmasClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Renders "Karmas Client" in the brand colour above every vanilla inventory screen.
 *
 * <p>We hook into {@code render()} after the screen background is drawn so our
 * text sits on top of it, and we position it above the GUI widget so nothing
 * overlaps vanilla content.</p>
 */
@Environment(EnvType.CLIENT)
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {

    @Shadow protected int x;
    @Shadow protected int y;
    @Shadow protected int backgroundWidth;

    /**
     * Injected after the background texture is drawn (RETURN target of
     * {@code drawBackground}) so our label is always on top.
     */
    @Inject(
        method = "render",
        at = @At("TAIL")
    )
    private void karmas$renderScreenLabel(DrawContext context, int mouseX, int mouseY,
                                          float delta, CallbackInfo ci) {
        net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
        if (mc.textRenderer == null) return;

        Text label = Text.literal("Karmas Client");
        int labelWidth = mc.textRenderer.getWidth(label);

        // Centred above the GUI panel; 14 px gap looks clean on all screen sizes.
        int drawX = x + (backgroundWidth / 2) - (labelWidth / 2);
        int drawY = y - 14;

        context.drawText(mc.textRenderer, label, drawX, drawY, KarmasClient.BRAND_COLOR, false);
    }
}
