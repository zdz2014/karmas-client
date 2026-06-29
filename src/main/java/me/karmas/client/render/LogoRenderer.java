package me.karmas.client.render;

import me.karmas.client.KarmasClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Renders the "Ka" logo in the Karmas Client brand style.
 *
 * <p>The logo is intentionally minimal: two characters, no background,
 * no circle, rendered in {@link KarmasClient#BRAND_COLOR}.</p>
 */
@Environment(EnvType.CLIENT)
public final class LogoRenderer {

    /** The logo text — short, modern, recognisable. */
    public static final String LOGO_TEXT = "Ka";

    /** Kerning tweak so the two characters sit closer together, mimicking a logotype feel. */
    private static final int LETTER_SPACING = -1;

    private LogoRenderer() {}

    /**
     * Draws the "Ka" logo centred horizontally at the given Y position.
     *
     * @param context      the current draw context
     * @param textRenderer Minecraft's text renderer
     * @param centerX      the X coordinate to centre on
     * @param y            the top Y coordinate for the text
     * @param scale        uniform scale factor (1.0f = normal, 1.5f = 50% bigger, etc.)
     */
    public static void drawCentered(DrawContext context, TextRenderer textRenderer,
                                    int centerX, int y, float scale) {
        Text logo = Text.literal(LOGO_TEXT);
        int textWidth = textRenderer.getWidth(logo);

        // Apply scale around the intended centre point.
        context.getMatrices().push();
        context.getMatrices().translate(centerX, y, 0);
        context.getMatrices().scale(scale, scale, 1.0f);

        // After scaling the origin is (0,0); draw so text is centred there.
        int drawX = -(int) (textWidth / 2f);
        context.drawText(textRenderer, logo, drawX, 0, KarmasClient.BRAND_COLOR, false);

        context.getMatrices().pop();
    }

    /**
     * Returns the pixel width of the logo at scale 1.0.
     */
    public static int getWidth(TextRenderer textRenderer) {
        return textRenderer.getWidth(LOGO_TEXT);
    }
}
