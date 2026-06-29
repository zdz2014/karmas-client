package me.karmas.client.mixin;

import me.karmas.client.render.LogoRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects the "Ka" logo into the tab-list header area.
 *
 * <p>The header text itself is rendered by vanilla; we draw our logo
 * <em>above</em> it so both can coexist. If the server sets no header,
 * the logo still appears at a sensible default position.</p>
 */
@Environment(EnvType.CLIENT)
@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {

    /**
     * Called at the very start of {@code render()} so we paint the logo before
     * vanilla draws the rest of the tab list.
     *
     * <p>We target the HEAD of the method so we have the draw context ready
     * but the background has not been painted yet — this lets us pick the
     * exact Y position regardless of whether a server header is present.</p>
     */
    @Inject(
        method = "render",
        at = @At("HEAD")
    )
    private void karmas$renderTabLogo(DrawContext context, int scaledWidth,
                                      Text header, Text footer,
                                      CallbackInfo ci) {
        // Draw the logo centred at the top of the screen, 4 px from the edge.
        // Scale 1.5× to give it presence without overwhelming the list.
        LogoRenderer.drawCentered(
            context,
            net.minecraft.client.MinecraftClient.getInstance().textRenderer,
            scaledWidth / 2,
            4,
            1.5f
        );
    }
}
